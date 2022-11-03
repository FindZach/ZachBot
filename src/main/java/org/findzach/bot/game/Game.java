package org.findzach.bot.game;

/**
 * @author Zach (zach@findzach.com)
 * @since 11/2/2022
 */
public enum Game {
    COINFLIP,
    OVERUNDER;


    public static Game getGameFromString(String option) {
        for (Game game: Game.values()) {
            if (option.equalsIgnoreCase(game.toString())) {
                return game;
            }
        }
        return null;
    }
}
