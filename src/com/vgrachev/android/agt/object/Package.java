package com.vgrachev.android.agt.object;

/**
 * Created by vgrachev on 09/06/14.
 */
public class Package {

    public String name;
    public String versionCode;
    public String versionName;

    public Package() {

    }

    public Package(String name, String versionCode, String versionName) {
        this.name = name;
        this.versionCode = versionCode;
        this.versionName = versionName;
    }

}
