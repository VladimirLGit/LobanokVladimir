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
import java.io.File;
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
    private MainDao mainDao;
    private IGuestDao guestDao;
    private IRoomDao roomDao;
    private IServiceDao serviceDao;
    private IGuestService guestService;
    private IRoomService roomService;
    private IServiceService hotelService;


    private HotelController() {
        DataSource ds = null;
        try {
            LogManager.getLogManager().readConfiguration(HotelController.class.getResourceAsStream("/logging.properties"));
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
        if(instance == null){		//если объект еще не создан
            instance = new HotelController();	//создать новый объект
        }
        return instance;		// вернуть ранее созданный объект
    }

    private void createDao(DataSource ds){
        mainDao = new MainDao(ds);
        guestDao = new GuestDao(ds);
        roomDao = new RoomDao(ds);
        serviceDao = new ServiceDao(ds);
    }

    public void setUp(DataSource ds) {
        createDao(ds);
        mainDao.createHotelBase();
        mainDao.createTableGuests();
        mainDao.createLinkTableServices();
        mainDao.createTableHistoryGuests();

        mainDao.createTableRooms();
        mainDao.createLinkTableRooms();
        mainDao.createTableServices();
        guestService = new GuestService(ds);
        roomService = new RoomService(ds);
        hotelService = new HotelService(ds);
    }

    public void addRoom() {
        Random RANDOM = new Random();
        Room room = new Room(RANDOM.nextInt(100),
                RANDOM.nextInt(15)*10,
                RANDOM.nextInt(5)+1,
                TypeRoom.values()[RANDOM.nextInt(TypeRoom.values().length)]);
        roomService.addRoom(room);
    }
    public void deleteRoom(int indexRoom) {
        roomService.deleteRoom(roomService.viewRoom(indexRoom));
    }


    public void addService() {
        Random RANDOM = new Random();
        Service service = new Service(listNameServices[RANDOM.nextInt(listNameServices.length - 1)], RANDOM.nextInt(99) + 1);
        hotelService.addService(service);
    }
    public void deleteService(int indexService) {
        hotelService.deleteService(hotelService.viewService(indexService));
    }

    public void addGuest() {
        Random RANDOM = new Random();
        String nameGuest = listNameGuests[RANDOM.nextInt(listNameGuests.length-1)];
        Guest guest = new Guest(nameGuest);
        guest.setDateOfCheckIn(LocalDate.of(2016, 3, 30));
        guestService.addGuest(guest);
    }
    public void deleteGuest(int indexGuest) {
        guestService.deleteGuest(guestService.viewGuest(indexGuest));
    }


    public void viewRooms() {
        roomService.listNumber();
    }

    public void changePriceRoom(int newPrice, int indexRoom) {
        Random RANDOM = new Random();
        List<Room> rooms = roomService.getRooms();
        if (rooms.size()>0) {
            roomService.changePriceRoom(newPrice, rooms.get(RANDOM.nextInt(rooms.size() - 1)));
        }
        else
            logger.info("rooms.size=0");
    }

    public void changeStateRoom(StateRoom stateRoom, int indexRoom) {
        Random RANDOM = new Random();
        List<Room> rooms = roomService.getRooms();
        if (rooms.size()>0) {
            roomService.changeStateRoom(stateRoom, rooms.get(RANDOM.nextInt(rooms.size() - 1)));
        }
        else
            logger.info("rooms.size=0");
    }

    public void viewService() {
        hotelService.listOrder();
    }

    public void changePriceService(int newPrice, int indexService) {
        Random RANDOM = new Random();
        List<Service> services = hotelService.getServices();
        if (services.size()>0) {
            hotelService.changePriceOrder(RANDOM.nextInt(services.size() - 1), newPrice);
        }
        else
            logger.info("services.size=0");
    }

    public void checkInGuest() {
        List<Guest> guests;
        guests = guestService.getGuests();
        for (Guest guest : guests) {
            if (guest.getState() == StateGuest.NO_STATE) {
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
    }

    public void checkOutGuest() {
        List<Guest> guests = guestService.getGuests();
        for (Guest guest : guests) {
            if (guest.getState() == StateGuest.CHECK_IN) {
                guestService.leave(guest);
                break;
            }
        }
    }

    public void viewGuests() {
        guestService.listGuests();
    }

    public void callService() {
        Random RANDOM = new Random();
        List<Guest> guests = guestService.getGuests();
        List<Service> services = hotelService.getServices();
        Service service = services.get(RANDOM.nextInt(services.size() - 1));
        Guest guest = guests.get(RANDOM.nextInt(guests.size() - 1));
        guest.addOrderedService(service);
        serviceDao.addOrderGuest(guest,service);
    }

    public void serializationMarshal(){
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
    /*
    public void serializationsData(){
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory()
                .configure(YAMLGenerator.Feature.WRITE_DOC_START_MARKER, false)
        )
                .enable(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED);
        mapper.setVisibility(mapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE)
                .withIsGetterVisibility(JsonAutoDetect.Visibility.NONE));
        List<Guest> guests = guestService.getGuests();
        File guestsOutput = new File("src/eu/senla/Hotel/resources/guests.yaml");
        try {
            mapper.writeValue(guestsOutput, guests);
        }
        catch (JsonProcessingException e) {
            System.out.println(
                    String.format("Problem Serializing POJO Because Of Error %s", e)
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void serializationsObjects() {
        List<Guest> guests = guestService.getGuests();
        List<Room> rooms = roomService.getRooms();
        List<Service> services = hotelService.getServices();
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory().disable(Feature.WRITE_DOC_START_MARKER));
        //mapper.findAndRegisterModules();
        //mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        File guestsOutput = new File("src/eu/senla/Hotel/resources/guests.yaml");

        try {
            mapper.writeValue(guestsOutput, guestService);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deserializationObjects() {
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory()
                .configure(YAMLGenerator.Feature.WRITE_DOC_START_MARKER, false)
        )
                .enable(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED);
        mapper.setVisibility(mapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE)
                .withIsGetterVisibility(JsonAutoDetect.Visibility.NONE));
        File guestsOutput = new File("src/eu/senla/Hotel/resources/guests.yaml");
        try {
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            GuestService guestService1 = mapper.readValue(guestsOutput, GuestService.class);
            //List<Guest> guests = mapper.readValue(guestsOutput, new TypeReference<List<Guest>>(){});
            //guests.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */

}
