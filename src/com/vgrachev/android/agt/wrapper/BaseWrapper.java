package com.vgrachev.android.agt.wrapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by vgrachev on 09/06/14.
 */
public abstract class BaseWrapper {

    protected volatile String executable;

    public void setExecutable(String executable) throws WrapperException {
        this.executable = executable;
        check();
    }

    public abstract void check() throws WrapperException;

    public abstract String getProgramName();

    protected String executeCommand(String command) throws WrapperException {
        if (executable == null || executable.isEmpty()) {
            throw new WrapperException("adb executable has not been defined");
        }

        StringBuffer output = new StringBuffer();

        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            int result = p.exitValue();

            if (result != 0) {
                throw new WrapperException("adb exit code " + result + ". error while executing command " + command);
            }

            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine())!= null) {
                output.append(line + "\n");
            }

        } catch (Exception e) {
            throw new WrapperException(e.getLocalizedMessage());
        }

        if (output == null) {
            throw new WrapperException("adb: empty output. command: " + command);
        }

        return output.toString();
    }
}
