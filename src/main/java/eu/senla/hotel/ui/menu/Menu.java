package eu.senla.hotel.ui.menu;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    private int indexMenu;
    private String name;
    private List<MenuItem> menuItems;

    public Menu(int index, String name) {
        this.indexMenu = index;
        this.name = name;
    }

    public List<MenuItem> getMenuItems() {
        if (menuItems == null) {
            menuItems = new ArrayList<>();
        }
        return menuItems;
    }

    public int getIndexMenu() {
        return indexMenu;
    }

    public void setIndexMenu(int indexMenu) {
        this.indexMenu = indexMenu;
    }

    public void addMenuItem(MenuItem item) {
        if (menuItems == null) {
            menuItems = new ArrayList<>();
        }
        menuItems.add(item);
    }

    @Override
    public String toString() {
        return Integer.toString(indexMenu + 1) + ". " + name + "->";
    }
}
