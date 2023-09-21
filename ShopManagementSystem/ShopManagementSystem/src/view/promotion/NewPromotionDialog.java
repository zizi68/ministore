/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.promotion;

import com.raven.swing.TimePicker;
import view.imports.*;
import controller.ImportsController;
import controller.ProductController;
import controller.PromotionController;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import model.ImportDetail;
import model.Product;
import model.Promotion;
import model.PromotionDetail;
import model.Response;
import swing.UIController;
import view.login.LoginFrame;
import view.products.*;

public class NewPromotionDialog extends javax.swing.JDialog {

    private ProductController pc;
    private PromotionController prc;
    private DefaultTableModel dtmPromotionDetail;
    private DefaultTableModel dtmProduct;
    public static List<PromotionDetail> promotionDetail;
    public static boolean isEdit = false;
    public boolean flag = false;
    private ProductPromotionDialog productPromotionDialog;

    public NewPromotionDialog() {
    }
    /**
     * Creates new form ImportHistoryDialog
     */
    public NewPromotionDialog(java.awt.Frame parent, boolean modal, Promotion promotion, List<PromotionDetail> list) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        this.getContentPane().setBackground(Color.WHITE);
        
        dtmProduct = (DefaultTableModel) jTable_Product.getModel();
        dtmPromotionDetail = (DefaultTableModel) jTable_PromotionDetail.getModel();
        pc = new ProductController();
        prc = new PromotionController();
        promotionDetail = new ArrayList<>();
        
        UIController.setDefaultTableHeader(jTable_Product);
        UIController.setDefaultTableHeader(jTable_PromotionDetail);     
        
        if(promotion != null){
            jDateChooser_StartDate.setDate(promotion.getStartDate());
            jDateChooser_FinishDate.setDate(promotion.getFinishDate());
            
            SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm a");
            jLabel_StartTime.setText(sdf2.format(promotion.getStartDate()));
            jLabel_FinishTime.setText(sdf2.format(promotion.getFinishDate()));
            
            PromotionDetail temp = null;
            for(PromotionDetail p : list) {
                temp = new PromotionDetail(p.getProduct(), p.getPercentage());
                promotionDetail.add(temp);
            }
            loadPromotionDetail();
            jLabel_Title.setText("Update Promotion");
            jLabel_UserID.setText("Promotion ID:");
            jLabel_ID.setText(promotion.getId().toString());
            flag = true;
        }
        else
            jLabel_ID.setText(String.valueOf(LoginFrame.userID));
        
