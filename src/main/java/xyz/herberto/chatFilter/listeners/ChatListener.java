package xyz.herberto.chatFilter.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import xyz.herberto.chatFilter.ChatFilter;
import xyz.herberto.chatFilter.utils.CC;
import xyz.herberto.chatFilter.utils.ChatUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncChatEvent event) {
        if (ChatFilter.getInstance().getConfig().getBoolean("bypass-permission") && event.getPlayer().hasPermission("chatfilter.bypass")) {
            return;
        }

        String filteredMessage = PlainTextComponentSerializer.plainText().serialize(event.message());
        String filteredWord = null;

        for (String word : ChatUtils.blockedWords) {
            Pattern pattern = Pattern.compile("(?i)" + Pattern.quote(word));
            Matcher matcher = pattern.matcher(filteredMessage);

            if (matcher.find()) {
                filteredWord = matcher.group();
                if (ChatUtils.replacement.equalsIgnoreCase("<stars>")) {
                    String replacement = "*".repeat(filteredWord.length());
                    filteredMessage = matcher.replaceAll(replacement);
                } else {
                    filteredMessage = matcher.replaceAll(ChatUtils.replacement);
                }
            }


            if (filteredWord != null && ChatFilter.getInstance().getConfig().getBoolean("message.send")) {
                if (ChatFilter.getInstance().getConfig().getBoolean("cancel-message", false)) {
                    event.setCancelled(true);
                    return;
                }

                String warningMessage = ChatFilter.getInstance().getConfig().getString("message.message", "&cYour message has been filtered for containing &f&n<word>");
                warningMessage = warningMessage.replace("<word>", filteredWord);

                event.getPlayer().sendMessage(CC.translate(warningMessage));
            }

            event.message(LegacyComponentSerializer.legacyAmpersand().deserialize(filteredMessage));
        }
    }


}
