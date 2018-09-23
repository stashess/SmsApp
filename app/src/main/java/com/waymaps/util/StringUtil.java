package com.waymaps.util;

public class StringUtil {

    public static final int CYRILLIC_MESSAGE_LENGTH = 70;
    public static final int DEFAULT_MESSAGE_LENGTH = 140;

    public static boolean containCyrillic(String text) {
        if (text == null) {
            return false;
        }

        for (int i = 0; i < text.length(); i++) {
            if (Character.UnicodeBlock.of(text.charAt(i)).equals(Character.UnicodeBlock.CYRILLIC)) {
                return true;
            }
        }

        return false;
    }

    public static String cutString(String text, boolean isCyrillic) {
        if (text == null){
            return null;
        }
        if (isCyrillic) {
            return text.substring(0,Math.min(text.length(),CYRILLIC_MESSAGE_LENGTH));
        } else {
            return text.substring(0,Math.min(text.length(),DEFAULT_MESSAGE_LENGTH));
        }
    }
}
