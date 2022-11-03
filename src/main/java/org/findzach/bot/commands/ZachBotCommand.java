package org.findzach.bot.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * @author Zach <zach@findzach.com>
 * @since 11/2/2022
 */
public interface ZachBotCommand {

    /**
     * Executes the command
     * @param discordUUID - The UUID of the user who requested to perform command
     * @param args - The args associated with the command
     */
    void executeCommand(MessageReceivedEvent event, String[] args);
}
