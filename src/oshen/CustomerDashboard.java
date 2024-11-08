/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package oshen;

import model.SQL;
import java.sql.ResultSet;
import java.util.Vector;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.jfree.chart.plot.PiePlot;
import oshen.CustomerRegistration;
import oshen.CustomerSupportTickets;

/**
 *
 * @author User
 */
public class CustomerDashboard extends javax.swing.JFrame {

    private static final Logger logger = Logger.getLogger(CustomerDashboard.class.getName());

    private void setLogger() {
        try {
            // Create the directory if it does not exist
            java.nio.file.Path logDir = java.nio.file.Paths.get("Log Reports");
            if (!java.nio.file.Files.exists(logDir)) {
                java.nio.file.Files.createDirectories(logDir);
            }

            // Only add a file handler if none is added
            if (logger.getHandlers().length == 0) {
                FileHandler fileHandler = new FileHandler("Log Reports/Customer Dashboard Log Report.log", true);
                fileHandler.setFormatter(new SimpleFormatter());
                logger.addHandler(fileHandler);
                logger.setLevel(Level.ALL);  // Set level to capture all logs
                logger.info("Customer Dashboard Logger initialized");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.SEVERE, "Logger initialization failed", e);
        }
    }

    /**
     * Creates new form CustomerRegistration
     */
    public CustomerDashboard() {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        initComponents();
        setupPieChart();
        loadTable();
        setLogger();
    }

    private void loadTable() {
        DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
        dtm.setRowCount(0);
        try {
            ResultSet result = SQL.executeSearch("SELECT * FROM `customer`");
            while (result.next()) {
                Vector<Object> vector = new Vector<>();
                vector.add(result.getString("first_name"));
                vector.add(result.getString("last_name"));
                vector.add(result.getString("mobile"));
                vector.add(result.getString("registered_date"));
                vector.add(result.getString("email"));

                dtm.addRow(vector);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.SEVERE, "Error loading customer data: {0}", e.getMessage());
        }
    }

    private PieDataset createDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        try {
            try (ResultSet rs = SQL.executeSearch("SELECT cus_support_ticket_status_id, COUNT(*) AS count FROM customer_support WHERE cus_support_ticket_status_id IN (1, 2) GROUP BY cus_support_ticket_status_id")) {
                while (rs.next()) {
                    int id = rs.getInt("cus_support_ticket_status_id");
                    int count = rs.getInt("count");
                    if (id == 1) {
                        dataset.setValue("Pending", count);
                    } else if (id == 2) {
                        dataset.setValue("Completed", count);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataset;
    }

    private static final java.awt.Color PIE_COLOR_1 = java.awt.Color.decode("#697565");
    private static final java.awt.Color PIE_COLOR_2 = java.awt.Color.decode("#ECDFCC");

    private JFreeChart createChart(PieDataset dataset) {
        JFreeChart chart = ChartFactory.createPieChart(
                "Customer Support Tickets",
                dataset,
                true, // include legend
                true,
                false
        );

        // Enable anti-aliasing for smoother edges
        chart.setAntiAlias(true);

        // Customize the chart
        chart.setBackgroundPaint(java.awt.Color.DARK_GRAY);

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setBackgroundPaint(null);
        plot.setOutlineVisible(false);
        plot.setLabelBackgroundPaint(null);
        plot.setLabelOutlinePaint(null);
        plot.setLabelShadowPaint(null);
        plot.setLabelFont(new java.awt.Font("Calibri", java.awt.Font.BOLD, 12));
        plot.setLabelPaint(java.awt.Color.WHITE);
        plot.setSectionPaint("Pending", PIE_COLOR_1);
        plot.setSectionPaint("Completed", PIE_COLOR_2);
        plot.setShadowPaint(null);
        plot.setLabelGap(0.02);

        return chart;
    }

    private void setupPieChart() {
        PieDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(600, 400)); // Increase size for a modern look
        piechartPanel.setLayout(new BorderLayout());
        piechartPanel.add(chartPanel, BorderLayout.CENTER);
        piechartPanel.validate();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        piechartPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Customer Dashboard");
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Arial Black", 1, 36)); // NOI18N
        jLabel1.setText("Customer Dashboard");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "First Name", "Last Name", "Mobile No", "Date of Registration", "Email"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jButton1.setFont(new java.awt.Font("Helvetica LT Std", 1, 14)); // NOI18N
        jButton1.setText("New Customer Registration");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Helvetica LT Std", 1, 14)); // NOI18N
        jButton2.setText("Customer Support");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout piechartPanelLayout = new javax.swing.GroupLayout(piechartPanel);
        piechartPanel.setLayout(piechartPanelLayout);
        piechartPanelLayout.setHorizontalGroup(
            piechartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );
        piechartPanelLayout.setVerticalGroup(
            piechartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 666, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(piechartPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(410, 410, 410))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(piechartPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    new PendingTickets().setVisible(true);
        dispose();
                   
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        new CustomerRegistration().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        // Install FlatDarkLaf
        FlatDarkLaf.setup();

        // Launch your application
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CustomerDashboard().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JPanel piechartPanel;
    // End of variables declaration//GEN-END:variables
}
