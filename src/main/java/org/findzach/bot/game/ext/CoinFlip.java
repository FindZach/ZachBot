package org.findzach.bot.game.ext;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import org.findzach.bot.game.DiscordSinglePlayerGame;
import org.findzach.bot.game.Game;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author Zach S <zach@findzach.com>
 * @since 11/2/2022
 */
public class CoinFlip extends DiscordSinglePlayerGame {


    private CoinFace selectedCoin;

    public CoinFlip(JDA api, MessageChannelUnion textChannel, String contestant, double wager) {
        super(api, textChannel, contestant, wager);
    }

    @Override
    public Game getGame() {
        return Game.COINFLIP;
    }

    @Override
    public void startGame() {
        getTextChannel().sendMessage("Started a Coin Flip game for " + getContestantName() + "! \n Type 'heads' or 'tails' to pick a side").queue();
    }

    @Override
    public void sendCurrentDirections() {
        getTextChannel().sendMessage(getContestantName()+ ", you already have this game active! \n Pick an option; simply type: \n \"Heads\" or \"Tails\"").queue();
    }

    @Override
    public void handleGameOption(String option) {
        if (option.equalsIgnoreCase("flip")) {
            if (selectedCoin == null) {
                sendCurrentDirections();
                return;
            }

            getTextChannel().sendMessage("*"+getContestantName() + " is flipping the coin....*").queue();

            List<CoinFace> coinFlip = Arrays.asList(CoinFace.HEADS, CoinFace.TAILS);

            CoinFace selectedFace = getRandomCoinFace(coinFlip);
            if (selectedFace == selectedCoin) {
                setWinner(true);
                getTextChannel().sendMessage(getContestantName() + " flipped " + selectedFace + "! WINNER!").queueAfter(2000, TimeUnit.MILLISECONDS);
            } else {
                getTextChannel().sendMessage(getContestantName() + " flipped " + selectedFace + "! "+ getContestantName() +" is a LOSER!").queueAfter(2000, TimeUnit.MILLISECONDS);
            }

            destroyGame();
            return;
        }

        List<String> coinChoices = Arrays.asList("heads", "tails");

        if (coinChoices.contains(option.toLowerCase())) {
            System.out.println("CoinFlip GameOption: " + option);
            selectedCoin = CoinFace.getFromOption(option);
            getTextChannel().sendMessage("You've selected: " + selectedCoin.toString() +" type 'flip' when ready!").queue();
        }
    }

    public CoinFace getRandomCoinFace(List<CoinFace> list)
    {
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }

    enum CoinFace {
        HEADS,
        TAILS;

        public static CoinFace getFromOption(String option) {
            return option.equalsIgnoreCase("heads") ? HEADS : TAILS;
        }
    }
}
