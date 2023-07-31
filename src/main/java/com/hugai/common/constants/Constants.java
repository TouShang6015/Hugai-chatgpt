package com.hugai.common.constants;

/**
 * 通用常量
 *
 * @author WuHao
 * @date 2022/5/12 17:13
 */
public interface Constants extends com.org.bebas.constants.Constants {

    interface DelFlag {
        /**
         * 未删除
         */
        String NORMAL = "0";
        /**
         * 已删除
         */
        String DEL = "1";
    }

    interface Disable {
        /**
         * 正常
         */
        String NORMAL = "0";
        /**
         * 停用
         */
        String DISABLE = "1";
    }

    interface Status {
        /**
         * 正常
         */
        String NORMAL = "0";
        /**
         * 不正常
         */
        String NO_NORMAL = "1";
    }

    interface BOOLEAN {
        /**
         * 否
         */
        String FALSE = "0";
        /**
         * 是
         */
        String TRUE = "1";
    }

    interface OPEN_STATUS {
        /**
         * 关闭
         */
        String OPEN = "0";
        /**
         * 开启
         */
        String CLOSE = "1";
    }

    interface VISIBLE {
        /**
         * 显示
         */
        String SHOW = "0";
        /**
         * 隐藏
         */
        String HIDE = "1";
    }

    /**
     * 可使用状态
     */
    interface EnableStatus {
        /**
         * 可用
         */
        String USABLE = "0";
        /**
         * 停用
         */
        String DISABLE = "1";
    }

    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */
    interface MENU_TYPE {
        /**
         * 目录
         */
        String M = "M";
        /**
         * 菜单
         */
        String C = "C";
    }

    interface MENU_INFO {
        /**
         * 是否菜单外链（否）
         */
        String NO_FRAME = "1";

        /**
         * Layout组件标识
         */
        String LAYOUT = "Layout";

        /**
         * InnerLink组件标识
         */
        String INNER_LINK = "InnerLink";

        /**
         * ParentView组件标识
         */
        String PARENT_VIEW = "ParentView";
    }

}
