package org.findzach.bot.game;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;

import java.sql.Time;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Zach S <zach@findzach.com>
 * @since 11/2/2022
 *
 * Every custom game will have this
 */
public abstract class DiscordGame {

    private MessageChannelUnion textChannel;
    private JDA api;

    private final String contestantId;

    public DiscordGame(JDA api, MessageChannelUnion textChannel, String discordID) {
        this.textChannel = textChannel;
        this.api = api;
        this.contestantId = discordID;

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
        GameHandler.getGameHandler().flushGame(contestantId, getGame());
    }

    public String getContestantId() {
        return contestantId;
    }
}
