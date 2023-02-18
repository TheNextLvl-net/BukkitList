package net.nonswag.bkt.gui;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.nonswag.bkt.item.ItemBuilder;
import net.nonswag.bkt.plugin.SimplePlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.HashMap;

@Getter(AccessLevel.PROTECTED)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class GUI implements Listener {
    private final HashMap<Integer, GUIItem> items = new HashMap<>();
    private final Inventory inventory;
    private final SimplePlugin plugin;
    @Getter(AccessLevel.PROTECTED)
    private boolean disposed;

    public GUI(SimplePlugin plugin, @Nullable InventoryHolder owner, String title, int rows) {
        this(Bukkit.createInventory(owner, rows * 9, title), plugin);
        getPlugin().registerListener(this);
    }

    public GUI(SimplePlugin plugin, String title, int rows) {
        this(plugin, null, title, rows);
    }

    public void setSlot(int slot, GUIItem item) {
        checkDisposed();
        getItems().put(slot, item);
        getInventory().setItem(slot, item.stack());
    }

    public void open(HumanEntity player) {
        checkDisposed();
        player.openInventory(getInventory());
    }

    public void dispose() {
        checkDisposed();
        disposed = true;
        getInventory().getViewers().forEach(HumanEntity::closeInventory);
        HandlerList.unregisterAll(this);
    }

    protected void formatDefault() {
        checkDisposed();
        ItemStack placeholder = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).name("§7-§8/§7-");
        for (int i = 0; i < getInventory().getSize(); i++) {
            if (getInventory().getItem(i) == null) getInventory().setItem(i, placeholder);
        }
    }

    protected void checkDisposed() throws IllegalStateException {
        if (isDisposed()) throw new IllegalStateException("Trying to access disposed GUI");
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onInventoryClick(InventoryClickEvent event) {
        if (getInventory().equals(event.getView().getTopInventory())) try {
            if (event.getView().getBottomInventory().equals(event.getClickedInventory())) return;
            GUIItem item = getItems().get(event.getSlot());
            if (item != null) item.action().click(event.getWhoClicked());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPluginDisable(PluginDisableEvent event) {
        if (event.getPlugin().equals(getPlugin())) dispose();
    }
}
