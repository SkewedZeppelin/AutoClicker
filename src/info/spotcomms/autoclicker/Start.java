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
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        GUI g = new GUI();
    }

}
