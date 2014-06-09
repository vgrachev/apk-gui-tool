package com.vgrachev.android.agt.object;

/**
 * Created by vgrachev on 09/06/14.
 */
public class Application {

    public String label;
    public Package pkg;

    public Application() {

    }

    public Application(String label, Package pkg) {
        this.label = label;
        this.pkg = pkg;
    }
}
