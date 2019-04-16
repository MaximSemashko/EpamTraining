package com.example.epamtraining;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.epamtraining.backend.IWebService;
import com.example.epamtraining.backend.StudentsWebService;
import com.example.epamtraining.entities.Student;
import com.example.epamtraining.util.ICallback;

import java.util.List;


public class StudentsActivity extends AppCompatActivity {

    public static final int PAGE_SIZE = 10;
    public static final int MAX_VISIBLE_ITEMS = 20;

    private boolean isLoading = false;
    private StudentsAdapter adapter;
    private LinearLayoutManager layoutManager;
    private final IWebService<Student> webService = new StudentsWebService();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_students);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final RecyclerView recyclerView = findViewById(R.id.students_list);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new StudentsAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator() {
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
            public void onResult(List<Student> result) {
                adapter.addItems(result);
                isLoading = false;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.add_student) {
            showAddStudentDialog();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void showAddStudentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_student, null);

        EditText studentNameText = dialogView.findViewById(R.id.student_name_edit_text);
        EditText studentHomeworksText = dialogView.findViewById(R.id.student_homeworks_edit_text);
        TextView changeStudentView = dialogView.findViewById(R.id.alertTitle);
        changeStudentView.setText(R.string.add_student_title);

        builder.setView(dialogView)
                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String name = studentNameText.getText().toString();
                        int homeworks = Integer.parseInt(String.valueOf(studentHomeworksText.getText()));
                        adapter.addStudent(new Student(name, homeworks));
                    }
                })


                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        builder.show();
    }
}
