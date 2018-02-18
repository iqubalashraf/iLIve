package com.ilve.ilive.interfaces;

import com.ilve.ilive.dataModel.SearchResults;

import java.util.ArrayList;

/**
 * Created by ashrafiqubal on 17/02/18.
 */

public interface NetworkResponse {
    void onNetworkResponse(int requestCode, String response);
    void onNewSelection(ArrayList<SearchResults> selectedResults);
}
