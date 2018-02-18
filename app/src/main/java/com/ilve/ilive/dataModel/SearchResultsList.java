package com.ilve.ilive.dataModel;

import java.util.ArrayList;

/**
 * Created by ashrafiqubal on 17/02/18.
 */

public class SearchResultsList {
    public ArrayList<SearchResults> getSearchResultsArrayList() {
        return searchResultsArrayList;
    }

    public void setSearchResultsArrayList(ArrayList<SearchResults> searchResultsArrayList) {
        this.searchResultsArrayList = searchResultsArrayList;
    }

    ArrayList<SearchResults> searchResultsArrayList = new ArrayList<>();
}
