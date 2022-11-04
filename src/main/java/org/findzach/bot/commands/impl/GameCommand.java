package org.findzach.bot.commands.impl;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.findzach.bot.commands.ZachBotCommand;
import org.findzach.bot.eco.EconomyController;
import org.findzach.bot.game.Game;
import org.findzach.bot.game.GameHandler;

/**
 * @author Zach S <zach@findzach.com>
 * @since 11/2/2022
 */
public class GameCommand implements ZachBotCommand {

    @Override
    public void executeCommand(MessageReceivedEvent event, String[] args) {
        for (String arg: args) {
            System.out.println("GameCommand Args: "+arg);
        }

        //This means user typed only '!game'
        if (args.length == 1) {
            event.getChannel().sendMessage("Current Games Options: [Coinflip, OverUnder]").queue();
            return;
        }

        if (args.length > 1) {
            Game possibleGame = Game.getGameFromString(args[1]);

            if (!EconomyController.getEconomyController().hasBankAccount(event.getAuthor().getId())) {
                EconomyController.getEconomyController().createBankAccount(event.getAuthor().getId());
            }

            double wager = 0;
            //Tries to detect game and start, or execute whatever is desired
            if (args.length > 2) {
                String wagerString = args[2];
                try {
                    double playerBal = EconomyController.getEconomyController().getBankBalance(event.getAuthor().getId());
                    wager = Double.parseDouble(wagerString);
                    if (playerBal < wager) {
                        event.getChannel().sendMessage("You cannot afford to bet " + wager + "!").queue();
                        return;
                    }
                } catch (Exception e) {

                    //Not correct wager, ignoring for now
                    wager = 0;
                }

            }
            if (possibleGame != null) {
                GameHandler.getGameHandler().startGame(event, possibleGame, wager);
            } else {
                event.getChannel().sendMessage("ERROR: " + args[1] + " is not a valid game!").queue();
            }
        }
    }
}
