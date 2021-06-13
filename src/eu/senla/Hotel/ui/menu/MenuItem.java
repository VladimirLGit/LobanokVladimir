package eu.senla.Hotel.ui.menu;

import eu.senla.Hotel.ui.actions.IAction;

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

    }

    public Menu getNextMenu() {
        return nextMenu;
    }

    @Override
    public String toString() {
        return indexItem + 1 + ". " + title;
    }
}
