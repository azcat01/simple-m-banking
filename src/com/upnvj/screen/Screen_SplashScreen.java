/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.upnvj.screen;

/**
 *
 * @author DAPIT
 */
public class Screen_SplashScreen extends javax.swing.JFrame {

    /**
     * Creates new form Screen_SplashScreen
     */
    public Screen_SplashScreen() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_backGround = new javax.swing.JPanel();
        label_title = new javax.swing.JLabel();
        button_start = new javax.swing.JButton();
        text_kelompok = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Simple M-Banking");
        setMaximumSize(new java.awt.Dimension(840, 480));
        setMinimumSize(new java.awt.Dimension(840, 480));
        setResizable(false);

        panel_backGround.setBackground(new java.awt.Color(109, 152, 134));

        label_title.setFont(new java.awt.Font("Poppins", 1, 48)); // NOI18N
        label_title.setForeground(new java.awt.Color(242, 231, 213));
        label_title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_title.setText("Simple M-Banking");
        label_title.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        button_start.setBackground(new java.awt.Color(109, 152, 134));
        button_start.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        button_start.setForeground(new java.awt.Color(247, 247, 247));
        button_start.setText("Click to get started");
        button_start.setBorder(null);
        button_start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_startActionPerformed(evt);
            }
        });

        text_kelompok.setEditable(false);
        text_kelompok.setBackground(new java.awt.Color(109, 152, 134));
        text_kelompok.setColumns(20);
        text_kelompok.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        text_kelompok.setForeground(new java.awt.Color(247, 247, 247));
        text_kelompok.setRows(5);
        text_kelompok.setText("Kelompok 7 :\n\n1. David Siddi\t\t\t(2110511061)\n2. Muhammad Daffa Raihan Nurrizqi\t(2110511062)\n3. Muhammad Irsyad Abdurrahman\t(2110511065)");
        text_kelompok.setBorder(null);

        javax.swing.GroupLayout panel_backGroundLayout = new javax.swing.GroupLayout(panel_backGround);
        panel_backGround.setLayout(panel_backGroundLayout);
        panel_backGroundLayout.setHorizontalGroup(
            panel_backGroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_backGroundLayout.createSequentialGroup()
                .addContainerGap(200, Short.MAX_VALUE)
                .addGroup(panel_backGroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(label_title, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(text_kelompok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(button_start))
                .addGap(170, 170, 170))
        );
        panel_backGroundLayout.setVerticalGroup(
            panel_backGroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_backGroundLayout.createSequentialGroup()
                .addContainerGap(126, Short.MAX_VALUE)
                .addComponent(label_title)
                .addGap(18, 18, 18)
                .addComponent(text_kelompok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(76, 76, 76)
                .addComponent(button_start)
                .addGap(70, 70, 70))
        );

        getContentPane().add(panel_backGround, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void button_startActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_startActionPerformed
        // TODO add your handling code here:
        Screen_SignIn signin = new Screen_SignIn();
        signin.setVisible(true);
        dispose();
    }//GEN-LAST:event_button_startActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Screen_SplashScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Screen_SplashScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Screen_SplashScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Screen_SplashScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Screen_SplashScreen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton button_start;
    private javax.swing.JLabel label_title;
    private javax.swing.JPanel panel_backGround;
    private javax.swing.JTextArea text_kelompok;
    // End of variables declaration//GEN-END:variables
}
