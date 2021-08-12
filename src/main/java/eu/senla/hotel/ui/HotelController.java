package eu.senla.hotel.ui;


import eu.senla.hotel.api.dao.IGuestDao;
import eu.senla.hotel.api.dao.IRoomDao;
import eu.senla.hotel.api.dao.IServiceDao;
import eu.senla.hotel.api.sevice.IGuestService;
import eu.senla.hotel.api.sevice.IRoomService;
import eu.senla.hotel.api.sevice.IServiceService;

import eu.senla.hotel.dependency2.annotation.Autowired;
import eu.senla.hotel.dependency2.annotation.Component;
import eu.senla.hotel.dependency2.annotation.Qualifier;

import eu.senla.hotel.model.*;
import eu.senla.hotel.service.GuestService;
import eu.senla.hotel.service.HotelService;
import eu.senla.hotel.service.RoomService;

import javax.sql.DataSource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;

import java.time.LocalDate;
import java.util.*;
import java.util.logging.Logger;

@Component
public class HotelController {
    public final Logger logger = Logger.getLogger(
            HotelController.class.getName());

    private String[] listNameGuests = new String[]{
            "Tom",
            "Jack",
            "Rose",
            "Alice",
            "Oscar",
            "Leo",
            "John"};
    String[] listNameServices = new String[]{"Заказ еды в номер",
            "Уборка номера",
            "Массаж",
            "Вызов такси",
            "Чистка одежды",
            "Покупка сувениров",
            "Тренажерный зал"};
    private HotelController instance; //static
    @Autowired
    @Qualifier(value = "LGuestDao")
    private IGuestDao guestDao;
    @Autowired
    @Qualifier(value = "LRoomDao")
    private IRoomDao roomDao;
    @Autowired
    @Qualifier(value = "LServiceDao")
    private IServiceDao serviceDao;

    private IGuestService guestService;
    private IRoomService roomService;
    private IServiceService hotelService;



//    private HotelController() {
//        DataSource ds = null;
//        try {
//            LogManager.getLogManager().readConfiguration(HotelController.class.getClassLoader().getResourceAsStream("logging.properties"));
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//        try {
//            ds = DataSourceFactory.getDataSource();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        setUp(ds);
//    }


    public HotelController getInstance() { //static
        if (instance == null) {        //если объект еще не создан
            instance = new HotelController();    //создать новый объект
        }
        return instance;        // вернуть ранее созданный объект
    }


    public void setUp(DataSource ds) {
        //guestDao = new LGuestDao();
        //roomDao = new LRoomDao();
        //serviceDao = new LServiceDao();
        guestService = new GuestService(guestDao);
        roomService = new RoomService(roomDao);
        hotelService = new HotelService(serviceDao);
    }

    public void addRoom() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Введите номер");
            int number = Integer.parseInt(reader.readLine());
            System.out.println("Введите вместимость");
            int numberOfGuests = Integer.parseInt(reader.readLine());
            System.out.println("Введите стоимость");
            int price = Integer.parseInt(reader.readLine());
            System.out.println(Arrays.toString(TypeRoom.values()));
            System.out.println("Выберите тип номера. Номер типа");
            int indexType = Integer.parseInt(reader.readLine());
            Room room = new Room(number,
                    price,
                    numberOfGuests,
                    TypeRoom.values()[indexType]);
            roomService.addRoom(room);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteRoom(int indexRoom) {
        roomService.deleteRoom(roomService.viewRoom(indexRoom));
    }


    public void addService() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        for (int i = 0; i < listNameServices.length; i++) {
            System.out.println(i + " " + listNameServices[i]);
        }

