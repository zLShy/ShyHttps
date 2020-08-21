package com.zl.shyhttp.http.retrifit;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import androidx.annotation.Nullable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;

/**
 * Created by ZhangL on 2020-04-02.
 * 静态代理 文件上传监听
 */
public class FileMultipartBody extends RequestBody {

    RequestBody requestBody;
    private ProgressListener mListener;
    private int mCurrentlength = 0;

    public FileMultipartBody(RequestBody requestBody) {
        this.requestBody = requestBody;
    }

    public FileMultipartBody(RequestBody requestBody, ProgressListener listener) {
        this.requestBody = requestBody;
        this.mListener = listener;
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return requestBody.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        return requestBody.contentLength();
    }

    @Override
    public void writeTo(@NotNull BufferedSink bufferedSink) throws IOException {

        ForwardingSink forwardingSink = new ForwardingSink(bufferedSink) {
            @Override
            public void write(@NotNull Buffer source, long byteCount) throws IOException {
                mCurrentlength += byteCount;
                if (mListener != null) {
                    mListener.progress(contentLength(),mCurrentlength);
                }
                super.write(source, byteCount);
            }
        };
        BufferedSink sink = Okio.buffer(forwardingSink);
        requestBody.writeTo(sink);
        sink.flush();
    }
}
