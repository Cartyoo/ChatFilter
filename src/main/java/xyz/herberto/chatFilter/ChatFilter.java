package xyz.herberto.chatFilter;

import co.aikar.commands.PaperCommandManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.herberto.chatFilter.commands.ChatFilterCommand;
import xyz.herberto.chatFilter.listeners.ChatListener;

public final class ChatFilter extends JavaPlugin {
    @Getter public static ChatFilter instance;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new ChatListener(), this);

        PaperCommandManager manager = new PaperCommandManager(this);

        manager.enableUnstableAPI("api");

        manager.registerCommand(new ChatFilterCommand());

    }

    @Override
    public void onDisable() {

    }
}
