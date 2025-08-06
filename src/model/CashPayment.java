/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Akash Weerasinghe
 */
public class CashPayment extends PaymentProcessor{

    public CashPayment(double amount) {
        super(amount);
    }

    @Override
    protected void authenticate(StringBuilder log) {       
    }

    @Override
    protected void pay(StringBuilder log) {
        log.append("Collecting LKR ")
                .append(String.format("%.2f", amount))
                .append(" in cash from customer...\n");
    }

    @Override
    protected boolean needsAuthentication() {
       return false;
    }

    @Override
    protected boolean sendConfirmation() {
        return false;
    }
    
}
