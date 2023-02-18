package net.nonswag.bkt.gui;

import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;

public record GUIItem(ItemStack stack, ClickAction action) {
    public interface ClickAction {
        void click(HumanEntity player);
    }
}
