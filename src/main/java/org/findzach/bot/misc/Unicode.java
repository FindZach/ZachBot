package org.findzach.bot.misc;

/**
 * @author Zach (zach@findzach.com)
 * @since 11/2/2022
 */
public enum Unicode {
    PHONE("U+1F4DE"),
    THUMBS_UP("U+1F44D"),
    PIN("U+1F4CD"),
    CHECKMARK("✅"),
    CHECKMARK_HEAVY("✔"),
    BLUE_SQUARE("\uD83D\uDFE6"),
    ;

    private final String code;
    Unicode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}
