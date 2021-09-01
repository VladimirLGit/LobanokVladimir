package eu.senla.hotel.ui.menu;


import eu.senla.hotel.ui.actions.IAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

public class Builder {
    private static Builder instance;
    private Menu rootMenu;
    private IAction addGuest;
    private IAction checkInGuest;
    private IAction checkOutGuest;
    private IAction callService;
    private IAction viewGuestsHotel;
    private IAction changePriceService;
    private IAction viewServices;
    private IAction deleteService;
    private IAction addService;
    private IAction changeStateRoom;
    private IAction changePriceRoom;
    private IAction viewRooms;
    private IAction deleteRoom;
    private IAction addRoom;
    private IAction serializationsObjects;
    private IAction deserializationObject;

    public static Builder getInstance() {
        if (instance == null) {        //если объект еще не создан
            instance = new Builder();    //создать новый объект
        }
        return instance;        // вернуть ранее созданный объект
    }

    public void buildMenu() {
        rootMenu = new Menu(0, "RootMenu");
        rootMenu.addMenuItem(new MenuItem(0, "Обслуживание номеров", () -> {
            System.out.println("----");
        }, createRoomMenu(0)));
        rootMenu.addMenuItem(new MenuItem(1, "Обслуживание гостей", () -> {
            System.out.println("----");
        }, createGuestMenu(1)));
        rootMenu.addMenuItem(new MenuItem(2, "Дополнительные услуги", () -> {
            System.out.println("----");
        }, createServiceMenu(2)));
        rootMenu.addMenuItem(new MenuItem(3, "Загрузка и сохранение объектов", () -> {
            System.out.println("----");
        }, createSerializationsMenu(3)));
        rootMenu.addMenuItem(new MenuItem(4, "Выход из программы", () -> {
            System.out.println("----");
        }, null));
    }

    private Menu createSerializationsMenu(int indexMenu) {
        Menu serializationsMenu = new Menu(indexMenu, "SerializationsMenu");
        serializationsMenu.addMenuItem(new MenuItem(0, "Загрузить объекты", deserializationObject, serializationsMenu));
        serializationsMenu.addMenuItem(new MenuItem(1, "Сохранить объекты", serializationsObjects, serializationsMenu));
        serializationsMenu.addMenuItem(new MenuItem(2, "Выход в предыдущее меню", () -> {
            System.out.println("----");
        }, rootMenu));
        return serializationsMenu;
    }

    public Menu getRootMenu() {
        return rootMenu;
    }

    public Menu createRoomMenu(int index) {
        Menu roomMenu = new Menu(index, "RoomMenu");
        roomMenu.addMenuItem(new MenuItem(0, "Добавить комнату", addRoom, roomMenu));
        roomMenu.addMenuItem(new MenuItem(1, "Удалить комнату", deleteRoom, roomMenu));
        roomMenu.addMenuItem(new MenuItem(2, "Вывести комнаты", viewRooms, roomMenu));
        roomMenu.addMenuItem(new MenuItem(3, "Изменить стоимость комнаты", changePriceRoom, roomMenu));
        roomMenu.addMenuItem(new MenuItem(4, "Изменить статус комнаты", changeStateRoom, roomMenu));
        roomMenu.addMenuItem(new MenuItem(5, "Выход в предыдущее меню", () -> {
            System.out.println("----");
        }, rootMenu));

        return roomMenu;
    }

    public Menu createServiceMenu(int index) {
        Menu serviceMenu = new Menu(index, "ServiceMenu");
        serviceMenu.addMenuItem(new MenuItem(0, "Добавить услугу", addService, serviceMenu));
        serviceMenu.addMenuItem(new MenuItem(1, "Удалить услугу", deleteService, serviceMenu));
        serviceMenu.addMenuItem(new MenuItem(2, "Вывести услуги", viewServices, serviceMenu));
        serviceMenu.addMenuItem(new MenuItem(3, "Изменить стоимость услуги", changePriceService, serviceMenu));
        serviceMenu.addMenuItem(new MenuItem(4, "Выход в предыдущее меню", () -> {
            System.out.println("----");
        }, rootMenu));

        return serviceMenu;
    }

    public Menu createGuestMenu(int index) {
        Menu guestMenu = new Menu(index, "GuestMenu");
        guestMenu.addMenuItem(new MenuItem(0, "Добавить(Создать) гостя", addGuest, guestMenu));
        guestMenu.addMenuItem(new MenuItem(1, "Заселить гостя в свободный номер", checkInGuest, guestMenu));
        guestMenu.addMenuItem(new MenuItem(2, "Выселить гостя из номера", checkOutGuest, guestMenu));
        guestMenu.addMenuItem(new MenuItem(3, "Заказать услугу", callService, guestMenu));
        guestMenu.addMenuItem(new MenuItem(4, "Список всех постояльцев", viewGuestsHotel, guestMenu));
        guestMenu.addMenuItem(new MenuItem(5, "Выход в предыдущее меню", () -> {
            System.out.println("----");
        }, rootMenu));

        return guestMenu;
    }

    @Autowired
    @Qualifier("addGuest")
    public void setAddGuest(IAction addGuest) {
        this.addGuest = addGuest;
    }

    @Autowired
    @Qualifier("checkInGuest")
    public void setCheckInGuest(IAction checkInGuest) {
        this.checkInGuest = checkInGuest;
    }

    @Autowired
    @Qualifier("viewRooms")
    public void setViewRooms(IAction viewRooms) {
        this.viewRooms = viewRooms;
    }

    @Autowired
    @Qualifier("checkOutGuest")
    public void setCheckOutGuest(IAction checkOutGuest) {
        this.checkOutGuest = checkOutGuest;
    }

    @Autowired
    @Qualifier("callService")
    public void setCallService(IAction callService) {
        this.callService = callService;
    }

    @Autowired
    @Qualifier("viewGuestsHotel")
    public void setViewGuestsHotel(IAction viewGuestsHotel) {
        this.viewGuestsHotel = viewGuestsHotel;
    }

    @Autowired
    @Qualifier("changePriceService")
    public void setChangePriceService(IAction changePriceService) {
        this.changePriceService = changePriceService;
    }

    @Autowired
    @Qualifier("viewServices")
    public void setViewServices(IAction viewServices) {
        this.viewServices = viewServices;
    }

    @Autowired
    @Qualifier("deleteService")
    public void setDeleteService(IAction deleteService) {
        this.deleteService = deleteService;
    }

    @Autowired
    @Qualifier("addService")
    public void setAddService(IAction addService) {
        this.addService = addService;
    }

    @Autowired
    @Qualifier("changeStateRoom")
    public void setChangeStateRoom(IAction changeStateRoom) {
        this.changeStateRoom = changeStateRoom;
    }

    @Autowired
    @Qualifier("changePriceRoom")
    public void setChangePriceRoom( IAction changePriceRoom) {
        this.changePriceRoom = changePriceRoom;
    }

    @Autowired
    @Qualifier("deleteRoom")
    public void setDeleteRoom(IAction deleteRoom) {
        this.deleteRoom = deleteRoom;
    }

    @Autowired
    @Qualifier("addRoom")
    public void setAddRoom(IAction addRoom) {
        this.addRoom = addRoom;
    }

    @Autowired
    @Qualifier("serializationsObjects")
    public void setSerializationsObjects(IAction serializationsObjects) {
        this.serializationsObjects = serializationsObjects;
    }

    @Autowired
    @Qualifier("deserializationObject")
    public void setDeserializationObject(IAction deserializationObject) {
        this.deserializationObject = deserializationObject;
    }
}
