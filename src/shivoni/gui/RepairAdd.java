/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package shivoni.gui;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import model.MySQL;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import model.RepairProductItem;

/**
 *
 * @author Anne
 */
public class RepairAdd extends javax.swing.JFrame {

    private HashMap<String, RepairProductItem> repairProductMap = new HashMap<>();

    public RepairAdd() {
        initComponents();
        loadInvoiceItems();
        generateRepairProductId();
        jTextField2.setText("shivoni@gmail.com");
    }
    
    public JTextField getjTextField3(){
        return jTextField3;
    }

    private String repairID;

    private void generateRepairProductId() {

        long timestamp = System.currentTimeMillis();
        long randomNum = new Random().nextInt(9999);

        String repairID = timestamp + "RI" + randomNum;
        this.repairID = repairID;
        jTextField1.setText(String.valueOf(repairID));

    }

    private void loadInvoiceItems() {

        try {
            String query = "SELECT * FROM `invoice_item` INNER JOIN `stock` ON `stock`.`id`=`invoice_item`.`stock_id` INNER JOIN `invoice` ON `invoice`.`id`=`invoice_item`.`invoice_id`  INNER JOIN `product` ON `product`.`id`=`stock`.`product_id` INNER JOIN `warranty` ON `warranty`.`id`=`product`.`warranty_id`";

            String iid = jTextField3.getText();

            List<String> conditions = new ArrayList<>();

            if (!iid.isEmpty()) {
                conditions.add("`invoice`.`id` LIKE '" + iid + "%'");
            }

            conditions.add("`return_statement`='yes'");

            if (!conditions.isEmpty()) {
                query += " WHERE" + String.join(" AND", conditions);
            }

            ResultSet rs = MySQL.executeSearch(query);

            DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
            dtm.setRowCount(0);

            while (rs.next()) {
                Vector<String> vector = new Vector<>();

                vector.add(rs.getString("invoice.id"));
                vector.add(rs.getString("invoice.employee_email"));
                vector.add(rs.getString("stock.id"));
                vector.add(rs.getString("product.name"));
                vector.add(rs.getString("stock.selling_price"));
                vector.add(rs.getString("invoice_item.qty"));
                vector.add(rs.getString("warranty.period"));
                vector.add(rs.getString("warranty.conditions"));
                vector.add(rs.getString("invoice.date"));

                ResultSet rs2 = MySQL.executeSearch("select * from `repair_request_item` where `invoice_id`='" + rs.getString("invoice.id") + "' and `stock_id`='" + rs.getString("stock.id") + "'  ");

                if (rs2.next()) {
                    vector.add("yes");
                } else {
                    vector.add("no");
                }

                dtm.addRow(vector);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    public void invokeLoadInvoiceItems() {
    loadInvoiceItems();
    
    }

    private void loadOldRepairInvoiceItems() {

        try {

            int row = jTable2.getSelectedRow();
            //  String oldRepairId = String.valueOf(jTable2.getValueAt(row, 0));
            String oldRepairInvoiceId = String.valueOf(jTable2.getValueAt(row, 1));
            String oldRepairStockId = String.valueOf(jTable2.getValueAt(row, 2));

            String query = "SELECT * FROM `invoice_item` INNER JOIN `stock` ON `stock`.`id`=`invoice_item`.`stock_id` INNER JOIN `invoice` ON `invoice`.`id`=`invoice_item`.`invoice_id`  INNER JOIN `product` ON `product`.`id`=`stock`.`product_id` INNER JOIN `warranty` ON `warranty`.`id`=`product`.`warranty_id` WHERE `invoice_id`='" + oldRepairInvoiceId + "' AND `stock_id`='" + oldRepairStockId + "' ";

            ResultSet rs = MySQL.executeSearch(query);

            DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
            dtm.setRowCount(0);

            while (rs.next()) {
                Vector<String> vector = new Vector<>();

                vector.add(rs.getString("invoice.id"));
                vector.add(rs.getString("invoice.employee_email"));
                vector.add(rs.getString("stock.id"));
                vector.add(rs.getString("product.name"));
                vector.add(rs.getString("stock.selling_price"));
                vector.add(rs.getString("invoice_item.qty"));
                vector.add(rs.getString("warranty.period"));
                vector.add(rs.getString("warranty.conditions"));
                vector.add(rs.getString("invoice.date"));

                ResultSet rs2 = MySQL.executeSearch("select * from `repair_request_item` where `invoice_id`='" + rs.getString("invoice.id") + "' and `stock_id`='" + rs.getString("stock.id") + "'  ");

                if (rs2.next()) {
                    vector.add("yes");
                } else {
                    vector.add("no");
                }

                dtm.addRow(vector);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void loadRepairProductItem() {
        DefaultTableModel dtm2 = (DefaultTableModel) jTable3.getModel();
        dtm2.setRowCount(0);

        for (RepairProductItem repairPI : repairProductMap.values()) {
            Vector<String> vector = new Vector<>();

            vector.add(repairPI.getRepairID());
            vector.add(String.valueOf(repairPI.getStockId()));
            vector.add(repairPI.getProblem());
            vector.add(String.valueOf(repairPI.getQty()));
            vector.add(String.valueOf(repairPI.getInvoiceId()));
            dtm2.addRow(vector);

        }

    }

    private void loadOldRepairProducts(String oldInvoiceId) {

        try {

            String query = "SELECT * FROM `repair_request_item` inner join `repair_status` on `repair_status`.`id`=`repair_request_item`.`repair_status_id` inner join `repair_request` on `repair_request`.`repair_id`=`repair_request_item`.`repair_request_repair_id` WHERE `repair_request_item`.`invoice_id` like '" + oldInvoiceId + "%'";

            List<String> conditions = new ArrayList();

            int row = jTable1.getSelectedRow();
            if (row != -1) {
                String oldStockId = String.valueOf(jTable1.getValueAt(row, 2));

                conditions.add("`repair_request_item`.`stock_id` LIKE '" + oldStockId + "%' ");

                if (!conditions.isEmpty()) {
                    query += "AND" + String.join(" AND", conditions);
                }
            }

            query += "ORDER BY `repair_request_item`.`repair_iteration` DESC ";

            ResultSet rs = MySQL.executeSearch(query);

            DefaultTableModel dtm = (DefaultTableModel) jTable2.getModel();
            dtm.setRowCount(0);

            while (rs.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(rs.getString("repair_request.repair_id"));
                vector.add(rs.getString("repair_request_item.invoice_id"));
                vector.add(rs.getString("stock_id"));
                vector.add(rs.getString("repair_status.name"));
                vector.add(rs.getString("repair_request_item.repair_iteration"));
                dtm.addRow(vector);

            }

        } catch (Exception e) {
            e.printStackTrace();
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton4 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jButton5 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add Repair");
        setAlwaysOnTop(true);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Log New Repair");

        jLabel2.setText("Repair ID");

        jTextField1.setEditable(false);
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel3.setText("Employee");

        jTextField2.setEditable(false);
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jLabel4.setText("Invoice ID");

        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });
        jTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField3KeyReleased(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Invoice ID", "Employee Email(sold)", "StockID", "Product Name", "Price", "Qty", "Warranty", "W.Conditions", "P.Date", "Repair History"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
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
        jScrollPane2.setViewportView(jTable1);

        jLabel8.setText("Problem");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton4.setText("Add New Repair");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel5.setText("Quantity");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 475, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2))
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                            .addComponent(jTextField3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5))
                        .addGap(42, 42, 42)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(19, 19, 19)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(26, 26, 26)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jLabel8))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(jButton4))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Repair ID", "Stock ID", "Problem", "Qty", "Invoice ID"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable3.getTableHeader().setReorderingAllowed(false);
        jTable3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable3MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jTable3);

