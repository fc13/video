package com.fc.vedio.helper;


import android.content.Context;
import android.content.DialogInterface;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * @author 范超 on 2017/9/7
 */

public abstract class RxSubscribe<T> implements Observer<T> {
    private Context mContext;
    private SweetAlertDialog dialog;
    private String msg;

    protected boolean showDialog() {
        return true;
    }

    /**
     * @param context context
     * @param msg     dialog message
     */
    public RxSubscribe(Context context, String msg) {
        this.mContext = context;
        this.msg = msg;
    }

    /**
     * @param context context
     */
    public RxSubscribe(Context context) {
        this(context, "请稍后...");
    }

    @Override
    public void onComplete() {
        if (showDialog())
            dialog.dismiss();
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        if (showDialog()) {
            dialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE)
                    .setTitleText(msg);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(true);
            //点击取消的时候取消订阅
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    if(!d.isDisposed()){
                        d.dispose();
                    }
                }
            });
            dialog.show();
        }
    }

//    @Override
//    public void onSubscribe() {
//        super.onSubscribe();
//        if (showDialog()) {
//            dialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE)
//                    .setTitleText(msg);
//            dialog.setCancelable(true);
//            dialog.setCanceledOnTouchOutside(true);
//            //点击取消的时候取消订阅
//            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                @Override
//                public void onCancel(DialogInterface dialog) {
//                    if(!isDisposed()){
//                        dispose();
//                    }
//                }
//            });
//            dialog.show();
//        }
//    }
    @Override
    public void onNext(T t) {
        _onNext(t);
    }
    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        /*if (Util.isNetConnected(mContext)) { //这里自行替换判断网络的代码
            _onError("网络不可用");
        } else */if (e instanceof ServerException) {
            _onError(e.getMessage());
        } else {
            _onError("请求失败，请稍后再试...");
        }
        if (showDialog())
            dialog.dismiss();
    }

    protected abstract void _onNext(T t);

    protected abstract void _onError(String message);
}