        try {
            System.out.println("Выберите услугу, которую хотите добавить");
            int indexService = Integer.parseInt(reader.readLine());
            System.out.println("Введите стоимость");
            int price = Integer.parseInt(reader.readLine());
            Service service = new Service(listNameServices[indexService], price);
            hotelService.addService(service);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteService(int indexService) {
        hotelService.deleteService(hotelService.viewService(indexService));
    }

    public void addGuest() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        for (int i = 0; i < listNameGuests.length; i++) {
            System.out.println(i + " " + listNameGuests[i]);
        }
        try {
            System.out.println("Выберите имя гостя");
            int indexName = Integer.parseInt(reader.readLine());
            Guest guest = new Guest(listNameGuests[indexName]);
            guest.setDateOfCheckIn(LocalDate.of(2016, 3, 30));
            guestService.addGuest(guest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteGuest(int indexGuest) {
        guestService.deleteGuest(guestService.viewGuest(indexGuest));
    }


    public void viewRooms() {
        roomService.listNumber();
    }

    public void changePriceRoom() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            List<Room> rooms = roomService.getRooms();
            System.out.println(rooms);
            if (rooms.size() > 0) {
                System.out.println("Введите номер комнаты");
                int indexRoom = Integer.parseInt(reader.readLine());
                System.out.println("Введите новую стоимость номера");
                int newPrice = Integer.parseInt(reader.readLine());
                roomService.changePriceRoom(newPrice, rooms.get(indexRoom));
            } else
                logger.info("rooms.size=0");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeStateRoom() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            List<Room> rooms = roomService.getRooms();
            System.out.println(rooms);
            if (rooms.size() > 0) {
                System.out.println("Введите номер комнаты");
                int indexRoom = Integer.parseInt(reader.readLine());
                System.out.println(Arrays.toString(StateRoom.values()));
                System.out.println("Выбирите новый статус номера. Номер статуса");
                int indexState = Integer.parseInt(reader.readLine());
                roomService.changeStateRoom(StateRoom.values()[indexState], rooms.get(indexRoom));
            } else
                logger.info("rooms.size=0");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void viewService() {
        hotelService.listOrder();
    }

    public void changePriceService() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            List<Service> services = hotelService.getServices();
            System.out.println(services);
            if (services.size() > 0) {
                System.out.println("Введите номер услуги");
                int indexService = Integer.parseInt(reader.readLine());
                System.out.println("Введите новую стоимость услуги");
                int newPrice = Integer.parseInt(reader.readLine());
                hotelService.changePriceOrder(indexService, newPrice);
            } else
                logger.info("services.size=0");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void checkInGuest() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            List<Guest> guests;
            guests = guestService.getGuests();
            for (Guest guest : guests) {
                if (guest.getState() == StateGuest.NO_STATE) {
                    System.out.println(guest);
                }
            }
            System.out.println("Выбирите имя гостя, которого надо заселить");
            String nameGuest = reader.readLine();
            for (Guest guest : guests) {
                if ((guest.getState() == StateGuest.NO_STATE) &&
                        (guest.getName().equals(nameGuest))) {

                    if (roomService.checkIn(guest)) {
                        LocalDate today = LocalDate.now();
                        guest.setState(StateGuest.CHECK_IN);
                        guest.setDateOfCheckIn(today);
                        Random RANDOM = new Random();
                        guest.setDateOfCheckOut(today.plusDays(RANDOM.nextInt(5) + 1));
                        guestService.updateGuest(guest);
                    }
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void checkOutGuest() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        List<Guest> guests = guestService.getGuests();
        for (Guest guest : guests) {
            if (guest.getState() == StateGuest.CHECK_IN) {
                System.out.println(guest);
            }
        }

        try {
            System.out.println("Выбирите имя гостя, которого надо выселить");
            String nameGuest = reader.readLine();
            for (Guest guest : guests) {
                if ((guest.getState() == StateGuest.CHECK_IN) &&
                        (guest.getName().equals(nameGuest))) {
                    guestService.leave(guest);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void viewGuests() {
        guestService.listGuests();
    }

    public void callService() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            List<Service> services = hotelService.getServices();
            System.out.println(services);
            if (services.size() > 0) {
                System.out.println("Введите номер услуги");
                int indexService = Integer.parseInt(reader.readLine());
                List<Guest> guests = guestService.getGuests();
                for (Guest guest : guests) {
                    if (guest.getState() == StateGuest.CHECK_IN) {
                        System.out.println(guest);
                    }
                }
                System.out.println("Выбирите имя гостя, который хочет заказать услугу");
                String nameGuest = reader.readLine();
                for (Guest guest : guests) {
                    if ((guest.getState() == StateGuest.CHECK_IN) &&
                            (guest.getName().equals(nameGuest))) {
                        Service service = services.get(indexService);
                        guest.addOrderedService(service);
                        break;
                    }
                }
            }
            else
                logger.info("services.size=0");
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public void serializationMarshal() {
        final String GUESTS_XML = "src/main/resources/guests-jaxb.xml";
        final String ROOMS_XML = "src/main/resources/rooms-jaxb.xml";
        final String SERVICES_XML = "src/main/resources/services-jaxb.xml";
        try {
            Guests guests = new Guests();
            guests.setGuestsList(guestService.getGuests());
            marshalIt(guests, GUESTS_XML);
            Rooms rooms = new Rooms();
            rooms.setRooms(roomService.getRooms());
            marshalIt(rooms, ROOMS_XML);
            Services services = new Services();
            services.setServices(hotelService.getServices());
            marshalIt(services, SERVICES_XML);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }

    public void deserializationObjects() {
        final String GUESTS_XML = "src/main/resources/guests-jaxb.xml";
        final String ROOMS_XML = "src/main/resources/rooms-jaxb.xml";
        final String SERVICES_XML = "src/main/resources/services-jaxb.xml";
        try {
            Guests guests = (Guests)unmarshalIt(Guests.class, GUESTS_XML);
            guestService.setGuests(guests.getGuestsList());
            Rooms rooms = (Rooms)unmarshalIt(Rooms.class, ROOMS_XML);
            roomService.setRooms(rooms.getRooms());
            Services services = (Services)unmarshalIt(Services.class, SERVICES_XML);
            hotelService.setServices(services.getServices());

            guestService.listGuests();
            roomService.listNumber();
            hotelService.listOrder();

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public void marshalIt(Object objectName, String path) throws JAXBException {

        JAXBContext jaxbContext = JAXBContext.newInstance(objectName.getClass());
        Marshaller marshaller = jaxbContext.createMarshaller();
        // For Pretty printing output
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(objectName, new File(path));

    }

    public static Object unmarshalIt(Class<?> className, String path) throws JAXBException {

        JAXBContext jaxbContext = JAXBContext.newInstance(className);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return unmarshaller.unmarshal(new File(path));

    }

}
