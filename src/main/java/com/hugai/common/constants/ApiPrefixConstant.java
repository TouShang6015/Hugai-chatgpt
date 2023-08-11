package com.hugai.common.constants;

/**
 * api请求前缀常量类
 *
 * @author WuHao
 * @date 2022/5/18 17:56
 */
public interface ApiPrefixConstant {

    String MODEL_API = "/module";

    interface Auth {

        String SYSTEM = "/auth/system";

    }

    interface Modules {

        String SYSTEM = MODEL_API + "/system";

        String CONFIG = MODEL_API + "/config";

        String SESSION = MODEL_API + "/session";

        String QUARTZ = MODEL_API + "/quartz";

        String USER = MODEL_API + "/user";

        String STATISTICS = MODEL_API + "/statistics";

    }

    interface Common {

        String COMMON = "/common";

    }

}
