package com.fc.vedio.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fc.vedio.Api;
import com.fc.vedio.R;
import com.fc.vedio.cache.CacheManager;
import com.fc.vedio.entity.Group;
import com.fc.vedio.entity.LargeCover;
import com.fc.vedio.entity.NeiHanEntity;
import com.fc.vedio.entity.NeiHanUser;
import com.fc.vedio.entity.SpecificData;
import com.fc.vedio.entity.SpecificDataInformation;
import com.fc.vedio.entity.Video;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.vov.vitamio.widget.VideoView;
import okhttp3.ResponseBody;

/**
 * @author 范超 on 2017/10/16
 */

public class VideoRecyclerAdapter extends RecyclerView.Adapter<VideoRecyclerAdapter.VideoHolder> {
    private Context context;
    private List<NeiHanEntity> neiHanEntities;

    public VideoRecyclerAdapter(Context context) {
        this.context = context;
        neiHanEntities = new ArrayList<>();
    }

    public void addItem(List<NeiHanEntity> neiHanEntities) {
        this.neiHanEntities.clear();
        this.neiHanEntities.addAll(neiHanEntities);
        notifyDataSetChanged();
    }

    @Override
    public VideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_item, parent, false);
        return new VideoHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoHolder holder, int position) {
        NeiHanEntity neiHanEntity = neiHanEntities.get(0);
        String message = neiHanEntity.getMessage();
        if (message.equals("success")) {
            SpecificDataInformation data = neiHanEntity.getData();
            SpecificData specificData = data.getData();
            Group group = specificData.getGroup();
            Video video = group.get_720p_video();
            LargeCover largeCover = group.getLarge_cover();
            NeiHanUser neiHanUser = group.getUser();

        }
    }

    public void downloadAvatar(String url,CircleImageView circleImageView) {
        Api.getDefault().download(url)
                .subscribeOn(Schedulers.io())
                .map(new Function<ResponseBody, Bitmap>() {
                    @Override
                    public Bitmap apply(@NonNull ResponseBody responseBody) throws Exception {
                        return BitmapFactory.decodeStream(responseBody.byteStream());
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Bitmap>() {
                    @Override
                    public void accept(Bitmap bitmap) throws Exception {
                        circleImageView.setImageBitmap(bitmap);
                        CacheManager.saveObject(context,bitmap,"");
                    }
                });
    }

    @Override
    public int getItemCount() {
        return neiHanEntities.size();
    }

    static class VideoHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.video_view)
        VideoView videoView;
        @BindView(R.id.probar)
        ProgressBar probar;
        @BindView(R.id.download_rate)
        TextView downloadRate;
        @BindView(R.id.load_rate)
        TextView loadRate;
        @BindView(R.id.user_portrait)
        CircleImageView userPortrait;
        @BindView(R.id.user_name)
        TextView userName;
        @BindView(R.id.video_describe)
        TextView videoDescribe;

        VideoHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
