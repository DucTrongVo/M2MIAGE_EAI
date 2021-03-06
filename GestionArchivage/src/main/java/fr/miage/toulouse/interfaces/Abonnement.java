/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.toulouse.interfaces;

import fr.miage.toulouse.gestionarchivage.GestionArchive;
import fr.miage.toulouse.gestiondto.AbonnementDTO;
import fr.miage.toulouse.gestiondto.Constants;
import fr.miage.toulouse.gestiondto.TitreDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author trongvo
 */
public class Abonnement extends javax.swing.JFrame {
    private GestionArchive gestionArchive;
    private DefaultTableModel tableModel;
    private List<TitreDTO> listeTitres = new ArrayList<>();
    private final String[] tableTitreHeaders = {"Code Titre", "Nom", "Description", "Rythme (semaine)"};
    private TitreDTO titreChosen;
    private String nbCopies;
    private String duration;
    /**
     * Creates new form Abonnement
     */
    public Abonnement(GestionArchive gestionArchive) {
        initComponents();
        this.gestionArchive = gestionArchive;
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(tableTitreHeaders);
        jTableTitres.setModel(tableModel);
        this.titreChosen = null;
        this.nbCopies = this.duration = "";
        tableInit();
        
        
        this.listeTitres.add(new TitreDTO(Constants.THEME_CODE_1, Constants.THEME_NAMES_1, "Jeux Vidéo", 4));
        this.listeTitres.add(new TitreDTO(Constants.THEME_CODE_2, Constants.THEME_NAMES_2, "Science et Fiction", 4));
        this.listeTitres.add(new TitreDTO(Constants.THEME_CODE_3, Constants.THEME_NAMES_3, "Connaissance générale", 5));
        this.listeTitres.add(new TitreDTO(Constants.THEME_CODE_4, Constants.THEME_NAMES_4, "Economie en générale", 54));
    }

    private TitreDTO getTitreFromListe(String codeTitre){
        if(this.listeTitres.size() < 0){
            return null;
        }
        TitreDTO titreDto = null;
        for(TitreDTO titre : this.listeTitres){
            if(titre.getCodeTitre().equals(codeTitre)){
                titreDto = titre;
            }
        }
        return titreDto;
    }
    private void tableInit(){
        jTableTitres.getColumnModel().getColumn(0).setPreferredWidth(50);
        jTableTitres.getColumnModel().getColumn(1).setPreferredWidth(120);
        jTableTitres.getColumnModel().getColumn(2).setPreferredWidth(180);
        jTableTitres.getColumnModel().getColumn(3).setPreferredWidth(40);
        jTableTitres.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
       
        
//        tableModel.addRow(new Object[]{
//            Constants.THEME_CODE_1, Constants.THEME_NAMES_1, "Jeux Vidéo", "4"
//        });
//        tableModel.addRow(new Object[]{
//            Constants.THEME_CODE_2, Constants.THEME_NAMES_2, "Science et Fiction", "4"
//        });
//        tableModel.addRow(new Object[]{
//            Constants.THEME_CODE_3, Constants.THEME_NAMES_3, "Connaissance générale", "5"
//        });
//        tableModel.addRow(new Object[]{
//            Constants.THEME_CODE_4, Constants.THEME_NAMES_4, "Economie en générale", "5"
//        });
        
        
        jTableTitres.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                String codeChosen = jTableTitres.getValueAt(jTableTitres.getSelectedRow(), 0).toString();
                System.out.println("codeChosen : "+codeChosen);
                titreChosen = getTitreFromListe(codeChosen);
                System.out.println("TitreChosen : "+titreChosen.toString());
            }
        });
    }
     
    private boolean validated(){
        if(this.titreChosen == null){
            JOptionPane.showMessageDialog(this, Constants.UNKNOWN_TITRE, "Inane warning", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if(jTextFieldNbCopies.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Il faut choisir un nombre de copies", "Inane warning", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if(jTextFieldDuration.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Il faut choisir une duration", "Inane warning", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButtonAbonner = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableTitres = new javax.swing.JTable();
        jButtonAnnuler = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldNbCopies = new javax.swing.JTextField();
        jTextFieldDuration = new javax.swing.JTextField();
        jButtonCharge = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButtonAbonner.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jButtonAbonner.setText("S'INSCRE AU TITRE");
        jButtonAbonner.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonAbonner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAbonnerActionPerformed(evt);
            }
        });

        jTableTitres.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTableTitres.setColumnSelectionAllowed(true);
        jScrollPane1.setViewportView(jTableTitres);
        jTableTitres.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        jButtonAnnuler.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jButtonAnnuler.setText("ANNULER");
        jButtonAnnuler.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonAnnuler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAnnulerActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel1.setText("Duration de l'abonnement (semaine)");

        jLabel2.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel2.setText("Nombre de copies");

        jButtonCharge.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jButtonCharge.setText("CHARGER TABLEAU");
        jButtonCharge.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonCharge.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonChargeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButtonAbonner, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonAnnuler, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 824, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonCharge, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(55, 55, 55)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldDuration, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldNbCopies, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonCharge, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldNbCopies, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldDuration, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAnnuler, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonAbonner, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 848, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 503, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 12, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAnnulerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAnnulerActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_jButtonAnnulerActionPerformed

    private void jButtonAbonnerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAbonnerActionPerformed
        if(validated()){
            this.nbCopies = jTextFieldNbCopies.getText();
            this.duration = jTextFieldDuration.getText();
            System.out.println("nbCopies : "+this.nbCopies + " - duration : "+this.duration);
            System.out.println("currentUser ID : "+GestionArchivageMain.currentUser.getMail());
            System.out.println("titreChosen ID : "+titreChosen.getCodeTitre());
            AbonnementDTO abonDto = gestionArchive.abonner(GestionArchivageMain.currentUser.getMail(), titreChosen.getCodeTitre(), this.nbCopies, this.duration);
            JOptionPane.showMessageDialog(this, Constants.SUCCES);
            this.titreChosen = null;
            this.nbCopies = this.duration = "";
            jTextFieldNbCopies.setText("");
            jTextFieldDuration.setText("");
        }
    }//GEN-LAST:event_jButtonAbonnerActionPerformed

    private void jButtonChargeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonChargeActionPerformed
        try {
            listeTitres = gestionArchive.getAllTitres();
            tableModel.setRowCount(0);
            Thread.sleep(2000);
            if(this.listeTitres != null && this.listeTitres.size() > 0){
                for(TitreDTO titreDto : listeTitres){
                    tableModel.addRow(new Object[]{
                        titreDto.getCodeTitre(), titreDto.getNomTitre(), titreDto.getDescription(), titreDto.getRythmSortie()
                    });
                }
            }else{
                JOptionPane.showMessageDialog(this, "Liste des titres est vide!");
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Abonnement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonChargeActionPerformed

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
            java.util.logging.Logger.getLogger(Abonnement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Abonnement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Abonnement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Abonnement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
    GestionArchive gestionArchive = new GestionArchive();
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Abonnement(gestionArchive).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAbonner;
    private javax.swing.JButton jButtonAnnuler;
    private javax.swing.JButton jButtonCharge;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableTitres;
    private javax.swing.JTextField jTextFieldDuration;
    private javax.swing.JTextField jTextFieldNbCopies;
    // End of variables declaration//GEN-END:variables
}
