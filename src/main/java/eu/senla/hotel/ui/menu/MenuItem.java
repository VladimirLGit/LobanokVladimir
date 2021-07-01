package main.java.eu.senla.hotel.ui.menu;

import main.java.eu.senla.hotel.ui.actions.IAction;

public class MenuItem {
    private int indexItem;
    private String title;
    private IAction iAction;
    private Menu nextMenu;

    public MenuItem(int indexItem, String title, IAction iAction, Menu nextMenu) {
        this.indexItem = indexItem;
        this.title = title;
        this.iAction = iAction;
        this.nextMenu = nextMenu;
    }

    public void doAction() {
        iAction.execute();
    }

    public Menu getNextMenu() {
        return nextMenu;
    }

    @Override
    public String toString() {
        return indexItem + 1 + ". " + title;
    }
}
