/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.toulouse.interfaces;

import fr.miage.toulouse.gestionpublicite.Publicite;
import fr.miage.toulouse.journaliste.Entity.Article;
import fr.miage.toulouse.gestiondto.Constants;
import fr.miage.toulouse.misesouspresse.MiseSousPresse;
import fr.miage.toulouse.misesouspresse.Volume;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author trongvo
 */
public class MiseSousPressMain extends javax.swing.JFrame {
    
    private final String[] tableArticleReceivedHeaders = {"Choisir", "Theme","Nom Artcile", "Code Article", "Auteur", "Contenu", "Date Création"};
    private final String[] tablePubliciteReceivedHeaders = {"Choisir", "Nom", "Description"};
    
    private DefaultTableModel tableArticlesModel;
    private DefaultTableModel tablePubsModel;
    
    private MiseSousPresse miseSousPresse;
    
    private String codeVolumeCreated;
    private String numVolumeCreated;
    private String nomVolumeCreated;
    private List<Article> listArticleOfVolume;
    private List<Publicite> listPubsOfVolume;
    private Volume volumeToSend;
    
    private String messError;
    /**
     * Creates new form MiseSousPressMain
     */
    public MiseSousPressMain() {
        initComponents();
        this.setSize(1200, 780);
        messError="";
        listArticleOfVolume = new ArrayList<>();
        listPubsOfVolume = new ArrayList<>();
        miseSousPresse = new MiseSousPresse();
        
        miseSousPresse.start();
        tableArticlesModel = new DefaultTableModel(){
        @Override
        public Class getColumnClass(int columnIndex) {
           return columnIndex == 0 ? Boolean.class : super.getColumnClass(columnIndex);
        }};
        tablePubsModel = new DefaultTableModel(){
        @Override
        public Class getColumnClass(int columnIndex) {
           return columnIndex == 0 ? Boolean.class : super.getColumnClass(columnIndex);
        }};
        
        tableArticlesModel.setColumnIdentifiers(tableArticleReceivedHeaders);
        tablePubsModel.setColumnIdentifiers(tablePubliciteReceivedHeaders);
        tableArticles.setModel(tableArticlesModel);
        tablePubs.setModel(tablePubsModel);
        
        buttonSendVolume.setVisible(false);
        
        setTablesSize();
        initiateComboBoxCodeTheme();
    }

    public void setTablesSize(){
        tableArticles.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableArticles.getColumnModel().getColumn(0).setPreferredWidth(80);
        tableArticles.getColumnModel().getColumn(1).setPreferredWidth(135);
        tableArticles.getColumnModel().getColumn(2).setPreferredWidth(135);
        tableArticles.getColumnModel().getColumn(3).setPreferredWidth(100);
        tableArticles.getColumnModel().getColumn(4).setPreferredWidth(150);
        tableArticles.getColumnModel().getColumn(5).setPreferredWidth(330);
        tableArticles.getColumnModel().getColumn(6).setPreferredWidth(195);
        
        tablePubs.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tablePubs.getColumnModel().getColumn(0).setPreferredWidth(150);
        tablePubs.getColumnModel().getColumn(1).setPreferredWidth(175);
        tablePubs.getColumnModel().getColumn(2).setPreferredWidth(800);
    }
    
    public void initiateComboBoxCodeTheme(){
        comboBoxCodeTheme.removeAllItems();
        comboBoxCodeTheme.addItem(Constants.THEME_CODE_1);
        comboBoxCodeTheme.addItem(Constants.THEME_CODE_2);
        comboBoxCodeTheme.addItem(Constants.THEME_CODE_3);
        comboBoxCodeTheme.addItem(Constants.THEME_CODE_4);
    }
    
    public void fillTableArticle(){
        tableArticlesModel.setRowCount(0);
        for(Map.Entry<String, List<Article>> entry : miseSousPresse.getListArticles().entrySet()){
            List<Article> listOfEntry = entry.getValue();
            for(Article article : listOfEntry){
                tableArticlesModel.addRow(new Object[]{
                    Boolean.FALSE, entry.getKey(), article.getNameArticle(), article.getCodeArticle(),
                    article.getNameAuthor(), article.getContent(), article.getDate()
                });
            }
        }
    }
    
    public void fillTablePublicite(){
        tablePubsModel.setRowCount(0);
        for(Publicite pub : miseSousPresse.getListPublicites()){
            tablePubsModel.addRow(new Object[]{Boolean.FALSE, pub.getNomPub(), pub.getDescriptionPub()});
        }
    }
    
    public Article getArticleFromCode(String codeArticle, HashMap<String, List<Article>> listArticles){
        for(Map.Entry<String, List<Article>> entry : listArticles.entrySet()){
            List<Article> listOfEntry = entry.getValue();
            for(Article article : listOfEntry){
                if(article.getCodeArticle().equals(codeArticle)){
                    return article;
                }
            }
        }
        return null;
    }
    
    public Publicite getPubFromNom(String nomPub, List<Publicite> listPubs){
        for(Publicite pub : listPubs){
            if(pub.getNomPub().equals(nomPub)){
                return pub;
            }
        }
        return null;
    }
    
