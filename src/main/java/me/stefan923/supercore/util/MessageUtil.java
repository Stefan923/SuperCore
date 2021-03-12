package me.stefan923.supercore.util;

public final class MessageUtil {

    private static final char COLOR_CHAR = '\u00A7';
    private static final char ALT_COLOR_CHAR = '&';

    private static final String FORMAT_CHARS = "0123456789AaBbCcDdEeFfKkLlMmNnOoRr";

    private MessageUtil() { }

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

    public static String centerMessage(String message) {
        if (message == null || message.isEmpty()) {
            return null;
        }

        int messagePxSize = 0;
        boolean previousCode = false;
        boolean isBold = false;

        for (char c : message.toCharArray()) {
            if (c == '§') {
                previousCode = true;
            } else if (previousCode) {
                previousCode = false;
                isBold = c == 'l' || c == 'L';
            } else {
                FontSize dFI = FontSize.getDefaultFontInfo(c);
                messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
                messagePxSize++;
            }
        }

        int halvedMessageSize = messagePxSize / 2;
        int toCompensate = 154 - halvedMessageSize;
        int spaceLength = FontSize.SPACE.getLength() + 1;
        int compensated = 0;
        StringBuilder spaces = new StringBuilder();
        while (compensated < toCompensate) {
            spaces.append(" ");
            compensated += spaceLength;
        }

        return formatAll(spaces.toString() + message);
    }

}
