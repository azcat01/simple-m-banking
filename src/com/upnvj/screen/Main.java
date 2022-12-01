package com.upnvj.screen;

import javax.swing.UIManager;
import com.formdev.flatlaf.intellijthemes.FlatMonokaiProIJTheme;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatMonokaiProIJTheme());
        } catch (Exception e) {
            e.printStackTrace();
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Screen_SplashScreen().setVisible(true);
            }
        });
    }
}