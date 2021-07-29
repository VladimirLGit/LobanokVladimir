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
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
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
    private IGuestService guestService;
    private IRoomService roomService;
    private IServiceService hotelService;


    private HotelController() {
        DataSource ds = null;
        try {
            //System.out.printf("Resource Path: %s\n", HotelController.class
            //        .getResource("").getPath());
            //System.out.printf("Resource Path: %s\n", this.getClass().getClassLoader().getResource("").getPath());
            //this.getClass().getClassLoader().getResource("").getPath().replace("/target/classes", "/src/main/resources/logging.properties");
            //LogManager.getLogManager().readConfiguration(HotelController.class.getResourceAsStream("src/main/resources/logging.properties"));
            LogManager.getLogManager()
                    .readConfiguration(HotelController
                            .class
                            .getResourceAsStream(this
                                    .getClass()
                                    .getClassLoader()
                                    .getResource("")
                                    .getPath().replace("/target/classes/", "/src/main/resources/logging.properties")));
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
        guestService = new GuestService(ds);
        roomService = new RoomService(ds);
        hotelService = new HotelService(ds);
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
        final String GUESTS_XML = "src/main/java/resources/guests-jaxb.xml";
        final String ROOMS_XML = "src/main/java/resources/rooms-jaxb.xml";
        final String SERVICES_XML = "src/main/java/resources/services-jaxb.xml";
        //создание объекта Marshaller, который выполняет сериализацию
        JAXBContext context;
        try {
            context = JAXBContext.newInstance(GuestService.class);
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
            marshaller.marshal(hotelService, new File(SERVICES_XML));

        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }

    public void deserializationObjects() {
        final String GUESTS_XML = "src/main/java/resources/guests-jaxb.xml";
        final String ROOMS_XML = "src/main/java/resources/rooms-jaxb.xml";
        final String SERVICES_XML = "src/main/java/resources/services-jaxb.xml";
        //создание объекта Marshaller, который выполняет десериализацию
        JAXBContext context;
        try {
            context = JAXBContext.newInstance(GuestService.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            // сама десериализация
            GuestService guestService2 = (GuestService) unmarshaller.unmarshal(new File(GUESTS_XML));

            context = JAXBContext.newInstance(RoomService.class);
            unmarshaller = context.createUnmarshaller();
            RoomService roomService2 = (RoomService) unmarshaller.unmarshal(new File(ROOMS_XML));

            context = JAXBContext.newInstance(HotelService.class);
            unmarshaller = context.createUnmarshaller();
            HotelService hotelService2 = (HotelService) unmarshaller.unmarshal(new File(SERVICES_XML));

            guestService2.listGuests();
            roomService2.listNumber();
            hotelService2.listOrder();

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

}