        loadProduct();
        
    }
    
    public void loadProduct() {
        pc.loadTable(pc.getAllProduct(), dtmProduct);
    }
    
    public void loadPromotionDetail() {
        prc.loadProductTable(promotionDetail, dtmPromotionDetail);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        timeComponent1 = new com.raven.swing.TimeComponent();
        timeComponent2 = new com.raven.swing.TimeComponent();
        jLabel_Title = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jButton_ClearSearch = new javax.swing.JButton();
        jTextField_NameSearch = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel_UserID = new javax.swing.JLabel();
        jLabel_Name1 = new javax.swing.JLabel();
        jLabel_Email1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel_ID = new javax.swing.JLabel();
        jDateChooser_StartDate = new com.toedter.calendar.JDateChooser();
        jDateChooser_FinishDate = new com.toedter.calendar.JDateChooser();
        jPanel8 = new javax.swing.JPanel();
        jLabel_StartTime = new javax.swing.JLabel();
        jLabel_FinishTime = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable_PromotionDetail = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_Product = new javax.swing.JTable();
        jButton_Create = new javax.swing.JButton();
        jButton_Choose = new javax.swing.JButton();
        jButton_Edit = new javax.swing.JButton();
        jButton_Delete = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("New Import");
        setBackground(new java.awt.Color(255, 255, 255));

        jLabel_Title.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel_Title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_Title.setText("New Promotion");

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search for products", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 2, 14), new java.awt.Color(153, 153, 153))); // NOI18N

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel10.setText("Product name");

        jButton_ClearSearch.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jButton_ClearSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/clear.png"))); // NOI18N
        jButton_ClearSearch.setText("Clear");
        jButton_ClearSearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jTextField_NameSearch.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jTextField_NameSearch.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextField_NameSearchCaretUpdate(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addComponent(jTextField_NameSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addComponent(jButton_ClearSearch)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jButton_ClearSearch)
                    .addComponent(jTextField_NameSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Promotion information", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 2, 14), new java.awt.Color(153, 153, 153))); // NOI18N

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new java.awt.GridLayout(3, 1, 0, 10));

        jLabel_UserID.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel_UserID.setText("User ID:");
        jPanel5.add(jLabel_UserID);

        jLabel_Name1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel_Name1.setText("Start Date:");
        jPanel5.add(jLabel_Name1);

        jLabel_Email1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel_Email1.setText("Finish Date:");
        jPanel5.add(jLabel_Email1);

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 15, 120, 110));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.GridLayout(3, 1, 0, 10));

        jLabel_ID.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel_ID.setText("ID");
        jPanel3.add(jLabel_ID);

        jDateChooser_StartDate.setDate(new java.util.Date());
        jDateChooser_StartDate.setDateFormatString("dd-MM-yyyy");
        jDateChooser_StartDate.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jPanel3.add(jDateChooser_StartDate);

        jDateChooser_FinishDate.setDate(new java.util.Date());
        jDateChooser_FinishDate.setDateFormatString("dd-MM-yyyy");
        jDateChooser_FinishDate.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jPanel3.add(jDateChooser_FinishDate);

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 15, 160, 110));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setLayout(new java.awt.GridLayout(2, 1, 0, 10));

        jLabel_StartTime.setText("05:00 AM");
        jPanel8.add(jLabel_StartTime);

        jLabel_FinishTime.setText("05:00 AM");
        jPanel8.add(jLabel_FinishTime);

        jPanel1.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 60, 60, 60));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setLayout(new java.awt.GridLayout(2, 1, 0, 10));

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel9.add(jButton1);

        jButton2.setText("jButton2");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel9.add(jButton2);

        jPanel1.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 64, 20, 50));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Promotion detail", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 2, 14), new java.awt.Color(153, 153, 153))); // NOI18N

        jTable_PromotionDetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Percentage"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable_PromotionDetail.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTable_PromotionDetailFocusLost(evt);
            }
        });
        jTable_PromotionDetail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_PromotionDetailMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable_PromotionDetail);
        if (jTable_PromotionDetail.getColumnModel().getColumnCount() > 0) {
            jTable_PromotionDetail.getColumnModel().getColumn(0).setMaxWidth(100);
            jTable_PromotionDetail.getColumnModel().getColumn(2).setMaxWidth(100);
        }

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 668, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel6Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 638, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(20, Short.MAX_VALUE)))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 291, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel6Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Product List", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 2, 14), new java.awt.Color(153, 153, 153))); // NOI18N

        jTable_Product.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Category", "Brand", "Unit", "Price", "Remain Quantity"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable_Product.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTable_ProductFocusLost(evt);
            }
        });
        jTable_Product.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_ProductMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable_Product);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1244, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jButton_Create.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jButton_Create.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tick3.png"))); // NOI18N
        jButton_Create.setText("Create");
        jButton_Create.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_Create.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_CreateActionPerformed(evt);
            }
        });

        jButton_Choose.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jButton_Choose.setText("Choose");
        jButton_Choose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_Choose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ChooseActionPerformed(evt);
            }
        });

        jButton_Edit.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jButton_Edit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/edit.png"))); // NOI18N
        jButton_Edit.setText("Edit");
        jButton_Edit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_Edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_EditActionPerformed(evt);
            }
        });

        jButton_Delete.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jButton_Delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/trash.png"))); // NOI18N
        jButton_Delete.setText("Delete");
        jButton_Delete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_Delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_DeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel_Title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                .addComponent(jButton_Choose, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(90, 90, 90)
                                                .addComponent(jButton_Edit, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jButton_Delete, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 23, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton_Create, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(580, 580, 580))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel_Title)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton_Choose, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton_Delete)
                            .addComponent(jButton_Edit))))
                .addGap(47, 47, 47)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_Create)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTable_ProductFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTable_ProductFocusLost
        // TODO add your handling code here:
        //        jButton_Modify.setEnabled(false);
        //        jButton_Remove.setEnabled(false);
    }//GEN-LAST:event_jTable_ProductFocusLost

    private void jTable_ProductMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_ProductMouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jTable_ProductMouseClicked

    private void jTable_PromotionDetailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTable_PromotionDetailFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable_PromotionDetailFocusLost

    private void jTable_PromotionDetailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_PromotionDetailMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable_PromotionDetailMouseClicked

    private void jButton_ChooseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ChooseActionPerformed
        // TODO add your handling code here:
        isEdit = false;
        int selectedRow = jTable_Product.convertRowIndexToModel(jTable_Product.getSelectedRow());
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "There is no selected product");
            return;
        }
        
        int id = Integer.parseInt(jTable_Product.getValueAt(selectedRow, 0).toString());
        
        for(int i = 0; i < promotionDetail.size(); i++){
            if(promotionDetail.get(i).getProduct().getProductId() == id){
                JOptionPane.showMessageDialog(this, "This item has been already added to Promotion Detail");
                return;
            }
        }
        
        productPromotionDialog = new ProductPromotionDialog(null, true, this, 
                jTable_Product.getValueAt(selectedRow, 0).toString(), 
                jTable_Product.getValueAt(selectedRow, 1).toString(), 1);

        productPromotionDialog.setVisible(true);
        loadPromotionDetail();
    }//GEN-LAST:event_jButton_ChooseActionPerformed

    private void jButton_EditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_EditActionPerformed
        // TODO add your handling code here:
        isEdit = true;
        int selectedRow = jTable_PromotionDetail.convertRowIndexToModel(jTable_PromotionDetail.getSelectedRow());
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "There is no selected import detail");
            return;
        }
        
        productPromotionDialog = new ProductPromotionDialog(null, true, this, 
                jTable_PromotionDetail.getValueAt(selectedRow, 0).toString(), 
                jTable_PromotionDetail.getValueAt(selectedRow, 1).toString(),
                Integer.parseInt(jTable_PromotionDetail.getValueAt(selectedRow, 2).toString()));

        productPromotionDialog.setVisible(true);
        
        loadPromotionDetail();
    }//GEN-LAST:event_jButton_EditActionPerformed

    private void jButton_DeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_DeleteActionPerformed
        // TODO add your handling code here:
        int selectedRow = jTable_PromotionDetail.convertRowIndexToModel(jTable_PromotionDetail.getSelectedRow());
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "There is no selected import detail");
            return;
        }
        
        for(int i = 0; i < promotionDetail.size(); i++){
            if(promotionDetail.get(i).getProduct().getProductId() == Integer.parseInt(jTable_PromotionDetail.getValueAt(selectedRow, 0).toString())){
                promotionDetail.remove(i);
            }
        }
        loadPromotionDetail();
    }//GEN-LAST:event_jButton_DeleteActionPerformed

    public Date convertDate(Date date, String time) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
        String newDate = sdf1.format(date) + " " + time;
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
        try {
            return sdf2.parse(newDate);
        } catch (ParseException ex) {
            Logger.getLogger(NewPromotionDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private void jButton_CreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_CreateActionPerformed
        // TODO add your handling code here:
        if(promotionDetail.size() == 0)
            JOptionPane.showMessageDialog(this, "Promotion detail is empty");
        else{
            Date date1 = convertDate(jDateChooser_StartDate.getDate(), jLabel_StartTime.getText());
            Date date2 = convertDate(jDateChooser_FinishDate.getDate(), jLabel_FinishTime.getText());
            
            if(date1.compareTo(new Date()) < 0) {
                JOptionPane.showMessageDialog(this, "Start date should be greater then Today");
                return;
            }
            
            if(date1.compareTo(date2) >= 0) {
                JOptionPane.showMessageDialog(this, "Finish date should be greater then Start date");
                return;
            }
            
            Response res = null;
            if(flag == true)
                res = prc.editPromotion(Integer.parseInt(jLabel_ID.getText()), date1, date2, promotionDetail);
            else
                res = prc.addPromotion(LoginFrame.userID, date1, date2, promotionDetail);
            
            JOptionPane.showMessageDialog(null, prc.convertResponse(res.getMessage()).getMessage());
            
        }
    }//GEN-LAST:event_jButton_CreateActionPerformed

    private void jTextField_NameSearchCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextField_NameSearchCaretUpdate
        // TODO add your handling code here:
        String tuKhoa = jTextField_NameSearch.getText();
        TableRowSorter<TableModel> trs = new TableRowSorter<>(jTable_Product.getModel());
        jTable_Product.setRowSorter(trs);
        trs.setRowFilter(RowFilter.regexFilter("(?i)" + tuKhoa));
    }//GEN-LAST:event_jTextField_NameSearchCaretUpdate

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
       TimePicker picker = new TimePicker();
       picker.setLocation(this.getParent().getWidth()/2 - 50, this.getParent().getHeight()/2 - 50);
       picker.showPopup(this, 100, 100);
       picker.addActionListener(new ActionListener(){
           @Override
           public void actionPerformed(ActionEvent e) {
               jLabel_StartTime.setText(picker.getSelectedTime());
               System.out.println("Start Date: " + convertDate(jDateChooser_StartDate.getDate(), picker.getSelectedTime()));
           }
       });
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        TimePicker picker = new TimePicker();
        picker.setLocation(this.getParent().getWidth()/2 - 50, this.getParent().getHeight()/2 - 50);
        picker.showPopup(this, 100, 100);
        picker.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                jLabel_FinishTime.setText(picker.getSelectedTime());
                System.out.println("Finish Date: " + convertDate(jDateChooser_FinishDate.getDate(), picker.getSelectedTime()));
            }
        });
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(NewPromotionDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(NewPromotionDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(NewPromotionDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(NewPromotionDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//
//        /* Create and display the dialog */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                NewPromotionDialog dialog = new NewPromotionDialog(new javax.swing.JFrame(), true);
//                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//                    @Override
//                    public void windowClosing(java.awt.event.WindowEvent e) {
//                        System.exit(0);
//                    }
//                });
//                dialog.setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton_Choose;
    private javax.swing.JButton jButton_ClearSearch;
    private javax.swing.JButton jButton_Create;
    private javax.swing.JButton jButton_Delete;
    private javax.swing.JButton jButton_Edit;
    private com.toedter.calendar.JDateChooser jDateChooser_FinishDate;
    private com.toedter.calendar.JDateChooser jDateChooser_StartDate;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel_Email1;
    private javax.swing.JLabel jLabel_FinishTime;
    private javax.swing.JLabel jLabel_ID;
    private javax.swing.JLabel jLabel_Name1;
    private javax.swing.JLabel jLabel_StartTime;
    private javax.swing.JLabel jLabel_Title;
    private javax.swing.JLabel jLabel_UserID;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable_Product;
    private javax.swing.JTable jTable_PromotionDetail;
    private javax.swing.JTextField jTextField_NameSearch;
    private com.raven.swing.TimeComponent timeComponent1;
    private com.raven.swing.TimeComponent timeComponent2;
    // End of variables declaration//GEN-END:variables
}
