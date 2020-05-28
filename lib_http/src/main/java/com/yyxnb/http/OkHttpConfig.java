package com.yyxnb.http;

public class OkHttpConfig {

    private volatile static OkHttpConfig okHttpConfig;

    public static OkHttpConfig getInstance() {
        if (okHttpConfig == null) {
            synchronized (OkHttpConfig.class) {
                if (okHttpConfig == null) {
                    okHttpConfig = new OkHttpConfig();
                }
            }
        }
        return okHttpConfig;
    }

    public static final int READ_TIME_OUT = 10;
    public static final int WRITE_TIME_OUT = 10;
    public static final int CONNECT_TIME_OUT = 10;

    private BaseRetrofitConfig retrofitConfig = new DefaultRetrofitConfig();

    public BaseRetrofitConfig getRetrofitConfig() {
        return retrofitConfig;
    }

    public void setRetrofitConfig(BaseRetrofitConfig retrofitConfig) {
        this.retrofitConfig = retrofitConfig;
    }

    public static final String HTTP_REQUEST_TYPE_KEY = "requestType";

    public static final String KEY = "key";

}