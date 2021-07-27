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
import java.util.*;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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
        //generationData();
        printerService.getPrintersFilterForPrice(1000).forEach(System.out::println);
        pcService.getPCsFilterForPrice(100).forEach(System.out::println);
        laptopService.getLaptopsFilterForPrice(1600).forEach(System.out::println);
        IntSummaryStatistics intSummaryStatistics = pcService.getPCs()
                .stream()
                .mapToInt(PC::getPrice)
                .summaryStatistics();
        System.out.println(intSummaryStatistics.getMax());
        System.out.println(intSummaryStatistics.getAverage());
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

    public void task1() {
        pcService.getPCsFilterForPrice(600).forEach(System.out::println);
    }
    public void task2() {
        printerService.getPrinters().forEach(printer -> System.out.println(printer.getMaker()));
    }
    public void task3() {
        pcService.getPCsFilterForPrice(1000).forEach(System.out::println);
    }
    public void task4() {
        printerService.getPrinters()
                .stream()
                .filter(printer -> printer.getColor() > 0)
                .collect(Collectors.toList())
                .forEach(System.out::println);
    }
    public void task5() {
        pcService.getPCs()
                .stream()
                .filter(pc -> (pc.getPrice() < 600) && ((pc.getCd() == 12) || (pc.getCd() == 24)))
                .collect(Collectors.toList())
                .forEach(System.out::println);
    }
    public void task6() {
        pcService.getPCs()
                .stream()
                .filter(pc -> pc.getHd()>=10)
                .collect(Collectors.toList())
                .forEach(System.out::println);
    }
    public void task7() {
        Random RANDOM = new Random();
        String findMaker = listMakers[RANDOM.nextInt(listMakers.length - 1)];
        pcService.getPCs()
                .stream()
                .filter(pc -> pc.getMaker().equals(findMaker))
                .collect(Collectors.toList())
                .forEach(System.out::println);

        laptopService.getLaptops()
                .stream()
                .filter(laptop -> laptop.getMaker().equals(findMaker))
                .collect(Collectors.toList())
                .forEach(System.out::println);
        printerService.getPrinters()
                .stream()
                .filter(printer -> printer.getMaker().equals(findMaker))
                .collect(Collectors.toList())
                .forEach(System.out::println);
    }
    public void task8() {

    }
    public void task9() {
        pcService.getPCs()
                .stream()
                .filter(pc -> pc.getSpeed()>=450)
                .collect(Collectors.toList())
                .forEach(System.out::println);
    }
    public void task10() {
        Printer printer, printerMax = null;
        List<Printer> printerList = printerService.getPrinters();
        Integer maxInt = 0;
        for (int i = 0; i < printerList.size(); i++) {
            printer = printerList.get(i);
            if (printer.getPrice()>maxInt) {
                maxInt = printer.getPrice();
                printerMax = printer;
            }
            if (Objects.nonNull(printerMax))
                System.out.println(printerMax);
        }
    }
    public void task11() {
        int allSpeed = 0;
        //Найдите среднюю скорость ПК.
        List<PC> pcs = pcService.getPCs();
        for (int i = 0; i < pcs.size(); i++) {
            allSpeed += pcs.get(i).getSpeed();
        }
        System.out.println("pcs.size = " + pcs.size());
        System.out.println("allSpeed/pcs.size = " + String.valueOf(allSpeed/pcs.size()));
        IntSummaryStatistics intSummaryStatistics = pcService.getPCs()
                .stream()
                .mapToInt(PC::getSpeed).summaryStatistics();
        System.out.println(intSummaryStatistics.getAverage());
    }
    public void task12() {
        //Найдите среднюю скорость ПК-блокнотов, цена которых превышает 1000 дол.
        List<Laptop> list = laptopService.getLaptopsFilterForPrice(1000);
        int allSpeed = 0;
        for (int i = 0; i < list.size(); i++) allSpeed += list.get(i).getSpeed();
        System.out.println("list.size = " + list.size());
        System.out.println("allSpeed/list.size = " + String.valueOf(allSpeed/list.size()));
        IntSummaryStatistics intSummaryStatistics = laptopService.getLaptops()
                .stream()
                .filter(laptop -> laptop.getPrice() > 1000)
                .mapToInt(Laptop::getSpeed).summaryStatistics();
        System.out.println(intSummaryStatistics.getAverage());

    }
    public void task13() {
        Random RANDOM = new Random();
        String findMaker = listMakers[RANDOM.nextInt(listMakers.length - 1)];
        List<PC> pcList = pcService.getPCs()
                .stream()
                .filter(pc -> pc.getMaker().equals(findMaker))
                .collect(Collectors.toList());
        int allSpeed = 0;
        for (int i = 0; i < pcList.size(); i++) {
            allSpeed = pcList.get(i).getSpeed();
        }
        System.out.println(findMaker);
        System.out.println("pcList.size = " + pcList.size());
        System.out.println("allSpeed/pcList.size = " + String.valueOf(allSpeed/pcList.size()));

        IntSummaryStatistics intSummaryStatistics = pcService.getPCs()
                .stream()
                .filter(pc -> pc.getMaker().equals(findMaker))
                .mapToInt(PC::getSpeed).summaryStatistics();
        System.out.println(intSummaryStatistics.getAverage());
    }
    public void task14() {
      /*
      Для каждого значения скорости найдите среднюю стоимость ПК с такой же скоростью процессора.
      Вывести: скорость, средняя цена
      */
        //Map<Integer, Integer> collect = pcService.getPCs()
        //        .stream()
        //        .collect(Collectors.toMap(PC::getSpeed, PC::getPrice);
    }
    public void task15() {

    }
    public void task16() {
        /*
        Найдите пары моделей PC, имеющих одинаковые скорость и RAM. В результате каждая пара
        указывается только один раз, т.е. (i,j), но не (j,i), Порядок вывода: модель с большим номером,
        модель с меньшим номером, скорость и RAM
         */
        IntSummaryStatistics intSummaryStatistics = pcService.getPCs()
                .stream()
                .mapToInt(PC::getPrice)
                .summaryStatistics();
        System.out.println(intSummaryStatistics.getMax());
    }

    public void task17() {
        //Найдите модели ПК-блокнотов, скорость которых меньше скорости любого из ПК.
        IntSummaryStatistics intSummaryStatistics = pcService.getPCs()
                .stream()
                .mapToInt(PC::getSpeed).summaryStatistics();
        laptopService.getLaptops()
                .stream()
                .filter(laptop -> laptop.getSpeed() < intSummaryStatistics.getMin())
                .collect(Collectors.toList()).forEach(System.out::println);
    }
    public void task18() {
        Printer printer1 = printerService.getPrinters()
                .stream()
                .filter(printer -> printer.getColor() > 0)
                .sorted((o1, o2) -> {
                    if (o1.getPrice() > o2.getPrice()) return 0;
                    else if (o1.getPrice() < o2.getPrice()) return -1;
                    else
                        return 1;

                })
                .findFirst()
                .get();
        System.out.println(printer1);

    }
    public void task19() {
       //Для каждого производителя найдите средний размер экрана выпускаемых им ПК-блокнотов.
       //Вывести: maker, средний размер экрана.
        for (String maker: listMakers) {
            IntSummaryStatistics intSummaryStatistics = laptopService.getLaptops()
                    .stream()
                    .filter(laptop -> laptop.getMaker().equals(maker))
                    .mapToInt(Laptop::getScreen).summaryStatistics();
            System.out.println(intSummaryStatistics.getAverage());
        }

    }

    public void task20() {}
    public void task21() {
        //Найдите максимальную цену ПК, выпускаемых каждым производителем.
        for (String maker: listMakers) {
            IntSummaryStatistics intSummaryStatistics = pcService.getPCs()
                    .stream()
                    .filter(pc -> pc.getMaker().equals(maker))
                    .mapToInt(PC::getPrice).summaryStatistics();
            System.out.println(maker + " " + intSummaryStatistics.getMax());
        }
    }
    public void task22() {}
    public void task23() {
        for (String maker: listMakers) {
            List<PC> pcList = pcService.getPCs()
                    .stream()
                    .filter(pc -> pc.getMaker().equals(maker))
                    .filter(pc -> pc.getSpeed() > 750)
                    .collect(Collectors.toList());
            List<Laptop> laptopList = laptopService.getLaptops()
                    .stream()
                    .filter(laptop -> laptop.getMaker().equals(maker))
                    .filter(laptop -> laptop.getSpeed() > 750)
                    .collect(Collectors.toList());
            if (pcList.size()>0 && laptopList.size()>0) {
                System.out.println(maker);
                IntSummaryStatistics intSummaryStatisticsPC = pcList
                        .stream()
                        .mapToInt(PC::getPrice)
                        .summaryStatistics();
                System.out.println("PC sumPrice = " + intSummaryStatisticsPC.getAverage());
                IntSummaryStatistics intSummaryStatisticsLaptop = laptopList
                        .stream()
                        .mapToInt(Laptop::getPrice)
                        .summaryStatistics();
                System.out.println(intSummaryStatisticsLaptop.getAverage());
            }
        }
    }
    public void task24() {

    }



}
