package com.vgrachev.android.agt.wrapper;

import com.vgrachev.android.agt.object.Progress;

import javax.swing.SwingWorker;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vgrachev on 08/06/14.
 */
public class AdbWrapper extends BaseWrapper {

    public static final String PROGRAM_NAME = "adb";

    public static final String CMD_DEVICES = "devices";
    public static final String CMD_INSTALL = "install -r";

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

    public void check() throws WrapperException {
        executeCommand(executable + " " + CMD_DEVICES);
    }

    @Override
    public String getProgramName() {
        return PROGRAM_NAME;
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

    public void installApk(final String apk, final WrapperListener listener) {
        SwingWorker<String, Progress> worker = new SwingWorker<String, Progress>() {
            @Override
            protected String doInBackground() throws Exception {
                List<String> devices = getDevices();

                int success = 0;
                int count = 0;

                for (String device : devices) {
                    try {
                        String cmd = executable + " -s " + device + " " + CMD_INSTALL + " " + apk;
                        publish(new Progress(count, devices.size(), "Installing: " + cmd));
                        String output = executeCommand(cmd);
                        publish(new Progress(count, devices.size(), output));
                        if (output.startsWith(INSTALL_CANT_FIND)) {
//                        throw new WrapperException("Install Error. " + output);
                        }
                        String[] lines = output.split(NEWLINE);
                        if (lines[lines.length - 1].startsWith(INSTALL_FAILURE)) {
//                        throw new WrapperException("Install Error. device: " + device + " Reason: " + output);
                        } else {
                            success++;
                        }
                    } catch (WrapperException e) {
                        publish(new Progress(count, devices.size(), e.getLocalizedMessage()));
                        e.printStackTrace();
                    }
                    count++;
                    publish(new Progress(count, devices.size(), null));
                }

                publish(new Progress(count, devices.size(), "Apk has been installed on " + success + " devices"));

                return null;
            }

            @Override
            protected void process(List<Progress> chunks) {
                if (listener != null) {
                    for (Progress chunk : chunks) {
                        listener.onProgress(chunk);
                    }
                }
            }

            @Override
            protected void done() {
                if (listener != null) {
                    listener.onDone();
                }
            }
        };

        worker.execute();
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
}
