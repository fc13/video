package com.fc.vedio.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;

import com.fc.vedio.App;
import com.fc.vedio.utils.Util;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * @author 范超 on 2017/9/20
 */

public class NetConnectReceiver extends BroadcastReceiver {
    private final String ACTION_NAME = "android.net.conn.CONNECTIVITY_CHANGE";
    private SweetAlertDialog dialog;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (ACTION_NAME.equals(action)) {
            Log.e("type", "type=" + Util.getNetType(context));
            if (!Util.isNetConnected(context)) {
                netTips(context, "无网络", "要前往打开网络吗？","关闭应用",0);
            } else {
                if (Util.getNetType(context) != 1 && Util.getNetType(context) != 0) {
                    netTips(context, "当前使用的是移动数据", "要前往开启WIFI吗？","取消",1);
                } else if (dialog != null && dialog.isShowing()) {
                    dialog.cancel();
                }
            }
        }
    }

    private void netTips(final Context context, String title, String content,String cancelText,int flag) {
        if (dialog != null && dialog.isShowing()) {
            dialog.cancel();
        }
        dialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(title)
                .setContentText(content)
                .showCancelButton(true)
                .setConfirmText("打开网络")
                .setCancelText(cancelText)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        context.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        if (flag == 1) {
                            sweetAlertDialog.cancel();
                        }else if (flag == 0){
                            App.appExit();
                        }
                    }
                });
        dialog.show();
    }
}
