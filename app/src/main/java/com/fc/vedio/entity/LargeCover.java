package com.fc.vedio.entity;

import java.util.List;

/**
 * @author 范超 on 2017/10/14
 */

public class LargeCover {

/*    "url_list": [
    {
        "url": "http://p1.pstatp.com/large/3cb700118f299cbf72be.webp"
    },
    {
        "url": "http://pb3.pstatp.com/large/3cb700118f299cbf72be.webp"
    },
    {
        "url": "http://pb9.pstatp.com/large/3cb700118f299cbf72be.webp"
    }
    ],
    "uri": "large/3cb700118f299cbf72be"*/
    private List<String> url_list;
    private String uri;

    public List<String> getUrl_list() {
        return url_list;
    }

    public void setUrl_list(List<String> url_list) {
        this.url_list = url_list;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
