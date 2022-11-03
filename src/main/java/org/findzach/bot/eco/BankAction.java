package org.findzach.bot.eco;

/**
 * @author Zach <zach@findzach.com>
 * @since 11/3/2022
 */
public interface BankAction {

    double removeAmount(double amount);
    double addAmount(double amount);
    double setAmount(double amount);

}
