package org.findzach.bot.commands;

import org.findzach.bot.commands.impl.GameCommand;
import org.findzach.bot.commands.impl.PingPongCommand;

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
        commandHashMap.put("!ping", new PingPongCommand());
        commandHashMap.put("!game", new GameCommand());
    }

    public ZachBotCommand getCommandFromKey(String key) {
        return commandHashMap.get(key);
    }

    public static CommandManager getCommandManager() {
        return COMMAND_MANAGER;
    }
}
