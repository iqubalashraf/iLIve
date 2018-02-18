package com.ilve.ilive;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ilve.ilive.activities.UserInputs;
import com.ilve.ilive.utiles.Constants;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    AppCompatActivity activity;
    TextView user_name, user_age, problems_symptoms, physical_examinations, diagnosis, tests, date;
    RelativeLayout main_layout, options_view;
    TextView button_preview, button_send;

    @Override
    public void onClick(View view) {
        Intent userInputsIntent = new Intent(activity, UserInputs.class);;

        switch (view.getId()){
            case R.id.problems_symptoms:
                userInputsIntent.putExtra(Constants.KEY_USER_POSITION, Constants.PROBLEMS_POSITIONS);
                startActivity(userInputsIntent);
                break;
            case R.id.physical_examinations:
                userInputsIntent.putExtra(Constants.KEY_USER_POSITION, Constants.PHYSICAL_EXAMINATION_POSITIONS);
                startActivity(userInputsIntent);
                break;
            case R.id.diagnosis:
                userInputsIntent.putExtra(Constants.KEY_USER_POSITION, Constants.DIAGNOSIS_POSITIONS);
                startActivity(userInputsIntent);
                break;
            case R.id.tests:
                userInputsIntent.putExtra(Constants.KEY_USER_POSITION, Constants.TESTS_POSITIONS);
                startActivity(userInputsIntent);
                break;
            case R.id.button_preview:
                break;
            case R.id.button_send:
                break;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeView();
        initializeOnClickListener();
    }

    @SuppressLint("SimpleDateFormat")
    private void initializeView() {
        activity = this;
        user_age = findViewById(R.id.user_age);
        problems_symptoms = findViewById(R.id.problems_symptoms);
        physical_examinations = findViewById(R.id.physical_examinations);
        diagnosis = findViewById(R.id.diagnosis);
        tests = findViewById(R.id.tests);
        main_layout = findViewById(R.id.main_layout);
        options_view = findViewById(R.id.options_view);
        button_preview = findViewById(R.id.button_preview);
        button_send = findViewById(R.id.button_send);
        user_name = findViewById(R.id.user_name);
        date = findViewById(R.id.date);
        date.setText(new SimpleDateFormat("dd MMM, yyyy").format(new Date()));
    }

    private void initializeOnClickListener() {
        problems_symptoms.setOnClickListener(this);
        physical_examinations.setOnClickListener(this);
        diagnosis.setOnClickListener(this);
        tests.setOnClickListener(this);
        button_preview.setOnClickListener(this);
        button_send.setOnClickListener(this);
    }
}
