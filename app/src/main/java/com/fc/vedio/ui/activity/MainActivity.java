package com.fc.vedio.ui.activity;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.fc.vedio.App;
import com.fc.vedio.R;
import com.fc.vedio.base.BaseActivity;
import com.fc.vedio.helper.BottomNavigationViewHelper;
import com.fc.vedio.helper.NavHelper;
import com.fc.vedio.ui.fragment.AboutFragment;
import com.fc.vedio.ui.fragment.MainFragment;
import com.fc.vedio.ui.fragment.MineFragment;
import com.fc.vedio.ui.fragment.RecommendFragment;
import com.fc.vedio.view.PortraitView;

import net.qiujuer.genius.ui.Ui;
import net.qiujuer.genius.ui.widget.FloatActionButton;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener,
        NavHelper.OnTabChangedListener<Integer[]> {

    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.im_portrait)
    PortraitView imPortrait;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.im_search)
    ImageView imSearch;
    @BindView(R.id.layout_container)
    FrameLayout layoutContainer;
    @BindView(R.id.btn_floatAction)
    FloatActionButton btnFloatAction;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    private NavHelper<Integer[]> navHelper;
    private long time;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        //初始化底部辅助工具类
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navHelper = new NavHelper<Integer[]>(this, getSupportFragmentManager(),
                R.id.layout_container, this);
        navHelper.add(R.id.action_home, new NavHelper.Tab<>(MainFragment.class, new Integer[]{R.string.title_home,R.color.colorAccent}))
                .add(R.id.action_group, new NavHelper.Tab<>(RecommendFragment.class, new Integer[]{R.string.title_group,R.color.colorPrimary}))
                .add(R.id.action_contact, new NavHelper.Tab<>(MineFragment.class, new Integer[]{R.string.title_contact,R.color.red_a700}))
                .add(R.id.action_personal,new NavHelper.Tab<>(AboutFragment.class,new Integer[]{R.string.title_personal,R.color.yellow_a700}));
        //添加底部按钮点击的监听
        navigation.setOnNavigationItemSelectedListener(this);
        Glide.with(this)
                .load(R.drawable.bg_src_morning)
                .centerCrop()
                .into(new ViewTarget<View, GlideDrawable>(appbar) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        this.view.setBackground(resource.getCurrent());
                    }
                });
    }

    @Override
    protected void initData() {
        super.initData();
        //从底部导航栏中接管Menu，然后进行手动触发第一次点击
        Menu menu = navigation.getMenu();
        //触发首次选中Home
        menu.performIdentifierAction(R.id.action_home, 0);
    }

    @OnClick({R.id.im_portrait, R.id.im_search, R.id.btn_floatAction})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_portrait:
                break;
            case R.id.im_search:
                break;
            case R.id.btn_floatAction:
                break;
        }
    }

    /**
     * 当底部导航点击的时候触发
     *
     * @param item
     * @return true：代表能够处理
     */
    private boolean isFirst = true;
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return navHelper.performClickMenu(item.getItemId());
    }

    //NavHelper处理回调的方法
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onTabChanged(NavHelper.Tab<Integer[]> newTab, NavHelper.Tab<Integer[]> oldTab) {
        txtTitle.setText(newTab.extra[0]);
        navigation.setBackground(getDrawable(newTab.extra[1]));

        //对浮动按钮进行隐藏与显示的动画
        float transY = 0;
        float rotation = 0;
        if (Objects.equals(newTab.extra[0], R.string.title_home)) {
            transY = Ui.dipToPx(getResources(), 76);
        } else {
            if (Objects.equals(newTab.extra[0], R.string.title_group)) {
                btnFloatAction.setImageResource(R.drawable.ic_group_add);
                rotation = 360;
            } else if (Objects.equals(newTab.extra[0 ], R.string.title_contact)){
                btnFloatAction.setImageResource(R.drawable.ic_contact_add);
                rotation = -360;
            }
        }
        btnFloatAction.animate()
                .rotation(rotation)
                .translationY(transY)
                .setDuration(480)
                .setInterpolator(new AnticipateOvershootInterpolator())
                .start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - time > 2000) {
                time = System.currentTimeMillis();
                Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
            } else {
                App.appExit();
            }
            return true;
        }
        return false;
    }
}
