package eu.senla.mysql.service;

import eu.senla.mysql.dao.PcDao;
import eu.senla.mysql.dao.ProductDao;
import eu.senla.mysql.exception.NotExistObject;
import eu.senla.mysql.model.PC;
import eu.senla.mysql.model.Product;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PcService {
    public static final Logger logger = Logger.getLogger(
            PcService.class.getName());
    private final PcDao pcDao;
    private final ProductDao productDao;
    List<PC> pcList;

    public PcService(DataSource ds) {
        this.pcDao = new PcDao(ds);
        this.productDao = new ProductDao(ds);
        this.pcList = new ArrayList<>();
    }

    public void addPc(PC item) {
        pcList.add(item);
        pcDao.add(item);
        productDao.add(new Product(item.getMaker(), item.getModel(), "PC"));
    }

    public void deletePc(PC item) {
        int index = pcList.indexOf(item);
        if (index != -1) {
            try {
                pcDao.delete(item);
                productDao.delete(new Product(item.getMaker(), item.getModel(), "PC"));
            } catch (NotExistObject notExistObject) {
                logger.info(notExistObject.getMessage());
            }
            pcList.remove(index);
        }
    }

    public void updatePc(PC item) {
        try {
            pcDao.update(item);
        } catch (NotExistObject notExistObject) {
            logger.info(notExistObject.getMessage());
        }
    }

    public List<PC> getPCs() {
        return pcList;
    }
}
