package com.fc.vedio.download;

import java.io.IOException;

import io.reactivex.annotations.Nullable;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * @author 范超 on 2017/9/20
 */

public class DownloadProgressResponseBody extends ResponseBody {
    private ResponseBody responseBody;
    private DownloadProgressListener downloadProgressListener;
    private BufferedSource bufferedSource;

    public DownloadProgressResponseBody(ResponseBody responseBody, DownloadProgressListener downloadProgressListener) {
        this.responseBody = responseBody;
        this.downloadProgressListener = downloadProgressListener;
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(source(responseBody.source()));
        }
        return bufferedSource;
    }

    private Source source(Source source) {
        return new ForwardingSource(source) {
            long totalBytesRead = 0L;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                if (downloadProgressListener != null) {
                    downloadProgressListener
                            .update(totalBytesRead, responseBody.contentLength(), bytesRead == -1);
                }
                return bytesRead;
            }
        };
    }
}