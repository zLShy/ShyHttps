package com.zl.shyhttp.commonAdapter;

import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by zhangli on 2019/4/23.
 */

public class ViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mView;

    public ViewHolder(View itemView) {
        super(itemView);
        mView = new SparseArray<>();
    }

    /**
     * 缓存View 范型
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mView.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mView.put(viewId, view);
        }
        return (T) view;
    }


    public ViewHolder setText(int viewId, CharSequence text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        //希望能够链式调用
        return this;
    }

    public ViewHolder setImageResource(int viewId, int resourceId) {
        ImageView imageView = getView(viewId);
        imageView.setImageResource(resourceId);
        return this;
    }

    public ViewHolder setImageLoader(int viewId, HolderImageLoadler holderImageLoadler) {
        ImageView imageView = getView(viewId);
        holderImageLoadler.loadImage(imageView, holderImageLoadler.getmPath());
        return this;
    }

    public abstract static class HolderImageLoadler {

        private String mPath;

        public HolderImageLoadler(String mPath) {
            this.mPath = mPath;
        }

        public String getmPath() {
            return mPath;
        }


        public abstract void loadImage(ImageView imageView, String path);
    }
}
