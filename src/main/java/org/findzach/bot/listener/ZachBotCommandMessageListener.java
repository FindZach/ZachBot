package org.findzach.bot.listener;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.findzach.bot.commands.CommandManager;
import org.findzach.bot.commands.ZachBotCommand;
import org.findzach.bot.game.GameHandler;

/**
 * @author Zach S <zach@findzach.com>
 * @since 11/2/2022
 */
public class ZachBotCommandMessageListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        if (event.getAuthor().isBot()) return;

        Message message = event.getMessage();
        String content = message.getContentRaw();

        String[] args = content.split(" ");

        if (args.length > 0) {
           ZachBotCommand possibleCommand = CommandManager.getCommandManager().getCommandFromKey(args[0]);
           if (possibleCommand != null) {
               //Command Found, we will execute
               possibleCommand.executeCommand(event, args);
               return;
           }
        }

        GameHandler.getGameHandler().executeGameOption(event.getAuthor().getId(), content);
    }

}
