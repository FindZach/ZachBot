package org.findzach.bot.commands.impl;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.findzach.bot.commands.ZachBotCommand;

/**
 * @author Zach S <zach@findzach.com>
 * @since 11/3/2022
 */
public class VioletCommand implements ZachBotCommand {
    @Override
    public void executeCommand(MessageReceivedEvent event, String[] args) {

        event.getChannel().sendMessage(""+VioletPicture.ONE.getImageUrl()).queue();
    }



    enum VioletPicture {
        ONE("https://i.gyazo.com/b3cd17510642567763d24aa1abe3cbd2.png");

        private String imageUrl;
        VioletPicture(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getImageUrl() {
            return imageUrl;
        }
    }
}
