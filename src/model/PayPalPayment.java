/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Akash Weerasinghe
 */
public class PayPalPayment extends PaymentProcessor{

    public PayPalPayment(double amount) {
        super(amount);
    }

    @Override
    protected void authenticate(StringBuilder log) {
        log.append("Redirecting to the PayPal login...\n");
    }

    @Override
    protected void pay(StringBuilder log) {
        log.append("Processing PayPal payment of LKR ")
                .append(String.format("%.2f", finalAmount))
                .append("...\n");
    }
    
}
