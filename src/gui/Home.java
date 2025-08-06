/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gui;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import model.CardPayment;
import model.CashPayment;
import model.PayPalPayment;
import model.PaymentProcessor;

/**
 *
 * @author Akash Weerasinghe
 */
public class Home extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    public Home() {
        initComponents();
        validateDoubleValue();
        loadPaymentMethods();
    }

    private void validateDoubleValue() {
//        NumberFormat format = NumberFormat.getNumberInstance();
//        format.setGroupingUsed(false);
//        format.setMaximumFractionDigits(2);
//        format.setMinimumFractionDigits(0);

        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        NumberFormatter formatter = new NumberFormatter(decimalFormat);
        formatter.setValueClass(Double.class);
        formatter.setAllowsInvalid(false);
        formatter.setMinimum(0.0);
        formatter.setMaximum(Double.MAX_VALUE);

        DefaultFormatterFactory defaultFormatterFactory = new DefaultFormatterFactory(formatter);
        amountField.setFormatterFactory(defaultFormatterFactory);
    }

    private void loadPaymentMethods() {
        String[] methods = new String[]{"Payment Type", "Cash", "Card", "PayPal"};
        DefaultComboBoxModel<String> dcm = new DefaultComboBoxModel<>(methods);
        paymentCombo.setModel(dcm);
    }

    private void processPayment() {
        String paymentType = String.valueOf(paymentCombo.getSelectedItem());
        String input = amountField.getText().trim();
        double amount;

        if (input.isBlank()) {
            outputArea.setText("Please enter the amount...");
            return;
        }
        try {
            amount = Double.parseDouble(input);
            if (amount <= 0) {
                outputArea.setText("Amount must be greater than 0...");
                return;
            }
        } catch (NumberFormatException e) {
            outputArea.setText("Invalid amount Format");
            return;
        }
        PaymentProcessor paymentProcessor;
        switch (paymentType) {
            case "Cash":
                paymentProcessor = new CashPayment(amount);
                break;
            case "Card":
                paymentProcessor = new CardPayment(amount);
                break;
            case "PayPal":
                paymentProcessor = new PayPalPayment(amount);
                break;
            default:
                outputArea.setText("Unknown payment method...");
                return;
        }
        StringBuilder log = new StringBuilder();
        paymentProcessor.processPayment(log);
        outputArea.setText(log.toString());

        double tax = amount * (paymentProcessor.TAX_PERCENTAGE / 100);
        double total = amount + tax;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    showReceipt(paymentType, amount, tax, total);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

    public void showReceipt(String paymentType, double amount, double tax, double total) {
        StringBuilder receipt = new StringBuilder();
        receipt.append("===============RECEIPT===============\n");
        receipt.append("Date/Time: ").append(new SimpleDateFormat("yyyy-MMM-dd hh:mm:a").format(new Date())).append("\n");
        receipt.append("Payment Type: ").append(paymentType).append("\n");
        receipt.append("--------------------------------------\n");
        receipt.append(String.format("Amount       : LKR %.2f%n", amount)).append("\n");
        receipt.append("GST(").append(PaymentProcessor.TAX_PERCENTAGE).append("): LKR ").append(String.format("%.2f", tax)).append("\n");
        receipt.append("Total to Pay: LKR ").append(String.format("%.2f", total)).append("\n");
        receipt.append("---------------------------------------\n");
        receipt.append("Status: Paid Successfully\n");
        receipt.append("Thank You for your payment\n");
        receipt.append("=======================================\n");

        ReceiptDialog dialog = new ReceiptDialog(this, true, receipt.toString());
        dialog.setVisible(true);
    }

    private class ReceiptDialog extends JDialog {

        public ReceiptDialog(JFrame parent, boolean modal, String receiptText) {
            super(parent, modal);
            init(receiptText);
        }

        private void init(String receiptText) {
            JTextArea receiptArea = new JTextArea(receiptText);
            receiptArea.setEditable(false);
            receiptArea.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12));
            JScrollPane scrollPane = new JScrollPane(receiptArea);

            getContentPane().add(scrollPane);
            setTitle("Payment Receipt");
            setSize(400, 300);
            setLocationRelativeTo(getParent());
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

        jLabel1 = new javax.swing.JLabel();
        paymentCombo = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        payBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        outputArea = new javax.swing.JTextArea();
        amountField = new javax.swing.JFormattedTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("JetBrains Mono", 1, 14)); // NOI18N
        jLabel1.setText("Payment Method:");

        paymentCombo.setFont(new java.awt.Font("JetBrains Mono", 0, 14)); // NOI18N

        jLabel2.setFont(new java.awt.Font("JetBrains Mono", 1, 14)); // NOI18N
        jLabel2.setText("Enter Amount:");

        payBtn.setFont(new java.awt.Font("JetBrains Mono", 1, 14)); // NOI18N
        payBtn.setText("Process Payment");
        payBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                payBtnActionPerformed(evt);
            }
        });

        outputArea.setColumns(20);
        outputArea.setRows(5);
        jScrollPane1.setViewportView(outputArea);

        amountField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(paymentCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(amountField)))
                    .addComponent(payBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(paymentCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(amountField, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(payBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void payBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_payBtnActionPerformed
        processPayment();
    }//GEN-LAST:event_payBtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FlatMacDarkLaf.setup();
                new Home().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFormattedTextField amountField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea outputArea;
    private javax.swing.JButton payBtn;
    private javax.swing.JComboBox<String> paymentCombo;
    // End of variables declaration//GEN-END:variables
}
