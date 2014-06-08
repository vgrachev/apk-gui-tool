package com.vgrachev.android.agt.wrapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vgrachev on 08/06/14.
 */
public class AdbWrapper {

    private String executable;

    public static final String CMD_DEVICES = "devices";
    public static final String CMD_INSTALL = "install";

    public static final String DEVICES_FIRST_LINE = "List of devices attached";
    public static final String DEVICES_DEVICE = "device";

    public static final String HELP_FIRST_LINE = "Android Debug Bridge version";

    public static final String INSTALL_CANT_FIND = "can't find";
    public static final String INSTALL_SUCCESS = "Success";
    public static final String INSTALL_FAILURE = "Failure";



    public static final String NEWLINE = "\n";
    public static final String TAB = "\t";

    public AdbWrapper() {
    }

    public void setExecutable(String executable) throws WrapperException {
        this.executable = executable;
        check();
    }

    public void check() throws WrapperException {
        executeCommand(executable + " " + CMD_DEVICES);
    }

    public List<String> getDevices() throws WrapperException {
        List<String> result = new ArrayList<String>();

        String output = executeCommand(executable + " " + CMD_DEVICES);

        String[] lines = output.split(NEWLINE);

        for (String line : lines) {
            if (line.startsWith(DEVICES_FIRST_LINE)) {
                continue;
            }
            if (line.contains(DEVICES_DEVICE)) {
                result.add(line.substring(0, line.indexOf(TAB)));
            }
        }

        return result;
    }

    public int installApk(String apk) throws WrapperException {
        List<String> devices = getDevices();

        int count = 0;

        for (String device : devices) {
            String cmd = executable + " -s " + device + " " + CMD_INSTALL + " " + apk;
            String output = executeCommand(cmd);
            System.out.println(output);
            if (output.startsWith(INSTALL_CANT_FIND)) {
                throw new WrapperException("Install Error. " + output);
            }
            String[] lines = output.split(NEWLINE);
            if (lines[lines.length - 1].startsWith(INSTALL_FAILURE)) {
                throw new WrapperException("Install Error. device: " + device + " Reason: " + output);
            }
            else {
                count++;
            }
        }

        return count;
    }

    private String executeCommand(String command) throws WrapperException {

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

        if (output.toString().startsWith(HELP_FIRST_LINE)) {
            throw new WrapperException("adb error. Bad command: " + command);
        }

        return output.toString();

    }
}