        jButton5.setText("Save New Repair");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(153, 0, 0));
        jLabel6.setText("Old Repair");

        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });
        jTextField5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField5KeyReleased(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Repair Id", "Invoice ID", "Stock ID", "Repair Status", "Repair Term"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.getTableHeader().setReorderingAllowed(false);
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable2);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel6)
                        .addGap(0, 267, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTextField5)))
                .addContainerGap())
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel6)
                .addGap(28, 28, 28)
                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                .addContainerGap())
        );

        jButton3.setText("Clear All");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3)
                        .addGap(27, 27, 27)))
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyReleased
        loadInvoiceItems();
    }//GEN-LAST:event_jTextField3KeyReleased

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        int row = jTable1.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            boolean canAdd = false;
            
            if (jTable2.getRowCount() > 0) {
                jTable2.setRowSelectionInterval(0, 0);
                String oldRepairStatus = String.valueOf(jTable2.getValueAt(0, 3));
                if (!oldRepairStatus.equals("Collected")) {
                    JOptionPane.showMessageDialog(this, "This product is still in repair process", "Warning", JOptionPane.WARNING_MESSAGE);
                    
                }else{
                    canAdd=true;
                }
            }else{
               canAdd=true; 
            }
            
            if(canAdd){
                if (jTextField4.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please type quantity", "Warning", JOptionPane.WARNING_MESSAGE);
                } else if (!jTextField4.getText().matches("^\\d+$")) {
                    JOptionPane.showMessageDialog(this, "Invalid Quantity ", "Warning", JOptionPane.WARNING_MESSAGE);
                } else if (jTextArea1.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please type the problem", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {

                    Double repairqty = Double.parseDouble(jTextField4.getText());
                    Double invoiceItemQty = Double.parseDouble(String.valueOf(jTable1.getValueAt(row, 5)));

                    if (repairqty > invoiceItemQty) {
                        JOptionPane.showMessageDialog(this, "Add valid qty less than " + invoiceItemQty, "Warning", JOptionPane.WARNING_MESSAGE);
                    } else {

                        RepairProductItem rpi = new RepairProductItem();
                        rpi.setRepairID(repairID);
                        rpi.setEmpEmail(String.valueOf(jTextField2.getText()));
                        rpi.setInvoiceId(String.valueOf(jTable1.getValueAt(row, 0)));
                        rpi.setProblem(jTextArea1.getText());
                        rpi.setQty(jTextField4.getText());
                        rpi.setStockId(String.valueOf(jTable1.getValueAt(row, 2)));

                        String uniqueKey = String.valueOf(jTable1.getValueAt(row, 0)) + "-" + String.valueOf(jTable1.getValueAt(row, 2));

                        if (repairProductMap.get(uniqueKey) == null) {
                            repairProductMap.put(uniqueKey, rpi);
                        } else {
                            RepairProductItem found = repairProductMap.get(uniqueKey);

                            int option = JOptionPane.showConfirmDialog(this, "Do you want to update quantity of the repair product: " + String.valueOf(jTable1.getValueAt(row, 3)), "Warning ", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

                            if (option == JOptionPane.YES_OPTION) {
                                Double newQty = Double.parseDouble(found.getQty()) + repairqty;

                                if (newQty > invoiceItemQty) {
                                    JOptionPane.showMessageDialog(this, "Add valid qty less than " + invoiceItemQty, "Warning", JOptionPane.WARNING_MESSAGE);
                                } else {

                                    found.setQty(String.valueOf(newQty));
                                }
                            }

                        }

                        loadRepairProductItem();
                        jTextArea1.setText("");
                        // jTextField4.setText("");
                        jTextField3.setText("");
                    }
                }
            }
        }

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

        if (jTextField4.getText().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Please add a repair product", "Warning", JOptionPane.WARNING_MESSAGE);

        } else {

            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

            String empEmail = jTextField2.getText();

            try {

                //insert into repair request table
                MySQL.executeIUD("INSERT INTO `repair_request` (`repair_id`,`employee_email`) VALUES ('" + this.repairID + "','" + empEmail + "') ");

                for (RepairProductItem rpi : repairProductMap.values()) {

                    int repairIteration = 1;

                    ResultSet rs = MySQL.executeSearch("SELECT `repair_iteration` from `repair_request_item` where `stock_id`='" + rpi.getStockId() + "' AND `invoice_id`='" + rpi.getInvoiceId() + "' ORDER BY `submit_date` ASC  ");

                    while (rs.next()) {
                        repairIteration = rs.getInt("repair_iteration") + 1;
                    }

                    //insert into repair request item table
                    MySQL.executeIUD("insert into `repair_request_item` (`problem`,`qty`,`submit_date`,`repair_status_id`,`stock_id`,`repair_request_repair_id`,`invoice_id`,`repair_iteration`) values ('" + rpi.getProblem() + "','" + rpi.getQty() + "','" + date + "','1','" + rpi.getStockId() + "','" + rpi.getRepairID() + "','" + rpi.getInvoiceId() + "','" + repairIteration + "') ");

                }

                clearAll();

            } catch (Exception e) {

                e.printStackTrace();
            }

        }


    }//GEN-LAST:event_jButton5ActionPerformed

    private FindRepairs fr;
    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

        jTextField4.setText("1");

        int row = jTable1.getSelectedRow();

        String invoiceId = String.valueOf(jTable1.getValueAt(row, 0));

        jTextField3.setText(String.valueOf(jTable1.getValueAt(row, 0)));

        if (evt.getClickCount() == 1) {

            loadOldRepairProducts(invoiceId);

        } else if (evt.getClickCount() == 2) {

            if (String.valueOf(jTable1.getValueAt(row, 9)).equals("yes")) {

                int option = JOptionPane.showConfirmDialog(this, "Do you want to view repair history?", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

                if (option == JOptionPane.YES_OPTION) {

                    if (fr == null || !fr.isDisplayable()) {
                        fr = new FindRepairs();
                        fr.setVisible(true);
                        fr.getjTextField1().setText(invoiceId);
                        fr.getjTextField1().setEnabled(false);

                    } else {
                        fr.toFront();
                        fr.requestFocus();

                    }
                }

            }
        }

    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        clearAll();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField5KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField5KeyReleased
        loadOldRepairProducts(jTextField5.getText());

        jTable1.clearSelection();
    }//GEN-LAST:event_jTextField5KeyReleased

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked

        int row = jTable2.getSelectedRow();

        String status = String.valueOf(jTable2.getValueAt(row, 3));

        if (status.equals("Collected")) {

            loadOldRepairInvoiceItems();
        } else {

            JOptionPane.showMessageDialog(this, "Product is not valid for repair", "Warning", JOptionPane.WARNING_MESSAGE);

        }
    }//GEN-LAST:event_jTable2MouseClicked

    private void jTable3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable3MouseClicked
        int row = jTable3.getSelectedRow();

        if (evt.getClickCount() == 2) {

            String removeUniqueKey = String.valueOf(jTable3.getValueAt(row, 4)) + "-" + String.valueOf(jTable3.getValueAt(row, 1));

            if (repairProductMap.containsKey(removeUniqueKey)) {
                repairProductMap.remove(removeUniqueKey);
                loadRepairProductItem();
            }
        }


    }//GEN-LAST:event_jTable3MouseClicked

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        
    }//GEN-LAST:event_jTextField3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        FlatMacDarkLaf.setup();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RepairAdd().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    // End of variables declaration//GEN-END:variables

    private void clearAll() {

        generateRepairProductId();
       
       
        jTextField5.setText("");
        
        jTable1.clearSelection();
        jTable3.clearSelection();
        jTextField4.setText("");
        jTextField3.setText("");
        jTextArea1.setText("");
        jTextField3.setText("");
         loadInvoiceItems();
        loadRepairProductItem();
         repairProductMap.clear();
        loadOldRepairProducts(jTextField5.getText());

    }
}
