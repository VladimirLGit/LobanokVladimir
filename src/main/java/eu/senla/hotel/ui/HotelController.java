package eu.senla.hotel.ui;


import eu.senla.hotel.api.dao.IGuestDao;
import eu.senla.hotel.api.dao.IRoomDao;
import eu.senla.hotel.api.dao.IServiceDao;
import eu.senla.hotel.api.sevice.IGuestService;
import eu.senla.hotel.api.sevice.IRoomService;
import eu.senla.hotel.api.sevice.IServiceService;
import eu.senla.hotel.dao.GuestDao;
import eu.senla.hotel.dao.MainDao;
import eu.senla.hotel.dao.RoomDao;
import eu.senla.hotel.dao.ServiceDao;
import eu.senla.hotel.dao.collection.LGuestDao;
import eu.senla.hotel.dao.collection.LRoomDao;
import eu.senla.hotel.dao.collection.LServiceDao;
import eu.senla.hotel.dao.ds.DataSourceFactory;
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
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.LogManager;
import java.util.logging.Logger;


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
    private static HotelController instance;

    private IGuestDao guestDao;
    private IRoomDao roomDao;
    private IServiceDao serviceDao;
    private IGuestService guestService;
    private IRoomService roomService;
    private IServiceService hotelService;



    private HotelController() {
        DataSource ds = null;
        try {
            LogManager.getLogManager().readConfiguration(HotelController.class.getClassLoader().getResourceAsStream("logging.properties"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            ds = DataSourceFactory.getDataSource();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        setUp(ds);
    }


    public static HotelController getInstance() {
        if (instance == null) {        //если объект еще не создан
            instance = new HotelController();    //создать новый объект
        }
        return instance;        // вернуть ранее созданный объект
    }


    public void setUp(DataSource ds) {
        guestDao = new LGuestDao();
        roomDao = new LRoomDao();
        serviceDao = new LServiceDao();
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
                    LocalDate today = LocalDate.now();
                    guest.setState(StateGuest.CHECK_IN);
                    guest.setDateOfCheckIn(today);
                    Random RANDOM = new Random();
                    guest.setDateOfCheckOut(today.plusDays(RANDOM.nextInt(5) + 1));
                    roomService.checkIn(guest);
                    guestService.updateGuest(guest);
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
        //создание объекта Marshaller, который выполняет сериализацию
        JAXBContext context;
        try {
            marshalIt(guestService.getGuestObjects(), GUESTS_XML);
            marshalIt(roomService.getRoomObjects(), ROOMS_XML);
            marshalIt(hotelService.getServiceObjects(), SERVICES_XML);
            /*context = JAXBContext.newInstance(GuestService.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            // сама сериализация
            // Write to File
            marshaller.marshal(guestService, new File(GUESTS_XML));
            context = JAXBContext.newInstance(RoomService.class);
            marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(roomService, new File(ROOMS_XML));
            context = JAXBContext.newInstance(HotelService.class);
            marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(hotelService, new File(SERVICES_XML));*/

        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }

    public void deserializationObjects() {
        final String GUESTS_XML = "src/main/resources/guests-jaxb.xml";
        final String ROOMS_XML = "src/main/resources/rooms-jaxb.xml";
        final String SERVICES_XML = "src/main/resources/services-jaxb.xml";
        //создание объекта Marshaller, который выполняет десериализацию
        JAXBContext context;
        try {
            context = JAXBContext.newInstance(GuestService.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            // сама десериализация
            GuestService guestService2 = (GuestService) unmarshaller.unmarshal(new File(GUESTS_XML));
            guestDao.setGuests(guestService2.getGuests());
            guestService2.reloadDao(guestDao);
            context = JAXBContext.newInstance(RoomService.class);
            unmarshaller = context.createUnmarshaller();
            RoomService roomService2 = (RoomService) unmarshaller.unmarshal(new File(ROOMS_XML));
            roomDao.setRooms(roomService2.getRooms());
            roomService2.reloadDao(roomDao);
            context = JAXBContext.newInstance(HotelService.class);
            unmarshaller = context.createUnmarshaller();
            HotelService hotelService2 = (HotelService) unmarshaller.unmarshal(new File(SERVICES_XML));
            serviceDao.setServices(hotelService2.getServices());
            hotelService2.reloadDao(serviceDao);
            guestService2.listGuests();
            roomService2.listNumber();
            hotelService2.listOrder();

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

    public static Object unmarshalIt(Class<?> className, String xml) throws JAXBException {

        JAXBContext jaxbContext = JAXBContext.newInstance(className);

        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        StringReader reader = new StringReader(xml);

        return unmarshaller.unmarshal(reader);

    }

}
