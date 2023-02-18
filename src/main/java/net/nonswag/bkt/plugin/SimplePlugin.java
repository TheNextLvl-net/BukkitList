package net.nonswag.bkt.plugin;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.command.TabExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class SimplePlugin extends JavaPlugin {

    @Override
    public final void onEnable() {
        enable();
        registerListeners();
        registerCommands();
    }

    protected void enable() {
    }

    public void registerCommands() {
    }

    public void registerListeners() {
    }

    public void registerListener(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, this);
    }

    public void registerCommand(String label, TabExecutor executor) {
        registerCommand(label, executor, executor);
    }

    public void registerCommand(String label, CommandExecutor executor) {
        registerCommand(label, executor, null);
    }

    public void registerCommand(String label, CommandExecutor executor, @Nullable TabCompleter completer) {
        PluginCommand command = getCommand(label);
        if (command == null) return;
        command.setExecutor(executor);
        command.setTabCompleter(completer);
    }
}
