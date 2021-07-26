package eu.senla.mysql.service;

import eu.senla.mysql.dao.LaptopDao;
import eu.senla.mysql.dao.ProductDao;
import eu.senla.mysql.exception.NotExistObject;
import eu.senla.mysql.model.Laptop;
import eu.senla.mysql.model.Product;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class LaptopService {
    public static final Logger logger = Logger.getLogger(
            LaptopService.class.getName());
    private final LaptopDao laptopDao;
    private final ProductDao productDao;
    List<Laptop> laptopList;

    public LaptopService(DataSource ds) {
        this.laptopDao = new LaptopDao(ds);
        this.productDao = new ProductDao(ds);
        this.laptopList = new ArrayList<>();
        laptopList.addAll(getAllLaptops());
    }

    public void addLaptop(Laptop item) {
        laptopList.add(item);
        laptopDao.add(item);
        productDao.add(new Product(item.getMaker(), item.getModel(), "laptop"));
    }

    public void deleteLaptop(Laptop item) {
        int index = laptopList.indexOf(item);
        if (index != -1) {
            try {
                laptopDao.delete(item);
                productDao.delete(new Product(item.getMaker(), item.getModel(), "laptop"));
            } catch (NotExistObject notExistObject) {
                logger.info(notExistObject.getMessage());
            }
            laptopList.remove(index);
        }
    }

    public void updateLaptop(Laptop item) {
        try {
            laptopDao.update(item);
        } catch (NotExistObject notExistObject) {
            logger.info(notExistObject.getMessage());
        }
    }

    public List<Laptop> getLaptops() {
        return laptopList;
    }

    private List<Laptop> getAllLaptops() {
        List<Laptop> allLaptop = laptopDao.listItem();
        allLaptop.forEach(laptop -> laptop.setMaker(productDao.get(laptop.getModel()).getMaker()));
        return allLaptop;
    }

    public List<Laptop> getLaptopsFilterForPrice(int price) {
        return laptopList.stream()
                .filter(laptop -> laptop.getPrice() > price)
                .collect(Collectors.toList());
    }
}
