package org.findzach.bot.commands.impl;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.findzach.bot.commands.ZachBotCommand;

/**
 * @author Zach S <zach@findzach.com>
 * @since 11/3/2022
 */
public class GitHubCommand implements ZachBotCommand {
    @Override
    public void executeCommand(MessageReceivedEvent event, String[] args) {
        event.getChannel().sendMessage("View the Source of this bot at: https://github.com/FindZach/ZachBot").queue();
    }
}
