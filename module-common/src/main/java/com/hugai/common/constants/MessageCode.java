package com.hugai.common.constants;

/**
 * 响应消息常量
 *
 * @author wuhao
 * @date 2022/11/11 11:11
 */
public interface MessageCode {

    interface Common{
        String RESULT_QUERY_SUCCESS = "common.request.query.success";
        String REQUEST_INSERT_SUCCESS = "common.request.insert.success";
        String REQUEST_UPDATE_SUCCESS = "common.request.update.success";
        String REQUEST_DELETE_SUCCESS = "common.request.delete.success";
        String REQUEST_QUERY_FAIL = "common.request.query.fail";
        String REQUEST_INSERT_FAIL = "common.request.insert.fail";
        String REQUEST_UPDATE_FAIL = "common.request.update.fail";
        String REQUEST_DELETE_FAIL = "common.request.delete.fail";
    }

    /**
     * 10 系统消息
     */
    interface System {
        int SYSTEM_NOT_OPEN_REGISTER = 10001;
        int STATUS_DISABLE_OPEN_FAIL = 10002;
        int EXISTS_DOWN_TYPE_NOT_HANDLE = 10003;
        int LOGOUT_SUCCESS = 10004;
    }

    /**
     * 20 用户消息
     */
    interface User {
        int USER_UNIQUE = 20001;
        int USER_REGISTER_SUCCESS = 20002;
        int USER_REGISTER_FAIL = 20003;
        int USER_NOT_FOUND_PASSWORD_NOT_MATCH = 20004;
        int LOGIN_FAIL_PARAM_NO_MATCH = 20005;
        int LOGIN_SUCCESS = 20006;
        int USER_NAME_EXISTS_HANDLE_FAIL = 20007;
        int PHONE_EXISTS_HANDLE_FAIL = 20008;
        int EMAIL_EXISTS_HANDLE_FAIL = 20009;
        int NOT_HANDLE_ADMIN_USER = 20010;
    }

    /**
     * 角色 21
     */
    interface Role {
        int ROLE_NAME_EXISTS = 21001;
        int ROLE_PERMISSION_EXISTS = 21002;
        int SYSTEM_ROLE_NOT_HANDLE = 21003;
        int SYSTEM_ROLE_NOT_REMOVE = 21004;
        int ROLE_ALREADY_ALLOT_NOT_REMOVE = 21005;
    }

    /**
     * 部门 22
     */
    interface Dept {
        int DEPT_NAME_EXISTS_HANDLE_FAIL = 22001;
        int DEPT_CLOSE = 22002;
        int PARENT_NOT_SELF_HANDLE_FAIL = 22003;
        int DEPT_INCLUDE_NOT_CLOSE_CHILDREN = 22004;
        int EXISTS_CHILD_DEPT_NOT_HANDLE = 22005;
        int EXISTS_CHILD_USER_NOT_HANDLE = 22006;
    }

    /**
     * 岗位 23
     */
    interface Post {
        int POST_NAME_UNIQUE = 23001;
        int POST_CODE_UNIQUE = 23002;
    }

    /**
     * 权限 24
     */
    interface Permission {
        int NOT_ADD_ROUTE = 24001;
    }

}
