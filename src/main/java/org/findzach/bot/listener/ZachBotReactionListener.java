package org.findzach.bot.listener;

import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.logging.Logger;

/**
 * @author Zach S <zach@findzach.com>
 * @since 11/2/2022
 */
public class ZachBotReactionListener extends ListenerAdapter {

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event){
        event.getChannel().retrieveMessageById(event.getMessageId()).queue(message -> {
            System.out.println(message.getContentDisplay());
        });
        Logger.getAnonymousLogger().info("Event: " + event);
        Logger.getAnonymousLogger().info("Reaction: " + event.getReaction().toString());
        Logger.getAnonymousLogger().info("Event MessageID: " + event.getMessageId());
    }
}
