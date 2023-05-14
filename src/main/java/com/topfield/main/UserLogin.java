
package com.topfield.main;

import com.topfield.controller.RestController;
import com.topfield.user.UserInfo;
import com.topfield.dao.UserDao;
import com.topfield.daoImpl.UserDaoImpl;
import static com.topfield.main.InternalFrameDemo.createAndShowGUI;
import com.topfield.modal.TclProjects;
import com.topfield.modal.Users;
import com.topfield.singleton.UserLoginValidations;
import com.topfield.user.HybridData;
import com.topfield.user.UserSettingsData;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicScrollBarUI;
import org.json.JSONObject;

public class UserLogin extends javax.swing.JFrame {
    
   

    public UserLogin() {        
        setUndecorated(true);
        setPreferredSize(new Dimension(889,500));
        ImageIcon img = new ImageIcon(getClass().getResource("/coachspecs/images/icons/TCL_Logo_32x32.png"));
        setIconImage(img.getImage()); 

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        setLocation((width/6), (height/2) - 250);
        
        
        //setShape(new RoundRectangle2D.Double(0, 0, 447, 300, 50, 50));
        initComponents();
        

        
        lblError.setVisible(false);
                
        inpUsername.addFocusListener(placeholder("Username"));
        inpPassword.addFocusListener(placeholder("Password"));
        
        inpPassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e){
                if (e.getKeyCode()==KeyEvent.VK_ENTER) {
                    btnLogin.doClick();
                }
            }
        });
        
        jScrollPane1.getVerticalScrollBar().setPreferredSize(new Dimension(15, Integer.MAX_VALUE));
        jScrollPane1.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override 
            protected void configureScrollBarColors(){
                this.thumbColor = new java.awt.Color(84,153,199);
            }
        });
        
        
    }
    
    private FocusListener placeholder(String text){
        FocusListener focusListener = new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                JTextField jTextField = (JTextField) e.getSource();
                if(jTextField.getText().equals(text)){
                    jTextField.setText("");
                    jTextField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                JTextField jTextField = (JTextField) e.getSource();
                if(jTextField.getText().isEmpty()){
                    jTextField.setForeground(Color.GRAY);
                    jTextField.setText(text);
                }
            }
        };
        return focusListener;
    }    
    
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnLogin = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        lblError = new javax.swing.JLabel();
        inpUsername = new javax.swing.JTextField();
        inpPassword = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        label2 = new java.awt.Label();
        label3 = new java.awt.Label();
        label5 = new java.awt.Label();
        label7 = new java.awt.Label();
        label8 = new java.awt.Label();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(77, 77, 77));
        setResizable(false);
        setSize(new java.awt.Dimension(447, 300));

        jPanel1.setBackground(new java.awt.Color(63, 130, 190));
        jPanel1.setPreferredSize(new java.awt.Dimension(889, 500));
        // create a seperator
        JSeparator s = new JSeparator();
        s.setBackground(Color.red);
        // set layout as vertical
        s.setOrientation(SwingConstants.VERTICAL);

        btnLogin.setBackground(new java.awt.Color(84, 153, 199));
        btnLogin.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnLogin.setForeground(new java.awt.Color(255, 255, 255));
        btnLogin.setText("Login");
        btnLogin.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        btnLogin.setContentAreaFilled(false);
        btnLogin.setOpaque(true);
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        btnExit.setBackground(new java.awt.Color(84, 153, 199));
        btnExit.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnExit.setForeground(new java.awt.Color(255, 255, 255));
        btnExit.setText("Cancel");
        btnExit.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        btnExit.setContentAreaFilled(false);
        btnExit.setOpaque(true);
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        lblError.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblError.setForeground(new java.awt.Color(102, 0, 0));
        lblError.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblError.setText("Error message goes here");
        lblError.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        inpUsername.setBackground(new java.awt.Color(255, 255, 255));
        inpUsername.setForeground(java.awt.Color.gray);
        inpUsername.setText("Username");
        inpUsername.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 5, 1, 5));
        inpUsername.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        inpPassword.setBackground(new java.awt.Color(255, 255, 255));
        inpPassword.setForeground(java.awt.Color.gray);
        inpPassword.setText("Password");
        inpPassword.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 5, 1, 5));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coachspecs/images/logo.png"))); // NOI18N

        jLabel4.setBackground(new java.awt.Color(66, 131, 223));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coachspecs/images/profile_icon.png"))); // NOI18N
        jLabel4.setOpaque(true);

        jLabel5.setBackground(new java.awt.Color(66, 131, 223));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coachspecs/images/password_icon.png"))); // NOI18N
        jLabel5.setOpaque(true);

        label2.setFont(new java.awt.Font("Haettenschweiler", 0, 36)); // NOI18N
        label2.setForeground(new java.awt.Color(255, 255, 255));
        label2.setText("STEMS");

        label3.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        label3.setForeground(new java.awt.Color(255, 255, 255));
        label3.setText("Password");

        label5.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        label5.setForeground(new java.awt.Color(255, 255, 255));
        label5.setText("Login");

        label7.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        label7.setForeground(new java.awt.Color(255, 255, 255));
        label7.setText("RAILWAY AND TRANSPORTATION ENGINEERING TOOL");

        label8.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        label8.setForeground(new java.awt.Color(255, 255, 255));
        label8.setText("User Name");

        jTextArea1.setEditable(false);
        jTextArea1.setBackground(new java.awt.Color(63, 130, 190));
        jTextArea1.setColumns(20);
        jTextArea1.setForeground(new java.awt.Color(255, 255, 255));
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setText("Transportation engineering or transport engineering is the application of technology and scientific principles to the planning, functional design, operation and management of facilities for any mode of transportation in order to provide for the safe, efficient, rapid, comfortable, convenient, economical, and environmentally compatible movement of people and goods transport.\n\nThe planning aspects of transportation engineering relate to elements of urban planning, and involve technical forecasting decisions and political factors. Technical forecasting of passenger travel usually involves an urban transportation planning model, requiring the estimation of trip generation (number of purposeful trips), trip distribution (destination choice, where the traveler is going), mode choice (mode that is being taken), and route assignment (the streets or routes that are being used). More sophisticated forecasting can include other aspects of traveler decisions, including auto ownership, trip chaining (the decision to link individual trips together in a tour) and the choice of residential or business location (known as land use forecasting). Passenger trips are the focus of transportation engineering because they often represent the peak of demand on any transportation system.\n\nA review of descriptions of the scope of various committees indicates that while facility planning and design continue to be the core of the transportation engineering field, such areas as operations planning, logistics, network analysis, financing, and policy analysis are also important, particularly to those working in highway and urban transportation. The National Council of Examiners for Engineering and Surveying (NCEES) list online the safety protocols, geometric design requirements, and signal timing.");
        jTextArea1.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTextArea1.setFocusable(false);
        jTextArea1.setCaretPosition(0);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel1.setForeground(new java.awt.Color(204, 204, 204));
        jLabel1.setText("<html>@ 2019 Topfield Consultancy Limited. All rights reserved <br> Version 1.0</html>");

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("jLabel2");
        jLabel2.setOpaque(true);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel3)
                        .addGap(112, 112, 112))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 426, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)))
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(label5, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label8, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                                .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(inpUsername)
                            .addComponent(inpPassword)))
                    .addComponent(lblError, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(label5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(inpUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(inpPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnExit)
                    .addComponent(btnLogin))
                .addGap(33, 33, 33)
                .addComponent(lblError, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(label7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(15, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16))
        );

        btnLogin.setOpaque(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 831, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        dispose();
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        String username = inpUsername.getText();
        char[] pass = inpPassword.getPassword();
        String password = new String(pass);
        String massage ="";
       
        
          
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        try {
           massage = UserLoginValidations.doValidation(username, password);
           
          if(!massage.equals("")){
          
          
          
          }else{

                
               /* new Thread() {
                    public void run() {
                        setUserLevelData(username);
                        InternalFrameDemo.mainFrame.setUserMenuTag();
                        InternalFrameDemo.mainFrame.setLeftPanel();
                   }
                }.start();*/
            
                 MessageDigest md5 = MessageDigest.getInstance("MD5");
                 md5.update(StandardCharsets.UTF_8.encode(password));
                 password = String.format("%032x", new BigInteger(1, md5.digest()));
            
            }
        } catch (NoSuchAlgorithmException ex) {
             ex.printStackTrace();
            Logger.getLogger(UserLogin.class.getName()).log(Level.SEVERE, null, ex);
        } catch(NullPointerException exN){
            exN.printStackTrace();
           massage ="Invalid username ...!!";
        }finally{
               
            //btnLogin.setBackground(Color.cyan);
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
              if(!(massage.equals(""))){
                lblError.setVisible(true);
                lblError.setText(massage);
              }else{
                //System.exit(0);
                lblError.setVisible(true);
                //lblError.setText("Please Wait..!!!");
                  dispose();
                javax.swing.SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        InternalFrameDemo.createAndShowGUI();
                        /* Below code Effececy check */
                        //setUserLevelData(username);
                        InternalFrameDemo.mainFrame.setUserMenuTag();
                        InternalFrameDemo.mainFrame.setLeftPanel();
                    }
                });
              }
            System.out.println("Main Frame");
       
        }
       
    }//GEN-LAST:event_btnLoginActionPerformed

   
    
   

    
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
            java.util.logging.Logger.getLogger(UserLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UserLogin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnLogin;
    private javax.swing.JPasswordField inpPassword;
    private javax.swing.JTextField inpUsername;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private java.awt.Label label2;
    private java.awt.Label label3;
    private java.awt.Label label5;
    private java.awt.Label label7;
    private java.awt.Label label8;
    private javax.swing.JLabel lblError;
    // End of variables declaration//GEN-END:variables
}
