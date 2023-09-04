package org.findzach.bot;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.findzach.bot.commands.CommandManager;
import org.findzach.bot.config.Config;
import org.findzach.bot.eco.EconomyController;
import org.findzach.bot.game.GameHandler;
import org.findzach.bot.listener.ZachBotCommandMessageListener;
import org.findzach.bot.listener.ZachBotReactionListener;

import java.io.File;
import java.io.IOException;

/**
 * @author Zach S <zach@findzach.com>
 * @since 11/2/2022
 */
public class BotRunner extends ListenerAdapter {

    static String BOT_TOKEN;


    public static void main(String[] args) {
        initBotToken();

        JDABuilder bot = JDABuilder.createDefault(BOT_TOKEN, GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS)
                .addEventListeners(new ZachBotCommandMessageListener())
                .addEventListeners(new ZachBotReactionListener())
                .setActivity(Activity.of(Activity.ActivityType.STREAMING, "RuneScape"));

        bot.build();


        new CommandManager();//We do not need to save the variable here as we do when the class is created
        new GameHandler();//We do not need to save the variable here as we do when the class is created
        new EconomyController();
    }


    public static void initBotToken() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Config config = objectMapper.readValue(new File("privatedata.json"), Config.class);
            String discordKey = config.getDiscordKey();
            BOT_TOKEN = discordKey;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
