/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gui;

import com.sun.source.tree.ParenthesizedTree;
import java.awt.Component;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.MySQL;

/**
 *
 * @author Anne
 */
public class RepairCompletion extends javax.swing.JFrame {

    private static HashMap<String, String> repairStatusMap = new HashMap<>();

    private String repairId;
    private String invoiceId;
    private String sid;
    private String productName;
    private String submitDate;
    private String empEmail;
    private String problem;
    private String warranty;
    private String purchDate;
    
    private JFrame parent;

    public RepairCompletion(JFrame parent,String repairId, String invoiceId, String sid, String productName, String submitDate, String empEmail, String problem, String warranty, String purchasedDate,String repairStatus) {  
        initComponents();
        this.repairId = repairId;
        this.invoiceId = invoiceId;
        this.sid = sid;
        this.productName = productName;
        this.problem = problem;
        this.submitDate = submitDate;
        this.empEmail = empEmail;
        this.warranty = warranty;
        this.purchDate = purchasedDate;
        
        this.parent = parent;

        jTextField1.setText(this.repairId);
        jTextField3.setText(this.sid);
        jTextField5.setText(this.warranty);
        jTextField2.setText(this.invoiceId);
        jTextField4.setText(this.productName);
        jTextArea1.setText(this.problem);
        jTextField7.setText(this.submitDate);
        jTextField10.setText(this.empEmail);
        jTextField6.setText(this.purchDate);
        jLabel10.setText(repairStatus);

        jDateChooser1.setEnabled(false);
        jDateChooser2.setEnabled(false);

       loadTextfieldEnable();

        loadRepairStatus();
        loadRepairStatusTable();
        loadRepairCompletionDetails();

    }

