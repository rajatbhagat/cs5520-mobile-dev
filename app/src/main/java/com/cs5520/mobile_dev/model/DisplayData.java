package com.cs5520.mobile_dev.model;

public class DisplayData {

    private static String DEFAULT_STRING = "Pressed: ";
    private static String INITIAL_STATE = "-";
    private String pressedButtonData;

    public DisplayData(String initialState) {
        if (initialState == null) {
            initialState = INITIAL_STATE;
        }
        this.pressedButtonData = DEFAULT_STRING + initialState;
    }

    public String getPressedButtonData() {
        return pressedButtonData;
    }

    public void setPressedButtonData(String pressedButtonData) {
        this.pressedButtonData = DEFAULT_STRING + pressedButtonData;
    }

    public void resetData() {
        this.setPressedButtonData(INITIAL_STATE);
    }
}
