/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package shivoni.gui;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import java.io.InputStream;
import java.security.interfaces.RSAKey;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import model.MySQL;
 import shivoni.gui.RepairAdd;

import shivoni.model.ReturnProductItem;

public class ReturnProductManagement extends javax.swing.JFrame {

    private static HashMap<String, String> conditionMap = new HashMap<>();
    HashMap<String, ReturnProductItem> returnProductMap = new HashMap<>();

    public ReturnProductManagement(String email) {

        try {

            UIManager.setLookAndFeel(new FlatMacDarkLaf());

        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        SwingUtilities.updateComponentTreeUI(this);
        this.revalidate();
        this.repaint();

        initComponents();
        jTextField5.setText(email);
        loadCondition();
        loadInvoiceItem();
        generateReturnId();

    }

    private void loadInvoiceItem() {
        try {

            String stockId = jTextField1.getText();

            String invoiceId = jTextField2.getText();

            String query = "SELECT * FROM `invoice_item` INNER JOIN `stock` ON `stock`.`id`=`invoice_item`.`stock_id` INNER JOIN `invoice` ON `invoice`.`id`=`invoice_item`.`invoice_id` INNER JOIN `product` ON `product`.`id`=`stock`.`product_id` INNER JOIN `warranty` ON `warranty`.`id`=`product`.`warranty_id`";
            List<String> conditions = new ArrayList<>();

            if (!stockId.isEmpty()) {

                conditions.add("`invoice_item`.`stock_id` LIKE '" + stockId + "%'");

            }

            if (!invoiceId.isEmpty()) {
                conditions.add("`invoice_item`.`invoice_id` LIKE '" + invoiceId + "%'");
            }

            conditions.add("`return_statement` = 'yes'");

            if (!conditions.isEmpty()) {
                query += " WHERE" + String.join(" AND", conditions);
            }

            //  query +=" AND `return_statement`='NULL' ";
            ResultSet resultSet = MySQL.executeSearch(query);

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);

            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();
                 vector.add(resultSet.getString("invoice_item.invoice_id"));
                vector.add(resultSet.getString("invoice.employee_email"));
                vector.add(resultSet.getString("invoice_item.stock_id"));
                vector.add(resultSet.getString("stock.selling_price"));
                vector.add(resultSet.getString("invoice_item.qty"));
                vector.add(resultSet.getString("warranty.period"));
                vector.add(resultSet.getString("warranty.conditions"));
              
                
                
                ResultSet rs2 = MySQL.executeSearch("SELECT * FROM `repair_request_item` INNER JOIN `repair_status` ON `repair_status`.`id`=`repair_request_item`.`repair_status_id` WHERE `stock_id`='"+resultSet.getString("invoice_item.stock_id")+"' AND `invoice_id`='"+resultSet.getString("invoice_item.invoice_id")+"' ");
                
                if(rs2.next()){
                    if(rs2.getString("repair_status.name").equals("Collected") ||rs2.getString("repair_status.name").equals("Canceled")){
                          vector.add("No"); 
                    }else{
                       vector.add("Yes");  
                    }
                  
                 
                }else{
                    vector.add("No"); 
                }

                model.addRow(vector);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadCondition() {

        try {

            ResultSet resultset = MySQL.executeSearch("SELECT * FROM `return_product_condition`");

            Vector<String> vector = new Vector<>();
            vector.add("Select");

            while (resultset.next()) {

                vector.add(resultset.getString("name"));
                conditionMap.put(resultset.getString("name"), resultset.getString("id"));

            }

            DefaultComboBoxModel dcm = new DefaultComboBoxModel(vector);
            jComboBox1.setModel(dcm);

        } catch (Exception e) {
        }

    }
    private double total;

    private void loadReturnProductItem() {

        DefaultTableModel model2 = (DefaultTableModel) jTable2.getModel();
        model2.setRowCount(0);
        total = 0;
        // double total = 0;

        for (ReturnProductItem returnProductItem : returnProductMap.values()) {
            Vector<String> vector = new Vector<>();

            vector.add(returnProductItem.getReturnID());
            vector.add(returnProductItem.getCondition());
            vector.add(returnProductItem.getStockID());
            vector.add(returnProductItem.getInvoiceID());
            vector.add(returnProductItem.getQty());
            vector.add(String.valueOf(returnProductItem.getStockPrice()));
            model2.addRow(vector);

            double itemTotal = Double.parseDouble(returnProductItem.getQty()) * returnProductItem.getStockPrice();
            total += itemTotal;
            jFormattedTextField1.setText(String.valueOf(total));

        }

    }

    private void searchReturnProductItem(String rid) {
        try {
            ResultSet rs = MySQL.executeSearch("SELECT * FROM `returnproduct` INNER JOIN `product_condition` ON `product_condition`.`id`=`returnproduct`.`product_condition_id` WHERE `returnId` LIKE '" + rid + "%' ");

            DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
            model.setRowCount(0);

            while (rs.next()) {
                Vector<String> vector = new Vector<>();

                vector.add(rs.getString("returnId"));
                vector.add(rs.getString("product_condition.name"));
                vector.add(rs.getString("stock_id"));
                vector.add(rs.getString("invoice_id"));
                vector.add(rs.getString("qty"));
                vector.add(rs.getString("returnPriceDiscount"));
                model.addRow(vector);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String returnId;

    private void generateReturnId() {
        long timestamp = System.currentTimeMillis();
        long randomNum = new Random().nextInt(9999);

        String returnId = timestamp + "-" + randomNum;
        this.returnId = returnId;
        jTextField4.setText(String.valueOf(returnId));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel8 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        saveReturnInvoiceButton = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        jLabel8.setText("jLabel8");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Return Product Management");
        setAlwaysOnTop(true);
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 204, 204));
        jLabel1.setText("Return Product Management");

        jLabel2.setText("Stock ID");

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        jLabel3.setText("Invoic ID");

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField2KeyReleased(evt);
            }
        });

        jLabel6.setText("Employee email");

        jTextField5.setEditable(false);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Invoice ID", "Employee Email(sold)", "Stock ID", "Price", "qty", "W.Period", "W.Condition", "Under Repair"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel5.setText("Condition");

        jLabel9.setText("ReturnProductID");

        jTextField4.setEditable(false);
        jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField4KeyReleased(evt);
            }
        });

        jButton3.setText("Clear");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel6)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton3))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel4.setText("QTY");

        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jLabel7.setText("Discount Price");

        jFormattedTextField1.setEditable(false);
        jFormattedTextField1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Condition", "Stock ID", "Invoice ID", "qty", "stock price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(jTable2);

        jButton1.setText("Add to Return Product");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        saveReturnInvoiceButton.setText("Save Return Invoice");
        saveReturnInvoiceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveReturnInvoiceButtonActionPerformed(evt);
            }
        });

        jButton2.setText("Clear All");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(414, 414, 414)
                                .addComponent(saveReturnInvoiceButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 791, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(21, 21, 21)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveReturnInvoiceButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2))
                .addGap(17, 17, 17))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased

        loadInvoiceItem();
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jTextField2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyReleased

        loadInvoiceItem();
    }//GEN-LAST:event_jTextField2KeyReleased

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked


    }//GEN-LAST:event_jTable1MouseClicked
