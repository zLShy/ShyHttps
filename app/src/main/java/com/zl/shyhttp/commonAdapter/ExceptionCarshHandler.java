package com.zl.shyhttp.commonAdapter;

import android.content.Context;
import android.util.Log;

/**
 * Created by zhangli on 2019/4/29.
 */

public class ExceptionCarshHandler implements Thread.UncaughtExceptionHandler {
    private static ExceptionCarshHandler mInstance;
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private Context mContext;

    private ExceptionCarshHandler() {
    }

    public static ExceptionCarshHandler getInstance() {
        if (mInstance == null) {
            synchronized (ExceptionCarshHandler.class) {
                if (mInstance == null) {
                    mInstance = new ExceptionCarshHandler();
                }
            }
        }
        return mInstance;
    }

    public void init(Context context) {
        this.mContext = context.getApplicationContext();
        Thread.currentThread().setUncaughtExceptionHandler(this);
        this.mDefaultHandler = Thread.currentThread().getDefaultUncaughtExceptionHandler();

    }
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        Log.e("TAG","exception");
        mDefaultHandler.uncaughtException(t,e);

    }
}
