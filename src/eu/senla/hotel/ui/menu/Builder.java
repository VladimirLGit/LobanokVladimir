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
            System.out.println("----");
        }, createRoomMenu(rootMenu.getIndexMenu())));
        rootMenu.addMenuItem(new MenuItem(1,"Обслуживание гостей", () -> {
            System.out.println("----");
        }, createGuestMenu(rootMenu.getIndexMenu())));
        rootMenu.addMenuItem(new MenuItem(2,"Дополнительные услуги", () -> {
            System.out.println("----");
        }, createServiceMenu(rootMenu.getIndexMenu())));

        rootMenu.addMenuItem(new MenuItem(3,"Загрузка и сохранение объектов", () -> {
            System.out.println("----");
        }, createSerializationsMenu(rootMenu.getIndexMenu())));
        rootMenu.addMenuItem(new MenuItem(4,"Выход из программы", () -> {
            System.out.println("----");
        }, null));

    }

    private Menu createSerializationsMenu(int indexMenu) {
        Menu serializationsMenu = new Menu(indexMenu,"SerializationsMenu");
        serializationsMenu.addMenuItem(new MenuItem(0,"Загрузить объекты", new DeserializationObject(), serializationsMenu));
        serializationsMenu.addMenuItem(new MenuItem(1,"Сохранить объекты", new SerializationsObjects(), serializationsMenu));
        serializationsMenu.addMenuItem(new MenuItem(2,"Выход в предыдущее меню", ()->{
            System.out.println("----");
        }, rootMenu));
        return serializationsMenu;
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
            System.out.println("----");
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
            System.out.println("----");
        }, rootMenu));

        return serviceMenu;
    }

    public Menu createGuestMenu(int index) {
        Menu guestMenu = new Menu(index,"GuestMenu");
        guestMenu.addMenuItem(new MenuItem(0,"Добавить(Создать) гостя", new AddGuest(), guestMenu));
        guestMenu.addMenuItem(new MenuItem(1,"Заселить гостя в свободный номер", new CheckInGuest(), guestMenu));
        guestMenu.addMenuItem(new MenuItem(2,"Выселить гостя из номера", new CheckOutGuest(), guestMenu));
        guestMenu.addMenuItem(new MenuItem(3,"Заказать услугу", new CallService(), guestMenu));
        guestMenu.addMenuItem(new MenuItem(4,"Список всех постояльцев", new ViewGuestsHotel(), guestMenu));
        guestMenu.addMenuItem(new MenuItem(5,"Выход в предыдущее меню", ()->{
            System.out.println("----");
        }, rootMenu));

        return guestMenu;
    }
}
