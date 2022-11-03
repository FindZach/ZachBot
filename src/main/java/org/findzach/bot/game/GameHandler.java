package org.findzach.bot.game;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.findzach.bot.eco.EconomyController;
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

    private HashMap<String, List<DiscordSinglePlayerGame>> activeGames = new HashMap<>();


    /**
     * Will verify we're not starting a game if we haven't finished yet.
     * @param UUID
     * @param gameType
     * @return
     */
    public DiscordSinglePlayerGame getActiveGame(String UUID, Game gameType) {
        if (!activeGames.containsKey(UUID)) return null;

        for(DiscordSinglePlayerGame game: activeGames.get(UUID)) {
            if (game.getGame() == gameType) {
                return game;
            }
        }

        return null;
    }
    public void startGame(MessageReceivedEvent event, Game game, double wager) {

        Logger.getAnonymousLogger().info("Attempting to start a game!");

        String discordUserID = event.getAuthor().getId();
        DiscordSinglePlayerGame discordSinglePlayerGame = getActiveGame(discordUserID, game);

        if (discordSinglePlayerGame != null) {

            discordSinglePlayerGame.sendCurrentDirections();
            //send message telling user what to do
            //We don't want to start a game that's already started.
            return;
        }

        //Decide which game and do startup
        switch (game) {
            case COINFLIP:
                discordSinglePlayerGame = new CoinFlip(event.getJDA(), event.getChannel(), discordUserID, wager);
                break;
            case OVERUNDER:
                discordSinglePlayerGame = new OverUnder(event.getJDA(), event.getChannel(), discordUserID, wager);
                break;
        }

        if (discordSinglePlayerGame == null) {
            Logger.getAnonymousLogger().info("No game was found!");
            return;
        }

        Logger.getAnonymousLogger().info("Wagering " + wager + " on this game!");
        if (wager > 0)
        EconomyController.getEconomyController().getBankOptions(discordUserID).removeAmount(wager);
        
        if (activeGames.containsKey(discordUserID)) {
            activeGames.get(discordUserID).add(discordSinglePlayerGame);
        } else {
            //First game setup
            List<DiscordSinglePlayerGame> gameList = new ArrayList<>();
            gameList.add(discordSinglePlayerGame);
            activeGames.put(discordUserID, gameList);
        }
    }

    public void executeGameOption(String discordUserID, String option) {
        if (activeGames.get(discordUserID) != null) {
            for (DiscordSinglePlayerGame game: activeGames.get(discordUserID)) {
                game.handleGameOption(option);
            }
        }
    }

    public void flushGame(String discordUserID, Game game) {
        if (!activeGames.containsKey(discordUserID)) {
            return;
        }

        List<DiscordSinglePlayerGame> gameList = new ArrayList<>(activeGames.get(discordUserID));

        for (int i = 0; i < gameList.size(); i++) {
            DiscordSinglePlayerGame possibleGame = gameList.get(i);
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
