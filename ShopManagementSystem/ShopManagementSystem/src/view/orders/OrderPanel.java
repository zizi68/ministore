/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.orders;

import controller.OrderController;
import controller.ReturnController;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import model.Order;
import model.OrderDetail;
import model.Response;
import model.Return;
import swing.UIController;
import view.login.LoginFrame;

/**
 *
 * @author Admin
 */
public class OrderPanel extends javax.swing.JPanel {
    
    public static int orderId;
    public final int CHO_XU_LY = 1;
    public final int DANG_GIAO = 3;    
    public final int YEU_CAU_HUY = 2;
    public final int DA_GIAO = 4;
    public final int DA_HUY = 5;
    public final int DA_TRA = 8;
    public final int DA_NHAN = 7;    
    public final int YEU_CAU_TRA = 9;
    private DefaultTableModel dtmOrder;
    private DefaultTableModel dtmProduct;
    private OrderController oc;
    private ReturnController rc;
    private ShipperDialog shipperDialog;
    private ReturnDetailDialog returnDetailDialog;
    public static String shipperId;

    private enum Mode {
        ADD,
        MODIFY,
        FREE
    }
    private Mode mode;
    private HistoryDialog history;

    /**
     * Creates new form StaffPanel
     */
    public OrderPanel() {
        initComponents();
        oc = new OrderController();
        rc = new ReturnController();
        dtmProduct = (DefaultTableModel) jTable_Product.getModel();
        dtmOrder = (DefaultTableModel) jTable_Order.getModel();
        UIController.setDefaultTableHeader(jTable_Order);
        UIController.setDefaultTableHeader(jTable_Product);

        loadData(1);
        setEditableForAll(false);
    }

    public void loadData(int statusId) {
        List<Order> list = oc.getOrderByStatusId(statusId);
        oc.loadTable(list, dtmOrder);
        jTextField_ID.setText("");
        jTextField_User.setText("");
        jTextField_Price.setText("");
        jTextField_Date.setText("");
        dtmProduct.setRowCount(0);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField_ID = new javax.swing.JTextField();
        jTextField_User = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField_Price = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable_Product = new javax.swing.JTable();
        jTextField_Date = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_Order = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jRadioButton_DaHuy = new javax.swing.JRadioButton();
        jRadioButton_ChoXacNhan = new javax.swing.JRadioButton();
        jRadioButton_DangGiao = new javax.swing.JRadioButton();
        jRadioButton_DaGiao = new javax.swing.JRadioButton();
        jRadioButton_YeuCauTra = new javax.swing.JRadioButton();
        jRadioButton_DaTra = new javax.swing.JRadioButton();
        jRadioButton_DaNhan = new javax.swing.JRadioButton();
        jPanel_Card3 = new javax.swing.JPanel();
        jButton_Yes = new javax.swing.JButton();
        jButton_No = new javax.swing.JButton();
        jButton_Search = new javax.swing.JButton();
        jTextField_NameSearch = new javax.swing.JTextField();
        jButton_ReturnInfo = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Order detail", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 2, 14), new java.awt.Color(153, 153, 153))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel1.setText("Order ID");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel2.setText("Username");

        jTextField_ID.setEditable(false);
        jTextField_ID.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        jTextField_User.setEditable(false);
        jTextField_User.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel3.setText("Total price");

        jTextField_Price.setEditable(false);
        jTextField_Price.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel4.setText("Date");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(153, 153, 153));
        jLabel5.setText("VNĐ");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Products", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 2, 14), new java.awt.Color(153, 153, 153))); // NOI18N

