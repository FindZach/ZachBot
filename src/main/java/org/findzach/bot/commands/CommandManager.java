package org.findzach.bot.commands;

import org.findzach.bot.commands.impl.*;

import java.util.HashMap;
import java.util.logging.Logger;

/**
 * @author Zach S <zach@findzach.com>
 * @since 11/2/2022
 *
 * Will handle all of our commands
 */
public class CommandManager {

    //Default Java Logger
    private final Logger logger = Logger.getGlobal();
    private static CommandManager COMMAND_MANAGER;

    private HashMap<String, ZachBotCommand> commandHashMap = new HashMap<>();


    public CommandManager() {
        if (COMMAND_MANAGER != null) {
            logger.info("CommandManager has already been initialized!");
            return;
        }

        COMMAND_MANAGER = this;
        setCommands();

        logger.info("CommandManager has been initialized!");
    }

    /**
     * Declares the Commands that we want the bot to recognize
     */
    private void setCommands() {
        commandHashMap.put("!ping", new PingPongCommand()); //Simple Ping/Pong
        commandHashMap.put("!game", new GameCommand()); //Game Options
        commandHashMap.put("!beep", new BeepBoopCommand()); //Game Options

        commandHashMap.put("!violet", new VioletCommand()); //Random Picture of Violet

        commandHashMap.put("!marco", new MarcoPoloCommand()); //Marco POLO

        commandHashMap.put("!github", new GitHubCommand()); //Shows Github
        commandHashMap.put("!source", new GitHubCommand()); //Shows github

        //Same Command
        commandHashMap.put("!eco", new BankCommand()); //Banking commands
        commandHashMap.put("!bal", new BankCommand());
        commandHashMap.put("!money", new BankCommand());
        commandHashMap.put("!balance", new BankCommand());
        commandHashMap.put("!powell", new BankCommand());
    }

    public ZachBotCommand getCommandFromKey(String key) {
        return commandHashMap.get(key);
    }

    public static CommandManager getCommandManager() {
        return COMMAND_MANAGER;
    }
}
