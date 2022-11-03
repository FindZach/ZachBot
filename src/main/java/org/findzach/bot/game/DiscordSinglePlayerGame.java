package org.findzach.bot.game;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import org.findzach.bot.eco.EconomyController;

import java.text.NumberFormat;
import java.time.Instant;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * @author Zach S <zach@findzach.com>
 * @since 11/2/2022
 *
 * Every custom game will have this
 */
public abstract class DiscordSinglePlayerGame {

    private boolean winner = false;
    private MessageChannelUnion textChannel;
    private JDA api;

    private final String contestantId;

    private final double wager;

    public DiscordSinglePlayerGame(JDA api, MessageChannelUnion textChannel, String discordID, double wager) {
        this.textChannel = textChannel;
        this.api = api;
        this.contestantId = discordID;
        this.wager = wager;

        startGame();
    }

    private Long startEpochSecond = Instant.now().getEpochSecond();
    public abstract Game getGame();

    public abstract void startGame();
    public abstract void sendCurrentDirections();

    public abstract void handleGameOption(String option);

    public Long getDelay() {
        return Instant.now().getEpochSecond() - startEpochSecond;
    }

    public MessageChannelUnion getTextChannel() {
        return textChannel;
    }

    public JDA getApi() {
        return api;
    }

    String contestantName = "";

    public String getContestantName() {
        if (contestantName == "") {
            this.contestantName = getApi().getUserById(contestantId).getName();
        }
        return contestantName;
    }

    protected void destroyGame() {
        if (winner) {
            double winnings = wager * 2;
            EconomyController.getEconomyController().getBankOptions(getContestantId()).addAmount(winnings);

            Locale usa = new Locale("en", "US");
            NumberFormat dollarFormat = NumberFormat.getCurrencyInstance(usa);

            getTextChannel().sendMessage(getContestantName() + " has won " + dollarFormat.format(winnings) + " from that game!").queueAfter(3000, TimeUnit.MILLISECONDS);
        }
        GameHandler.getGameHandler().flushGame(contestantId, getGame());
    }

    public String getContestantId() {
        return contestantId;
    }

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }
}
