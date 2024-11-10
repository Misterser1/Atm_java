package by.rustem;

import java.io.Serializable;

public class Account implements Serializable {
    private static final long serialVersionUID = 1L;
    private double balance;
    private String encryptedPin;

    public Account(double balance, String encryptedPin){
        this.balance = balance;
        this.encryptedPin = encryptedPin;
    }

    public double getBalance(){
        return balance;
    }

    public void setBalance(double balance){
        this.balance = balance;
    }

    public String getEncryptedPin(){
        return encryptedPin;
    }

    public void setEncryptedPin(String encryptedPin) {
        this.encryptedPin = encryptedPin;
    }
}
