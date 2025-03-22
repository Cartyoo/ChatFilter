package xyz.herberto.chatFilter.utils;

import xyz.herberto.chatFilter.ChatFilter;

import java.util.List;

public class ChatUtils {

    public static final List<String> blockedWords = ChatFilter.getInstance().getConfig().getStringList("blocked-words");
    public static final String replacement = ChatFilter.getInstance().getConfig().getString("replacement", "<stars>");

}
