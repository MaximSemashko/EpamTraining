package com.example.epamtraining;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

public class ItemTouchCallback extends ItemTouchHelper.SimpleCallback {

    private final StudentsAdapter adapter;
    private final RecyclerView recycler;

    public ItemTouchCallback(final RecyclerView recycler, final StudentsAdapter adapter) {
        super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.START | ItemTouchHelper.END);
        this.adapter = adapter;
        this.recycler = recycler;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView,
                          @NonNull RecyclerView.ViewHolder viewHolder,
                          @NonNull RecyclerView.ViewHolder viewHolder1) {
        adapter.onItemMove(viewHolder.getAdapterPosition(), viewHolder1.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        int adapterPosition = viewHolder.getAdapterPosition();

        if (RecyclerView.NO_POSITION != adapterPosition) {
            adapter.onItemDismiss(adapterPosition);
        }
    }
}
