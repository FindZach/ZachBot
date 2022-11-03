package org.findzach.bot.game.ext;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import org.findzach.bot.game.DiscordGame;
import org.findzach.bot.game.Game;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author Zach S <zach@findzach.com>
 * @since 11/2/2022
 */
public class OverUnder extends DiscordGame {

    private OverUnderOption selectedOption;

    public OverUnder(JDA api, MessageChannelUnion textChannel, String discordID) {
        super(api, textChannel, discordID);
    }

    @Override
    public Game getGame() {
        return Game.OVERUNDER;
    }

    @Override
    public void startGame() {
        System.out.println("Overunder Game Started!");
        sendCurrentDirections();
    }

    @Override
    public void sendCurrentDirections() {

        getTextChannel().sendMessage("Type either 'Over' or 'Under'").queue();

    }

    @Override
    public void handleGameOption(String option) {

        if (option.equalsIgnoreCase("roll")) {
            if (this.selectedOption == null) {
                sendCurrentDirections();
                return;
            }

            getTextChannel().sendMessage("*"+getContestantName() + " grips the dice with their sweaty palms and rolls*").queue();
            int myRandInt = new Random().nextInt(100) + 1;


            String resultText = "big fat LOSER!!!";

            getTextChannel().sendMessage(getContestantName() + " rolls a " + myRandInt +" and selected: " + selectedOption).queueAfter(2000, TimeUnit.MILLISECONDS);

            //Winner Options
            //If OVER and rolls over 50
            //if UNDER and rolls under 50
            //Everything else == LOSER
            if (selectedOption == OverUnderOption.OVER && myRandInt > 50
            || selectedOption == OverUnderOption.UNDER && myRandInt < 50
            ) {
                //Winner
                resultText = "HUGE WINNER!";
            }

            getTextChannel().sendMessage(getContestantName() + " is a " + resultText).queueAfter(3000, TimeUnit.MILLISECONDS);
            destroyGame();
        }

        //end

        List<String> options = Arrays.asList("over", "under");

        if (options.contains(option.toLowerCase())) {
            this.selectedOption = OverUnderOption.getOption(option.toLowerCase());
            getTextChannel().sendMessage(getContestantName() + " has selected the option: " + selectedOption + "\n" + "Type 'roll' when ready!").queue();
        }
    }

    enum OverUnderOption {
        OVER,
        UNDER;

        public static OverUnderOption getOption(String option) {
            return option.equalsIgnoreCase("over") ? OVER:UNDER;
        }
    }
}
