package org.findzach.bot.commands.impl;

import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.findzach.bot.commands.ZachBotCommand;
import org.findzach.bot.misc.Unicode;

/**
 * @author Zach S <zach@findzach.com>
 * @since 11/2/2022
 */
public class MarcoPoloCommand implements ZachBotCommand {

    @Override
    public void executeCommand(MessageReceivedEvent event, String[] args) {
        //We already verified the user typed !ping
        event.getMessage().addReaction(Emoji.fromFormatted(Unicode.CHECKMARK.toString())).queue();
        event.getChannel().sendMessage("Polo!").queue();
    }
}
