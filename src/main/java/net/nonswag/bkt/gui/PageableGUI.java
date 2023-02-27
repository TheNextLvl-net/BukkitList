package net.nonswag.bkt.gui;

import lombok.Getter;
import net.nonswag.bkt.item.ItemBuilder;
import net.nonswag.bkt.plugin.SimplePlugin;
import org.bukkit.Material;
import org.bukkit.inventory.InventoryHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public abstract class PageableGUI<T> extends GUI {
    private final List<T> elements;
    private final int[] slots;
    private int currentPage;

    public PageableGUI(SimplePlugin plugin, InventoryHolder owner, String title, int rows, List<T> elements, int[] slots) {
        super(plugin, owner, title, rows);
        this.elements = elements;
        this.slots = slots;
        loadPage(1);
    }

    /**
     * Construct an item for a given element
     * @param element the element to construct the item for
     * @return the item representing the element
     */
    public abstract GUIItem constructItem(T element);

    /**
     * This method is called after the page was successfully loaded
     */
    public abstract void pageLoaded();

    /**
     * loads the desired page
     * @param page the page to load
     */
    public void loadPage(int page) {
        getInventory().clear();
        if (!isPageEmpty(page)) {
            this.currentPage = page;
            var slots = Arrays.stream(getSlots()).iterator();
            getElements(page).forEach(element -> setSlot(slots.next(), constructItem(element)));
        }
        pageLoaded();
    }

    /**
     * @param page the page to get the elements for
     * @return the elements on the desired page
     */
    public List<T> getElements(int page) {
        if (page <= 0) return new ArrayList<>(0);
        var startingPoint = getStartingPoint(page);
        if (elements.size() < startingPoint) return new ArrayList<>(0);
        return new ArrayList<>(elements).subList(startingPoint, getEndPoint(page));
    }

    /**
     * @param page the page to get the starting point for
     * @return the starting point of the desired page
     */
    public int getStartingPoint(int page) {
        return slots.length * page;
    }

    /**
     * @param page the page to get the end point for
     * @return the end point of the desired page
     */
    public int getEndPoint(int page) {
        return Math.min(elements.size(), getStartingPoint(page) + slots.length);
    }

    /**
     * @param page the page to check for elements
     * @return whether the page contains elements
     */
    public boolean isPageEmpty(int page) {
        return page <= 0 || getElements(page).isEmpty();
    }

    /**
     * loads the next page
     */
    public void nextPage() {
        loadPage(getCurrentPage() + 1);
    }

    /**
     * loads the previous page
     */
    public void previousPage() {
        loadPage(getCurrentPage() - 1);
    }

    @Override
    protected void formatDefault() {
        var previous = new ItemBuilder(Material.ARROW).name("§fGo to page§8: §a" + (getCurrentPage() - 1)).toGUIItem(player -> previousPage());
        var next = new ItemBuilder(Material.ARROW).name("§fGo to page§8: §a" + (getCurrentPage() + 1)).toGUIItem(player -> nextPage());
        if (isEmpty(getSize() - 1) && !isPageEmpty(getCurrentPage() + 1)) setSlot(getSize() - 1, next);
        if (isEmpty(getSize() - 9) && !isPageEmpty(getCurrentPage() - 1)) setSlot(getSize() - 9, previous);
        super.formatDefault();
    }
}
