/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package rashmika.gui;

import com.myapp.themes.MyCustomLaf;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import pasan.gui.ProductRegistration;
import pasan.gui.companyRegistration;
import pasan.gui.supplierRegistration;
import rashmika.model.GRNItems;
import model.MySQL;
import java.sql.ResultSet;
import java.util.Date;

/**
 *
 * @author VICTUS
 */
public class GRN extends javax.swing.JFrame {

    HashMap<String, GRNItems> grnItemMap = new HashMap<>();

    //product Id
    public JTextField getProductId() {
        return jTextField2;
    }
    //product Name

    public JLabel getProductName() {
        return jLabel6;
    }

    //model
    public JLabel getModel() {
        return jLabel8;
    }

    //brand
    public JLabel getBrand() {
        return jLabel10;
    }

    //category 
    public JLabel getCategory() {
        return jLabel12;
    }

    // waranty
    public JLabel getWaranty() {
        return jLabel14;
    }

    //Company Name
    public JLabel getCompanyName() {
        return jLabel20;
    }

    //Company branch
    public JLabel getBranch() {
        return jLabel22;
    }

    //Company hotline
    public JLabel getHotline() {
        return jLabel24;
    }

    //Supplier Name
    public JLabel getSupplier() {
        return jLabel26;
    }

    //Supplier Contact
    public JTextField getContact() {
        return jTextField3;
    }

    public GRN() {
        initComponents();
        genarateGRNid();

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        jTable1.setDefaultRenderer(Object.class, renderer);

        jTextField1.setEditable(false);
        jTextField2.setEditable(false);

        jLabel3.setText("shivoni@gmail.com");
    }

    public void genarateGRNid() {

        long mTime = System.currentTimeMillis();
        int customId = (int) (mTime % 1000000); // Get the last 6 digits
        String formattedId = String.format("%04d", customId); // Format to ensure it's always 6 digits

        String customText = "CM";
        String finalId = customText + formattedId;

        jTextField1.setText(finalId);
        jButton5.setEnabled(false);
    }

    public void loadGRNItem() {

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        double total = 0;

        for (GRNItems gRNItems : grnItemMap.values()) {

            Vector<String> vector = new Vector<>();

            vector.add(gRNItems.getProductId());
            vector.add(gRNItems.getProductName());
            vector.add(gRNItems.getModel());
            vector.add(gRNItems.getBrand());
            vector.add(gRNItems.getCategory());
            vector.add(gRNItems.getWaranty());
            vector.add(String.valueOf(gRNItems.getSellingPrice()));
            vector.add(String.valueOf(gRNItems.getBuyingPrice()));
            vector.add(String.valueOf(gRNItems.getQuantity()));

            double itemTotal = gRNItems.getQuantity() * gRNItems.getBuyingPrice();

            total += itemTotal;

            vector.add(String.valueOf(itemTotal));

            model.addRow(vector);
        }

        jLabel30.setText(String.valueOf(total));

    }

    public void calculate() {

        String total = jLabel30.getText();
        String payment = jFormattedTextField4.getText();

        if (payment.isEmpty()) {
            payment = "0";
        } else if (!payment.matches("^(0|[1-9]\\d*)?(\\.\\d+)?(?<=\\d)$")) {
            jLabel33.setText("invalid");
        } else {

            double balance = Double.parseDouble(payment) - Double.parseDouble(total);
            jLabel33.setText(String.valueOf(balance));

        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jLabel16 = new javax.swing.JLabel();
        jFormattedTextField2 = new javax.swing.JFormattedTextField();
        jPanel8 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel18 = new javax.swing.JLabel();
        jFormattedTextField3 = new javax.swing.JFormattedTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jFormattedTextField4 = new javax.swing.JFormattedTextField();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setPreferredSize(new java.awt.Dimension(853, 200));
        jPanel1.setLayout(new java.awt.GridLayout(1, 7));

        jPanel7.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanel7.setLayout(new java.awt.GridLayout(4, 0, 0, 10));

        jLabel1.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Add New GRN");
        jPanel7.add(jLabel1);

        jLabel2.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("GRN ID");
        jPanel7.add(jLabel2);

        jTextField1.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel7.add(jTextField1);

        jLabel3.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Employee");
        jPanel7.add(jLabel3);

        jPanel1.add(jPanel7);

        jPanel6.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanel6.setLayout(new java.awt.GridLayout(4, 0, 0, 10));

        jLabel4.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Product Id");
        jPanel6.add(jLabel4);

        jTextField2.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jTextField2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel6.add(jTextField2);

        jLabel5.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Product Name");
        jPanel6.add(jLabel5);

        jLabel6.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("NULL");
        jPanel6.add(jLabel6);

        jPanel1.add(jPanel6);

        jPanel10.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanel10.setLayout(new java.awt.GridLayout(4, 0, 0, 10));

        jLabel7.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Model");
        jPanel10.add(jLabel7);

        jLabel8.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 51, 51));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("NULL");
        jPanel10.add(jLabel8);

