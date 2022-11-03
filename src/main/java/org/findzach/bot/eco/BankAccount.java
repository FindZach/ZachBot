package org.findzach.bot.eco;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * @author Zach S <zach@findzach.com>
 * @since 11/3/2022
 *
 * Will Represent a discord users bank
 */
public class BankAccount implements BankAction {
    private double balance;

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        Locale usa = new Locale("en", "US");
        NumberFormat dollarFormat = NumberFormat.getCurrencyInstance(usa);
        return dollarFormat.format(balance);
    }

    @Override
    public double removeAmount(double amount) {
        if (amount > balance) {
            return 0;
        }
        this.balance = balance - amount;
        return balance;
    }

    @Override
    public double addAmount(double amount) {
        if (amount > 0) {
            this.balance = amount + balance;
            return balance;
        }
        return balance;
    }

    @Override
    public double setAmount(double amount) {
        setBalance(amount);
        return getBalance();
    }
}
