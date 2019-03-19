package com.example.epamtraining;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.epamtraining.entities.Student;
import com.example.epamtraining.backend.IWebService;
import com.example.epamtraining.backend.StudentsWebService;
import com.example.epamtraining.util.ICallback;

import java.util.List;


public class StudentsActivity extends AppCompatActivity {

    public static final int PAGE_SIZE = 10;
    public static final int MAX_VISIBLE_ITEMS = 40;

    private boolean isLoading = false;
    private StudentsAdapter adapter;
    private LinearLayoutManager layoutManager;
    private final IWebService<Student> webService = new StudentsWebService();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_students);

        final RecyclerView recyclerView = findViewById(R.id.students_list);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new StudentsAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator(){
            @Override
            public boolean animateMove(RecyclerView.ViewHolder holder, int fromX, int fromY, int toX, int toY) {
                return super.animateMove(holder, fromX, fromY, toX, toY);
            }
        });
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        new ItemTouchHelper(new ItemTouchCallback(recyclerView, adapter)).attachToRecyclerView(recyclerView);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int totalItemCount = layoutManager.getItemCount();

                if (totalItemCount > MAX_VISIBLE_ITEMS) {
                    adapter.setShowLastViewAsLoading(false);

                    return;
                }

                int visibleItemCount = layoutManager.getChildCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if (!isLoading) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0
                            && totalItemCount >= PAGE_SIZE) {
                        loadMoreItems(totalItemCount, totalItemCount + PAGE_SIZE);
                    }
                }
            }
        });

        loadMoreItems(0, PAGE_SIZE);
    }

    private void loadMoreItems(final int startPosition, final int endPosition) {
        isLoading = true;
        adapter.setShowLastViewAsLoading(true);
        webService.getEntities(startPosition, endPosition, new ICallback<List<Student>>() {

            @Override
            public void onResult(List<Student> pResult) {
                adapter.addItems(pResult);
                isLoading = false;
            }
        });
    }
}
