package com.fc.vedio.entity;

/**
 * @author 范超 on 2017/9/20
 */

public class ApkDesc {
    private String version;
    private String description;
    private String url;

    public ApkDesc(String version, String description, String url) {
        this.version = version;
        this.description = description;
        this.url = url;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
