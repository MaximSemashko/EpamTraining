package com.example.epamtraining;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    public interface OnItemClickListener {
        void onItemClicked(View v);
    }

    private boolean isShowLastViewAsLoading = false;
    private final LayoutInflater inflater;
    private final List<Student> students = new ArrayList<>();
    private final IWebService<Student> webService = new StudentsWebService();
    private int adaptersPosition;
    private OnItemClickListener onItemClickListener;

    public StudentsAdapter(final Context context, final OnItemClickListener listener) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        onItemClickListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent,
                                                      @ViewType final int viewType) {
        if (viewType == ViewType.STUDENT) {
            BaseViewHolder<StudentItemView> studentItemViewBaseViewHolder = new BaseViewHolder<>(new StudentItemView(parent.getContext()));
            studentItemViewBaseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adaptersPosition = studentItemViewBaseViewHolder.getAdapterPosition();
                    onItemClickListener.onItemClicked(v);
                }
            });

            return studentItemViewBaseViewHolder;
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
        }
    }

    public void changeStudent(String name, int homeworks) {
        students.get(adaptersPosition).setName(name);
        students.get(adaptersPosition).setHwCounter(homeworks);
        webService.editEntity(adaptersPosition, name, homeworks);
        notifyItemChanged(adaptersPosition);
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