    public boolean checkValidate(){
        
        if(textFieldNumVolume.getText().equals("")){
            messError = Constants.EMPTY_NUM_VOLUME;
            return false;
        }
        
        if(textFieldNomVolume.getText().equals("")){
            messError = Constants.EMPTY_NOM_VOLUME;
            return false;
        }
        
        if(listArticleOfVolume.isEmpty()){
            messError = Constants.EMPTY_LIST_ARTICLE_CHOSEN;
            return false;
        }
        
        if(listPubsOfVolume.isEmpty()){
            messError = Constants.EMPTY_LIST_PUBS;
            return false;
        }
        
        return true;
    }
    
    public void getInsertValueFromUser(){
        codeVolumeCreated = comboBoxCodeTheme.getSelectedItem().toString();
        numVolumeCreated = "#"+textFieldNumVolume.getText();
        nomVolumeCreated = textFieldNomVolume.getText();
        
        for(int i=0; i<tableArticles.getRowCount();i++){
            if(tableArticles.getValueAt(i, 0).equals(Boolean.TRUE)){
                listArticleOfVolume.add(getArticleFromCode(tableArticles.getValueAt(i, 3).toString(), miseSousPresse.getListArticles()));
            }
        }
        
        for(int j=0; j<tablePubs.getRowCount(); j++){
            if(tablePubs.getValueAt(j, 0).equals(Boolean.TRUE)){
                listPubsOfVolume.add(getPubFromNom(tablePubs.getValueAt(j, 1).toString(), miseSousPresse.getListPublicites()));
            }
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabGeneral = new javax.swing.JTabbedPane();
        tabGetArticlesPubs = new javax.swing.JPanel();
        buttonGetArticles = new javax.swing.JButton();
        jLabelArticlesReceived = new javax.swing.JLabel();
        jLabelPubsReceived1 = new javax.swing.JLabel();
        jTextNbrArticles = new javax.swing.JTextField();
        jTextNbrPubs = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabCreateVolume = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableArticles = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablePubs = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        buttonCreateVolume = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        comboBoxCodeTheme = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        textFieldNumVolume = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        textFieldNomVolume = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        textFieldNomTheme = new javax.swing.JTextField();
        buttonSendVolume = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1090, 693));

        tabGeneral.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Mise Sous Presse", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 12), new java.awt.Color(36, 228, 100))); // NOI18N

        tabGetArticlesPubs.setPreferredSize(new java.awt.Dimension(1090, 637));

