package eu.senla.mysql.service;

import eu.senla.mysql.dao.PrinterDao;
import eu.senla.mysql.dao.ProductDao;
import eu.senla.mysql.exception.NotExistObject;
import eu.senla.mysql.model.Laptop;
import eu.senla.mysql.model.PC;
import eu.senla.mysql.model.Printer;
import eu.senla.mysql.model.Product;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class PrinterService {
    public static final Logger logger = Logger.getLogger(
            PrinterService.class.getName());
    private final PrinterDao printerDao;
    private final ProductDao productDao;
    List<Printer> printerList;

    public PrinterService(DataSource ds) {
        this.printerDao = new PrinterDao(ds);
        this.productDao = new ProductDao(ds);
        this.printerList = new ArrayList<>();
        printerList.addAll(getAllPrinters());
    }

    public void addPrinter(Printer item) {
        printerList.add(item);
        printerDao.add(item);
        productDao.add(new Product(item.getMaker(), item.getModel(), "printer"));
    }

    public void deletePrinter(Printer item) {
        int index = printerList.indexOf(item);
        if (index != -1) {
            try {
                printerDao.delete(item);
                productDao.delete(new Product(item.getMaker(), item.getModel(), "printer"));
            } catch (NotExistObject notExistObject) {
                logger.info(notExistObject.getMessage());
            }
            printerList.remove(index);
        }
    }

    public void updatePrinter(Printer item) {
        try {
            printerDao.update(item);
        } catch (NotExistObject notExistObject) {
            logger.info(notExistObject.getMessage());
        }
    }

    public List<Printer> getPrinters() {
        return printerList;
    }

    private List<Printer> getAllPrinters() {
        List<Printer> allPrinter = printerDao.listItem();
        allPrinter.forEach(printer -> printer.setMaker(productDao.get(printer.getModel()).getMaker()));
        return allPrinter;
    }

    public List<Printer> getPrintersFilterForPrice(int price) {
        return printerList.stream()
                .filter(printer -> printer.getPrice() > price)
                .collect(Collectors.toList());
    }
}
