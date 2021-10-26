package com.gsh91.asdfasdfasdf;

import android.app.Application;

import com.kakao.sdk.common.KakaoSdk;

public class GlobalApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        KakaoSdk.init(this,"91bcfa4107a0f9ec5f8ca851941107de");
    }
}
