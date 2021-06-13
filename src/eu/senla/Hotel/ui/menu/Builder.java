package eu.senla.Hotel.ui.menu;

import eu.senla.Hotel.ui.actions.AddRoom;

public class Builder {
    private static Builder instance;
    private Menu rootMenu;
    private Builder() {}

    public static Builder getInstance() {
        if(instance == null){		//если объект еще не создан
            instance = new Builder();	//создать новый объект
        }
        return instance;		// вернуть ранее созданный объект
    }

    public void buildMenu() {
        rootMenu = new Menu(0,"RootMenu");
        rootMenu.addMenuItem(new MenuItem(0,"Обслуживание номеров", () -> {
            System.out.println("EXIT");
        }, createRoomMenu(rootMenu.getIndexMenu())));
        rootMenu.addMenuItem(new MenuItem(1,"Обслуживание гостей", () -> {
            System.out.println("EXIT");
        }, createRoomMenu(rootMenu.getIndexMenu())));
        rootMenu.addMenuItem(new MenuItem(2,"Дополнительные услуги", () -> {
            System.out.println("EXIT");
        }, createRoomMenu(rootMenu.getIndexMenu())));
        rootMenu.addMenuItem(new MenuItem(3,"Выход из программы", () -> {
            System.out.println("EXIT");
        }, null));

    }

    public Menu getRootMenu() {
        return rootMenu;
    }

    public Menu createRoomMenu(int index) {
        Menu roomMenu = new Menu(index,"RoomMenu");
        roomMenu.addMenuItem(new MenuItem(0,"Добавить комнату", new AddRoom(), roomMenu));
        roomMenu.addMenuItem(new MenuItem(1,"Удалить комнату", ()->{
            System.out.println("Печать текста");
        }, roomMenu));
        roomMenu.addMenuItem(new MenuItem(1,"Вывести комнаты", ()->{
            System.out.println("Печать текста");
        }, roomMenu));
        roomMenu.addMenuItem(new MenuItem(2,"Изменить стоимость комнаты", ()->{
            System.out.println("Печать текста");
        }, roomMenu));
        roomMenu.addMenuItem(new MenuItem(3,"Изменить статус комнаты", ()->{
            System.out.println("Печать текста");
        }, roomMenu));
        roomMenu.addMenuItem(new MenuItem(4,"Выход в предыдущее меню", ()->{
            System.out.println("Печать текста");
        }, rootMenu));

        return roomMenu;
    }

    public Menu createServiceMenu(int index) {
        Menu roomMenu = new Menu(index,"ServiceMenu");
        roomMenu.addMenuItem(new MenuItem(0,"Какое-то действие", new AddRoom(), roomMenu));
        roomMenu.addMenuItem(new MenuItem(1,"Какое-то действие", ()->{
            System.out.println("Печать текста");
        }, roomMenu));
        roomMenu.addMenuItem(new MenuItem(2,"Выход в предыдущее меню", ()->{
            System.out.println("Печать текста");
        }, rootMenu));

        return roomMenu;
    }

    public Menu createGuestMenu(int index) {
        Menu roomMenu = new Menu(index,"GuestMenu");
        roomMenu.addMenuItem(new MenuItem(0,"Какое-то действие", new AddRoom(), roomMenu));
        roomMenu.addMenuItem(new MenuItem(1,"Какое-то действие", ()->{
            System.out.println("Печать текста");
        }, roomMenu));
        roomMenu.addMenuItem(new MenuItem(2,"Выход в предыдущее меню", ()->{
            System.out.println("Печать текста");
        }, rootMenu));

        return roomMenu;
    }
}
