package c.g.a.x.lib_http.constant;

import c.g.a.x.lib_http.BuildConfig;

/**
 * Created by Administrator on 2018/2/27.
 */

public final class Constant {

    public static final class Data {

        public static final int PAGE_COUNT = 20;
        public static final int PAGE_COUNT_MAX = Integer.MAX_VALUE;

        public static final String JOIN_SEPARATOR = "/";

    }

    public static final class URL {
        //    项目请求地址
        public static final String BASE_URL = BuildConfig.base_url;
    }


}
