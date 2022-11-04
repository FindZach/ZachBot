package org.findzach.bot.commands.impl;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.findzach.bot.commands.ZachBotCommand;
import org.findzach.bot.eco.BankAccount;
import org.findzach.bot.eco.EconomyController;

/**
 * @author Zach S <zach@findzach.com>
 * @since 11/3/2022
 */
public class BankCommand implements ZachBotCommand {
    @Override
    public void executeCommand(MessageReceivedEvent event, String[] args) {

        if (args.length > 0) {
            switch (args[0].toLowerCase()) {

                case "!powell":
                    if (!EconomyController.getEconomyController().hasBankAccount(event.getAuthor().getId())) {
                        EconomyController.getEconomyController().createBankAccount(event.getAuthor().getId());
                    }
                    event.getChannel().sendMessage("Don't worry, we print money and get away with it, heres your cut!").queue();
                    EconomyController.getEconomyController().getBankOptions(event.getAuthor().getId()).addAmount(10000);
                    break;

                case "!money":
                case "!bal":
                case "!balance":
                case "!eco":
                    if (args.length == 1) {
                        String authorID = event.getAuthor().getId();
                        if (EconomyController.getEconomyController().hasBankAccount(authorID)) {
                            event.getChannel().sendMessage("Balance: " + EconomyController.getEconomyController().getBankBalanceFormatted(authorID)).queue();
                        } else {
                            EconomyController.getEconomyController().createBankAccount(authorID);
                            event.getChannel().sendMessage("You did not have a bank account! \n One has been created for you; type the command again!").queue();
                        }
                    }
                    break;
            }
        }
    }
}
