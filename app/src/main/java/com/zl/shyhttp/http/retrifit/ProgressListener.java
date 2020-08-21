package com.zl.shyhttp.http.retrifit;

/**
 * Created by ZhangL on 2020-04-02.
 */
public interface ProgressListener {
    void progress(long totle, int current);
}
