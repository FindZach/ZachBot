package org.findzach.bot.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Zach S <zach@findzach.com>
 * @since 9/4/2023
 */
@Getter
@Setter
public class Config {
    @JsonProperty("discordKey")
    private String discordKey;
}