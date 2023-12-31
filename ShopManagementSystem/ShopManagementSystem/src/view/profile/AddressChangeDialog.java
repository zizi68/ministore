/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.profile;

import controller.AddressController;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import model.Address;
import model.District;
import model.Province;
import model.Response;
import model.Ward;
import swing.UIController;
import utils.MessageConstants;
import view.login.LoginFrame;

/**
 *
 * @author Admin
 */
public class AddressChangeDialog extends javax.swing.JDialog {

    private List<Address> addresses = new ArrayList<>();
    private AddressController ac;
    private DefaultTableModel dtm;
    private Integer addressId;

    /**
     * Creates new form PasswordChangeDialog
     *
     * @param parent
     * @param modal
     * @param parentPanel
     */
    public AddressChangeDialog(java.awt.Frame parent, boolean modal, JPanel parentPanel) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        dtm = (DefaultTableModel) jTable_Address.getModel();
        ac = new AddressController();
        UIController.setDefaultTableHeader(jTable_Address);
        loadData();
        loadProvince();
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
        jPanel4 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jComboBox_Province = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jComboBox_District = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jComboBox_Commune = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jTextField_Address = new javax.swing.JTextField();
        jPanel_Card1 = new javax.swing.JPanel();
        jButton_Add = new javax.swing.JButton();
        jButton_Modify = new javax.swing.JButton();
        jButton_Remove = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_Address = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Change your password");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Address information", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 2, 14), new java.awt.Color(153, 153, 153))); // NOI18N

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel9.setText("Province");

        jComboBox_Province.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jComboBox_Province.setPreferredSize(new java.awt.Dimension(50, 30));
        jComboBox_Province.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox_ProvinceItemStateChanged(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel10.setText("District");

        jComboBox_District.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jComboBox_District.setPreferredSize(new java.awt.Dimension(50, 30));
        jComboBox_District.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox_DistrictItemStateChanged(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel11.setText("Commune");

        jComboBox_Commune.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel7.setText("Address");

        jTextField_Address.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        jPanel_Card1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel_Card1.setMaximumSize(new java.awt.Dimension(30000, 33));
        jPanel_Card1.setPreferredSize(new java.awt.Dimension(439, 30));
        jPanel_Card1.setLayout(new java.awt.GridLayout(1, 0, 35, 0));

        jButton_Add.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jButton_Add.setForeground(new java.awt.Color(51, 51, 51));
        jButton_Add.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/add30px.png"))); // NOI18N
        jButton_Add.setText("Add");
        jButton_Add.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_Add.setMaximumSize(new java.awt.Dimension(95, 30));
        jButton_Add.setMinimumSize(new java.awt.Dimension(95, 30));
        jButton_Add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_AddActionPerformed(evt);
            }
        });
        jPanel_Card1.add(jButton_Add);

        jButton_Modify.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jButton_Modify.setForeground(new java.awt.Color(51, 51, 51));
        jButton_Modify.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/edit.png"))); // NOI18N
        jButton_Modify.setText("Modify");
        jButton_Modify.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_Modify.setEnabled(false);
        jButton_Modify.setMaximumSize(new java.awt.Dimension(95, 33));
        jButton_Modify.setMinimumSize(new java.awt.Dimension(95, 30));
        jButton_Modify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ModifyActionPerformed(evt);
            }
        });
        jPanel_Card1.add(jButton_Modify);

        jButton_Remove.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jButton_Remove.setForeground(new java.awt.Color(51, 51, 51));
        jButton_Remove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/trash.png"))); // NOI18N
        jButton_Remove.setText("Remove");
        jButton_Remove.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_Remove.setEnabled(false);
        jButton_Remove.setMaximumSize(new java.awt.Dimension(123, 35));
        jButton_Remove.setMinimumSize(new java.awt.Dimension(95, 30));
        jButton_Remove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_RemoveActionPerformed(evt);
            }
        });
        jPanel_Card1.add(jButton_Remove);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel7))
                .addGap(30, 30, 30)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jComboBox_Province, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(72, 72, 72)
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox_District, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70)
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox_Commune, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTextField_Address))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel_Card1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jComboBox_Province, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(jComboBox_District, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(jComboBox_Commune, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextField_Address, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel_Card1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTable_Address.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Specific address", "Commune", "District", "Province"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable_Address.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_AddressMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable_Address);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loadData() {
        addresses = ac.getAllAddressByUserId(LoginFrame.userID);
        ac.loadTable(addresses, dtm);
        jComboBox_District.removeAllItems();
        jComboBox_Commune.removeAllItems();
        jTextField_Address.setText("");
    }

    private void loadProvince() {
        List<Province> provinces = ac.getAllProvince();
        for (Province province : provinces) {
            jComboBox_Province.addItem(province);
        }
    }

    private void jComboBox_ProvinceItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox_ProvinceItemStateChanged
        // TODO add your handling code here:
        jComboBox_District.removeAllItems();
        jComboBox_Commune.removeAllItems();

        List<District> districts = ac.getAllDistrictByProvinceId(((Province) jComboBox_Province.getSelectedItem()).getProvinceId());
        for (District district : districts) {
            jComboBox_District.addItem(district);
        }
    }//GEN-LAST:event_jComboBox_ProvinceItemStateChanged

    private void jComboBox_DistrictItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox_DistrictItemStateChanged
        // TODO add your handling code here:
        jComboBox_Commune.removeAllItems();
        if (jComboBox_District.getSelectedItem() != null) {
            List<Ward> wards = ac.getAllVillageByDistrictId(((District) jComboBox_District.getSelectedItem()).getDistrictId());
            for (Ward ward : wards) {
                jComboBox_Commune.addItem(ward);
            }
        }
    }//GEN-LAST:event_jComboBox_DistrictItemStateChanged

    private void jButton_AddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_AddActionPerformed
        String addr = new String(jTextField_Address.getText());

        if (addr.equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, MessageConstants.ADDRESS_NOT_BLANK, "Thông báo", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Address address = new Address();
        address.setSpecificAddress(addr);

        address.setWard((Ward) jComboBox_Commune.getSelectedItem());
        Response res = ac.addAddress(LoginFrame.userID, address);
        //JOptionPane.showMessageDialog(null, res.getMessage());
        if (res.getResponseCode() == 200) {
            JOptionPane.showMessageDialog(null, MessageConstants.SAVE_ADD_SUCCESS);
            loadData();
            jButton_Modify.setEnabled(false);
            jButton_Remove.setEnabled(false);
        } else {
            JOptionPane.showMessageDialog(null, "Error!!!");
            return;
        }
    }//GEN-LAST:event_jButton_AddActionPerformed

    private void jButton_ModifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ModifyActionPerformed
        // TODO add your handling code here:
        String addr = new String(jTextField_Address.getText());

        if (addr.equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, MessageConstants.ADDRESS_NOT_BLANK, "Thông báo", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Address address = new Address();
        address.setAddressId(addressId);
        address.setSpecificAddress(addr);
        address.setWard((Ward) jComboBox_Commune.getSelectedItem());
        Response res = ac.updateAddressByID(address);
//        JOptionPane.showMessageDialog(null, res.getMessage());
        if (res.getResponseCode() == 200) {
            JOptionPane.showMessageDialog(null, MessageConstants.UPDATE_ADD_SUCCESS);
            loadData();
            jButton_Modify.setEnabled(false);
            jButton_Remove.setEnabled(false);
        } else {
            JOptionPane.showMessageDialog(null, "Error!!!");
            return;
        }
    }//GEN-LAST:event_jButton_ModifyActionPerformed

    private void jButton_RemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_RemoveActionPerformed
        // TODO add your handling code here:
        int luaChon = JOptionPane.showConfirmDialog(this, MessageConstants.CONFIRM_REMOVE_ADDRESS, "OK", 0);
        if (luaChon == JOptionPane.CANCEL_OPTION) {
            return;
        } else if (luaChon == JOptionPane.OK_OPTION) {
            System.out.println(addressId);
            Response response = ac.deleteAddressByID(addressId);
            JOptionPane.showMessageDialog(this, response.getMessage());
            if (response.getResponseCode() == 200) {
                loadData();
                jButton_Modify.setEnabled(false);
                jButton_Remove.setEnabled(false);
            } else {
                return;
            }
        }
    }//GEN-LAST:event_jButton_RemoveActionPerformed

    private void jTable_AddressMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_AddressMouseClicked
        // TODO add your handling code here:
        int selectedRow = jTable_Address.convertRowIndexToModel(jTable_Address.getSelectedRow());

        Address addr = new Address();
        for (int i = 0; i < addresses.size(); i++) {
            if (dtm.getValueAt(selectedRow, 0) == addresses.get(i).getAddressId()) {
                addr = addresses.get(i);
                break;
            }
        }
        for (int i = 0; i < jComboBox_Province.getItemCount(); i++) {
            if (jComboBox_Province.getItemAt(i).getProvinceId().equals(addr.getWard().getDistrict().getProvince().getProvinceId())) {
                jComboBox_Province.setSelectedIndex(i);
            }
        }

        List<District> districts = ac.getAllDistrictByProvinceId(((Province) jComboBox_Province.getSelectedItem()).getProvinceId());
        for (District district : districts) {
            jComboBox_District.addItem(district);
        }
        for (int i = 0; i < jComboBox_District.getItemCount(); i++) {
            if (jComboBox_District.getItemAt(i).getDistrictId().equals(addr.getWard().getDistrict().getDistrictId())) {
                jComboBox_District.setSelectedIndex(i);
            }
        }

        List<Ward> wards = ac.getAllVillageByDistrictId(((District) jComboBox_District.getSelectedItem()).getDistrictId());
        for (Ward ward : wards) {
            jComboBox_Commune.addItem(ward);
        }
        for (int i = 0; i < jComboBox_Commune.getItemCount(); i++) {
            if (jComboBox_Commune.getItemAt(i).getWardId().equals(addr.getWard().getWardId())) {
                jComboBox_Commune.setSelectedIndex(i);
            }
        }

        jTextField_Address.setText(addr.getSpecificAddress());
        addressId = addr.getAddressId();

        jButton_Modify.setEnabled(true);
        jButton_Remove.setEnabled(true);
    }//GEN-LAST:event_jTable_AddressMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_Add;
    private javax.swing.JButton jButton_Modify;
    private javax.swing.JButton jButton_Remove;
    private javax.swing.JComboBox<Ward> jComboBox_Commune;
    private javax.swing.JComboBox<District> jComboBox_District;
    private javax.swing.JComboBox<Province> jComboBox_Province;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel_Card1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable_Address;
    private javax.swing.JTextField jTextField_Address;
    // End of variables declaration//GEN-END:variables
}