        buttonGetArticles.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        buttonGetArticles.setText("Recuperer Les ARTICLES");
        buttonGetArticles.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        buttonGetArticles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonGetArticlesActionPerformed(evt);
            }
        });

        jLabelArticlesReceived.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabelArticlesReceived.setText("Nombre d'ARTICLES récupérés : ");

        jLabelPubsReceived1.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabelPubsReceived1.setText("Nombre de PUBLICITES récupérées : ");

        javax.swing.GroupLayout tabGetArticlesPubsLayout = new javax.swing.GroupLayout(tabGetArticlesPubs);
        tabGetArticlesPubs.setLayout(tabGetArticlesPubsLayout);
        tabGetArticlesPubsLayout.setHorizontalGroup(
            tabGetArticlesPubsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabGetArticlesPubsLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(tabGetArticlesPubsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonGetArticles, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(tabGetArticlesPubsLayout.createSequentialGroup()
                        .addGroup(tabGetArticlesPubsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelArticlesReceived, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelPubsReceived1, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(41, 41, 41)
                        .addGroup(tabGetArticlesPubsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextNbrPubs, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextNbrArticles, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(757, Short.MAX_VALUE))
        );
        tabGetArticlesPubsLayout.setVerticalGroup(
            tabGetArticlesPubsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabGetArticlesPubsLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(buttonGetArticles, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64)
                .addGroup(tabGetArticlesPubsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelArticlesReceived, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextNbrArticles, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 114, Short.MAX_VALUE)
                .addGroup(tabGetArticlesPubsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelPubsReceived1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextNbrPubs, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(329, 329, 329))
        );

        tabGeneral.addTab("Recuperer les Articles et les Publicités", tabGetArticlesPubs);

        tabCreateVolume.setPreferredSize(new java.awt.Dimension(1110, 680));

        tableArticles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(tableArticles);

        tablePubs.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane4.setViewportView(tablePubs);

        jLabel1.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Choisir les ARTICLES :");

        jLabel2.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Choisir les ARTICLES :");

        buttonCreateVolume.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        buttonCreateVolume.setText("CREER UN VOLUME");
        buttonCreateVolume.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        buttonCreateVolume.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCreateVolumeActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel3.setText("Code Thème :");

        comboBoxCodeTheme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxCodeThemeActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel4.setText("Numéro du Volume :");

        jLabel5.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel5.setText("Nom du Volume :");

        jLabel6.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel6.setText("Nom du thème :");

        buttonSendVolume.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        buttonSendVolume.setText("ENVOYER LE VOLUME");
        buttonSendVolume.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        buttonSendVolume.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSendVolumeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout tabCreateVolumeLayout = new javax.swing.GroupLayout(tabCreateVolume);
        tabCreateVolume.setLayout(tabCreateVolumeLayout);
        tabCreateVolumeLayout.setHorizontalGroup(
            tabCreateVolumeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabCreateVolumeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tabCreateVolumeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabCreateVolumeLayout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(21, Short.MAX_VALUE))
                    .addGroup(tabCreateVolumeLayout.createSequentialGroup()
                        .addGroup(tabCreateVolumeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(tabCreateVolumeLayout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comboBoxCodeTheme, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(textFieldNomTheme, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1)
                                .addComponent(textFieldNumVolume, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)
                                .addComponent(textFieldNomVolume, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(tabCreateVolumeLayout.createSequentialGroup()
                                .addComponent(buttonCreateVolume, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(133, 133, 133)
                                .addComponent(buttonSendVolume, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1131, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        tabCreateVolumeLayout.setVerticalGroup(
            tabCreateVolumeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabCreateVolumeLayout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(tabCreateVolumeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(tabCreateVolumeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(textFieldNumVolume, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(textFieldNomVolume, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(textFieldNomTheme, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(comboBoxCodeTheme, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addGroup(tabCreateVolumeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonCreateVolume, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonSendVolume, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27))
        );

        jScrollPane3.setViewportView(tabCreateVolume);

        tabGeneral.addTab("Creer un Volume", jScrollPane3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabGeneral, javax.swing.GroupLayout.DEFAULT_SIZE, 1182, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabGeneral, javax.swing.GroupLayout.DEFAULT_SIZE, 747, Short.MAX_VALUE)
        );

        tabGeneral.getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonGetArticlesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonGetArticlesActionPerformed
        
        jTextNbrArticles.setText(String.valueOf(miseSousPresse.getNbrArticles()));
        jTextNbrPubs.setText(String.valueOf(miseSousPresse.getListPublicites().size()));
        if(miseSousPresse.getNbrArticles() > 0 && miseSousPresse.getListPublicites().size() > 0){
            fillTableArticle();
            fillTablePublicite();
        }
        
    }//GEN-LAST:event_buttonGetArticlesActionPerformed

    private void comboBoxCodeThemeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxCodeThemeActionPerformed
        String codeTheme = comboBoxCodeTheme.getSelectedItem().toString();
        switch(codeTheme){
            case Constants.THEME_CODE_1: textFieldNomTheme.setText(Constants.THEME_NAMES_1);break;
            case Constants.THEME_CODE_2: textFieldNomTheme.setText(Constants.THEME_NAMES_2);break;
            case Constants.THEME_CODE_3: textFieldNomTheme.setText(Constants.THEME_NAMES_3);break;
            case Constants.THEME_CODE_4: textFieldNomTheme.setText(Constants.THEME_NAMES_4);break;
            default: textFieldNomTheme.setText(Constants.THEME_NAMES_1);break;
        }
    }//GEN-LAST:event_comboBoxCodeThemeActionPerformed

    private void buttonCreateVolumeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCreateVolumeActionPerformed
        getInsertValueFromUser();
        if(checkValidate()){
            volumeToSend = new Volume(codeVolumeCreated, numVolumeCreated, nomVolumeCreated, listArticleOfVolume, listPubsOfVolume);
            buttonSendVolume.setVisible(true);
            System.out.println("Volume : "+volumeToSend.toString());
            JOptionPane.showMessageDialog(this, Constants.SUCCES);
        }else{
            JOptionPane.showMessageDialog(this, messError, "Inane error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_buttonCreateVolumeActionPerformed

    private void buttonSendVolumeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSendVolumeActionPerformed
        Volume volume = miseSousPresse.sendVolume(volumeToSend);
        if(volume != null){
            JOptionPane.showMessageDialog(this, Constants.SUCCES);
        }else{
            JOptionPane.showMessageDialog(this, Constants.FAILED, "Inane error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_buttonSendVolumeActionPerformed

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
            java.util.logging.Logger.getLogger(MiseSousPressMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MiseSousPressMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MiseSousPressMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MiseSousPressMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MiseSousPressMain().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonCreateVolume;
    private javax.swing.JButton buttonGetArticles;
    private javax.swing.JButton buttonSendVolume;
    private javax.swing.JComboBox<String> comboBoxCodeTheme;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabelArticlesReceived;
    private javax.swing.JLabel jLabelPubsReceived1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextField jTextNbrArticles;
    private javax.swing.JTextField jTextNbrPubs;
    private javax.swing.JPanel tabCreateVolume;
    private javax.swing.JTabbedPane tabGeneral;
    private javax.swing.JPanel tabGetArticlesPubs;
    private javax.swing.JTable tableArticles;
    private javax.swing.JTable tablePubs;
    private javax.swing.JTextField textFieldNomTheme;
    private javax.swing.JTextField textFieldNomVolume;
    private javax.swing.JTextField textFieldNumVolume;
    // End of variables declaration//GEN-END:variables
}
