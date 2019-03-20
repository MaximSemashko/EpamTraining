package com.example.epamtraining;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.epamtraining.backend.IWebService;
import com.example.epamtraining.backend.StudentsWebService;
import com.example.epamtraining.base.BaseViewHolder;
import com.example.epamtraining.entities.Student;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StudentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private boolean isShowLastViewAsLoading = false;

    private final LayoutInflater inflater;
    private final List<Student> students = new ArrayList<>();
    private final IWebService<Student> webService = new StudentsWebService();


    public StudentsAdapter(final Context context) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent,
                                                      @ViewType final int viewType) {
        if (viewType == ViewType.STUDENT) {
            return new BaseViewHolder<>(new StudentItemView(parent.getContext()));
        } else {
            return new BaseViewHolder<>(inflater.inflate(R.layout.layout_progress, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int position) {
        if (getItemViewType(position) == ViewType.STUDENT) {
            final Student student = students.get(position);

            ((StudentItemView) viewHolder.itemView)
                    .setName(student.getName())
                    .setHwCounter(String.valueOf(student.getHwCounter()));

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showChangeDialog(v, position);
                }
            });
        }
    }

    private void showChangeDialog(View v, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        View dialogView = inflater.inflate(R.layout.dialog_student, null);

        EditText studentNameText = dialogView.findViewById(R.id.student_name_edit_text);
        EditText studentHomeworksText = dialogView.findViewById(R.id.student_homeworks_edit_text);
        TextView changeStudentView = dialogView.findViewById(R.id.alertTitle);
        changeStudentView.setText(R.string.change_student_title);

        builder.setView(dialogView)
                .setPositiveButton(R.string.change_student, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String name = studentNameText.getText().toString();
                        int homeworks = Integer.parseInt(studentHomeworksText.getText().toString());

                        changeStudent(name, homeworks, position);
                    }
                })

                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        builder.show();
    }

    private void changeStudent(String name, int homeworks, int position) {
        students.get(position).setName(name);
        students.get(position).setHwCounter(homeworks);
        webService.editEntity(position, name, homeworks);
        notifyItemChanged(position);
    }


    @ViewType
    @Override
    public int getItemViewType(final int position) {
        if (position < students.size()) {
            return ViewType.STUDENT;
        } else {
            return ViewType.LOADING;
        }
    }

    @Override
    public int getItemCount() {
        if (isShowLastViewAsLoading) {
            return students.size() + 1;
        } else {
            return students.size();
        }
    }

    public void setShowLastViewAsLoading(final boolean isShow) {
        if (isShow != isShowLastViewAsLoading) {
            isShowLastViewAsLoading = isShow;
            notifyDataSetChanged();
        }
    }

    public void addItems(final List<Student> result) {
        students.addAll(result);
        notifyDataSetChanged();
    }

    public void addStudent(final Student student) {
        webService.addEntity(student);
        students.add(student);
        notifyDataSetChanged();
    }

    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(students, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(students, i, i - 1);
            }
        }

        notifyItemMoved(fromPosition, toPosition);
    }

    public void deleteByIndex(int i) {
        webService.removeEntity((long) i);
        students.remove(i);
        notifyItemRemoved(i);
    }

    public void onItemDismiss(int adapterPosition) {
        deleteByIndex(adapterPosition);
    }

    @IntDef({ViewType.STUDENT, ViewType.LOADING})
    @Retention(RetentionPolicy.SOURCE)
    @interface ViewType {

        int STUDENT = 0;
        int LOADING = 1;
    }
}
