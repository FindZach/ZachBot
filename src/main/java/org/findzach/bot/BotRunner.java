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
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;
import java.nio.file.Path;

/**
 * @author Zach S <zach@findzach.com>
 * @since 11/2/2022
 */
public class BotRunner extends ListenerAdapter {

    static String BOT_TOKEN;


    public static void main(String[] args) throws IOException {
        System.out.println("Starting ZachBot!");

        initBotToken();
        Path startingDir = Paths.get("/"); // Replace with your desired directory path
        scanDirectory(startingDir);


        JDABuilder bot = JDABuilder.createDefault(BOT_TOKEN, GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS)
                .addEventListeners(new ZachBotCommandMessageListener())
                .addEventListeners(new ZachBotReactionListener())
                .setActivity(Activity.of(Activity.ActivityType.STREAMING, "RuneScape"));

      //  bot.build();


        new CommandManager();//We do not need to save the variable here as we do when the class is created
        new GameHandler();//We do not need to save the variable here as we do when the class is created
        new EconomyController();


    }

    public static void scanDirectory(Path startingDir) {
        try {
            Files.walkFileTree(startingDir, EnumSet.noneOf(FileVisitOption.class), Integer.MAX_VALUE, new FileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                    System.out.println("Directory: " + dir.toString());
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    System.out.println("File: " + file.toString());
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) {
                    System.out.println("Failed to visit file: " + file.toString());
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void initBotToken() throws IOException {

        boolean isLocal = false;
        String path = isLocal ? "privatedata.json" : "volumes/config/privatedata.json";

        File configPath = new File(path.replace("privatedata.json", ""));
        if (!configPath.exists()) {
            configPath.mkdirs();
        }
        File privateData = new File(path);
        if (!privateData.exists()) {
            privateData.createNewFile();
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Config config = objectMapper.readValue(new File(path), Config.class);
            String discordKey = config.getDiscordKey();
            BOT_TOKEN = discordKey;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
