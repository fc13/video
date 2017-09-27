package com.fc.vedio.ui.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fc.vedio.Api;
import com.fc.vedio.R;
import com.fc.vedio.base.BaseActivity;
import com.fc.vedio.download.DownloadProgressListener;
import com.fc.vedio.entity.ApkDesc;
import com.fc.vedio.helper.RxHelper;
import com.fc.vedio.helper.RxSubscribe;
import com.fc.vedio.utils.Util;

import java.io.File;
import java.io.InputStream;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * @author 范超 on 2017/9/19
 */

public class StartPageActivity extends BaseActivity {
    private static final String TAG = "StartPageActivity";
    @BindView(R.id.im_start)
    ImageView imStart;
    @BindView(R.id.text_logo)
    TextView textLogo;
    @BindView(R.id.check)
    LinearLayout check;
    @BindView(R.id.app_desc)
    TextView appDesc;
    @BindView(R.id.app_version)
    TextView appVersion;

    private Disposable disposable;
    private String version;
    private String description;
    private AlertDialog dialog;
    private ProgressDialog progressDialog;
    private File outFile;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_start_page;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        Glide.with(this)
                .load(R.drawable.start_page)
                .into(imStart);

    }

    @Override
    protected void initData() {
        super.initData();
        getAppVersion();
        appVersion.setText(version);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void showUpdateDialog(String url) {
        View view = LayoutInflater.from(this).inflate(R.layout.update_dialog, null);
        TextView title = (TextView) view.findViewById(R.id.txt_title);
        TextView content = (TextView) view.findViewById(R.id.txt_msg);
        Button btnNeg = (Button) view.findViewById(R.id.btn_neg);
        Button btnPos = (Button) view.findViewById(R.id.btn_pos);
        title.setText("更新");
        content.setText("当前版本：" + Util.getVersion(StartPageActivity.this) +
                "\n最新版本：" + version + "\n更新说明：\n" + description);
        btnNeg.setText("取消");
        btnPos.setText("确定");
        btnNeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnPos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                showProgressDialog();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        download(url);
                    }
                }).start();
            }
        });
        dialog = new AlertDialog.Builder(this)
                .setView(view)
                .show();
    }

    public void showProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);// 设置水平进度条
        progressDialog.setCancelable(false);// 设置是否可以通过点击Back键取消
        progressDialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
//        dialog.setIcon(R.drawable.ic_launcher);// 设置提示的title的图标，默认是没有的
        progressDialog.setTitle("更新");
        progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!disposable.isDisposed()) {
                            disposable.dispose();
                        }
                        dialog.dismiss();
                    }
                });
        progressDialog.setMessage("下载中");
        progressDialog.show();
    }

    public void getAppVersion() {
        Api.getDefault().getVersion()
                .compose(RxHelper.handleResult())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscribe<ApkDesc>(StartPageActivity.this) {
                    @Override
                    protected void _onNext(ApkDesc apkDesc) {
                        version = apkDesc.getVersion();
                        description = apkDesc.getDescription();
                        String url = apkDesc.getUrl();
                        Toast.makeText(StartPageActivity.this,"版本号为："+version,Toast.LENGTH_SHORT).show();
                        if (!TextUtils.isEmpty(version) && Util.getVersion(StartPageActivity.this) != Integer.parseInt(version)) {
                            showUpdateDialog(url);
                        } else {
                            check.setVisibility(View.GONE);
                            finish();
                            startActivity(new Intent(StartPageActivity.this, LoginActivity.class));
                        }
                    }

                    @Override
                    protected void _onError(String message) {
                        Toast.makeText(StartPageActivity.this, message, Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(StartPageActivity.this, LoginActivity.class));
                    }
                });
    }

    public void downloadApk(String apkUrl, File file, Observer observer, DownloadProgressListener listener) {
        Api.getDefault(listener).download(apkUrl)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map(new Function<ResponseBody, InputStream>() {
                    @Override
                    public InputStream apply(@NonNull ResponseBody responseBody) throws Exception {
                        return responseBody.byteStream();
                    }
                })
                .observeOn(Schedulers.computation())
                .doOnNext(new Consumer<InputStream>() {
                    @Override
                    public void accept(InputStream inputStream) throws Exception {
                        Util.writeFile(inputStream, file);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private void download(String url) {
        DownloadProgressListener listener = new DownloadProgressListener() {
            @Override
            public void update(long bytesRead, long contentLength, boolean done) {
                int progress = (int) ((bytesRead * 100) / contentLength);
                Log.e(TAG, "length=" + contentLength + "," + progress);
                progressDialog.setMax((int) contentLength);
                progressDialog.setProgress((int) bytesRead);
            }
        };
        outFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "file.apk");
        downloadApk(url, outFile, new Observer() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(@NonNull Object o) {

            }

            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
                downloadCompleted();
                Toast.makeText(StartPageActivity.this, "下载出错！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {
                downloadCompleted();
                Toast.makeText(StartPageActivity.this, "下载完成！", Toast.LENGTH_SHORT).show();
            }
        }, listener);
    }

    private void downloadCompleted() {

        //安装apk,Android N后不适用
//        Intent install = new Intent(Intent.ACTION_VIEW);
//        install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        install.setDataAndType(Uri.fromFile(outFile), "application/vnd.android.package-archive");
//        startActivity(install);

        //安装apk,兼容Android N
        Intent intent = new Intent(Intent.ACTION_VIEW);
        // 由于没有在Activity环境下启动Activity,设置下面的标签
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if(Build.VERSION.SDK_INT>=24) { //判读版本是否在7.0以上
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            Uri apkUri = FileProvider.getUriForFile(this,"com.fc.vedio.fileprovider",outFile);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        }else{
            intent.setDataAndType(Uri.fromFile(outFile),
                    "application/vnd.android.package-archive");
        }
        startActivity(intent);
    }
}
