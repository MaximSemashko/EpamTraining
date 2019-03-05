package com.example.epamtraining;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FirstFragment extends Fragment {

    private static final String VALUES_TEXT_VIEW_KEY = "valuesTextView";

    private TextView valuesTextView;
    private Button changeValueButton;
    private EditText valueEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        valuesTextView = view.findViewById(R.id.values_text_view);
        changeValueButton = view.findViewById(R.id.change_button);
        valueEditText = view.findViewById(R.id.value_edit_text);

        changeValueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = String.valueOf(valueEditText.getText());
                valuesTextView.setText(value);
                valueEditText.setText(null);
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(VALUES_TEXT_VIEW_KEY, valuesTextView.getText().toString());
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if(savedInstanceState != null){
            valuesTextView.setText(savedInstanceState.getString(VALUES_TEXT_VIEW_KEY));
        }
    }
}
