/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Akash Weerasinghe
 */
public class CardPayment extends PaymentProcessor{

    public CardPayment(double amount) {
        super(amount);
    }

    @Override
    protected void authenticate(StringBuilder log) {
        log.append("Verifying card number and PIN...\n");
    }

    @Override
    protected void pay(StringBuilder log) {
        log.append("Charging LKR ").append(String.format("%.2f", finalAmount)).append(" from card\n");
    }
    
    
}
