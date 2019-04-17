package com.example.epamtraining;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ChangeStudentDialogFragment extends DialogFragment {

    public interface ChangeStudentDialogListener {
        void onFinishEditDialog(String name, int homeworks);
    }

    private EditText studentNameText;
    private EditText studentHomeworksText;
    private TextView changeStudentView;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_student, null);

        studentNameText = dialogView.findViewById(R.id.student_name_edit_text);
        studentHomeworksText = dialogView.findViewById(R.id.student_homeworks_edit_text);
        changeStudentView = dialogView.findViewById(R.id.alertTitle);
        changeStudentView.setText(R.string.change_student_title);

        builder.setView(dialogView)
                .setPositiveButton(R.string.change_student, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String name = studentNameText.getText().toString();
                        int homeworks = Integer.parseInt(studentHomeworksText.getText().toString());
                        ChangeStudentDialogListener listener = (ChangeStudentDialogListener) getActivity();

                        listener.onFinishEditDialog(name, homeworks);
                    }
                })

                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
        return dialog;
    }
}
