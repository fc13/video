package com.fc.vedio.entity;

/**
 * @author 范超 on 2017/10/14
 */

public class Group {

    private Video _720p_video;
    private LargeCover large_cover;
    private NeiHanUser user;
    private int play_count;
    private int bury_count;
    private int digg_count;
    private int share_count;
    private String content;

    public Video get_720p_video() {
        return _720p_video;
    }

    public void set_720p_video(Video _720p_video) {
        this._720p_video = _720p_video;
    }

    public LargeCover getLarge_cover() {
        return large_cover;
    }

    public void setLarge_cover(LargeCover large_cover) {
        this.large_cover = large_cover;
    }

    public NeiHanUser getUser() {
        return user;
    }

    public void setUser(NeiHanUser user) {
        this.user = user;
    }

    public int getPlay_count() {
        return play_count;
    }

    public void setPlay_count(int play_count) {
        this.play_count = play_count;
    }

    public int getBury_count() {
        return bury_count;
    }

    public void setBury_count(int bury_count) {
        this.bury_count = bury_count;
    }

    public int getDigg_count() {
        return digg_count;
    }

    public void setDigg_count(int digg_count) {
        this.digg_count = digg_count;
    }

    public int getShare_count() {
        return share_count;
    }

    public void setShare_count(int share_count) {
        this.share_count = share_count;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
