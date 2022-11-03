package org.findzach.bot;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.findzach.bot.commands.CommandManager;
import org.findzach.bot.eco.EconomyController;
import org.findzach.bot.game.GameHandler;
import org.findzach.bot.listener.ZachBotCommandMessageListener;
import org.findzach.bot.listener.ZachBotReactionListener;

/**
 * @author Zach S <zach@findzach.com>
 * @since 11/2/2022
 */
public class BotRunner extends ListenerAdapter {

    static String BOT_TOKEN = "botKey";


    public static void main(String[] args) {
        JDABuilder bot = JDABuilder.createDefault(BOT_TOKEN, GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS)
                .addEventListeners(new ZachBotCommandMessageListener())
                .addEventListeners(new ZachBotReactionListener())
                .setActivity(Activity.of(Activity.ActivityType.STREAMING, "RuneScape"));

        bot.build();


        new CommandManager();//We do not need to save the variable here as we do when the class is created
        new GameHandler();//We do not need to save the variable here as we do when the class is created
        new EconomyController();
    }
}