private RepairAdd radd;
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int row = jTable1.getSelectedRow();
       

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            String invoiceid = String.valueOf(jTable1.getValueAt(row, 0));
            String stockid = String.valueOf(jTable1.getValueAt(row, 2));
            String returnPrice = String.valueOf(jTable1.getValueAt(row, 3));
            String repairStatus =String.valueOf(jTable1.getValueAt(row, 7)); 
            //   String returnQty =jTextField3.getText();
            //  String invoiceItmQty =  String.valueOf(jTable1.getValueAt(row, 4)) ;
            if(repairStatus.equals("Yes")){
                JOptionPane.showMessageDialog(this, "Product is under repair", "Warning", JOptionPane.WARNING_MESSAGE);
            }else if (jComboBox1.getSelectedItem().equals("Select")) {
                JOptionPane.showMessageDialog(this, "Please select a condition", "Warning", JOptionPane.WARNING_MESSAGE);
            }else if (jComboBox1.getSelectedItem().equals("Not Acceptable")) {
               int option1 =  JOptionPane.showConfirmDialog(this, "Do you want to repair this product, Invoice id: " +invoiceid +" and stock id: "+stockid,"Information",JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
               
               if(option1==JOptionPane.YES_OPTION){
                   this.dispose();
                   
                   if(radd==null || !radd.isDisplayable()){
                       radd = new RepairAdd();
                       radd.setVisible(true);
                       radd.getjTextField3().setText(invoiceid);
                       radd.invokeLoadInvoiceItems();
                   }else{
                       radd.toFront();
                       radd.requestFocus();
                   }
                   
               }
            } else if (jTextField3.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please type a quantity", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (!jTextField3.getText().matches("^\\d+$")) {
                JOptionPane.showMessageDialog(this, "Invalid Quantity ", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {

                Double returnQty = Double.parseDouble(jTextField3.getText().trim());

                Double invoiceItmQty = Double.parseDouble(jTable1.getValueAt(row, 4).toString().trim());
                Double discountPrice = returnQty * Double.parseDouble(returnPrice);

                if (returnQty > invoiceItmQty) {
                    JOptionPane.showMessageDialog(this, "Add valid qty less than " + invoiceItmQty, "Warning", JOptionPane.WARNING_MESSAGE);
                } else {

                    ReturnProductItem rpi = new ReturnProductItem();
                    rpi.setReturnID(returnId);
                    rpi.setCondition(conditionMap.get(String.valueOf(jComboBox1.getSelectedItem())));
                    rpi.setEmpEmail(String.valueOf(jTextField5.getText()));
                    rpi.setInvoiceID(invoiceid);
                    rpi.setQty(String.valueOf(returnQty));
                    rpi.setStockID(stockid);
                    rpi.setStockPrice(Double.parseDouble(returnPrice));

                    String uniqueKey = invoiceid + "-" + stockid;

                    if (returnProductMap.get(uniqueKey) == null) {

                        returnProductMap.put(uniqueKey, rpi);

                    } else {
                        ReturnProductItem found = returnProductMap.get(uniqueKey);

                        Double newQty = Double.parseDouble(found.getQty()) + returnQty;

                        if (newQty > invoiceItmQty) {
                            JOptionPane.showMessageDialog(this, "Add valid qty less than " + invoiceItmQty, "Warning", JOptionPane.WARNING_MESSAGE);

                        } else {

                            int option = JOptionPane.showConfirmDialog(this,
                                    "Do you want to update quantity of the stock(return):" + stockid,
                                    "Information", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

                            if (option == JOptionPane.YES_OPTION) {

                                found.setQty(String.valueOf(Double.parseDouble(found.getQty()) + returnQty));

                            }

                        }

                    }

                    loadReturnProductItem();

                }

            }

        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void saveReturnInvoiceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveReturnInvoiceButtonActionPerformed

        if (jFormattedTextField1.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please add a return product", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

//  int row = jTable2.getSelectedRow();
            //     String returnId = this.returnId;
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            //    String finalreturnQty = String.valueOf(jTable2.getValueAt(row, 4));
            String empEmail = jTextField5.getText();
            //   String invoiceId = String.valueOf(jTable2.getValueAt(row, 3));
            //    String stockID = String.valueOf(jTable2.getValueAt(row, 2));
            //    String condition = String.valueOf(jTable2.getValueAt(row, 1));
            String returnPriceDiscount = jFormattedTextField1.getText();

            try {

                for (ReturnProductItem returnPItem : returnProductMap.values()) {

                    //insert to returnProduct table
                    MySQL.executeIUD("INSERT INTO `return_product` "
                            + "(`returnId`, `return_date`, `qty`,`return_PriceDiscount`,`employee_email`, `invoice_id`, `stock_id`, `return_product_condition_id`) "
                            + "VALUES ('" + jTextField4.getText() + "', '" + date + "', '" + returnPItem.getQty() + "', '" + returnPItem.getStockPrice() + "', '"
                            + empEmail + "', '" + returnPItem.getInvoiceID() + "', '" + returnPItem.getStockID() + "', '"
                            + returnPItem.getCondition() + "')");

                    //update invoice item 
                    if (returnPItem.getCondition().equals("1")) {

                        MySQL.executeIUD("UPDATE `stock` SET `qty`=`qty`+'" + returnPItem.getQty() + "' WHERE `id`='" + returnPItem.getStockID() + "' ");
                    }

                    MySQL.executeIUD("UPDATE `invoice_item` SET `return_statement`='no' WHERE `stock_id`='" + returnPItem.getStockID() + "' AND `invoice_id`='" + returnPItem.getInvoiceID() + "' ");
                    loadInvoiceItem();
                    
                    JOptionPane.showMessageDialog(this, "Success", "Information", JOptionPane.INFORMATION_MESSAGE);

                }
                

                clearAll();

                // JOptionPane.showMessageDialog(this, "Return Products Saved", "Information", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }//GEN-LAST:event_saveReturnInvoiceButtonActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        jTable1.clearSelection();
        jTable2.clearSelection();
        jComboBox1.setSelectedIndex(0);
        generateReturnId();
        clearAll();


    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        jTextField1.setEditable(false);
        jTextField2.setEditable(false);
        jTextField3.setEditable(false);
        saveReturnInvoiceButton.setEnabled(false);
        jComboBox1.setSelectedIndex(0);
      //  jButton1.setEnabled(false);
        jComboBox1.setEditable(false);
        jTable1.setEnabled(true);
        jTable1.clearSelection();
        generateReturnId();
        //  jTable2.setEnabled(false);

        jTextField4.setEditable(true);
        jTextField4.setText("");


    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTextField4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyReleased
        String returnProductId = jTextField4.getText();

        searchReturnProductItem(returnProductId);
    }//GEN-LAST:event_jTextField4KeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JButton saveReturnInvoiceButton;
    // End of variables declaration//GEN-END:variables

    private void clearAll() {
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        jTable1.clearSelection();
        jTable2.clearSelection();
        jFormattedTextField1.setText("");
        generateReturnId();
        returnProductMap.clear();
        loadReturnProductItem();
    }
}
