package eu.senla.Hotel.ui.menu;

import java.util.List;

public class Navigator {
    private static Navigator instance;
    private Menu currentMenu;
    private Navigator() {}

    public static Navigator getInstance() {
        if(instance == null){		//если объект еще не создан
            instance = new Navigator();	//создать новый объект
        }
        return instance;		// вернуть ранее созданный объект
    }

    public Menu getCurrentMenu() {
        return currentMenu;
    }

    public void setCurrentMenu(Menu currentMenu) {
        this.currentMenu = currentMenu;
    }

    public void printMenu(){
        if (currentMenu != null) {
            System.out.println(currentMenu.toString());
            List<MenuItem> items = currentMenu.getMenuItems();
            items.stream().map(item -> currentMenu.getIndexMenu() + 1 + "." + item).forEach(System.out::println);

        }
    }

    public boolean navigate(int index){
        if (currentMenu != null) {
            List<MenuItem> menuItems = currentMenu.getMenuItems();
            if ((index == -1) || (index>=menuItems.size())) {
                System.out.println("Введите правильный пункт меню");
                return true;
            }
            MenuItem menuItem = currentMenu.getMenuItems().get(index);
            menuItem.doAction();
            currentMenu = menuItem.getNextMenu();
            return currentMenu != null;
        }
        return false;
    }
}
