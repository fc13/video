package com.fc.vedio.ui.fragment;

import android.content.res.Configuration;
import android.net.Uri;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fc.vedio.R;
import com.fc.vedio.base.BaseFragment;
import com.fc.vedio.mediaController.CustomMediaController;

import butterknife.BindView;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.VideoView;

/**
 * @author 范超 on 2017/9/21
 */

public class MainFragment extends BaseFragment implements MediaPlayer.OnInfoListener, MediaPlayer.OnBufferingUpdateListener {
    @BindView(R.id.video_view)
    VideoView videoView;
    @BindView(R.id.probar)
    ProgressBar probar;
    @BindView(R.id.download_rate)
    TextView downloadRate;
    @BindView(R.id.load_rate)
    TextView loadRate;

    //视频地址
    private String path = "http://baobab.wdjcdn.com/145076769089714.mp4";
    private Uri uri;
    private CustomMediaController mCustomMediaController;

    @Override
    protected int getContentLayout() {
        return R.layout.rv_item;
    }

    @Override
    protected void initData() {
        super.initData();
        initView();
        initData1();
    }

    //初始化控件
    private void initView() {
        mCustomMediaController = new CustomMediaController(getActivity(), videoView, getActivity());
        mCustomMediaController.setVideoName("白火锅 x 红火锅");
    }

    //初始化数据
    private void initData1() {
        uri = Uri.parse(path);
        videoView.setVideoURI(uri);//设置视频播放地址
        mCustomMediaController.show(5000);
        videoView.setMediaController(mCustomMediaController);
        videoView.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);//高画质
        videoView.requestFocus();
        videoView.setOnInfoListener(this);
        videoView.setOnBufferingUpdateListener(this);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setPlaybackSpeed(1.0f);
            }
        });
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        switch (what) {
            case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                if (videoView.isPlaying()) {
                    videoView.pause();
                    probar.setVisibility(View.VISIBLE);
                    downloadRate.setText("");
                    loadRate.setText("");
                    downloadRate.setVisibility(View.VISIBLE);
                    loadRate.setVisibility(View.VISIBLE);

                }
                break;
            case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                videoView.start();
                probar.setVisibility(View.GONE);
                downloadRate.setVisibility(View.GONE);
                loadRate.setVisibility(View.GONE);
                break;
            case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
                downloadRate.setText("" + extra + "kb/s" + "  ");
                break;
        }
        return true;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        loadRate.setText(percent + "%");
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        //屏幕切换时，设置全屏
        if (videoView != null) {
            videoView.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE, 0);
        }
        super.onConfigurationChanged(newConfig);
    }
}
