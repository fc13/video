package com.fc.vedio.download;

/**
 * @author 范超 on 2017/9/20
 */

public interface DownloadProgressListener {
    void update(long bytesRead, long contentLength, boolean done);
}
