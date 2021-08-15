package eu.senla.hotel.ui.actions;

import eu.senla.hotel.Main;
import eu.senla.hotel.dao.ds.DataSourceFactory;
import eu.senla.hotel.dependency2.injector.Injector;
import eu.senla.hotel.ui.HotelController;

import javax.sql.DataSource;


public abstract class AbstractAction {
    private final Injector injector = new Injector();
    protected HotelController hotelController;// = HotelController.getInstance();

    public AbstractAction() {
        if (hotelController == null) {
            try {
                this.injector.initFramework(Main.class);
                hotelController = this.injector.getService(HotelController.class);
                hotelController.setUp(DataSourceFactory.getDataSource());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        else
            hotelController = HotelController.getInstance();

    }
}
