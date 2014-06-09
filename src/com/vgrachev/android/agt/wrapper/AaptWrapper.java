package com.vgrachev.android.agt.wrapper;

import com.vgrachev.android.agt.object.Application;
import com.vgrachev.android.agt.parser.AaptDumpBadging;

/**
 * Created by vgrachev on 09/06/14.
 */
public class AaptWrapper extends BaseWrapper {

    public static final String PROGRAM_NAME = "aapt";
    public static final String DUMP_BADGING = " dump badging ";

    private static final AaptDumpBadging parser = new AaptDumpBadging();

    @Override
    public void check() throws WrapperException {
        //TODO: implement
    }

    @Override
    public String getProgramName() {
        return PROGRAM_NAME;
    }

    public Application dumpBadging(String apk) throws WrapperException {
        String output = executeCommand(executable + DUMP_BADGING + apk);

        return parser.parse(output);
    }
}
