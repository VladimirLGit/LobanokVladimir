package eu.senla.hotel.ui.menu;


import eu.senla.hotel.ui.actions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Builder {
    private static Builder instance;
    private Menu rootMenu;
    private IAction addGuest;
    private IAction checkInGuest;
    @Autowired
    @Qualifier(value = "CheckOutGuest")
    private IAction checkOutGuest;
    @Autowired
    @Qualifier(value = "CallService")
    private IAction callService;
    @Autowired
    @Qualifier(value = "ViewGuestsHotel")
    private IAction viewGuestsHotel;
    @Autowired
    @Qualifier(value = "ChangePriceService")
    private IAction changePriceService;
    @Autowired
    @Qualifier(value = "ViewServices")
    private IAction viewServices;
    @Autowired
    @Qualifier(value = "DeleteService")
    private IAction deleteService;
    @Autowired
    @Qualifier(value = "AddService")
    private IAction addService;
    @Autowired
    @Qualifier(value = "ChangeStateRoom")
    private IAction changeStateRoom;
    @Autowired
    @Qualifier(value = "ChangePriceRoom")
    private IAction changePriceRoom;

    private IAction viewRooms;
    @Autowired
    @Qualifier(value = "DeleteRoom")
    private IAction deleteRoom;
    @Autowired
    @Qualifier(value = "AddRoom")
    private IAction addRoom;
    @Autowired
    @Qualifier(value = "SerializationsObjects")
    private IAction serializationsObjects;
    @Autowired
    @Qualifier(value = "DeserializationObject")
    private IAction deserializationObject;

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
        }, createRoomMenu(0)));
        rootMenu.addMenuItem(new MenuItem(1,"Обслуживание гостей", () -> {
            System.out.println("----");
        }, createGuestMenu(1)));
        rootMenu.addMenuItem(new MenuItem(2,"Дополнительные услуги", () -> {
            System.out.println("----");
        }, createServiceMenu(2)));
        rootMenu.addMenuItem(new MenuItem(3,"Загрузка и сохранение объектов", () -> {
            System.out.println("----");
        }, createSerializationsMenu(3)));
        rootMenu.addMenuItem(new MenuItem(4,"Выход из программы", () -> {
            System.out.println("----");
        }, null));
    }

    private Menu createSerializationsMenu(int indexMenu) {
        Menu serializationsMenu = new Menu(indexMenu,"SerializationsMenu");
        serializationsMenu.addMenuItem(new MenuItem(0,"Загрузить объекты", deserializationObject, serializationsMenu));
        serializationsMenu.addMenuItem(new MenuItem(1,"Сохранить объекты", serializationsObjects, serializationsMenu));
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
        roomMenu.addMenuItem(new MenuItem(0,"Добавить комнату", addRoom, roomMenu));
        roomMenu.addMenuItem(new MenuItem(1,"Удалить комнату", deleteRoom, roomMenu));
        roomMenu.addMenuItem(new MenuItem(2,"Вывести комнаты", viewRooms, roomMenu));
        roomMenu.addMenuItem(new MenuItem(3,"Изменить стоимость комнаты",changePriceRoom, roomMenu));
        roomMenu.addMenuItem(new MenuItem(4,"Изменить статус комнаты", changeStateRoom, roomMenu));
        roomMenu.addMenuItem(new MenuItem(5,"Выход в предыдущее меню", ()->{
            System.out.println("----");
        }, rootMenu));

        return roomMenu;
    }

    public Menu createServiceMenu(int index) {
        Menu serviceMenu = new Menu(index,"ServiceMenu");
        serviceMenu.addMenuItem(new MenuItem(0,"Добавить услугу", addService, serviceMenu));
        serviceMenu.addMenuItem(new MenuItem(1,"Удалить услугу", deleteService, serviceMenu));
        serviceMenu.addMenuItem(new MenuItem(2,"Вывести услуги", viewServices, serviceMenu));
        serviceMenu.addMenuItem(new MenuItem(3,"Изменить стоимость услуги", changePriceService, serviceMenu));
        serviceMenu.addMenuItem(new MenuItem(4,"Выход в предыдущее меню", ()->{
            System.out.println("----");
        }, rootMenu));

        return serviceMenu;
    }

    public Menu createGuestMenu(int index) {
        Menu guestMenu = new Menu(index,"GuestMenu");
        guestMenu.addMenuItem(new MenuItem(0,"Добавить(Создать) гостя", addGuest, guestMenu));
        guestMenu.addMenuItem(new MenuItem(1,"Заселить гостя в свободный номер", checkInGuest, guestMenu));
        guestMenu.addMenuItem(new MenuItem(2,"Выселить гостя из номера", checkOutGuest, guestMenu));
        guestMenu.addMenuItem(new MenuItem(3,"Заказать услугу", callService, guestMenu));
        guestMenu.addMenuItem(new MenuItem(4,"Список всех постояльцев", viewGuestsHotel, guestMenu));
        guestMenu.addMenuItem(new MenuItem(5,"Выход в предыдущее меню", ()->{
            System.out.println("----");
        }, rootMenu));

        return guestMenu;
    }
    @Autowired
    @Qualifier(value = "AddGuest")
    public void setAddGuest(IAction addGuest) {
        this.addGuest = addGuest;
    }
    @Autowired
    @Qualifier(value = "CheckInGuest")
    public void setCheckInGuest(IAction checkInGuest) {
        this.checkInGuest = checkInGuest;
    }
    @Autowired
    @Qualifier(value = "ViewRooms")
    public void setViewRooms(IAction viewRooms) {
        this.viewRooms = viewRooms;
    }
}
