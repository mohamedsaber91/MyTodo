package com.example.mytodo;

import android.app.Application;

import com.example.mytodo.MyDataBase.MyDataBaseManger;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MyDataBaseManger.init(this);
    }
}
