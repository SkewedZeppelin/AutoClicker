/*
 * Copyright (c) 2015 Spot Communications, Inc.
 */

package info.spotcomms.autoclicker;

import javax.swing.*;

/**
 * Created using IntelliJ IDEA
 * User: Tad
 * Date: 6/29/15
 * Time; 5:16 PM
 */
public class Start {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        GUI g = new GUI();
    }

}