        jLabel9.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Brand");
        jPanel10.add(jLabel9);

        jLabel10.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("NULL");
        jPanel10.add(jLabel10);

        jPanel1.add(jPanel10);

        jPanel5.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanel5.setLayout(new java.awt.GridLayout(5, 0, 0, 10));

        jLabel11.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Category");
        jPanel5.add(jLabel11);

        jLabel12.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("NULL");
        jPanel5.add(jLabel12);

        jLabel13.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Warranty");
        jPanel5.add(jLabel13);

        jLabel14.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("NULL");
        jPanel5.add(jLabel14);

        jButton2.setText("SELECT A PRODUCT");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton2);

        jPanel1.add(jPanel5);

        jPanel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanel4.setLayout(new java.awt.GridLayout(4, 0, 0, 10));

        jLabel15.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Buying Price");
        jPanel4.add(jLabel15);

        jFormattedTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel4.add(jFormattedTextField1);

        jLabel16.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Selling Price");
        jPanel4.add(jLabel16);

        jFormattedTextField2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel4.add(jFormattedTextField2);

        jPanel1.add(jPanel4);

        jPanel8.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanel8.setLayout(new java.awt.GridLayout(4, 0, 0, 10));

        jLabel17.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("GRN Date");
        jPanel8.add(jLabel17);
        jPanel8.add(jDateChooser1);

        jLabel18.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Quantity");
        jPanel8.add(jLabel18);

        jFormattedTextField3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel8.add(jFormattedTextField3);

        jPanel1.add(jPanel8);

        jPanel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanel3.setLayout(new java.awt.GridLayout(6, 0, 0, 10));

        jLabel19.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("Company Name");
        jPanel3.add(jLabel19);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("NULL");
        jPanel3.add(jLabel20);

        jLabel21.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("Branch");
        jPanel3.add(jLabel21);

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("NULL");
        jPanel3.add(jLabel22);

        jLabel23.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("Hotline");
        jPanel3.add(jLabel23);

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("NULL");
        jPanel3.add(jLabel24);

        jPanel1.add(jPanel3);

        jPanel9.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanel9.setLayout(new java.awt.GridLayout(5, 0, 0, 10));

        jLabel25.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("Supplire Name");
        jPanel9.add(jLabel25);

        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("NULL");
        jPanel9.add(jLabel26);

        jLabel27.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("Supplier Contact Number");
        jPanel9.add(jLabel27);

        jTextField3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel9.add(jTextField3);

        jButton1.setText("SELECT SUPPLIER");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel9.add(jButton1);

        jPanel1.add(jPanel9);

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel11.setPreferredSize(new java.awt.Dimension(1296, 50));

        jButton3.setText("Cancel");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Add to GRN");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(857, Short.MAX_VALUE)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 436, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel2.add(jPanel11, java.awt.BorderLayout.PAGE_START);

        jPanel12.setPreferredSize(new java.awt.Dimension(1296, 200));

        jPanel14.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanel14.setLayout(new java.awt.GridLayout(3, 2));

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel29.setText("Total");
        jPanel14.add(jLabel29);

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel30.setText("0.00");
        jPanel14.add(jLabel30);

        jLabel31.setText("Payment");
        jPanel14.add(jLabel31);

        jFormattedTextField4.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jFormattedTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextField4ActionPerformed(evt);
            }
        });
        jFormattedTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jFormattedTextField4KeyReleased(evt);
            }
        });
        jPanel14.add(jFormattedTextField4);

        jLabel32.setText("Balance");
        jPanel14.add(jLabel32);

        jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel33.setText("0.00");
        jPanel14.add(jLabel33);

        jButton5.setText("Save GRN");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap(1038, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2.add(jPanel12, java.awt.BorderLayout.PAGE_END);

        jPanel13.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanel13.setLayout(new javax.swing.OverlayLayout(jPanel13));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Product Id", "Product Name", "Model", "Brand", "Category", "Waranty", "Selling Price", "Buying Price", "Quantity", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel13.add(jScrollPane1);

        jPanel2.add(jPanel13, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        ProductRegistration pr = new ProductRegistration();
        pr.setVisible(true);
        pr.setGRN(this);

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        supplierRegistration sr = new supplierRegistration();
        sr.setVisible(true);
        sr.setGrn(this);

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        String employee = jLabel3.getText();

        String productId = jTextField2.getText();
        String productName = jLabel6.getText();
        String model = jLabel8.getText();
        String brand = jLabel10.getText();
        String category = jLabel12.getText();
        String waranty = jLabel14.getText();
        String buyingPrice = jFormattedTextField1.getText();
        String sellingPrice = jFormattedTextField2.getText();
        String quantity = jFormattedTextField3.getText();
        String supplierContact = jTextField3.getText();

        if (employee == null) {
            JOptionPane.showMessageDialog(this, "Loging first", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (productId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Select a Prooduct", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            try {

                if (buyingPrice.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please Enter the Buying Price", "Warning", JOptionPane.WARNING_MESSAGE);

                } else if (Double.parseDouble(buyingPrice) < 0) {
                    JOptionPane.showMessageDialog(this, "Please Check the Buying Price", "Warning", JOptionPane.WARNING_MESSAGE);

                } else if (!buyingPrice.matches("^\\d+(\\.\\d+)?$")) {
                    JOptionPane.showMessageDialog(this, "Please Check(symbols) the Buying Price", "Warning", JOptionPane.WARNING_MESSAGE);

                } else if (sellingPrice.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please Enter the Buying Price", "Warning", JOptionPane.WARNING_MESSAGE);

                } else if (Double.parseDouble(sellingPrice) < 0) {
                    JOptionPane.showMessageDialog(this, "Please Check the Selling Price", "Warning", JOptionPane.WARNING_MESSAGE);

                } else if (!sellingPrice.matches("^\\d+(\\.\\d+)?$")) {
                    JOptionPane.showMessageDialog(this, "Please Check(symbols) the Selling Price", "Warning", JOptionPane.WARNING_MESSAGE);

                } else if (quantity.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please Enter the Quantity", "Warning", JOptionPane.WARNING_MESSAGE);

                } else if (Integer.parseInt(quantity) < 0) {
                    JOptionPane.showMessageDialog(this, "Please Enter the valid Quantity", "Warning", JOptionPane.WARNING_MESSAGE);

                } else if (supplierContact.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please Select A Supplier", "Warning", JOptionPane.WARNING_MESSAGE);

                } else {

                    GRNItems gRNItems = new GRNItems();

                    gRNItems.setProductId(productId);
                    gRNItems.setProductName(productName);
                    gRNItems.setModel(model);
                    gRNItems.setBrand(brand);
                    gRNItems.setCategory(category);
                    gRNItems.setWaranty(waranty);
                    gRNItems.setSellingPrice(Double.parseDouble(sellingPrice));
                    gRNItems.setBuyingPrice(Double.parseDouble(buyingPrice));
                    gRNItems.setQuantity(Integer.parseInt(quantity));

                    if (grnItemMap.get(jTextField2.getText()) == null) {
                        grnItemMap.put(jTextField2.getText(), gRNItems);
                        loadGRNItem();
                    } else {

                        GRNItems found = grnItemMap.get(jTextField2.getText());

                        if (found.getBuyingPrice() == Double.parseDouble(buyingPrice)
                                && found.getSellingPrice() == Double.parseDouble(sellingPrice)) {

                            int option = JOptionPane.showConfirmDialog(this, "Do you you Want to Update the Quantity of The Product :" + productName, "Message",
                                    JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

                            if (option == JOptionPane.YES_OPTION) {
                                found.setQuantity(found.getQuantity() + Integer.parseInt(quantity));
                                loadGRNItem();
                            }

                        } else {

                            JOptionPane.showMessageDialog(this, "GRN Item ALready adedd", "Warning", JOptionPane.ERROR_MESSAGE);
                        }

                    }

                    jButton5.setEnabled(true);

                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid Input", "Warning", JOptionPane.WARNING_MESSAGE);
            }

        }

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jFormattedTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextField4ActionPerformed

    }//GEN-LAST:event_jFormattedTextField4ActionPerformed

    private void jFormattedTextField4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField4KeyReleased
        String total = jLabel30.getText();
        String payment = jFormattedTextField4.getText();

        if (payment.isEmpty()) {
            payment = "0";
        } else if (!payment.matches("^(0|[1-9]\\d*)?(\\.\\d+)?(?<=\\d)$")) {
            jLabel33.setText("invalid");
        } else {

            double balance = Double.parseDouble(payment) - Double.parseDouble(total);
            jLabel33.setText(String.valueOf(balance));

        }
    }//GEN-LAST:event_jFormattedTextField4KeyReleased

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

        if (jFormattedTextField4.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Check the payment", "Warning", JOptionPane.WARNING_MESSAGE);

        } else {
            try {

                String grnId = jTextField1.getText();
                String supplierMoblie = jTextField3.getText();
                String employeeEmail = jLabel3.getText();
                String paidAmount = jFormattedTextField4.getText();

                String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

                MySQL.executeIUD("INSERT INTO `grn` (`id`,`date`,`paid_amount`,`employee_email`,`Supplier_mobile`)"
                        + " VALUES ('" + grnId + "','" + date + "','" + paidAmount + "','" + employeeEmail + "','" + supplierMoblie + "')");

                for (GRNItems gRNItems : grnItemMap.values()) {

                    ResultSet resultSet = MySQL.executeSearch("SELECT * FROM `stock` WHERE"
                            + "`product_id` = '" + gRNItems.getProductId() + "' AND "
                            + "`selling_price` = '" + gRNItems.getSellingPrice() + "'");

                    String stockId = "";

                    if (resultSet.next()) {
                        //exsisting stock

                        stockId = resultSet.getString("id");

                        String currentQty = resultSet.getString("qty");
                        String updateQty = String.valueOf(Double.parseDouble(currentQty) + gRNItems.getQuantity());

                        MySQL.executeIUD("UPDATE `stock` SET `qty` = '" + updateQty + "'"
                                + " WHERE `id` = '" + stockId + "'");

                    } else {

                        //new Stock
                        MySQL.executeIUD("INSERT INTO `stock` (`product_id`,`qty`,`selling_price`) VALUES"
                                + " ('" + gRNItems.getProductId() + "','" + gRNItems.getQuantity() + "','" + gRNItems.getSellingPrice() + "')");

                        ResultSet resultSet2 = MySQL.executeSearch("SELECT * FROM `stock` WHERE"
                                + "`product_id` = '" + gRNItems.getProductId() + "' AND "
                                + "`selling_price` = '" + gRNItems.getSellingPrice() + "'");

                        if (resultSet2.next()) {
                            stockId = resultSet2.getString("id");
                        }

                    }

                    MySQL.executeIUD("INSERT INTO `grn_item` (`stock_id`,`qty`,`buying_price`,`grn_id`) VALUES "
                            + " ('" + stockId + "','" + gRNItems.getQuantity() + "','" + gRNItems.getBuyingPrice() + "','" + grnId + "')");

                }

                JOptionPane.showMessageDialog(this, "Grn Aded Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                reset();
                genarateGRNid();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        reset();
    }//GEN-LAST:event_jButton3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        MyCustomLaf.setup();

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GRN().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JFormattedTextField jFormattedTextField2;
    private javax.swing.JFormattedTextField jFormattedTextField3;
    private javax.swing.JFormattedTextField jFormattedTextField4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables

    private void reset() {
        jTextField2.setText("");
        jLabel6.setText("NULL");
        jLabel8.setText("NULL");
        jLabel10.setText("NULL");
        jLabel12.setText("NULL");
        jLabel14.setText("NULL");
        jFormattedTextField1.setText("");
        jFormattedTextField2.setText("");
        jFormattedTextField3.setText("");
        jFormattedTextField4.setText("");
        jLabel20.setText("NULL");
        jLabel22.setText("NULL");
        jLabel24.setText("NULL");
        jLabel26.setText("NULL");
        jTextField3.setText("");
        jLabel30.setText("0.00");
        jLabel33.setText("0.00");
        
        DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
        model.setRowCount(0);
        
        grnItemMap.clear();

    }
}
