package com.ilve.ilive.dataModel;

/**
 * Created by ashrafiqubal on 17/02/18.
 */

public class SearchResults {
    int testId = 0;
    int price = 0;
    String testName = "";
    String constituents = "";

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getConstituents() {
        return constituents;
    }

    public void setConstituents(String constituents) {
        this.constituents = constituents;
    }
}
