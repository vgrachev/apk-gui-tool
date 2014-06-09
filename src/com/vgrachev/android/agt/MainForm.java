package com.vgrachev.android.agt;

import com.vgrachev.android.agt.wrapper.AdbWrapper;
import com.vgrachev.android.agt.wrapper.WrapperException;
import com.vgrachev.android.agt.wrapper.WrapperListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by vgrachev on 08/06/14.
 */
public class MainForm {

    static private final String newline = "\n";

    private JTextArea logArea;
    private JTextField adbText;
    private JButton adbButton;
    private JTextField apkText;
    private JButton apkButton;
    private JButton installButton;
    private JPanel panel;
    private JButton devicesButton;
    private JButton button1;
    private JButton button2;

    AdbWrapper adb = new AdbWrapper();

    public MainForm() {

        //TODO: Temporary
        adbText.setText("/Developer/SDKs/android-sdk/platform-tools/adb");
        try {
            adb.setExecutable("/Developer/SDKs/android-sdk/platform-tools/adb");
        } catch (WrapperException e) {
            adbText.setText("");
        }

        adbButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                JFileChooser openFile = new JFileChooser();
                int returnVal = openFile.showOpenDialog(panel);
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    logArea.append("You chose to open this file: " +
                            openFile.getSelectedFile().getName() + newline);
                    adbText.setText(openFile.getSelectedFile().getAbsolutePath());
                    try {
                        adb.setExecutable(adbText.getText());
                    } catch (WrapperException e) {
                        logArea.append(e.getLocalizedMessage() + newline);
                        e.printStackTrace();
                    }
                }
            }
        });

        apkButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                JFileChooser openFile = new JFileChooser();
                int returnVal = openFile.showOpenDialog(panel);
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    logArea.append("You chose to open this file: " +
                            openFile.getSelectedFile().getName() + newline);
                    apkText.setText(openFile.getSelectedFile().getAbsolutePath());
                }
            }
        });

        devicesButton.addActionListener(devicesAction);
        installButton.addActionListener(installAction);
    }

    ActionListener devicesAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            try {
                for (String device : adb.getDevices()) {
                    logArea.append(device + newline);
                    logArea.append("-----" + newline);
                }

            } catch (WrapperException e1) {
                logArea.append(e1.getLocalizedMessage() + newline);
            }
        }
    };

    ActionListener installAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {


            installButton.setEnabled(false);

            adb.installApk(apkText.getText(), new WrapperListener() {
                @Override
                public void onProgress(String chunk) {
                    logArea.append(chunk + newline);
                }

                @Override
                public void onError() {

                }

                @Override
                public void onDone() {
                    installButton.setEnabled(true);
                }
            });

//                logArea.append("Apk " + apkText.getText() + " has been installed on " + count + " devices" + newline);


        }
    };

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainForm");
        frame.setContentPane(new MainForm().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

        placeWindow(frame, 400, 300);
        frame.setVisible(true);
    }

    public static void placeWindow(Window window, int width, int height) {

        // New size for this window
        Dimension windowSize = new Dimension(width, height);
        window.setSize(windowSize);

        // Place in the 'dialog' position, centered aligned 1/3 from top
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        Point windowLocation = new Point(0, 0);
        windowLocation.x = (screenSize.width - windowSize.width) / 2;
        windowLocation.y = (screenSize.height / 3) - (windowSize.height / 2);

        // Set final size and location
        window.setLocation(windowLocation);
    }
}
