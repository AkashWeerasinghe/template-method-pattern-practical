package model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Akash Weerasinghe
 */
public abstract class PaymentProcessor {
    
    protected double amount;
    public static final double TAX_PERCENTAGE = 20;
    
    public PaymentProcessor(double amount){
        this.amount = amount;
    }
    
    protected void startTransaction(StringBuilder log){
        log.append("Starting Transaction of amount: LKR ").append(String.format("%.2f", amount)).append("\n");
    }
    
    protected void selectPaymentGateway(StringBuilder log){
        log.append("Selecting payment gateway...\n");
    }
    
    protected abstract void authenticate(StringBuilder log);
    
    protected abstract void pay(StringBuilder log);
    
    protected void calculateTax(StringBuilder log){
        double tax = amount * (TAX_PERCENTAGE / 100);
        double finalamount = amount + tax;
        log.append("Calculating ")
                .append(TAX_PERCENTAGE)
                .append("% GST: LKR ")
                .append(String.format("%.2f", tax))
                .append("\n");
        log.append("Final amount to be Paid: LKR")
                .append(String.format("%.2f", finalamount))
                .append("\n");
    }
    
    protected void generateReceipt(StringBuilder log){
        log.append("Generating Digital receipt...\n");
    }
    
    protected void sendConfirmationMessage(StringBuilder log){
        log.append("Sending payment confirmation SMS/Email...\n");
    }
    
    protected boolean needsAuthentication(){
        return true;
    }
    
    protected boolean sendConfirmation(){
        return true;
    }
    
    public final void processPayment(StringBuilder log){
        startTransaction(log);
        selectPaymentGateway(log);
        if(needsAuthentication()){
            authenticate(log);
        }
        calculateTax(log);
        pay(log);
        generateReceipt(log);
        if(sendConfirmation()){
            sendConfirmationMessage(log);
        }
    }
}
