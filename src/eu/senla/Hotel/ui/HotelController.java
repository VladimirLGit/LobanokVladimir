package eu.senla.Hotel.ui;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator.Feature;


import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import eu.senla.Hotel.dao.GuestDao;
import eu.senla.Hotel.dao.RoomDao;
import eu.senla.Hotel.dao.ServiceDao;
import eu.senla.Hotel.model.*;
import eu.senla.Hotel.service.GuestService;
import eu.senla.Hotel.service.HotelService;
import eu.senla.Hotel.service.RoomService;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.LogManager;
import java.util.logging.Logger;


public class HotelController {
    public static final Logger logger = Logger.getLogger(
            HotelController.class.getName());
    static {
        try {
            LogManager.getLogManager().readConfiguration(new FileInputStream("src/eu/senla/Hotel/resources/logging.properties"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
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
    private GuestDao guestDao;
    private RoomDao roomDao;
    private ServiceDao serviceDao;
    private GuestService guestService;
    private RoomService roomService;
    private HotelService hotelService;


    private HotelController() {
        setUp();
    }

    public static HotelController getInstance() {
        if(instance == null){		//если объект еще не создан
            instance = new HotelController();	//создать новый объект
        }
        return instance;		// вернуть ранее созданный объект
    }

    private void createDao(){
        guestDao = new GuestDao();
        roomDao = new RoomDao();
        serviceDao = new ServiceDao();
    }

    public void setUp() {
        createDao();
        guestDao.createHotelBase();
        guestDao.createTableGuests();
        guestDao.createLinkTableServices();
        guestDao.createTableHistoryGuests();

        roomDao.createTableRooms();
        roomDao.createLinkTableRooms();
        serviceDao.createTableServices();
        guestService = new GuestService();
        roomService = new RoomService();
        hotelService = new HotelService();
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
        guestService.addGuest(guest);
    }
    public void deleteGuest(int indexGuest) {
        guestService.deleteGuest(guestService.viewGuest(indexGuest));
    }


    public void viewRooms() {
        roomService.listNumber();
        //ArrayList<Room> rooms = roomService.getRooms();
        //rooms.forEach(System.out::println);
    }

    public void changePriceRoom(int newPrice, int indexRoom) {
        Random RANDOM = new Random();
        ArrayList<Room> rooms = roomService.getRooms();
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
        List<Guest> guests = null;
        guests = guestService.getGuests();
        for (int i = 0; i < guests.size(); i++) {
            Guest guest = guests.get(i);
            if (guest.getState()==StateGuest.NO_STATE) {
                LocalDate today = LocalDate.now();
                guest.setState(StateGuest.CHECK_IN);
                guest.setDateOfCheckIn(today);
                Random RANDOM = new Random();
                guest.setDateOfCheckOut(today.plusDays(RANDOM.nextInt(5)+1));
                roomService.checkIn(guest);
                guestService.updateGuest(guest);
                break;
            }
        }
    }

    public void checkOutGuest() {
        List<Guest> guests = guestService.getGuests();
        for (int i = 0; i < guests.size(); i++) {
            Guest guest = guests.get(i);
            if (guest.getState() == StateGuest.CHECK_IN) {
                guestService.leave(guest);
                break;
            }
        }
    }

    public void viewGuests() {
        guestService.listGuests();
    }

    public void serializationMarshal(){
        final String GUESTS_XML = "src/eu/senla/Hotel/resources/guests-jaxb.xml";
        //создание объекта Marshaller, который выполняет сериализацию
        JAXBContext context = null;
        try {
            context = JAXBContext.newInstance(GuestService.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            // сама сериализация
            // Write to File
            marshaller.marshal(guestService, new File(GUESTS_XML));

        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }

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
}
