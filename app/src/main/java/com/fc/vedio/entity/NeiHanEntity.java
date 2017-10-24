package com.fc.vedio.entity;

/**
 * @author 范超 on 2017/10/14
 */

public class NeiHanEntity {
    private String message;//成功时为 success
    private SpecificDataInformation data;//具体数据信息

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SpecificDataInformation getData() {
        return data;
    }

    public void setData(SpecificDataInformation data) {
        this.data = data;
    }
}