    private void loadRepairStatus() {
        try {

            ResultSet rs = MySQL.executeSearch("select * from `repair_status`");

            Vector<String> vector = new Vector<>();
            vector.add("Select");

            while (rs.next()) {
                vector.add(rs.getString("name"));
                repairStatusMap.put(rs.getString("name"), rs.getString("id"));
            }

            DefaultComboBoxModel dcm = new DefaultComboBoxModel(vector);
            jComboBox1.setModel(dcm);

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void loadRepairStatusTable() {
        try {
            ResultSet rs = MySQL.executeSearch("SELECT * FROM `repair_request_item` INNER JOIN `repair_status` ON `repair_status`.`id`=`repair_request_item`.`repair_status_id` WHERE `invoice_id`='" + this.invoiceId + "' AND `stock_id`='" + this.sid + "' ");

            DefaultTableModel dtm2 = (DefaultTableModel) jTable1.getModel();
            dtm2.setRowCount(0);

            while (rs.next()) {
                Vector<String> vector = new Vector<>();

                vector.add(rs.getString("repair_request_item.repair_request_repair_id"));
                vector.add(rs.getString("repair_request_item.stock_id"));
                vector.add(rs.getString("repair_status.name"));
                dtm2.addRow(vector);
                
             
            }

            jTable1.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {

                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

                    Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                    //   int row1 = jTable1.getSelectedRow();
                    // Check if the repairId matches in this row
                    String repairIdInRow = (String) table.getValueAt(row, 0); // Assuming first column contains `repair_request_repair_id`

                    if (repairIdInRow.equals(jTextField1.getText())) {
                        cell.setForeground(java.awt.Color.GREEN); // Set text color to green
                        cell.setFont(cell.getFont().deriveFont(java.awt.Font.BOLD));

                    } else {
                        cell.setForeground(java.awt.Color.WHITE); // Default color
                    }
                    return cell;
                }

            });
            
            
            
      

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void loadTextfieldEnable(){
        
        if(jLabel10.getText().equals("Completed")){
             jDateChooser1.setEnabled(true);
        }else if(jLabel10.getText().equals("Collected")){
            jDateChooser2.setEnabled(true);
        }
        
    }
    
    private void loadRepairCompletionDetails(){
        try {
            ResultSet rs = MySQL.executeSearch("SELECT * FROM `repair_request_item` INNER JOIN `repair_status` ON `repair_status`.`id`=`repair_request_item`.`repair_status_id` WHERE `invoice_id`='" + this.invoiceId + "' AND `stock_id`='" + this.sid + "' AND `repair_request_repair_id`='"+jTextField1.getText()+"' ");  
            
            while(rs.next()){
                jTextArea3.setText(rs.getString("note"));
                jDateChooser1.setDate(rs.getDate("completed_date"));
                jDateChooser2.setDate(rs.getDate("collected_date"));
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel10 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jLabel15 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jButton5 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);

        jLabel1.setText("Repair Completion Details");

        jLabel2.setText("Repair ID");

        jTextField1.setEditable(false);

        jLabel3.setText("Invoice ID");

        jTextField2.setEditable(false);

        jLabel4.setText("Stock ID");

        jTextField3.setEditable(false);
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jLabel5.setText("Product");

        jTextField4.setEditable(false);

        jLabel6.setText("Warrenty");

        jTextField5.setEditable(false);
        jTextField5.setForeground(new java.awt.Color(255, 0, 0));

        jLabel7.setText("Problem");

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel10.setText("Not Completed");
        jLabel10.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jLabel10FocusGained(evt);
            }
        });

        jLabel14.setText("Purchased Date");

        jTextField6.setEditable(false);
        jTextField6.setForeground(new java.awt.Color(255, 0, 0));

        jButton4.setText("Clear All");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField2))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel1))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(18, 18, 18)
                                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jTextField3))))
                                .addGap(28, 28, 28)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel14)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jTextField6))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addGap(46, 46, 46)
                                        .addComponent(jTextField5))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(20, 20, 20))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 695, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jButton4))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton2.setText("Add Note");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Repaire Invoice");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Repaire ID", "Stock ID", "Repaire Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
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

        jLabel11.setText("Collected Date");

        jButton1.setText("Change Repair Status");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Pending", "Checked", "Completed", "Collected", "Canceled", "with Warenty" }));

        jLabel8.setText("Created Date");

        jTextField7.setEditable(false);
        jTextField7.setForeground(new java.awt.Color(255, 0, 0));

        jLabel9.setText("Completed Date");

        jLabel12.setText("Assigned Employee");

        jTextField10.setEditable(false);
        jTextField10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField10ActionPerformed(evt);
            }
        });

        jLabel13.setText("Add a note about Repair Process :-");

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane3.setViewportView(jTextArea2);

        jTextArea3.setEditable(false);
        jTextArea3.setColumns(20);
        jTextArea3.setRows(5);
        jScrollPane4.setViewportView(jTextArea3);

        jDateChooser1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jDateChooser1MouseClicked(evt);
            }
        });
        jDateChooser1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jDateChooser1KeyPressed(evt);
            }
        });

        jLabel15.setText("Warranty");

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setText("Available");

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("Not Available");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane3)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox1, 0, 163, Short.MAX_VALUE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jDateChooser2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jDateChooser1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jTextField7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jRadioButton1)
                                        .addGap(29, 29, 29)
                                        .addComponent(jRadioButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 435, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(19, Short.MAX_VALUE))))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel11)
                            .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2))
                .addGap(18, 18, 18)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton5.setText("Save");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(15, 15, 15))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton5))
                .addGap(0, 29, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jTextField10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField10ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        if (evt.getClickCount() == 2) {

            int row = jTable1.getSelectedRow();

            try {

                ResultSet rs = MySQL.executeSearch("SELECT * FROM `repair_request_item` inner join `repair_request` on `repair_request_item`.`repair_request_repair_id`=`repair_request`.`repair_id` inner join `stock` on `stock`.`id`=`repair_request_item`.`stock_id` inner join `invoice` on `invoice`.`id`=`repair_request_item`.`invoice_id` inner join `product` on `product`.`id`=`stock`.`product_id` INNER JOIN `warranty` ON `warranty`.`id`=`product`.`warranty_id` inner join `repair_status` on `repair_status`.`id`=`repair_request_item`.`repair_status_id` WHERE `repair_request_repair_id`='" + String.valueOf(jTable1.getValueAt(row, 0)) + "' AND `repair_request_item`.`stock_id`='" + String.valueOf(jTable1.getValueAt(row, 1)) + "' AND `repair_request_item`.`invoice_id`='" + jTextField2.getText() + "'  ");

                if (rs.next()) {

                    jTextField1.setText(rs.getString("repair_request_repair_id"));
                    jTextField3.setText(rs.getString("repair_request_item.stock_id"));
                    jTextField5.setText(rs.getString("warranty.period"));
                    jTextField2.setText(rs.getString("repair_request_item.invoice_id"));
                    jTextField4.setText(rs.getString("product.name"));
                    jTextField6.setText(rs.getString("invoice.date"));
                    jTextArea1.setText(rs.getString("repair_request_item.problem"));
                    jTextField7.setText(rs.getString("repair_request_item.submit_date"));
                    jTextField10.setText(rs.getString("repair_request.employee_email"));
                    jTextArea3.setText(rs.getString("note"));
                    
                    jLabel10.setText(String.valueOf(jTable1.getValueAt(row, 2)));
                    loadTextfieldEnable();

                    loadRepairStatusTable();
                    loadRepairCompletionDetails();
                   

                }

            } catch (Exception e) {

                e.printStackTrace();
            }
            
             

        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String status = String.valueOf(jComboBox1.getSelectedItem());

        try {

            MySQL.executeIUD("update `repair_request_item` set `repair_status_id`='" + repairStatusMap.get(status) + "' WHERE `repair_request_repair_id`='" + jTextField1.getText() + "' AND `stock_id`='" + jTextField3.getText() + "' AND `invoice_id`='" + jTextField2.getText() + "' ");
            loadRepairStatusTable();
           jLabel10.setText(status);
           loadTextfieldEnable();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }//GEN-LAST:event_jButton1ActionPerformed

    private void jLabel10FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jLabel10FocusGained
       
    }//GEN-LAST:event_jLabel10FocusGained

    private void jDateChooser1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDateChooser1KeyPressed
     
    }//GEN-LAST:event_jDateChooser1KeyPressed

    private void jDateChooser1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jDateChooser1MouseClicked
       
    if (jLabel10.getText().equals("Completed")) {
        jDateChooser1.setEnabled(true);
    } else {
        JOptionPane.showMessageDialog(this, "Product status is not 'Completed'. You cannot select a date.", "Warning", JOptionPane.WARNING_MESSAGE);
        jDateChooser1.setEnabled(false);
    }
    }//GEN-LAST:event_jDateChooser1MouseClicked

   private RepairInvoice repairInvoice;
   
   public static JFrame findRepairs;
   
   private String warrantyStatus;
   
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        Date completedDate = jDateChooser1.getDate();
         Date collectedDate = jDateChooser2.getDate();
         
         if(jLabel10.getText().equals("Collected")){
             
            if(jDateChooser2.getDate() ==null){
                 JOptionPane.showMessageDialog(this, "Please add collected date", "Warning", JOptionPane.WARNING_MESSAGE);
            }else if(!jRadioButton1.isSelected() && !jRadioButton2.isSelected() ){
            
            JOptionPane.showMessageDialog(this, "Please select warranty status", "Warning", JOptionPane.WARNING_MESSAGE);
            
            }else{
                
                  
                 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                 
                try {
                    
                      MySQL.executeIUD("UPDATE `repair_request_item` SET `completed_date`='"+sdf.format(completedDate)+"',`collected_date`='"+sdf.format(collectedDate)+"',`note`='"+jTextArea3.getText()+"' WHERE `repair_request_repair_id`='"+jTextField1.getText()+"' AND `invoice_id`='"+jTextField2.getText()+"' AND `stock_id`='"+jTextField3.getText()+"' ");
                      
                      if(repairInvoice ==null || !repairInvoice.isDisplayable()){
                          
                          if(jRadioButton1.isSelected()){
                             this.warrantyStatus = "Warranty Available";
                          }else{
                               this.warrantyStatus = "No Warranty";
                          }
                          
                          repairInvoice = new RepairInvoice(jTextField1.getText(),jTextField3.getText(),jTextArea1.getText(),warrantyStatus);
                          repairInvoice.setVisible(true);
                          this.dispose();
                          parent.dispose();
                          
                      }else{
                          repairInvoice.toFront();
                          repairInvoice.requestFocus();
                      }
                    
                } catch (Exception e) {
                    
                    e.printStackTrace();
                }
            }
             
         }else{
           JOptionPane.showMessageDialog(this, "Product status is not 'Collected'. You cannot save repair invoice.", "Warning", JOptionPane.WARNING_MESSAGE);   
         }
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       String note = jTextArea2.getText();
       
       if(note.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please type a note", "Warning", JOptionPane.WARNING_MESSAGE);   
       }else{
         String prevTxt = jTextArea3.getText();
         if(prevTxt.isEmpty()){
              jTextArea3.setText(note);
            
         }else{
            jTextArea3.setText(prevTxt+" , "+note); 
            
         }
         
         jTextArea2.setText("");
         
         
           
           
       }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
       
       
       if(jTextArea3.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Please type a note", "Warning", JOptionPane.WARNING_MESSAGE);   
       }else if(jLabel10.getText().equals("Completed")){
           
           if(jDateChooser1.getDate() ==null){
                JOptionPane.showMessageDialog(this, "Please type completed date", "Warning", JOptionPane.WARNING_MESSAGE); 
           }else{
             try {
                 
                 Date completedDate = jDateChooser1.getDate();
                 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
               
               MySQL.executeIUD("UPDATE `repair_request_item` SET `completed_date`='"+sdf.format(completedDate)+"',`note`='"+jTextArea3.getText()+"' WHERE `repair_request_repair_id`='"+jTextField1.getText()+"' AND `invoice_id`='"+jTextField2.getText()+"' AND `stock_id`='"+jTextField3.getText()+"' ");
               loadRepairCompletionDetails();
           } catch (Exception e) {
               e.printStackTrace();
           }
           }
       
       
       }else{
        
           try {
               
               MySQL.executeIUD("UPDATE `repair_request_item` SET `note`='"+jTextArea3.getText()+"' WHERE `repair_request_repair_id`='"+jTextField1.getText()+"' AND `invoice_id`='"+jTextField2.getText()+"' AND `stock_id`='"+jTextField3.getText()+"' ");
               loadRepairCompletionDetails();
           } catch (Exception e) {
               e.printStackTrace();
           }
           
           
       }
    }//GEN-LAST:event_jButton5ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox<String> jComboBox1;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
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
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    // End of variables declaration//GEN-END:variables
}
