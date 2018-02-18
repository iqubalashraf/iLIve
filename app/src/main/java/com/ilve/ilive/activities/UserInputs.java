package com.ilve.ilive.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ilve.ilive.R;
import com.ilve.ilive.adapters.SearchResultsAdapters;
import com.ilve.ilive.adapters.SelectedResultsAdapters;
import com.ilve.ilive.dataModel.SearchResults;
import com.ilve.ilive.interfaces.NetworkResponse;
import com.ilve.ilive.utiles.AppStatus;
import com.ilve.ilive.utiles.Constants;
import com.ilve.ilive.utiles.GenralUtiles;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by ashrafiqubal on 17/02/18.
 */

public class UserInputs extends AppCompatActivity implements View.OnClickListener, NetworkResponse {
    static Handler handler;
    final String TAG = UserInputs.class.getSimpleName();
    AppCompatActivity activity;
    EditText user_input;
    TextView button_submit_next, title;

    String problems, physicals_examination, diagnosis;

    View layout_diagnositc_tests, preview_layout;

    EditText search_input_box;

    RecyclerView search_result, selected_results;
    NetworkResponse networkResponse;
    ArrayList<SearchResults> searchResultsArrayList = new ArrayList<>();
    SelectedResultsAdapters selectedResultsAdapters;
    Gson gson;
    int lastRequestCode = 0;
    ImageButton back_button, buttom_home;
    TextView symptoms_detailes, physical_examinations_detailes, diagnosis_detailes, tests_detailes;
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (search_input_box.getText().toString().trim().length() > 0) {
                String url = Constants.BASE_URL + Constants.SEARCH_URL + search_input_box.getText().toString();
                lastRequestCode++;
                if (AppStatus.getInstance(activity).isOnline())
                    GenralUtiles.performNetwrokRequest(activity, url, networkResponse, lastRequestCode);
                else
                    GenralUtiles.showMessage(getString(R.string.no_internet));
            }
        }
    };
    ArrayList<SearchResults> selectedResults = new ArrayList<>();
    SearchResultsAdapters searchResultsAdapters;
    LinearLayoutManager linearLayoutManager;
    private int currentPosition = 0;

    @Override
    public void onNewSelection(ArrayList<SearchResults> selectedResults) {
        this.selectedResults = selectedResults;
        selectedResultsAdapters.setSelectedResults(selectedResults);
        selectedResultsAdapters.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_inputs);
        Intent intent = getIntent();
        currentPosition = intent.getIntExtra(Constants.KEY_USER_POSITION, 0);
        initializeView();
        initializeOnClickListener();
        showCorrectView(currentPosition);

    }

    private void initializeView() {
        handler = new Handler();
        activity = this;
        networkResponse = this;
        linearLayoutManager = new LinearLayoutManager(activity);
        gson = new Gson();
        user_input = findViewById(R.id.user_input);
        button_submit_next = findViewById(R.id.button_submit_next);
        layout_diagnositc_tests = findViewById(R.id.layout_diagnositc_tests);
        search_input_box = findViewById(R.id.search_input_box);
        search_result = layout_diagnositc_tests.findViewById(R.id.search_result);
        searchResultsAdapters = new SearchResultsAdapters(activity, networkResponse);
        search_result.setLayoutManager(linearLayoutManager);
        search_result.setAdapter(searchResultsAdapters);
        selected_results = findViewById(R.id.selected_results);
        selectedResultsAdapters = new SelectedResultsAdapters(activity);
        selected_results.setAdapter(selectedResultsAdapters);
        selected_results.setLayoutManager(new LinearLayoutManager(activity));
        title = findViewById(R.id.title);
        back_button = findViewById(R.id.back_button);
        buttom_home = findViewById(R.id.buttom_home);
        preview_layout = findViewById(R.id.preview_layout);
        symptoms_detailes = findViewById(R.id.symptoms_detailes);
        physical_examinations_detailes = findViewById(R.id.physical_examinations_detailes);
        diagnosis_detailes = findViewById(R.id.diagnosis_detailes);
        tests_detailes = findViewById(R.id.tests_detailes);
        showOtherViews();
    }

    private void initializeOnClickListener() {
        button_submit_next.setOnClickListener(this);
        back_button.setOnClickListener(this);
        buttom_home.setOnClickListener(this);
        search_input_box.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                revokeCallBacks();
                callHandler();
            }
        });
    }

    private void showOtherViews() {
        layout_diagnositc_tests.setVisibility(View.GONE);
        user_input.setVisibility(View.VISIBLE);
        preview_layout.setVisibility(View.GONE);
    }

    private void showTestView() {
        layout_diagnositc_tests.setVisibility(View.VISIBLE);
        preview_layout.setVisibility(View.GONE);
        user_input.setVisibility(View.GONE);
    }

    private void showPreviewLayout() {
        layout_diagnositc_tests.setVisibility(View.GONE);
        preview_layout.setVisibility(View.VISIBLE);
        user_input.setVisibility(View.GONE);
        diagnosis_detailes.setText(diagnosis);
        physical_examinations_detailes.setText(physicals_examination);
        String testDetailes = "";
        for (int i = 0; i < selectedResults.size(); i++) {
            if (i == 0) {
                testDetailes = selectedResults.get(i).getTestName();
            } else {
                testDetailes = testDetailes + "\n" + selectedResults.get(i).getTestName();

            }
        }
        tests_detailes.setText(testDetailes);
        symptoms_detailes.setText(problems);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_submit_next:
                if (currentPosition == Constants.PROBLEMS_POSITIONS) {
                    if (user_input.getText().toString().trim().length() > 0) {
                        problems = user_input.getText().toString();
                        currentPosition++;
                        showCorrectView(currentPosition);
                    } else {
                        GenralUtiles.showMessage(getString(R.string.hint_text_problems));
                    }
                } else if (currentPosition == Constants.PHYSICAL_EXAMINATION_POSITIONS) {
                    if (user_input.getText().toString().trim().length() > 0) {
                        physicals_examination = user_input.getText().toString();
                        currentPosition++;
                        showCorrectView(currentPosition);
                    } else {
                        GenralUtiles.showMessage(getString(R.string.hint_text_reports));
                    }
                } else if (currentPosition == Constants.DIAGNOSIS_POSITIONS) {
                    if (user_input.getText().toString().trim().length() > 0) {
                        diagnosis = user_input.getText().toString();
                        currentPosition++;
                        showCorrectView(currentPosition);

                    } else {
                        GenralUtiles.showMessage(getString(R.string.hint_text_diagnosis_reports));
                    }
                } else if (currentPosition == Constants.TESTS_POSITIONS) {
                    currentPosition++;
                    showCorrectView(currentPosition);
                }
                break;

            case R.id.back_button:
                if (currentPosition == Constants.PROBLEMS_POSITIONS) {
                    showAleartDialog();

                } else {
                    currentPosition--;
                    showCorrectView(currentPosition);
                }
                break;

            case R.id.buttom_home:
                showAleartDialog();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        onClick(findViewById(R.id.back_button));
    }

    @Override
    public void onNetworkResponse(int requestCode, String response) {
        Log.d(TAG, "Response: " + response);
        if (requestCode == lastRequestCode) {
            Log.d(TAG, "Request code match " + requestCode);
            searchResultsArrayList.clear();
            try {
                JSONArray jsonArray = new JSONArray(response);
                if (jsonArray.length() > 0) {
                    int len = jsonArray.length() > 5 ? 5 : jsonArray.length();
                    for (int i = 0; i < len; i++) {
                        searchResultsArrayList.add(gson.fromJson(jsonArray.getString(i), SearchResults.class));
                    }
                }
                searchResultsAdapters.setSearchResultsArrayList(searchResultsArrayList);
                searchResultsAdapters.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.d(TAG, "Request code mis-match " + requestCode);
        }

    }

    private void callHandler() {
        handler.postDelayed(runnable, 200);
    }

    private void revokeCallBacks() {
        handler.removeCallbacks(runnable);
    }

    private void showCorrectView(int currentPosition) {
        switch (currentPosition) {
            case Constants.PROBLEMS_POSITIONS:
                title.setText(R.string.problems);
                user_input.setHint(getString(R.string.hint_text_problems));
                user_input.setText(problems);
                showOtherViews();
                break;
            case Constants.PHYSICAL_EXAMINATION_POSITIONS:
                title.setText(R.string.physical);
                user_input.setHint(getString(R.string.hint_text_reports));
                user_input.setText(physicals_examination);
                showOtherViews();
                break;
            case Constants.DIAGNOSIS_POSITIONS:
                title.setText(R.string.diagnosis);
                user_input.setHint(getString(R.string.hint_text_diagnosis_reports));
                user_input.setText(diagnosis);
                showOtherViews();
                break;
            case Constants.TESTS_POSITIONS:
                title.setText(R.string.diagnosis_test);
                showTestView();
                break;
            case Constants.PREVIEW_POSITION:
                showPreviewLayout();
                break;
        }
    }

    private void showAleartDialog(){
        final AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
        dialog.setTitle("Alert");
        dialog.setMessage("All saved data will be deleted, sure to exit.");
        dialog.setCancelable(true);
        dialog.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                // TODO Auto-generated method stub
            }
        });
        dialog.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        dialog.show();
    }
}