        jTable_Product.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Quantity", "Price"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable_Product);
        if (jTable_Product.getColumnModel().getColumnCount() > 0) {
            jTable_Product.getColumnModel().getColumn(0).setMaxWidth(70);
            jTable_Product.getColumnModel().getColumn(2).setMaxWidth(100);
            jTable_Product.getColumnModel().getColumn(3).setMaxWidth(100);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 505, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTextField_Date.setEditable(false);
        jTextField_Date.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField_ID, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                            .addComponent(jTextField_User)
                            .addComponent(jTextField_Date)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jTextField_Price)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5)))))
                .addGap(35, 35, 35)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField_ID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField_User, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField_Price, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField_Date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(45, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel14.setText("Search:");

        jTable_Order.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Order ID", "Username", "Total price", "Date", "Shipper", "Approver"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Double.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable_Order.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_OrderMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable_Order);
        if (jTable_Order.getColumnModel().getColumnCount() > 0) {
            jTable_Order.getColumnModel().getColumn(0).setMaxWidth(100);
        }

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Status", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 2, 14), new java.awt.Color(153, 153, 153))); // NOI18N

        jRadioButton_DaHuy.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(jRadioButton_DaHuy);
        jRadioButton_DaHuy.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jRadioButton_DaHuy.setText("Canceled");
        jRadioButton_DaHuy.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jRadioButton_DaHuyStateChanged(evt);
            }
        });

        jRadioButton_ChoXacNhan.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(jRadioButton_ChoXacNhan);
        jRadioButton_ChoXacNhan.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jRadioButton_ChoXacNhan.setSelected(true);
        jRadioButton_ChoXacNhan.setText("Being processed");
        jRadioButton_ChoXacNhan.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jRadioButton_ChoXacNhanStateChanged(evt);
            }
        });

        jRadioButton_DangGiao.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(jRadioButton_DangGiao);
        jRadioButton_DangGiao.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jRadioButton_DangGiao.setText("Delivering");
        jRadioButton_DangGiao.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jRadioButton_DangGiaoStateChanged(evt);
            }
        });

        jRadioButton_DaGiao.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(jRadioButton_DaGiao);
        jRadioButton_DaGiao.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jRadioButton_DaGiao.setText("Delivered");
        jRadioButton_DaGiao.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jRadioButton_DaGiaoStateChanged(evt);
            }
        });

        jRadioButton_YeuCauTra.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(jRadioButton_YeuCauTra);
        jRadioButton_YeuCauTra.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jRadioButton_YeuCauTra.setText("Request Return");
        jRadioButton_YeuCauTra.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jRadioButton_YeuCauTraStateChanged(evt);
            }
        });

        jRadioButton_DaTra.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(jRadioButton_DaTra);
        jRadioButton_DaTra.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jRadioButton_DaTra.setText("Returned");
        jRadioButton_DaTra.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jRadioButton_DaTraStateChanged(evt);
            }
        });

        jRadioButton_DaNhan.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(jRadioButton_DaNhan);
        jRadioButton_DaNhan.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jRadioButton_DaNhan.setText("Received");
        jRadioButton_DaNhan.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jRadioButton_DaNhanStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButton_ChoXacNhan)
                    .addComponent(jRadioButton_YeuCauTra)
                    .addComponent(jRadioButton_DaHuy))
                .addGap(46, 46, 46)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jRadioButton_DangGiao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jRadioButton_DaGiao)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jRadioButton_DaTra)
                            .addComponent(jRadioButton_DaNhan))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton_ChoXacNhan)
                    .addComponent(jRadioButton_DangGiao)
                    .addComponent(jRadioButton_DaGiao))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton_DaHuy)
                    .addComponent(jRadioButton_DaNhan))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton_DaTra)
                    .addComponent(jRadioButton_YeuCauTra)))
        );

        jPanel_Card3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel_Card3.setMaximumSize(new java.awt.Dimension(30000, 33));
        jPanel_Card3.setPreferredSize(new java.awt.Dimension(439, 30));
        jPanel_Card3.setLayout(new java.awt.GridLayout(0, 1, 35, 35));

        jButton_Yes.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jButton_Yes.setForeground(new java.awt.Color(51, 51, 51));
        jButton_Yes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Check.png"))); // NOI18N
        jButton_Yes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_Yes.setMaximumSize(new java.awt.Dimension(95, 30));
        jButton_Yes.setMinimumSize(new java.awt.Dimension(95, 30));
        jButton_Yes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_YesActionPerformed(evt);
            }
        });
        jPanel_Card3.add(jButton_Yes);

        jButton_No.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jButton_No.setForeground(new java.awt.Color(51, 51, 51));
        jButton_No.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/x.png"))); // NOI18N
        jButton_No.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton_No.setMaximumSize(new java.awt.Dimension(95, 33));
        jButton_No.setMinimumSize(new java.awt.Dimension(95, 30));
        jButton_No.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_NoActionPerformed(evt);
            }
        });
        jPanel_Card3.add(jButton_No);

        jButton_Search.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jButton_Search.setText("Search");
        jButton_Search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_SearchActionPerformed(evt);
            }
        });

        jTextField_NameSearch.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextField_NameSearchCaretUpdate(evt);
            }
        });

        jButton_ReturnInfo.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jButton_ReturnInfo.setText("Return Infomation");
        jButton_ReturnInfo.setEnabled(false);
        jButton_ReturnInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ReturnInfoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField_NameSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton_Search)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton_ReturnInfo)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel_Card3, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton_Search, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton_ReturnInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextField_NameSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel14)))
                        .addGap(25, 25, 25)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel_Card3, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    public void setEditableForAll(boolean editable) {
//        jDateChooser_DateOfBirth.setEnabled(editable);
//        jTextField_Name.setEditable(editable);
//        jTextField_PhoneNumber.setEditable(editable);
//        jTextField_Email.setEditable(editable);
//        jComboBox_Province.setEnabled(editable);
//        jComboBox_District.setEnabled(editable);
//        jComboBox_Commune.setEnabled(editable);
//        jComboBox_Role.setEnabled(editable);
    }

    private void jTable_OrderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_OrderMouseClicked
        // TODO add your handling code here:
        int selectedRow = jTable_Product.convertRowIndexToModel(jTable_Order.getSelectedRow());
        String id = jTable_Order.getValueAt(selectedRow, 0).toString();

        jTextField_ID.setText(id);
        jTextField_User.setText(jTable_Order.getValueAt(selectedRow, 1).toString());
        jTextField_Price.setText(jTable_Order.getValueAt(selectedRow, 2).toString());
        jTextField_Date.setText(jTable_Order.getValueAt(selectedRow, 3).toString());

        List<OrderDetail> list = oc.getOrderDetailByOrderId(Integer.parseInt(id));
        oc.loadProductTable(list, dtmProduct);
    }//GEN-LAST:event_jTable_OrderMouseClicked

    private void jButton_YesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_YesActionPerformed
        // TODO add your handling code here:
        String id = jTextField_ID.getText();
        if (id.equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(this, "There is no selected item");
            return;
        }

        int result = JOptionPane.showConfirmDialog(this, "Are you sure?");
        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        int orderId = Integer.parseInt(id);
        if (jRadioButton_ChoXacNhan.isSelected()) {
            shipperDialog = new ShipperDialog(null, true);
            shipperDialog.setVisible(true);
            if(shipperId == null)
                return;
            Response res = oc.updateOrderByID(orderId, DANG_GIAO, Integer.parseInt(shipperId), LoginFrame.userID);
            JOptionPane.showMessageDialog(null, oc.convertResponse(res.getMessage()).getMessage());
            if (res.getResponseCode() == 200) {
                loadData(CHO_XU_LY);
                shipperId = null;
            } else {
                return;
            }
        }
        
        if (jRadioButton_YeuCauTra.isSelected()) {
            Return r = rc.getReturnByOrderId(orderId);
            Response res = rc.updateReturnByID(r.getId(), 1);
            JOptionPane.showMessageDialog(null, rc.convertResponse(res.getMessage()).getMessage());
            if (res.getResponseCode() == 200) {
                loadData(YEU_CAU_TRA);
            } else {
                return;
            }
        }
    }//GEN-LAST:event_jButton_YesActionPerformed

    private void jButton_NoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_NoActionPerformed
        // TODO add your handling code here:
        String id = jTextField_ID.getText();
        if (id.equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(this, "There is no selected item");
            return;
        }

        int result = JOptionPane.showConfirmDialog(this, "Are you sure?");
        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        int orderId = Integer.parseInt(id);
        if (jRadioButton_ChoXacNhan.isSelected()) {
            Response res = oc.updateOrderByID(orderId, DA_HUY, 0, 0);
            JOptionPane.showMessageDialog(null, oc.convertResponse(res.getMessage()).getMessage());
            if (res.getResponseCode() == 200) {
                loadData(CHO_XU_LY);
            } else {
                return;
            }
        }
        
        if (jRadioButton_DangGiao.isSelected()) {
            Response res = oc.updateOrderByID(orderId, DA_HUY, 0, 0);
            JOptionPane.showMessageDialog(null, oc.convertResponse(res.getMessage()).getMessage());
            if (res.getResponseCode() == 200) {
                loadData(DANG_GIAO);
            } else {
                return;
            }
        }
        
        if (jRadioButton_YeuCauTra.isSelected()) {
            Return r = rc.getReturnByOrderId(orderId);
            Response res = rc.updateReturnByID(r.getId(), 0);
            JOptionPane.showMessageDialog(null, oc.convertResponse(res.getMessage()).getMessage());
            if (res.getResponseCode() == 200) {
                loadData(YEU_CAU_TRA);
            } else {
                return;
            }
        }
    }//GEN-LAST:event_jButton_NoActionPerformed

    public void setEnabledButton(boolean b) {
        jButton_Yes.setEnabled(b);
        jButton_No.setEnabled(b);
    }

    private void jRadioButton_ChoXacNhanStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jRadioButton_ChoXacNhanStateChanged
        // TODO add your handling code here:

        if (jRadioButton_ChoXacNhan.isSelected()) {
            filter("");
            loadData(CHO_XU_LY);
            setEnabledButton(true);
            jButton_ReturnInfo.setEnabled(false);
        }
    }//GEN-LAST:event_jRadioButton_ChoXacNhanStateChanged

    private void jRadioButton_DangGiaoStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jRadioButton_DangGiaoStateChanged
        // TODO add your handling code here:

        if (jRadioButton_DangGiao.isSelected()) {
            filter("");
            loadData(DANG_GIAO);
            setEnabledButton(false);
            jButton_No.setEnabled(true);
            jButton_ReturnInfo.setEnabled(false);
        }
    }//GEN-LAST:event_jRadioButton_DangGiaoStateChanged

    private void jRadioButton_DaGiaoStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jRadioButton_DaGiaoStateChanged
        // TODO add your handling code here:

        if (jRadioButton_DaGiao.isSelected()) {
            filter("");
            loadData(DA_GIAO);
            setEnabledButton(false);
            jButton_ReturnInfo.setEnabled(false);
        }
    }//GEN-LAST:event_jRadioButton_DaGiaoStateChanged

    private void jRadioButton_DaHuyStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jRadioButton_DaHuyStateChanged
        // TODO add your handling code here:

        if (jRadioButton_DaHuy.isSelected()) {
            filter("");
            loadData(DA_HUY);
            setEnabledButton(false);
            jButton_ReturnInfo.setEnabled(false);
        }
    }//GEN-LAST:event_jRadioButton_DaHuyStateChanged

    public void filter(String str) {
        TableRowSorter<TableModel> trs = new TableRowSorter<>(jTable_Order.getModel());
        jTable_Order.setRowSorter(trs);
        trs.setRowFilter(RowFilter.regexFilter(str));
    }

    private void jButton_SearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_SearchActionPerformed
        // TODO add your handling code here:
        String tuKhoa = jTextField_NameSearch.getText();
        TableRowSorter<TableModel> trs = new TableRowSorter<>(jTable_Order.getModel());
        jTable_Order.setRowSorter(trs);
        trs.setRowFilter(RowFilter.regexFilter("(?i)" + tuKhoa));
    }//GEN-LAST:event_jButton_SearchActionPerformed

    private void jTextField_NameSearchCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextField_NameSearchCaretUpdate
        // TODO add your handling code here:
        String tuKhoa = jTextField_NameSearch.getText();
        TableRowSorter<TableModel> trs = new TableRowSorter<>(jTable_Order.getModel());
        jTable_Order.setRowSorter(trs);
        trs.setRowFilter(RowFilter.regexFilter("(?i)" + tuKhoa));
    }//GEN-LAST:event_jTextField_NameSearchCaretUpdate

    private void jRadioButton_YeuCauTraStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jRadioButton_YeuCauTraStateChanged
        // TODO add your handling code here:
        if (jRadioButton_YeuCauTra.isSelected()) {
            filter("");
            loadData(YEU_CAU_TRA);
            setEnabledButton(true);
            jButton_ReturnInfo.setEnabled(true);
        }
    }//GEN-LAST:event_jRadioButton_YeuCauTraStateChanged

    private void jRadioButton_DaTraStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jRadioButton_DaTraStateChanged
        // TODO add your handling code here:
        if (jRadioButton_DaTra.isSelected()) {
            filter("");
            loadData(DA_TRA);
            setEnabledButton(false);
            jButton_ReturnInfo.setEnabled(true);
        }
    }//GEN-LAST:event_jRadioButton_DaTraStateChanged

    private void jRadioButton_DaNhanStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jRadioButton_DaNhanStateChanged
        // TODO add your handling code here:
        if (jRadioButton_DaNhan.isSelected()) {
            filter("");
            loadData(DA_NHAN);
            setEnabledButton(false);
            jButton_ReturnInfo.setEnabled(true);
        }
    }//GEN-LAST:event_jRadioButton_DaNhanStateChanged

    private void jButton_ReturnInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ReturnInfoActionPerformed
        // TODO add your handling code here:
        String id = jTextField_ID.getText();
        if (id.equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(this, "There is no selected item");
            return;
        }
        
        orderId = Integer.parseInt(id);
        
        Return return0 = rc.getReturnByOrderId(OrderPanel.orderId);
        if(return0.getId() == null){
            JOptionPane.showMessageDialog(null, "This order doesn't have return infomation");
            return;
        }
        
        returnDetailDialog = new ReturnDetailDialog(null, true);
        returnDetailDialog.setVisible(true);
    }//GEN-LAST:event_jButton_ReturnInfoActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton_No;
    private javax.swing.JButton jButton_ReturnInfo;
    private javax.swing.JButton jButton_Search;
    private javax.swing.JButton jButton_Yes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel_Card3;
    private javax.swing.JRadioButton jRadioButton_ChoXacNhan;
    private javax.swing.JRadioButton jRadioButton_DaGiao;
    private javax.swing.JRadioButton jRadioButton_DaHuy;
    private javax.swing.JRadioButton jRadioButton_DaNhan;
    private javax.swing.JRadioButton jRadioButton_DaTra;
    private javax.swing.JRadioButton jRadioButton_DangGiao;
    private javax.swing.JRadioButton jRadioButton_YeuCauTra;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable_Order;
    private javax.swing.JTable jTable_Product;
    private javax.swing.JTextField jTextField_Date;
    private javax.swing.JTextField jTextField_ID;
    private javax.swing.JTextField jTextField_NameSearch;
    private javax.swing.JTextField jTextField_Price;
    private javax.swing.JTextField jTextField_User;
    // End of variables declaration//GEN-END:variables
}
