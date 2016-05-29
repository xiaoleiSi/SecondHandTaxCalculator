package com.xiaolei.houseTax;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;

/**
 * Created by Administrator on 2016/5/18.
 */
public class MyLeanCloudApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this,"ViUh3owI6oCdwDbToFN3AOpt-gzGzoHsz","hnJI5PfisBgMeqDXnfCJe42J");
        AVOSCloud.useAVCloudCN();
    }
}
