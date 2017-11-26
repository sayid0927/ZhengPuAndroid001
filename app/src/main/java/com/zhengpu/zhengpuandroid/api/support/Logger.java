package com.zhengpu.zhengpuandroid.api.support;




public class Logger implements LoggingInterceptor.Logger {

    @Override
    public void log(String message) {
        com.orhanobut.logger.Logger.e("http : " + message);
    }
}
