package org.findzach.bot;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

/**
 * @author Zach S <zach@findzach.com>
 * @since 11/2/2022
 */
public class BotRunner extends ListenerAdapter {

    public static void main(String[] args) throws LoginException {
        String token = "MTAzNzQ0NzMwNDMyNTYyNzkwNA.GK4wM3.zGxkjxIXVWEBptRH5Xb9Ih3CO6rbfUXFQde3IY";
        JDABuilder builder = JDABuilder.createDefault(token);
        builder.addEventListeners(new BotRunner());
        builder.build();
        System.out.println("Discord Bot Running!");
    }


    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        System.out.println("Message ToString: " + event.getMessage().getContentDisplay());
        System.out.println("Message Raw???: " + event.getMessage().getContentRaw());

        if (event.getAuthor().isBot()) {
            return;
        }

        event.getChannel().sendMessage("gg idiot").submit();

        System.out.println("least message? "+event.getChannel().getHistory().getRetrievedHistory().get(0));

        if (event.getMessage().getContentRaw().equalsIgnoreCase("hi")) {
            //event.getChannel().sendMessage("Fat nerd");
            event.getChannel().sendMessage("gg idiots").submit();
        }
    }
}
