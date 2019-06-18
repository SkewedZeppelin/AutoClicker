/*
Copyright (c) 2015 Divested Computing Group

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/

package info.spotcomms.autoclicker;

import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created using IntelliJ IDEA
 * User: Tad
 * Date: 6/29/15
 * Time; 5:16 PM
 */
public class GUI extends JFrame implements ActionListener, NativeKeyListener {

    private boolean running = false;
    private Thread autoClicker = null;
    private boolean setting = false;
    private int hotkey = NativeKeyEvent.VC_KP_SUBTRACT;
    private long lastTime = System.currentTimeMillis();

    private JPanel panContent;
    private JPanel panTiming;
    private JTextField txtDelay;
    private JLabel lblDelay;
    private JPanel panClicking;
    private JComboBox drpClickType;
    private JComboBox drpClickAmt;
    private JPanel panRun;
    private JButton btnStart;
    private JButton btnStop;
    private JTextField txtStartDelay;
    private JLabel lblStartDelay;
    private JPanel panHotkey;
    private JButton btnHotkey;

    public GUI() {
        setTitle("AutoClicker");
        setLocation(150, 150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(panContent);
        pack();
        setVisible(true);
        btnHotkey.addActionListener(this);
        btnStart.addActionListener(this);
        btnStop.addActionListener(this);
        drpClickAmt.addItem("Single Click");
        drpClickAmt.addItem("Double Click");
        drpClickAmt.addItem("Triple Click");
        drpClickType.addItem("Left Click " + InputEvent.BUTTON1_MASK);
        drpClickType.addItem("Middle Click " + InputEvent.BUTTON3_MASK);
        drpClickType.addItem("Right Click " + InputEvent.BUTTON2_MASK);
        btnHotkey.setText(NativeKeyEvent.getKeyText(hotkey));
        try {
            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeKeyListener(this);
            //Thanks JNativeHook, there is totally nothing wrong with doing super verbose logging by default :\
            Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
            logger.setLevel(Level.OFF);
            Handler[] handlers = Logger.getLogger("").getHandlers();
            for (Handler handler : handlers) {
                handler.setLevel(Level.OFF);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnHotkey) {
            setting = true;
            btnHotkey.setText("Setting... press any key");
            btnHotkey.setEnabled(false);
        }
        if (e.getSource() == btnStart) {
            startAutoClicker();
        }
        if (e.getSource() == btnStop) {
            stopAutoClicker();
        }
    }

    private void startAutoClicker() {
        btnStart.setEnabled(false);
        btnStop.setEnabled(true);
        running = true;
        autoClicker = autoClicker(Integer.valueOf(txtStartDelay.getText()), Integer.valueOf(txtDelay.getText()), Integer.valueOf(drpClickType.getSelectedItem().toString().split(" ")[2]), (drpClickAmt.getSelectedIndex() + 1));
        autoClicker.start();
    }

    private void stopAutoClicker() {
        btnStart.setEnabled(true);
        btnStop.setEnabled(false);
        running = false;
        autoClicker.stop();
        autoClicker = null;
    }

    private Thread autoClicker(final int startDelay, final int delay, final int clickType, final int clickAmt) {
        return new Thread(() -> {
            try {
                Robot r = new Robot();
                r.delay(startDelay);
                while (true) {
                    if (running) {
                        for (int x = 0; x <= clickAmt; x++) {
                            r.mousePress(clickType);
                            r.delay(10);
                            r.mouseRelease(clickType);
                        }
                        r.delay(delay);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
        if (System.currentTimeMillis() - lastTime > 500) {
            if (setting) {
                hotkey = nativeKeyEvent.getKeyCode();
                btnHotkey.setEnabled(true);
                btnHotkey.setText(NativeKeyEvent.getKeyText(hotkey));
                setting = false;
                System.out.println(hotkey + nativeKeyEvent.getKeyCode());
            } else if (nativeKeyEvent.getKeyCode() == hotkey) {
                if (!running) {
                    startAutoClicker();
                } else {
                    stopAutoClicker();
                }
            }
            lastTime = System.currentTimeMillis();
        }
    }

    @Override public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {

    }

    @Override public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {

    }

    {
        // GUI initializer generated by IntelliJ IDEA GUI Designer
        // >>> IMPORTANT!! <<<
        // DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panContent = new JPanel();
        panContent.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
        panTiming = new JPanel();
        panTiming.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        panContent.add(panTiming, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panTiming.setBorder(BorderFactory.createTitledBorder(null, "Timing Control", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION));
        txtDelay = new JTextField();
        txtDelay.setText("250");
        panTiming.add(txtDelay, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(75, -1), null, 0, false));
        lblDelay = new JLabel();
        lblDelay.setText("Delay (ms)");
        panTiming.add(lblDelay, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtStartDelay = new JTextField();
        txtStartDelay.setText("3000");
        panTiming.add(txtStartDelay, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(75, -1), null, 0, false));
        lblStartDelay = new JLabel();
        lblStartDelay.setText("Start Delay (ms)");
        panTiming.add(lblStartDelay, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panClicking = new JPanel();
        panClicking.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panContent.add(panClicking, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panClicking.setBorder(BorderFactory.createTitledBorder(null, "Clicking Options", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION));
        drpClickType = new JComboBox();
        panClicking.add(drpClickType, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        drpClickAmt = new JComboBox();
        panClicking.add(drpClickAmt, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panRun = new JPanel();
        panRun.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panContent.add(panRun, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panRun.setBorder(BorderFactory.createTitledBorder(null, "Run", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION));
        btnStart = new JButton();
        btnStart.setText("Start");
        panRun.add(btnStart, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnStop = new JButton();
        btnStop.setEnabled(false);
        btnStop.setText("Stop");
        panRun.add(btnStop, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panHotkey = new JPanel();
        panHotkey.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panContent.add(panHotkey, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panHotkey.setBorder(BorderFactory.createTitledBorder(null, "Global Hotkey", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION));
        btnHotkey = new JButton();
        btnHotkey.setText("[KEYBIND]");
        panHotkey.add(btnHotkey, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panContent;
    }
}
