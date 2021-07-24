package eu.senla.mysql;


import eu.senla.mysql.dao.ds.DataSourceFactory;
import eu.senla.mysql.dao.MainDao;
import eu.senla.mysql.model.Laptop;
import eu.senla.mysql.model.PC;
import eu.senla.mysql.model.Printer;
import eu.senla.mysql.service.LaptopService;
import eu.senla.mysql.service.PcService;
import eu.senla.mysql.service.PrinterService;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Random;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class MarketController {
    public final Logger logger = Logger.getLogger(
            MarketController.class.getName());

    private String[] listMakers = new String[]{
            "LG",
            "HP",
            "DELL",
            "APPLE",
            "LENOVO",
            "HITACHI",
            "CANNON"};
    private String[] listType = new String[]{
            "Laser",
            "Jet",
            "Matrix"
    };

    private static MarketController instance;
    private MainDao mainDao;
    private LaptopService laptopService;
    private PcService pcService;
    private PrinterService printerService;


    private MarketController() {
        DataSource ds = null;
        try {
            LogManager.getLogManager().readConfiguration(MarketController.class.getResourceAsStream("/logging.properties"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            ds = DataSourceFactory.getDataSource();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        setUp(ds);
        generationData();
    }

    public static MarketController getInstance() {
        if(instance == null){		//если объект еще не создан
            instance = new MarketController();	//создать новый объект
        }
        return instance;		// вернуть ранее созданный объект
    }

    public void setUp(DataSource ds) {
        mainDao = new MainDao(ds);
        mainDao.createMarketBase();
        mainDao.createTableProducts();
        mainDao.createTablePCs();
        mainDao.createTableLaptops();
        mainDao.createTablePrinters();

        laptopService = new LaptopService(ds);
        pcService = new PcService(ds);
        printerService = new PrinterService(ds);
    }

    public void generationData() {
        Random RANDOM = new Random();
        for (int i = 0; i < 10; i++) {
            PC pc = new PC();
            pc.setMaker(listMakers[RANDOM.nextInt(listMakers.length - 1)]);
            pc.setSpeed((1 + RANDOM.nextInt(6)) * 1000);
            pc.setRam(RANDOM.nextDouble() * 10);
            pc.setHd(RANDOM.nextDouble() * 100);
            pc.setCd(1 + RANDOM.nextInt(4));
            pc.setPrice(100 + RANDOM.nextInt(2000));
            pcService.addPc(pc);
            Laptop laptop = new Laptop();
            laptop.setMaker(listMakers[RANDOM.nextInt(listMakers.length - 1)]);
            laptop.setSpeed((1 + RANDOM.nextInt(6)) * 1000);
            laptop.setRam(RANDOM.nextDouble() * 10);
            laptop.setHd(RANDOM.nextDouble() * 100);
            laptop.setPrice(100 + RANDOM.nextInt(2000));
            laptop.setScreen((byte) RANDOM.nextInt(16));
            laptopService.addLaptop(laptop);
            Printer printer = new Printer();
            printer.setMaker(listMakers[RANDOM.nextInt(listMakers.length - 1)]);
            printer.setType(listType[RANDOM.nextInt(listType.length - 1)]);
            printer.setColor((byte) RANDOM.nextInt(1));
            printer.setPrice(100 + RANDOM.nextInt(2000));
            printerService.addPrinter(printer);
        }

    }
}
