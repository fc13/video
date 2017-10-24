package com.fc.vedio.entity;

import java.util.List;

/**
 * @author 范超 on 2017/10/14
 */

public class Video {
    /*"width": 480,
            "url_list": [
    {
        "url": "http://ic.snssdk.com/neihan/video/playback/1508114411.59/?video_id=b65ecbe0f37d4f08a7881aa2f11b53d0&quality=360p&line=0&is_gif=0&device_platform=android"
    },
    {
        "url": "http://ic.snssdk.com/neihan/video/playback/1508114411.59/?video_id=b65ecbe0f37d4f08a7881aa2f11b53d0&quality=360p&line=1&is_gif=0&device_platform=android"
    }
    ],
            "uri": "360p/b65ecbe0f37d4f08a7881aa2f11b53d0",
            "height": 272*/

    private String width;
    private String height;
    private String uri;
    private List<String> url_list;

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public List<String> getUrl_list() {
        return url_list;
    }

    public void setUrl_list(List<String> url_list) {
        this.url_list = url_list;
    }
}
