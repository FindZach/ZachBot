package org.findzach.bot.game;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.findzach.bot.game.ext.CoinFlip;
import org.findzach.bot.game.ext.OverUnder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Zach S <zach@findzach.com>
 * @since 11/2/2022
 *
 * The middleman between the specific user and the game
 */
public class GameHandler {

    private static GameHandler GAME_HANDLER;

    public GameHandler() {
        if (GAME_HANDLER != null) {
            return;
        }

        GAME_HANDLER = this;

    }

    private HashMap<String, List<DiscordGame>> activeGames = new HashMap<>();


    /**
     * Will verify we're not starting a game if we haven't finished yet.
     * @param UUID
     * @param gameType
     * @return
     */
    public DiscordGame getActiveGame(String UUID, Game gameType) {
        if (!activeGames.containsKey(UUID)) return null;

        for(DiscordGame game: activeGames.get(UUID)) {
            if (game.getGame() == gameType) {
                return game;
            }
        }

        return null;
    }
    public void startGame(MessageReceivedEvent event, Game game) {

        Logger.getAnonymousLogger().info("Attempting to start a game!");

        String discordUserID = event.getAuthor().getId();
        DiscordGame discordGame = getActiveGame(discordUserID, game);

        if (discordGame != null) {

            discordGame.sendCurrentDirections();
            //send message telling user what to do
            //We don't want to start a game that's already started.
            return;
        }

        //Decide which game and do startup
        switch (game) {
            case COINFLIP:
                discordGame = new CoinFlip(event.getJDA(), event.getChannel(), discordUserID);
                break;
            case OVERUNDER:
                discordGame = new OverUnder(event.getJDA(), event.getChannel(), discordUserID);
                break;
        }

        if (discordGame == null) {
            Logger.getAnonymousLogger().info("No game was found!");
            return;
        }
        
        if (activeGames.containsKey(discordUserID)) {
            activeGames.get(discordUserID).add(discordGame);
        } else {
            //First game setup
            List<DiscordGame> gameList = new ArrayList<>();
            gameList.add(discordGame);
            activeGames.put(discordUserID, gameList);
        }
    }

    public void executeGameOption(String discordUserID, String option) {
        if (activeGames.get(discordUserID) != null) {
            for (DiscordGame game: activeGames.get(discordUserID)) {
                game.handleGameOption(option);
            }
        }
    }

    public void flushGame(String discordUserID, Game game) {
        if (!activeGames.containsKey(discordUserID)) {
            return;
        }

        List<DiscordGame> gameList = new ArrayList<>(activeGames.get(discordUserID));

        for (int i = 0; i < gameList.size(); i++) {
            DiscordGame possibleGame = gameList.get(i);
            if (possibleGame.getGame() == game) {
                gameList.remove(i);
                Logger.getAnonymousLogger().info("Flushed a game!");
            }

        }
        activeGames.put(discordUserID, gameList);
    }

    public static GameHandler getGameHandler() {
        return GAME_HANDLER;
    }

}
