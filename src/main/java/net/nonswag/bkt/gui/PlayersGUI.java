package net.nonswag.bkt.gui;

import net.nonswag.bkt.item.ItemBuilder;
import net.nonswag.bkt.plugin.SimplePlugin;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.InventoryHolder;

import java.util.List;

public class PlayersGUI extends PageableGUI<OfflinePlayer> {

    public PlayersGUI(SimplePlugin plugin, InventoryHolder owner, String title, int rows, List<OfflinePlayer> elements, int[] slots) {
        super(plugin, owner, title, rows, elements, slots);
    }

    @Override
    public GUIItem constructItem(OfflinePlayer player) {
        return new ItemBuilder(Material.PLAYER_HEAD).head(player).toGUIItem();
    }

    @Override
    public void pageLoaded() {
        formatDefault();
    }
}
