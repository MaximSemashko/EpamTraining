package com.example.epamtraining.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class BaseViewHolder<T extends View> extends RecyclerView.ViewHolder {

    public BaseViewHolder(@NonNull final T studentItemView) {
        super(studentItemView);
    }

    public T getView() {
        return (T) itemView;
    }
}
