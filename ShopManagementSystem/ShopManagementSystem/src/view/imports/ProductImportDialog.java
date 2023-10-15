/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.imports;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import model.ImportDetail;
import model.Product;
import static view.imports.NewImportDialog.importDetail;
import static view.imports.NewImportDialog.totalPrice;

public class ProductImportDialog extends javax.swing.JDialog {

    
    private JDialog parentPanel;
    private String productID;
    private String productName;
    private int quantity;
    private float oldPrice;

    /**
     * Creates new form importIngredientDialog
     */
    public ProductImportDialog(java.awt.Frame parent, boolean modal, /*Ingredient ingredient, */ JDialog parentPanel, String productID, String productName, int quantity, float oldPrice) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        this.getContentPane().setBackground(Color.WHITE);
//        this.ingredient = ingredient;
        this.parentPanel = parentPanel;
//        loadData();
//        addSpinnerListener();

        this.productID = productID;
        this.productName = productName;
        this.quantity = quantity;
        this.oldPrice = oldPrice;
        jTextField_ID.setText(productID);
        jTextField_Name.setText(productName);
        jSpinner_ImportAmount.setValue(quantity);
        jSpinner_Price.setValue(oldPrice);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel_ImportIngrTitle = new javax.swing.JLabel();
        jPanel_Info = new javax.swing.JPanel();
        jLabel_SelectIngr = new javax.swing.JLabel();
        jSpinner_ImportAmount = new javax.swing.JSpinner();
        jLabel_Amount = new javax.swing.JLabel();
        jLabel_Cost = new javax.swing.JLabel();
        jTextField_Name = new javax.swing.JTextField();
        jLabel_Unit = new javax.swing.JLabel();
        jSpinner_Price = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();
        jButton_Cancel = new javax.swing.JButton();
        jButton_Save = new javax.swing.JButton();
        jLabel_ID = new javax.swing.JLabel();
        jTextField_ID = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jLabel_ImportIngrTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel_ImportIngrTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_ImportIngrTitle.setText("Import Product");

        jPanel_Info.setBackground(new java.awt.Color(255, 255, 255));
        jPanel_Info.setForeground(new java.awt.Color(255, 255, 255));

        jLabel_SelectIngr.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel_SelectIngr.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel_SelectIngr.setText("Name");

        jSpinner_ImportAmount.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jSpinner_ImportAmount.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        jLabel_Amount.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel_Amount.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel_Amount.setText("Amount");

        jLabel_Cost.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel_Cost.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel_Cost.setText("Price");

        jTextField_Name.setEditable(false);
        jTextField_Name.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jLabel_Unit.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel_Unit.setText("Unit");

        jSpinner_Price.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jSpinner_Price.setModel(new javax.swing.SpinnerNumberModel(10000, 0, null, 10000));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel1.setText("đ");

        javax.swing.GroupLayout jPanel_InfoLayout = new javax.swing.GroupLayout(jPanel_Info);
        jPanel_Info.setLayout(jPanel_InfoLayout);
        jPanel_InfoLayout.setHorizontalGroup(
            jPanel_InfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_InfoLayout.createSequentialGroup()
                .addGroup(jPanel_InfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_InfoLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel_SelectIngr, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel_Amount, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_Cost, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_InfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField_Name, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel_InfoLayout.createSequentialGroup()
                        .addComponent(jSpinner_ImportAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel_Unit))
                    .addGroup(jPanel_InfoLayout.createSequentialGroup()
                        .addComponent(jSpinner_Price, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel_InfoLayout.setVerticalGroup(
            jPanel_InfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_InfoLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel_InfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_Name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_SelectIngr, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel_InfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jSpinner_ImportAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_Amount, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_Unit))
                .addGap(18, 18, 18)
                .addGroup(jPanel_InfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_Cost, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSpinner_Price)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 7, Short.MAX_VALUE))
        );

        jButton_Cancel.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jButton_Cancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cancel.png"))); // NOI18N
        jButton_Cancel.setText("Cancel");
        jButton_Cancel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_Cancel.setFocusPainted(false);
        jButton_Cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_CancelActionPerformed(evt);
            }
        });

        jButton_Save.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jButton_Save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/save.png"))); // NOI18N
        jButton_Save.setText("Save");
        jButton_Save.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_Save.setFocusPainted(false);
        jButton_Save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_SaveActionPerformed(evt);
            }
        });

        jLabel_ID.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel_ID.setText("ID");

        jTextField_ID.setEditable(false);
        jTextField_ID.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel_ImportIngrTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(59, 59, 59)
                                .addComponent(jPanel_Info, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(113, 113, 113)
                                .addComponent(jButton_Save, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(61, 61, 61)
                                .addComponent(jButton_Cancel)))
                        .addGap(0, 91, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(157, 157, 157)
                .addComponent(jLabel_ID)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField_ID, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel_ImportIngrTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_ID)
                    .addComponent(jTextField_ID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel_Info, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_Cancel)
                    .addComponent(jButton_Save, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Application message", JOptionPane.ERROR_MESSAGE, new ImageIcon(getClass().getResource("/img/error.png")));
    }

    private void showInfoMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Application message", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/img/infor.png")));
    }

    private void jButton_CancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_CancelActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jButton_CancelActionPerformed

    private void jButton_SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_SaveActionPerformed
        // TODO add your handling code here:
        int addQuantities = (int) jSpinner_ImportAmount.getValue();
        if(addQuantities == 0) {
            JOptionPane.showMessageDialog(null, "Quantity should be greater than 0");
            return;
        }
        
        float price = Float.parseFloat(jSpinner_Price.getValue().toString());
        
        if(price >= oldPrice) {
            JOptionPane.showMessageDialog(this, "Giá nhập lớn hơn/bằng giá bán hiên tại (" + oldPrice + "), bạn nên chỉnh lại giá bán");
        }
        
        ImportDetail detail = new ImportDetail();
        detail.setProduct(new Product(Integer.parseInt(productID), productName));
        detail.setQuantity(addQuantities);
        detail.setPrice(price);

        if(NewImportDialog.isEdit == false) {
            NewImportDialog.importDetail.add(detail);
            NewImportDialog.totalPrice += addQuantities*price;
        }
        else {
            NewImportDialog.importDetail.add(detail);
            NewImportDialog.totalPrice += addQuantities*price;
            NewImportDialog.totalPrice -= quantity*oldPrice;
            for(int i = 0; i < NewImportDialog.importDetail.size(); i++){
                if(NewImportDialog.importDetail.get(i).getProduct().getProductId() == Integer.parseInt(this.productID)){
                    NewImportDialog.importDetail.remove(i);
                }
            }
        }
        this.dispose();
    }//GEN-LAST:event_jButton_SaveActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_Cancel;
    private javax.swing.JButton jButton_Save;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel_Amount;
    private javax.swing.JLabel jLabel_Cost;
    private javax.swing.JLabel jLabel_ID;
    private javax.swing.JLabel jLabel_ImportIngrTitle;
    private javax.swing.JLabel jLabel_SelectIngr;
    private javax.swing.JLabel jLabel_Unit;
    private javax.swing.JPanel jPanel_Info;
    private javax.swing.JSpinner jSpinner_ImportAmount;
    private javax.swing.JSpinner jSpinner_Price;
    private javax.swing.JTextField jTextField_ID;
    private javax.swing.JTextField jTextField_Name;
    // End of variables declaration//GEN-END:variables
}