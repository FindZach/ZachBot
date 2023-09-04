package org.findzach.bot.config;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Zach S <zach@findzach.com>
 * @since 9/4/2023
 */
public class Config {
    @JsonProperty("discordKey")
    private String discordKey;

    // Getters and setters
    public String getDiscordKey() {
        return discordKey;
    }

    public void setDiscordKey(String discordKey) {
        this.discordKey = discordKey;
    }
}