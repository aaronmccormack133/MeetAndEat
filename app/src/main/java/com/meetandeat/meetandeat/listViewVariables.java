package com.meetandeat.meetandeat;

/**
 * Created by Aaron on 09/04/2017.
 */

public class listViewVariables {
    private String testName;
    private String testRest;

    public listViewVariables(String testName, String testRest){
        super();
        this.testName = testName;
        this.testRest = testRest;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestRest() {
        return testRest;
    }

    public void setTestRest(String testRest) {
        this.testRest = testRest;
    }
}
