package xyz.herberto.chatFilter.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import org.bukkit.command.CommandSender;
import xyz.herberto.chatFilter.ChatFilter;
import xyz.herberto.chatFilter.utils.CC;

import java.util.List;

@CommandAlias("chatfilter|filter")
public class ChatFilterCommand extends BaseCommand {

    @HelpCommand
    @Default
    public void help(CommandSender sender, CommandHelp help) {
        help.showHelp();
    }

    @Subcommand("add")
    @CommandPermission("chatfilter.command.add")
    public void add(CommandSender sender, String word) {
        List<String> blockedWords = ChatFilter.getInstance().getConfig().getStringList("blocked-words");

        if(blockedWords.contains(word)) {
            sender.sendMessage(CC.translate("&cThe blocked words list already contains &f&n" + word));
            return;
        }

        blockedWords.add(word);
        ChatFilter.getInstance().getConfig().set("blocked-words", blockedWords);
        ChatFilter.getInstance().saveConfig();

        sender.sendMessage(CC.translate("&aAdded &f&n" + word + "&a to the blocked word list."));
    }

    @Subcommand("remove")
    @CommandPermission("chatfilter.command.remove")
    public void remove(CommandSender sender, String word) {
        List<String> blockedWords = ChatFilter.getInstance().getConfig().getStringList("blocked-words");

        if(!blockedWords.contains(word)) {
            sender.sendMessage(CC.translate("&cThe blocked words list does not contain &f&n" + word));
            return;
        }

        blockedWords.remove(word);
        ChatFilter.getInstance().getConfig().set("blocked-words", blockedWords);
        ChatFilter.getInstance().saveConfig();

        sender.sendMessage(CC.translate("&aRemoved &f&n" + word + "&a from the blocked word list."));
    }

    @Subcommand("reload")
    @CommandPermission("chatfilter.command.reload")
    public void reload(CommandSender sender) {
        ChatFilter.getInstance().reloadConfig();
        sender.sendMessage(CC.translate("&aYou have reloaded the ChatFilter configuration."));
    }

}
