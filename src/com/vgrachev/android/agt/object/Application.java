package com.vgrachev.android.agt.object;

import com.vgrachev.android.agt.utils.TextUtils;

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Application: ").append(TextUtils.NEWLINE)
                .append("\tlabel: ").append(label).append(TextUtils.NEWLINE)
                .append("\tpackage: " ).append(pkg.name).append(TextUtils.NEWLINE)
                .append("\tversionCode: ").append(pkg.versionCode).append(TextUtils.NEWLINE)
                .append("\tversionName: ").append(pkg.versionName).append(TextUtils.NEWLINE);
        return sb.toString();
    }
}
