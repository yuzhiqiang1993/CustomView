package com.yzq.customview;

import android.app.Application;

import com.blankj.utilcode.util.LogUtils;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();


        final LogUtils.Config config = LogUtils.getConfig()
                .setLogSwitch(BuildConfig.DEBUG)// 设置 log 总开关，包括输出到控制台和文件，默认开
                .setConsoleSwitch(BuildConfig.DEBUG)// 设置是否输出到控制台开关，默认开
                .setSingleTagSwitch(true);// 一条日志仅输出一条，默认开，为美化 AS 3.1 的 Logcat


    }
}
