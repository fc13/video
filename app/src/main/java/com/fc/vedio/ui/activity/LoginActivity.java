package com.fc.vedio.ui.activity;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fc.vedio.Api;
import com.fc.vedio.App;
import com.fc.vedio.R;
import com.fc.vedio.base.BaseActivity;
import com.fc.vedio.entity.User;
import com.fc.vedio.helper.RxHelper;
import com.fc.vedio.helper.RxSubscribe;
import com.fc.vedio.utils.Constant;
import com.fc.vedio.utils.SharedPrefsUtil;
import com.fc.vedio.utils.Util;
import com.fc.vedio.view.CustomVideoView;
import com.fc.vedio.view.EditWithDeal;
import com.mob.MobSDK;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.functions.Consumer;

public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";
    @BindView(R.id.video_view)
    CustomVideoView videoView;
    @BindView(R.id.user_name)
    EditWithDeal userName;
    @BindView(R.id.user_password)
    EditWithDeal userPassword;
    @BindView(R.id.remember_password)
    CheckBox rememberPassword;
    @BindView(R.id.auto_login)
    CheckBox autoLogin;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.big_portrait)
    CircleImageView bigPortrait;
    @BindView(R.id.qq)
    ImageView qq;
    @BindView(R.id.wei_xin)
    ImageView weiXin;
    @BindView(R.id.wei_bo)
    ImageView weiBo;
    @BindView(R.id.extra_function)
    TextView extraFunction;
    @BindView(R.id.layout_other)
    LinearLayout layoutOther;
    @BindView(R.id.activity_login)
    RelativeLayout activityLogin;
    @BindView(R.id.layout_login)
    LinearLayout layoutLogin;
    @BindView(R.id.user_name_register)
    EditWithDeal userNameRegister;
    @BindView(R.id.user_password_register)
    EditWithDeal userPasswordRegister;
    @BindView(R.id.password_again_register)
    EditWithDeal passwordAgainRegister;
    @BindView(R.id.user_phone)
    EditWithDeal userPhone;
    @BindView(R.id.btn_registered)
    Button btnRegistered;
    @BindView(R.id.return_login)
    TextView returnLogin;
    @BindView(R.id.layout_registered)
    LinearLayout layoutRegistered;
    @BindView(R.id.relativeLayout)
    RelativeLayout relativeLayout;
    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.phone_hint)
    TextView phoneHint;
    @BindView(R.id.password_hint)
    TextView passwordHint;

    private long time;
    private String gender;
    //第三方登录
    private PlatformDb platDB;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {
        super.initData();
        initView();
        setExtraText();
        initSpinner();
        MobSDK.init(this, "213ddd5cb9be4", "658ffa78194088239e32ce18802caa65");
        if (SharedPrefsUtil.getBoolean(this, Constant.IS_REMEMBER)) {
            rememberPassword.setChecked(true);
            userName.setText(SharedPrefsUtil.getString(getApplicationContext(), Constant.USER_NAME));
            userPassword.setText(SharedPrefsUtil.getString(getApplicationContext(), Constant.USER_PASSWORD));
        }
        if (SharedPrefsUtil.getBoolean(this, Constant.IS_AUTO_LOGIN)) {
            autoLogin.setChecked(true);
            login(userName.getEditableText().toString(), userPassword.getEditableText().toString());
        }

    }

    private void initSpinner() {
        String[] genders = getResources().getStringArray(R.array.data);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.spinner_item, genders);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                gender = genders[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    /**
     * 记住密码
     */
    private void rememberData(String userName, String password) {
        if (rememberPassword.isChecked()) {
            SharedPrefsUtil.setValue(this, Constant.USER_NAME, userName);
            SharedPrefsUtil.setValue(this, Constant.USER_PASSWORD, password);
            SharedPrefsUtil.setValue(this, Constant.IS_REMEMBER, true);
        } else {
            SharedPrefsUtil.setValue(this, Constant.USER_NAME, "");
            SharedPrefsUtil.setValue(this, Constant.USER_PASSWORD, "");
            SharedPrefsUtil.setValue(this, Constant.IS_REMEMBER, false);
        }
    }

    private void initView() {
        //设置播放加载路径
        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video));
        //播放
        videoView.start();
        //循环播放
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                videoView.start();
            }
        });
        userPhone.addTextChangedListener(new CustomTextWatcher(1));
        passwordAgainRegister.addTextChangedListener(new CustomTextWatcher(2));
        userPasswordRegister.addTextChangedListener(new CustomTextWatcher(2));
    }

    //返回重启加载
    @Override
    protected void onRestart() {
        initView();
        super.onRestart();
    }

    //防止锁屏或者切出的时候，音乐在播放
    @Override
    protected void onStop() {
        videoView.stopPlayback();
        super.onStop();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - time > 2000) {
                time = System.currentTimeMillis();
                Toast.makeText(LoginActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
            } else {
                App.appExit();
            }
            return true;
        }
        return false;
    }

    @OnClick({R.id.btn_login, R.id.big_portrait, R.id.qq, R.id.wei_xin, R.id.wei_bo, R.id.return_login, R.id.btn_registered})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                String name = userName.getEditableText().toString();
                String password = userPassword.getEditableText().toString();
                if (rememberPassword.isChecked()) {
                    rememberData(name, password);
                }
                if (autoLogin.isChecked()) {
                    SharedPrefsUtil.setValue(this, Constant.IS_AUTO_LOGIN, true);
                } else {
                    SharedPrefsUtil.setValue(this, Constant.IS_AUTO_LOGIN, false);
                }
                if (TextUtils.isEmpty(name)) {
                    userName.setError("用户名不能为空");
                } else if (TextUtils.isEmpty(password)) {
                    userPassword.setError("密码不能为空");
                } else {
                    login(name, password);
                }
                break;
            case R.id.big_portrait:
                break;
            case R.id.qq:
                // qq登录
                Platform qq = ShareSDK.getPlatform(QQ.NAME);
                authorize(qq);
                break;
            case R.id.wei_xin:
                // 微信登录
                Platform weiXin = ShareSDK.getPlatform(Wechat.NAME);
                authorize(weiXin);
                break;
            case R.id.wei_bo:
                // 微博登录
                Platform weiBo = ShareSDK.getPlatform(SinaWeibo.NAME);
                authorize(weiBo);
                break;
            case R.id.return_login:
                animation(layoutRegistered, bigPortrait, layoutLogin, 1, 500, View.VISIBLE);
                userNameRegister.setText("");
                userPasswordRegister.setText("");
                passwordAgainRegister.setText("");
                spinner.setSelection(0);
                userPhone.setText("");
                break;
            case R.id.btn_registered:
                String userName = userNameRegister.getEditableText().toString();
                String userPassword = userPasswordRegister.getEditableText().toString();
                String passwordAgain = passwordAgainRegister.getEditableText().toString();
                String phone = userPhone.getEditableText().toString();
                String text = phoneHint.getText().toString();
                if (TextUtils.isEmpty(text) || text.equals("请输入11位的手机号")) {
                    userPhone.setError(text);
                } else if (text.equals("无效的手机号")) {

                }else if (!userPassword.equals(passwordAgain)){

                }else if (gender.equals("请选择性别")){
                    Toast.makeText(LoginActivity.this,"请选择性别",Toast.LENGTH_SHORT).show();
                }else {
                    User user = new User(userName, userPassword, gender, phone);
                    register(user);
                }
                break;
        }
    }

    /**
     * 设置‘记住密码’、‘注册账号’可以点击
     */
    public void setExtraText() {
        extraFunction.setMovementMethod(LinkMovementMethod.getInstance());
        SpannableString spannableString = new SpannableString(extraFunction.getText().toString());
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Snackbar.make(activityLogin, "打电话联系我吧：15221397348", Snackbar.LENGTH_SHORT)
                        .setAction("拨打电话", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                requestPermissions();
                            }
                        }).show();
                Toast.makeText(LoginActivity.this, "联系我找回密码", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(getResources().getColor(R.color.white_alpha_224));
                ds.setUnderlineText(false);
            }
        }, 0, 4, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {
                animation(layoutLogin, bigPortrait, layoutRegistered, 0, 500, View.GONE);
                userName.setText("");
                userPassword.setText("");
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(getResources().getColor(R.color.white_alpha_224));
                ds.setUnderlineText(false);
            }
        }, 7, 11, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        extraFunction.setText(spannableString);
    }

    /**
     * 第三方登录
     */
    private void authorize(Platform plat) {
        if (plat == null) {
            return;
        }
        //判断指定平台是否已经完成授权
        if (plat.isAuthValid()) {
            String token = plat.getDb().getToken();
            String userId = plat.getDb().getUserId();
            String userName = plat.getDb().getUserName();
            String userGender = plat.getDb().getUserGender();
            String userHeadImageUrl = plat.getDb().getUserIcon();
            String platformName = plat.getDb().getPlatformNname();
            if (userId != null) {
                //已经授权过，直接下一步操作
                if (platformName.equals(SinaWeibo.NAME)) {
                    //微博授权
                } else if (platformName.equals(QQ.NAME)) {
                    //QQ授权
                } else if (platformName.equals(Wechat.NAME)) {
                    //微信授权
                }
                return;
            }
        }
        // true不使用SSO授权，false使用SSO授权
        plat.SSOSetting(false);
        plat.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                String headImageUrl = null;//头像
                String userId;//userId
                String token;//token
                String gender;//性别
                String name = null;//用户名

                if (i == Platform.ACTION_USER_INFOR) {

                    platDB = platform.getDb(); // 获取平台数据DB

                    if (platform.getName().equals(Wechat.NAME)) {
                        //微信登录

                        // 通过DB获取各种数据
                        token = platDB.getToken();
                        userId = platDB.getUserId();
                        name = platDB.getUserName();
                        gender = platDB.getUserGender();
                        headImageUrl = platDB.getUserIcon();
                        if ("m".equals(gender)) {
                            gender = "1";
                        } else {
                            gender = "2";
                        }

                    } else if (platform.getName().equals(SinaWeibo.NAME)) {
                        // 微博登录

                        token = platDB.getToken();
                        userId = platDB.getUserId();
                        name = hashMap.get("nickname").toString(); // 名字
                        gender = hashMap.get("gender").toString(); // 年龄
                        headImageUrl = hashMap.get("figureurl_qq_2").toString(); // 头像figureurl_qq_2 中等图，figureurl_qq_1缩略图

                    } else if (platform.getName().equals(QQ.NAME)) {
                        // QQ登录
                        token = platDB.getToken();
                        userId = platDB.getUserId();
                        name = hashMap.get("nickname").toString(); // 名字
                        gender = hashMap.get("gender").toString(); // 年龄
                        headImageUrl = hashMap.get("figureurl_qq_2").toString(); // 头像figureurl_qq_2 中等图，figureurl_qq_1缩略图
                        userName.setText(name);
                        Glide.with(LoginActivity.this).load(headImageUrl).into(bigPortrait);
                    }
                }
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(Platform platform, int i) {

            }
        });
        plat.authorize();
        //获取用户资料
        plat.showUser(null);
    }

    //请求权限
    private void requestPermissions() {
        RxPermissions permissions = new RxPermissions(this);
        permissions
                .request(Manifest.permission.CALL_PHONE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:15221397348"));
                            LoginActivity.this.startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "通话权限被禁止，请打开通话权限", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void animation(LinearLayout layout1, CircleImageView circleImageView,
                           LinearLayout layout2, int alpha, long duration, int visible) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(layout1, "alpha", 0);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(circleImageView, "alpha", alpha);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animator, animator1);
        set.setDuration(duration);
        set.start();
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                layout1.setVisibility(View.GONE);
                layout2.setVisibility(View.VISIBLE);
                bigPortrait.setVisibility(visible);
                ObjectAnimator animator2 = ObjectAnimator.ofFloat(layout2, "alpha", 1);
                animator2.setDuration(duration);
                animator2.start();
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
    }

    //登录
    private void login(String userName, String password) {
        Api.getDefault().login(userName, password)
                .compose(RxHelper.handleResult())
                .subscribe(new RxSubscribe<String>(LoginActivity.this) {
                    @Override
                    protected void _onNext(String s) {
                        Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    protected void _onError(String message) {
                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //注册
    private void register(User user) {
        Api.getDefault().register(user)
                .compose(RxHelper.handleResult())
                .subscribe(new RxSubscribe<User>(LoginActivity.this) {
                    @Override
                    protected void _onNext(User s) {
                        Toast.makeText(LoginActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                        login(s.getName(),s.getPassword());
                    }

                    @Override
                    protected void _onError(String message) {
                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    class CustomTextWatcher implements TextWatcher {
        private int flag;

        public CustomTextWatcher(int flag) {
            this.flag = flag;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (flag==1){
                String result = Util.isMobileNumber(editable.toString());
                phoneHint.setText(result);
                if (result.equals("无效的手机号")) {
                    phoneHint.setTextColor(getResources().getColor(R.color.red_400));
                } else {
                    phoneHint.setTextColor(getResources().getColor(R.color.blue_400));
                }
            }else if (flag==2){
                if (!editable.toString().equals(userPasswordRegister.getEditableText().toString())){
                    passwordHint.setText("两次输入的密码不一致");
                }else {
                    passwordHint.setText("");
                }
            }
        }
    }
}


