package com.vgrachev.android.agt.parser;

import com.vgrachev.android.agt.object.Application;
import com.vgrachev.android.agt.object.Package;
import com.vgrachev.android.agt.utils.TextUtils;

/**
 * Created by vgrachev on 09/06/14.
 */
public class AaptDumpBadging {

    public static final String TAG_PACKAGE = "package:";
    public static final String TAG_APPLICATION = "application:";

    public static final String TAG_PACKAGE_NAME = "name=";
    public static final String TAG_PACKAGE_VERSION_CODE = "versionCode=";
    public static final String TAG_PACKAGE_VERSION_NAME = "versionName=";

    public static final String TAG_APPLICATION_LABEL = "label=";

    public Application parse(String input) {
        Application apk = null;
        Package pkg = null;

        for (String line : input.split(TextUtils.NEWLINE)) {
            if (line.startsWith(TAG_PACKAGE)) {
                pkg = parsePackageLine(line);
            }
            else if (line.startsWith(TAG_APPLICATION)) {
                apk = parseApplicationLine(line);
            }
        }

        if (apk != null && pkg != null) {
            apk.pkg = pkg;
        }
        return apk;
    }

    private Package parsePackageLine(String line) {
        Package pkg = new Package();
        for (String chunk : line.split(" ")) {
            if (chunk.startsWith(TAG_PACKAGE_NAME)) {
                pkg.name = getValue(chunk);
            }
            else if (chunk.startsWith(TAG_PACKAGE_VERSION_CODE)) {
                pkg.versionCode = getValue(chunk);
            }
            else if (chunk.startsWith(TAG_PACKAGE_VERSION_NAME)) {
                pkg.versionName = getValue(chunk);
            }
        }
        return pkg;
    }

    private Application parseApplicationLine(String line) {
        Application apk = new Application();
        for (String chunk : line.split(" ")) {
            if (chunk.startsWith(TAG_APPLICATION_LABEL)) {
                apk.label = getValue(chunk);
            }
        }
        return apk;
    }

    private String getValue(String chunk) {
        return chunk.split("'")[1];
    }
}
