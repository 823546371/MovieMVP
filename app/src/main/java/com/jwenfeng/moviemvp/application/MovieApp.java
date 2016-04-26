package com.jwenfeng.moviemvp.application;

import android.app.Application;

import com.zhy.http.okhttp.OkHttpUtils;

import cn.bmob.v3.Bmob;

/**
 * 当前类注释:
 * 项目名：MovieMVP
 * 包名：com.jwenfeng.moviemvp.application
 * 作者：jinwenfeng on 16/4/23 17:04
 * 邮箱：823546371@qq.com
 * QQ： 823546371
 * 公司：南京穆尊信息科技有限公司
 * © 2016 jinwenfeng
 * ©版权所有，未经允许不得传播
 */
public class MovieApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        OkHttpUtils.getInstance().debug("Movie");
        Bmob.initialize(this,"8a03f323df122fc0d10e3c558c55d624");
    }
}
