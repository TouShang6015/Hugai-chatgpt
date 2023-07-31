package com.hugai.modules.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.hugai.common.constants.Constants;
import com.hugai.common.constants.SecurityConstant;
import com.hugai.common.enums.permission.VisitRuleEnum;
import com.hugai.core.security.context.SecurityContextUtil;
import com.hugai.core.security.service.SecurityService;
import com.hugai.modules.system.entity.convert.SysPermissionConvert;
import com.hugai.modules.system.entity.dto.SysPermissionDTO;
import com.hugai.modules.system.entity.model.SysPermissionModel;
import com.hugai.modules.system.entity.model.SysRolePermissionModel;
import com.hugai.modules.system.entity.vo.permission.SysPermissionTreeVo;
import com.hugai.modules.system.mapper.SysPermissionMapper;
import com.hugai.modules.system.service.ISysPermissionService;
import com.hugai.modules.system.service.ISysRolePermissionService;
import com.hugai.modules.system.service.ISysUserRoleService;
import com.org.bebas.constants.RedisConstant;
import com.org.bebas.core.function.OR;
import com.org.bebas.core.model.build.QueryFastLambda;
import com.org.bebas.core.redis.RedisUtil;
import com.org.bebas.enums.ConditionEnum;
import com.org.bebas.exception.CommonException;
import com.org.bebas.mapper.cache.ServiceImpl;
import com.org.bebas.mapper.utils.ModelUtil;
import com.org.bebas.utils.OptionalUtil;
import com.org.bebas.utils.tree.TreeUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 权限管理 业务实现类
 *
 * @author WuHao
 * @date 2022-09-25 12:01:06
 */
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermissionModel> implements ISysPermissionService {

    private final String KEY_LIST = ModelUtil.modelMainKey(SysPermissionModel.class) + RedisConstant.Keyword.ALL_LIST;

    private final String KEY_KEYWORD = ModelUtil.modelMainKey(SysPermissionModel.class) + RedisConstant.Keyword.ID;

    @Autowired
    private SecurityService securityService;
    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;
    @Resource
    private ISysRolePermissionService sysRolePermissionService;
    @Resource
    private ISysUserRoleService sysUserRoleService;
    @Autowired
    private HttpSecurity httpSecurity;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public List<SysPermissionModel> listByParam(SysPermissionModel param) {
        return this.getPermissionList(param);
    }

    /**
     * 非超级管理员查询列表
     *
     * @param param
     * @return
     */
    private List<SysPermissionModel> getPermissionList(SysPermissionModel param) {
        OR.run(param.getAncestors(), Objects::nonNull, arg -> {
            QueryFastLambda.build(param).queryCondition(SysPermissionModel::getAncestors, ConditionEnum.FIND_IN_SET.name());
            if (arg.equals(SysPermissionModel.DEFAULT_ANCESTORS)) {
                param.setIfRoute(Integer.valueOf(Constants.BOOLEAN.TRUE));
                param.setParentId(Constants.DEFAULT_PARENT_ID);
            }
        });
        if (SecurityContextUtil.isAdmin(SecurityContextUtil.getUserId())) {
            return super.listByParam(param);
        }
        Set<Long> roleIds = SecurityContextUtil.getLoginUser().getRoleIds();
        List<Long> permissionIds = OptionalUtil.ofNullList(
                sysRolePermissionService.lambdaQuery()
                        .in(SysRolePermissionModel::getRoleId, roleIds)
                        .select(SysRolePermissionModel::getPermissionId).list()
        ).stream().map(SysRolePermissionModel::getPermissionId).distinct().collect(Collectors.toList());
        List<Long> finalPermissionIds = OptionalUtil.ofNullListDefault(permissionIds, -1L);
        QueryFastLambda.build(param).queryConditionIn(SysPermissionModel::getId, finalPermissionIds);
        return super.listByParam(param);
    }

    /**
     * 获取项目路由地址
     *
     * @return
     */
    @Override
    public List<SysPermissionModel> getProjectRequestMapping() {
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
        return handlerMethods.keySet().stream().map(requestMappingInfo -> {
            HandlerMethod handlerMethod = handlerMethods.get(requestMappingInfo);
            SysPermissionModel param = new SysPermissionModel();
            String moduleName = "";
            String title = "";
            String requestMethod = "";
            String routePath = "";

            Api annotation = handlerMethod.getBeanType().getAnnotation(Api.class);
            if (Objects.nonNull(annotation)) {
                moduleName = annotation.tags()[0];
            }
            for (RequestMethod method : requestMappingInfo.getMethodsCondition().getMethods()) {
                requestMethod = method.toString();
                break;
            }
            for (String pattern : requestMappingInfo.getPatternsCondition().getPatterns()) {
                routePath = pattern;
                break;
            }
            title = handlerMethod.getMethod().getName();

            param.setModuleController(handlerMethod.getBean().toString());
            param.setModuleName(moduleName);
            param.setTitle(title);
            param.setRequestMethod(requestMethod);
            param.setRoutePath(routePath);
            param.setOriginalRoutePath(routePath);
            param.setRouteVisitRule(VisitRuleEnum.AUTH.getValue());
            param.setAncestors(SysPermissionModel.DEFAULT_ANCESTORS);
            param.setIfRoute(Integer.valueOf(Constants.BOOLEAN.FALSE));
            return param;
        }).collect(Collectors.toList());
    }

    /**
     * 接口路由同步
     *
     * @return
     */
    @Override
    public boolean handleMappingSync() {
        // 获取所有接口列表
        List<String> originalRoutePathList = OptionalUtil.ofNullList(
                super.lambdaQuery()
                        .select(SysPermissionModel::getOriginalRoutePath)
                        .eq(SysPermissionModel::getIfRoute, Constants.BOOLEAN.TRUE)
                        .list()
        ).stream().map(SysPermissionModel::getOriginalRoutePath).collect(Collectors.toList());

        List<SysPermissionModel> projectRequestMapping = this.getProjectRequestMapping();
        projectRequestMapping.parallelStream().forEach(item -> {
            item.setIfRoute(Integer.valueOf(Constants.BOOLEAN.TRUE));
            // routePath
            Matcher matcher = Pattern.compile("\\{(.+?)\\}").matcher(item.getOriginalRoutePath());
            String routePath = item.getOriginalRoutePath();
            while (matcher.find()) {
                routePath = matcher.replaceFirst(StringPool.ASTERISK);
            }
            item.setRoutePath(routePath);
            // 路由标识
            String replaceStr = item.getRoutePath().replaceAll(StringPool.SLASH, StringPool.COLON);
            if (replaceStr.charAt(0) == ':') {
                replaceStr = replaceStr.substring(1);
            }
            item.setPermissionTag(replaceStr);
        });
        // 筛选需要新增的数据
        List<SysPermissionModel> INSERT_PARAM = projectRequestMapping.parallelStream().filter(item -> !originalRoutePathList.contains(item.getOriginalRoutePath())).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(INSERT_PARAM)) {
            super.saveBatch(INSERT_PARAM);
            log.info("[路由同步] 同步成功：{}", JSON.toJSONString(INSERT_PARAM));
        }
        return true;
    }

    /**
     * 分配路由模块
     *
     * @param parentId
     * @param permissionModelList
     * @return
     */
    @Override
    public boolean handleAllocationRouteModule(Long parentId, List<SysPermissionModel> permissionModelList) {
        List<SysPermissionModel> UPDATE_PARAM = OptionalUtil.ofNullList(permissionModelList).stream()
                .peek(item -> item.setParentId(parentId))
                .collect(Collectors.toList());
        if (UPDATE_PARAM.size() < 1) {
            return false;
        }
        return this.updateBatchById(UPDATE_PARAM);
    }

    /**
     * 构建树结构列表
     *
     * @param dtoList
     * @return
     */
    @Override
    public List<SysPermissionTreeVo> buildTreePermissionList(List<SysPermissionDTO> dtoList) {
        return TreeUtil.<SysPermissionDTO, SysPermissionTreeVo>build(dtoList)
                .convert(SysPermissionConvert.INSTANCE::dtoConvertTree)
                .builder();
    }

    /**
     * 获取角色的路由列表
     *
     * @param roleId
     * @return
     */
    @Override
    public List<Long> rolePermissionByRoleId(Long roleId) {
        List<SysRolePermissionModel> resultList = sysRolePermissionService.lambdaQuery()
                .select(SysRolePermissionModel::getPermissionId)
                .eq(SysRolePermissionModel::getRoleId, roleId)
                .list();
        return OptionalUtil.ofNullList(resultList).parallelStream().map(SysRolePermissionModel::getPermissionId).distinct().collect(Collectors.toList());
    }

    /**
     * 获取角色路由列表通过角色唯一标识
     *
     * @param roleKey
     * @return
     */
    @Override
    public Set<String> getPermissionByRoleKey(String roleKey) {
        return baseMapper.selectPermissionByRoleKey(roleKey);
    }

    /**
     * 刷新security动态权限
     */
    @Override
    public void flushPermissionConfig() {
        try {
            // 删除路由缓存
            String ROUTE_KEY = ModelUtil.modelMainKey(SysPermissionModel.class) + RedisConstant.Keyword.ALL_LIST;
            redisUtil.deleteObject(ROUTE_KEY);
            securityService.flushHttpSecurityConfig(httpSecurity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CommonException("刷新security配置失败!");
        }
    }

    /**
     * 获取所有控制器信息列表
     *
     * @return
     */
    @Override
    public List<SysPermissionModel> getRouteList() {
        List<SysPermissionModel> allList = CollUtil.newArrayList();
        List<SysPermissionModel> cacheList = redisUtil.getCacheList(KEY_LIST);
        if (CollUtil.isEmpty(cacheList)) {
            allList = super.lambdaQuery()
                    .select(
                            SysPermissionModel::getId
                            , SysPermissionModel::getParentId
                            , SysPermissionModel::getIfRoute
                            , SysPermissionModel::getRoutePath
                            , SysPermissionModel::getPermissionTag
                            , SysPermissionModel::getRouteVisitRule
                    )
                    .eq(SysPermissionModel::getIfRoute, Constants.BOOLEAN.TRUE)
                    .list();
            OR.run(allList, CollUtil::isNotEmpty, list -> redisUtil.setCacheList(KEY_LIST, list));
        } else {
            allList = cacheList;
        }
        return allList;
    }

    /**
     * 刷新路由缓存
     */
    @Override
    public void flushRouteCache() {
        redisUtil.deleteObject(KEY_LIST);
        this.getRouteList();
    }

    /**
     * 获取用户的权限标识
     *
     * @param userId
     * @return
     */
    @Override
    public Set<String> getUserPermissionTag(Long userId) {
        if (SecurityContextUtil.isAdmin(userId)) {
            return CollUtil.newHashSet(SecurityConstant.PERMISSION_TAG);
        }
        return OptionalUtil.ofNullSetDefault(baseMapper.selectListByUserId(userId).stream().map(SysPermissionModel::getPermissionTag).collect(Collectors.toSet()), "-1");
    }

    @Override
    public boolean save(SysPermissionModel entity) {
        Long parentId = entity.getParentId();
        SysPermissionModel parentModel = super.getById(parentId);
        if (Objects.nonNull(parentModel)) {
            entity.setAncestors(parentModel.getAncestors() + StringPool.COMMA + entity.getParentId());
        } else {
            entity.setAncestors(SysPermissionModel.DEFAULT_ANCESTORS);
        }
        return super.save(entity);
    }

    @Override
    public boolean updateById(SysPermissionModel entity) {
        SysPermissionModel newParentModel = super.getById(entity.getParentId());
        SysPermissionModel oldModel = super.getById(entity.getId());
        List<SysPermissionModel> updateList = CollUtil.newArrayList();
        if (Objects.nonNull(newParentModel) && Objects.nonNull(oldModel)) {
            String newAncestors = newParentModel.getAncestors() + StringPool.COMMA + newParentModel.getId();
            String oldAncestors = oldModel.getAncestors();
            entity.setAncestors(newAncestors);
            updateList.addAll(updateChildren(entity.getId(), newAncestors, oldAncestors));
        }
        updateList.add(entity);
        return this.updateBatchById(updateList);
    }

    @Override
    public boolean updateBatchById(Collection<SysPermissionModel> entityList) {
        List<Long> ids = entityList.stream().map(SysPermissionModel::getId).collect(Collectors.toList());
        List<Long> parentIds = entityList.stream().map(SysPermissionModel::getParentId).collect(Collectors.toList());
        List<Long> queryParamIds = CollUtil.newArrayList();
        queryParamIds.addAll(OptionalUtil.ofNullListDefault(ids, -1));
        queryParamIds.addAll(OptionalUtil.ofNullListDefault(parentIds, -1));
        queryParamIds = queryParamIds.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
        List<SysPermissionModel> modelList = lambdaQuery().in(SysPermissionModel::getId, queryParamIds).list();
        List<SysPermissionModel> updateList = entityList.stream()
                .map(entity -> getUpdateListByModelForAncestors(entity, modelList))
                .flatMap(Collection::stream)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        return super.updateBatchById(updateList);
    }

    @Override
    public boolean removeById(Serializable id) {
        return this.removeByIds(CollUtil.newArrayList(id));
    }

    @Override
    public boolean removeByIds(Collection<?> list) {
        if (super.removeByIds(list)) {
            sysRolePermissionService.lambdaUpdate().in(SysRolePermissionModel::getPermissionId,list).remove();
        }
        return true;
    }

    /**
     * 获取model需要修改的list 为了Ancestors
     *
     * @param model
     * @param modelList
     * @return
     */
    public List<SysPermissionModel> getUpdateListByModelForAncestors(SysPermissionModel model, List<SysPermissionModel> modelList) {
        if (Objects.isNull(model))
            return null;
        SysPermissionModel newParentModel = modelList.stream().filter(item -> item.getId().equals(model.getParentId())).findFirst().orElse(null);
        SysPermissionModel oldModel = modelList.stream().filter(item -> item.getId().equals(model.getId())).findFirst().orElse(null);
        List<SysPermissionModel> updateList = CollUtil.newArrayList();
        if (Objects.nonNull(newParentModel) && Objects.nonNull(oldModel)) {
            String newAncestors = newParentModel.getAncestors() + StringPool.COMMA + newParentModel.getId();
            String oldAncestors = oldModel.getAncestors();
            model.setAncestors(newAncestors);
            updateList.addAll(updateChildren(model.getId(), newAncestors, oldAncestors));
        }
        updateList.add(model);
        return updateList;
    }

    /**
     * 修改子元素关系
     *
     * @param id           被修改的ID
     * @param newAncestors 新的父ID集合
     * @param oldAncestors 旧的父ID集合
     */
    public List<SysPermissionModel> updateChildren(Long id, String newAncestors, String oldAncestors) {
        List<SysPermissionModel> list = OptionalUtil.ofNullList(baseMapper.selectChildrenById(id));
        list.parallelStream().forEach(item -> {
            item.setAncestors(item.getAncestors().replaceFirst(oldAncestors, newAncestors));
        });
        return list;
    }

}
