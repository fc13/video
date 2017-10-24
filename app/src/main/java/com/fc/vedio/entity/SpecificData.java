package com.fc.vedio.entity;

import java.util.List;

/**
 * @author 范超 on 2017/10/14
 */

public class SpecificData {
    private Group group;
    private List<String> comments;
    private int type;
    private double display_time;
    private double online_time;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getDisplay_time() {
        return display_time;
    }

    public void setDisplay_time(double display_time) {
        this.display_time = display_time;
    }

    public double getOnline_time() {
        return online_time;
    }

    public void setOnline_time(double online_time) {
        this.online_time = online_time;
    }
}
