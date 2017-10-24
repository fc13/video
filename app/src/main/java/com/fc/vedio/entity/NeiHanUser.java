package com.fc.vedio.entity;

import java.util.List;

/**
 * @author 范超 on 2017/10/14
 */

public class NeiHanUser {
   /* "user_id": 55080298393,
            "name": "用户55080298393",
            "followings": 0,
            "user_verified": false,
            "ugc_count": 29,
            "avatar_url": "http://p3.pstatp.com/medium/3641000597f6e8c34c32",
            "followers": 18,
            "is_following": false,
            "is_pro_user": false,
            "medals": []*/
    private String user_id;
    private String name;
    private int followings;
    private boolean user_verified;
    private int ugc_count;
    private String avatar_url;
    private int followers;
    private boolean is_following;
    private boolean is_pro_user;
    private List<String> medals;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFollowings() {
        return followings;
    }

    public void setFollowings(int followings) {
        this.followings = followings;
    }

    public boolean isUser_verified() {
        return user_verified;
    }

    public void setUser_verified(boolean user_verified) {
        this.user_verified = user_verified;
    }

    public int getUgc_count() {
        return ugc_count;
    }

    public void setUgc_count(int ugc_count) {
        this.ugc_count = ugc_count;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public boolean is_following() {
        return is_following;
    }

    public void setIs_following(boolean is_following) {
        this.is_following = is_following;
    }

    public boolean is_pro_user() {
        return is_pro_user;
    }

    public void setIs_pro_user(boolean is_pro_user) {
        this.is_pro_user = is_pro_user;
    }

    public List<String> getMedals() {
        return medals;
    }

    public void setMedals(List<String> medals) {
        this.medals = medals;
    }
}
