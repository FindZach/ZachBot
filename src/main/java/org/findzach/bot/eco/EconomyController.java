package org.findzach.bot.eco;

import java.util.HashMap;

/**
 * @author Zach S <zach@findzach.com>
 * @since 11/3/2022
 */
public class EconomyController {

    private static final int START_BALANCE = 10_000;

    private static EconomyController CONTROLLER;


    private HashMap<String, BankAccount> bankAccountMap = new HashMap<>();

    public EconomyController() {
        if (CONTROLLER != null) {
            return;
        }

        CONTROLLER = this;
    }

    public void addAmount(String discordID, double amount) {
        bankAccountMap.get(discordID).addAmount(amount);
    }

    public BankAccount getBankOptions(String discordID) {
        if (hasBankAccount(discordID)) {
            return bankAccountMap.get(discordID);
        }
        return null;
    }

    public void createBankAccount(String discordID) {
        if (hasBankAccount(discordID)) return;

        BankAccount newBankAccount = new BankAccount();
        newBankAccount.setAmount(START_BALANCE);

        bankAccountMap.put(discordID, newBankAccount);
    }

    public String getBankBalanceFormatted(String discordID) {
        if (hasBankAccount(discordID)) {
            return bankAccountMap.get(discordID).toString();
        }
        return "No Account";
    }

    public double getBankBalance(String discordID) {
        if (hasBankAccount(discordID)) {
            return bankAccountMap.get(discordID).getBalance();
        } else {
            createBankAccount(discordID);
        }
        return 0;
    }

    public boolean hasBankAccount(String discordID) {
        return bankAccountMap.containsKey(discordID);
    }

    public static EconomyController getEconomyController() {
        return CONTROLLER;
    }
}
