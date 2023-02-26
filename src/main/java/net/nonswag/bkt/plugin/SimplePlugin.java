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

    /**
     * Handle the startup logic of your plugin in here
     */
    protected void enable() {
    }

    /**
     * Register all your commands in here
     */
    public void registerCommands() {
    }

    /**
     * Register all your listeners in here
     */
    public void registerListeners() {
    }

    /**
     * Register a listener
     * @param listener the listener
     */
    public void registerListener(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, this);
    }

    /**
     * Register a command
     * @param label the command label
     * @param executor the command itself
     */
    public void registerCommand(String label, TabExecutor executor) {
        registerCommand(label, executor, executor);
    }

    /**
     * Register a command
     * @param label the command label
     * @param executor the command itself
     */
    public void registerCommand(String label, CommandExecutor executor) {
        registerCommand(label, executor, null);
    }

    /**
     * Register a command
     * @param label the command label
     * @param executor the command itself
     * @param completer the tab completer
     */
    public void registerCommand(String label, CommandExecutor executor, @Nullable TabCompleter completer) {
        PluginCommand command = getCommand(label);
        if (command == null) return;
        command.setExecutor(executor);
        command.setTabCompleter(completer);
    }
}
