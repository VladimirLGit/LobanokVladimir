package eu.senla.hotel.ui.actions;

import eu.senla.hotel.ui.HotelController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


public abstract class AbstractAction {
    @Autowired
    @Qualifier("hotelController")
    protected HotelController hotelController;// = HotelController.getInstance();

}
