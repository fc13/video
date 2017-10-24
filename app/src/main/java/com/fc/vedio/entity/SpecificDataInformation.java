package com.fc.vedio.entity;

/**
 * @author 范超 on 2017/10/14
 */

public class SpecificDataInformation {
    private boolean has_more;//是否有更多，一般情况都为 true
    private String tip;//更新提示
    private boolean has_new_message;//是否有新的数据
    private double max_time;
    private double min_time;
    private SpecificData data;//具体数据

    public boolean isHas_more() {
        return has_more;
    }

    public void setHas_more(boolean has_more) {
        this.has_more = has_more;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public boolean isHas_new_message() {
        return has_new_message;
    }

    public void setHas_new_message(boolean has_new_message) {
        this.has_new_message = has_new_message;
    }

    public double getMax_time() {
        return max_time;
    }

    public void setMax_time(double max_time) {
        this.max_time = max_time;
    }

    public double getMin_time() {
        return min_time;
    }

    public void setMin_time(double min_time) {
        this.min_time = min_time;
    }

    public SpecificData getData() {
        return data;
    }

    public void setData(SpecificData data) {
        this.data = data;
    }
}
