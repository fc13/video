package com.fc.vedio.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * @author 范超 on 2017/9/21
 */

public class InitApkBroadCastReceiver extends BroadcastReceiver {
    private static final String TAG = "InitApkBroadCastReceive";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_PACKAGE_ADDED.equals(intent.getAction())) {
            Log.e(TAG,"监听到系统广播添加");
        }

        if (Intent.ACTION_PACKAGE_REMOVED.equals(intent.getAction())) {
            Log.e(TAG,"监听到系统广播移除");
        }

        if (Intent.ACTION_PACKAGE_REPLACED.equals(intent.getAction())) {
            Log.e(TAG,"监听到系统广播替换");
        }
    }
}
