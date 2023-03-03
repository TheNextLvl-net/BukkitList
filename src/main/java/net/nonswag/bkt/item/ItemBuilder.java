package net.nonswag.bkt.item;

import net.nonswag.bkt.gui.GUIItem;
import net.nonswag.core.api.annotation.FieldsAreNullableByDefault;
import net.nonswag.core.api.annotation.MethodsReturnNonnullByDefault;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;

@FieldsAreNullableByDefault
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ItemBuilder extends ItemStack {
    private GUIItem guiItem;

    public ItemBuilder(Material type) {
        this(type, 1);
    }

    public ItemBuilder(Material type, int amount) {
        super(type, amount);
    }

    public ItemBuilder(ItemStack stack) throws IllegalArgumentException {
        super(stack);
    }

    public ItemBuilder name(String name) {
        return modify(meta -> meta.setDisplayName(name));
    }

    public ItemBuilder lore(String... lore) {
        return modify(meta -> meta.setLore(Arrays.asList(lore)));
    }

    public ItemBuilder appendLore(String... lore) {
        return modify(meta -> {
            var list = new ArrayList<String>();
            if (meta.getLore() != null) list.addAll(meta.getLore());
            list.addAll(Arrays.asList(lore));
            meta.setLore(list);
        });
    }

    public ItemBuilder head(OfflinePlayer player) {
        return modify(meta -> {
            if (meta instanceof SkullMeta skull) skull.setOwnerProfile(player.getPlayerProfile());
        });
    }

    public ItemBuilder modify(Consumer<ItemMeta> modification) {
        ItemMeta itemMeta = getItemMeta();
        if (itemMeta == null) return this;
        modification.accept(itemMeta);
        setItemMeta(itemMeta);
        return this;
    }

    public GUIItem toGUIItem(GUIItem.ClickAction action) {
        return guiItem == null ? guiItem = new GUIItem(this, action) : guiItem;
    }

    public GUIItem toGUIItem() {
        return toGUIItem((type, player) -> {
        });
    }
}
