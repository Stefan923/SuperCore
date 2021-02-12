package me.stefan923.supercore.util;

public final class MessageUtils {

    public static final char COLOR_CHAR = '\u00A7';
    public static final char ALT_COLOR_CHAR = '&';

    public static final String FORMAT_CHARS = "0123456789AaBbCcDdEeFfKkLlMmNnOoRr";

    private MessageUtils() { }

    public static String formatAll(String message) {
        char[] formattedMessage = message.toCharArray();
        for (int i = 0; i < formattedMessage.length - 1; i++) {
            if (formattedMessage[i] == ALT_COLOR_CHAR && FORMAT_CHARS.indexOf(formattedMessage[i+1]) > -1) {
                formattedMessage[i] = COLOR_CHAR;
                formattedMessage[i+1] = Character.toLowerCase(formattedMessage[i+1]);
            }
        }

        return new String(formattedMessage);
    }

    public static String removeFormat(String message) {
        for (int i = 0; i < FORMAT_CHARS.length(); ++i) {
            message = message.replace("&" + FORMAT_CHARS.charAt(i), "");
        }

        return message;
    }

}
