package eu.senla.hotel.ui.menu;

import eu.senla.hotel.ui.actions.*;

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
        }, createGuestMenu(rootMenu.getIndexMenu())));
        rootMenu.addMenuItem(new MenuItem(2,"Дополнительные услуги", () -> {
            System.out.println("EXIT");
        }, createServiceMenu(rootMenu.getIndexMenu())));
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
        roomMenu.addMenuItem(new MenuItem(1,"Удалить комнату", new DeleteRoom(), roomMenu));
        roomMenu.addMenuItem(new MenuItem(2,"Вывести комнаты", new ViewRooms(), roomMenu));
        roomMenu.addMenuItem(new MenuItem(3,"Изменить стоимость комнаты",new ChangePriceRoom(), roomMenu));
        roomMenu.addMenuItem(new MenuItem(4,"Изменить статус комнаты", new ChangeStateRoom(), roomMenu));
        roomMenu.addMenuItem(new MenuItem(5,"Выход в предыдущее меню", ()->{
            System.out.println("Печать текста");
        }, rootMenu));

        return roomMenu;
    }

    public Menu createServiceMenu(int index) {
        Menu serviceMenu = new Menu(index,"ServiceMenu");
        serviceMenu.addMenuItem(new MenuItem(0,"Добавить услугу", new AddService(), serviceMenu));
        serviceMenu.addMenuItem(new MenuItem(1,"Удалить услугу", new DeleteService(), serviceMenu));
        serviceMenu.addMenuItem(new MenuItem(2,"Вывести услуги", new ViewServices(), serviceMenu));
        serviceMenu.addMenuItem(new MenuItem(3,"Изменить стоимость услуги", new ChangePriceService(), serviceMenu));
        serviceMenu.addMenuItem(new MenuItem(4,"Выход в предыдущее меню", ()->{
            System.out.println("Печать текста");
        }, rootMenu));

        return serviceMenu;
    }

    public Menu createGuestMenu(int index) {
        Menu guestMenu = new Menu(index,"GuestMenu");
        guestMenu.addMenuItem(new MenuItem(0,"Добавить(Создать) гостя", new AddGuest(), guestMenu));
        guestMenu.addMenuItem(new MenuItem(1,"Заселить гостя в свободный номер", new CheckInGuest(), guestMenu));
        guestMenu.addMenuItem(new MenuItem(2,"Выселить гостя из номера", new CheckOutGuest(), guestMenu));
        guestMenu.addMenuItem(new MenuItem(3,"Список всех постояльцев", new ViewGuestsHotel(), guestMenu));
        guestMenu.addMenuItem(new MenuItem(4,"Выход в предыдущее меню", ()->{
            System.out.println("Печать текста");
        }, rootMenu));

        return guestMenu;
    }
}
