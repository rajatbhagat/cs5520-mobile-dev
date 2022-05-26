package com.cs5520.mobile_dev.model;

public class DisplayData {

    private static String INITIAL_STATE = "-";
    private String pressedButtonData;

    public DisplayData(String initialState) {
        if (initialState == null) {
            initialState = INITIAL_STATE;
        }
        this.pressedButtonData = initialState;
    }

    public String getPressedButtonData() {
        return pressedButtonData;
    }

    public void setPressedButtonData(String pressedButtonData) {
        this.pressedButtonData = pressedButtonData;
    }

    public void resetData() {
        this.setPressedButtonData(INITIAL_STATE);
    }
}
