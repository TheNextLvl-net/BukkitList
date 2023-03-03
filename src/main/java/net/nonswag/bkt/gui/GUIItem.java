package net.nonswag.bkt.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public record GUIItem(ItemStack stack, ClickAction action) {
    @FunctionalInterface
    public interface ClickAction {
        void click(ClickType type, Player player);
    }
}
