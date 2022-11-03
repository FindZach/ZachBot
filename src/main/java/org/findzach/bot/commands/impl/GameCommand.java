package org.findzach.bot.commands.impl;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.findzach.bot.commands.ZachBotCommand;
import org.findzach.bot.game.Game;
import org.findzach.bot.game.GameHandler;
import org.findzach.bot.game.ext.CoinFlip;

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
            if (possibleGame != null) {
                GameHandler.getGameHandler().startGame(event, possibleGame);
            } else {
                event.getChannel().sendMessage("ERROR: " + args[1] + " is not a valid game!").queue();
            }
        }
    }
}
