/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package traitgen.application.form.other;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import model.MySQL;
import raven.toast.Notifications;

/**
 *
 * @author User
 */
public class Inventory extends javax.swing.JPanel {

    /**
     * Creates new form Inventory
     */
    public Inventory() {
        initComponents();
        loadTable();
        loadEmployeeType();
        jButton1.setEnabled(false);
    }
    private void loadTotalAttendance(String empEmail) {
        try {
            // SQL Query to count total attendance
            String query = "SELECT SUM(count) AS total_attendance FROM attendance_count WHERE employee_email = '" + empEmail + "'";
            ResultSet resultSet = MySQL.executeSearch(query);

            int totalAttendance = 0;

            if (resultSet.next()) {
                totalAttendance = resultSet.getInt("total_attendance");
                jLabel4.setText(String.valueOf(totalAttendance));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadTotalLeave(String empEmail) {
    try {
        // SQL Query to count total leave records
        String query = "SELECT SUM(leave_count) AS total_leave FROM attendance_count WHERE employee_email = '"+empEmail+"'";
        ResultSet resultSet = MySQL.executeSearch(query);

        int totalLeave = 0;
        if (resultSet.next()) {
            totalLeave = resultSet.getInt("total_leave");
            jLabel12.setText(String.valueOf(totalLeave));
        }



    } catch (Exception e) {
        e.printStackTrace();
    }
}
    private void loadTotalPayments(String empEmail) {
        try {
            // SQL Query to sum total payment amounts
            String query = "SELECT IFNULL(SUM(net_pay), 0) AS total_payments FROM salary_record WHERE employee_email = '" + empEmail + "'";
            ResultSet resultSet = MySQL.executeSearch(query);

            double totalPayments = 0.0;
            if (resultSet.next()) {
                totalPayments = resultSet.getDouble("total_payments");
                jLabel13.setText("Rs. " + String.format("%.2f", totalPayments));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadEmployeeType() {
        try {

            ResultSet rs = MySQL.executeSearch("SELECT * FROM `employee_type`");

            DefaultComboBoxModel model = (DefaultComboBoxModel) jComboBox1.getModel();
            model.removeAllElements();

            Vector<String> v = new Vector<>();
            v.add("Select");

            while (rs.next()) {
                v.add(rs.getString("name"));
            }

            model.addAll(v);
            jComboBox1.setSelectedIndex(0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadTable() {
        String searchText = jTextField1.getText().trim();
        String mobileText = jTextField2.getText().trim();
        String selectedSort = (String) jComboBox1.getSelectedItem();
        try {
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(JLabel.CENTER);
            DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();

            dtm.setRowCount(0);

            String query = "SELECT * FROM `employee` "
                    + "INNER JOIN `employee_type` ON `employee`.`employee_type_id`=`employee_type`.`id` "
                    + "INNER JOIN `gender` ON `employee`.`gender_id`=`gender`.`id`";

            boolean whereClauseAdded = false;

            if (!searchText.isEmpty()) {
                if (whereClauseAdded) {
                    query += " AND `employee`.`first_name` LIKE '%" + searchText + "%'";
                } else {
                    query += " WHERE `employee`.`first_name` LIKE '%" + searchText + "%'";
                    whereClauseAdded = true;
                }
            }

            if (!mobileText.isEmpty()) {
                if (whereClauseAdded) {
                    query += " AND `employee`.`mobile` LIKE '%" + mobileText + "%'";
                } else {
                    query += " WHERE `employee`.`mobile` LIKE '%" + mobileText + "%'";
                    whereClauseAdded = true;
                }
            }

//            int empTypeId = (Integer) jComboBox1.getSelectedIndex();
//            String empType = (String) jComboBox1.getSelectedItem();
//
//            if (empTypeId != 0) {
//
//                if (!query.isEmpty()) {
//
//                    query += " AND `employee`.`employee_type_id`='" + empTypeId + "' ";
//                                    whereClauseAdded = true;
//
//                } else {
//                    query += " WHERE `employee`.`employee_type_id`='" + empTypeId + "'";
//                                    whereClauseAdded = true;
//
//                }
//
//            }
            query += "ORDER BY `first_name` ASC";

            ResultSet result = MySQL.executeSearch(query);

            while (result.next()) {
                Vector<String> vector = new Vector<>();

                vector.add(result.getString("email"));
                vector.add(result.getString("first_name"));
                vector.add(result.getString("last_name"));
                vector.add(result.getString("nic"));
                vector.add(result.getString("mobile"));
                vector.add(result.getString("register_date"));
                vector.add(result.getString("employee_type.name"));
                vector.add(result.getString("gender.gender_type"));

                dtm.addRow(vector);

            }
            for (int i = 0; i < jTable1.getColumnModel().getColumnCount(); i++) {
                jTable1.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
        } catch (Exception e) {
            e.printStackTrace();
//            logger.warning("Table load error");
        }
    }

    private void loadEmployeeDetails(String empEmail) {

        try {

            String query = "SELECT * FROM `attendance_count` INNER JOIN `employee` ON `attendance_count`.`employee_email` = `employee`.`email` "
                    + " WHERE `employee_email` = '" + empEmail + "'";

            ResultSet rs = MySQL.executeSearch(query);

            if (rs.next()) {

                jLabel5.setText(rs.getString("employee.first_name") + " " + rs.getString("employee.last_name"));
                jLabel6.setText(rs.getString("attendance_count.count"));
                jLabel7.setText(String.valueOf(21 - Integer.parseInt(rs.getString("attendance_count.count"))));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Filter ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel2.setLayout(new java.awt.GridLayout(1, 3, 10, 10));

        jTextField1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Search by name ", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });
        jPanel2.add(jTextField1);

        jTextField2.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Search by mobile", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField2KeyReleased(evt);
            }
        });
        jPanel2.add(jTextField2);

        jComboBox1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jComboBox1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Search by type", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });
        jPanel2.add(jComboBox1);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Email", "First Name", "Last Name", "NIC", "Mobile", "Registered Date", "Type", "Gender"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel1.setLayout(new java.awt.GridLayout(1, 3, 10, 10));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("0");
        jLabel4.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true), "Total Attendence", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jLabel4.setPreferredSize(new java.awt.Dimension(37, 75));
        jPanel1.add(jLabel4);

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("0");
        jLabel12.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true), "Total Leaves", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel1.add(jLabel12);

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Rs. 0.00");
        jLabel13.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true), "Total Payments", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel1.add(jLabel13);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Human Resources Management");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 795, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel5.setLayout(new java.awt.GridLayout(6, 1, 20, 10));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Employee Name");
        jLabel5.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Employee Name", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel5.add(jLabel5);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Current Attendance");
        jLabel6.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Current Attendance", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel5.add(jLabel6);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Current Leave");
        jLabel7.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Current Leave", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel5.add(jLabel7);

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton1.setText("Mark Attendance");
        jButton1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton1);

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("p.Status");
        jLabel11.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Status", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel5.add(jLabel11);

        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton2.setText("Add Leave");
        jButton2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased

    }//GEN-LAST:event_jTextField1KeyReleased

    private void jTextField2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyReleased
        loadTable();
    }//GEN-LAST:event_jTextField2KeyReleased

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

        int row = jTable1.getSelectedRow();

        jButton1.setEnabled(true);

        if (row != -1) {

            String empEmail = String.valueOf(jTable1.getValueAt(row, 0));

            loadEmployeeDetails(empEmail);
            loadTotalAttendance(empEmail);
            loadTotalLeave(empEmail);
            loadTotalPayments(empEmail);
        }

        //        if (evt.getButton() == MouseEvent.BUTTON3) {
            //            int row = jTable1.rowAtPoint(evt.getPoint());
            //            jTable1.setRowSelectionInterval(row, row);
            //            jPopupMenu1.show(evt.getComponent(), evt.getX(), evt.getY());
            //            UpdateInfo.setText("Update Info");
            //        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        int row = jTable1.getSelectedRow();

        String email = String.valueOf(jTable1.getValueAt(row, 0));
        String dateTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please Select The employee", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            try {
                //Attendance is marked for the day
                ResultSet attendanceResult = MySQL.executeSearch("SELECT * FROM `attendence` WHERE  `employee_email` = '" + email + "' AND `date` = '" + dateTime + "'");
                if (attendanceResult.next()) {
                    //Do nothing
                    //                JOptionPane.showMessageDialog(this, "Already Mark", "Inform", JOptionPane.INFORMATION_MESSAGE);
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Attendance Already Marked");

                } else {
                    MySQL.executeIUD("INSERT INTO `attendence` (`date`,`employee_email`) VALUES ('" + dateTime + "','" + email + "')");
                    //Attendance count +
                    ResultSet count = MySQL.executeSearch("SELECT * FROM `attendance_count` WHERE `employee_email` = '" + email + "'");
                    if (count.next()) {
                        int currentCount = count.getInt("count");
                        int newCount = currentCount + 1;
                        MySQL.executeIUD("UPDATE `attendance_count` SET `count`= '" + newCount + "' WHERE `employee_email` = '" + email + "'");

                        Notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "Attendance Marked Success");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int row = jTable1.getSelectedRow();

        String email = String.valueOf(jTable1.getValueAt(row, 0));
        String dateTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        try {

            ResultSet attendanceResult = MySQL.executeSearch("SELECT * FROM `attendence` WHERE  `employee_email` = '" + email + "' AND `date` = '" + dateTime + "'");
            if (attendanceResult.next()) {

                if (attendanceResult.getString("attendance_status_id").equals("2")) {

                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Can not Give leave Today");
                } else {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "This employee Present Today");

                }
                //Do nothing
                //                JOptionPane.showMessageDialog(this, "Already Mark", "Inform", JOptionPane.INFORMATION_MESSAGE);

            } else {

                MySQL.executeIUD("INSERT INTO `attendence` (`date`,`employee_email`,`attendance_status_id`) VALUES ('" + dateTime + "','" + email + "','2')");

                ResultSet count = MySQL.executeSearch("SELECT * FROM `attendance_count` WHERE `employee_email` = '" + email + "'");
                if (count.next()) {
                    int currentCount = count.getInt("leave_count");

                    if (currentCount == 5) {
                        Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "Leave Limit Completed");

                    } else {

                        int newCount = currentCount + 1;
                        MySQL.executeIUD("UPDATE `attendance_count` SET `leave_count`= '" + newCount + "' WHERE `employee_email` = '" + email + "'");

                        Notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER, "leave Given Success");
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
