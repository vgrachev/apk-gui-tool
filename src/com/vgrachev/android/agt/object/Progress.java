package com.vgrachev.android.agt.object;

/**
 * Created by vgrachev on 09/06/14.
 */
public class Progress {

    private int value;
    private int maximum;
    private String msg;

    public Progress(int value, int maximum, String message) {
        this.value = value;
        this.maximum = maximum;
        this.msg = message;
    }

    public int getValue() {
        return value;
    }

    public int getMaximum() {
        return maximum;
    }

    public String getMsg() {
        return msg;
    }
}
