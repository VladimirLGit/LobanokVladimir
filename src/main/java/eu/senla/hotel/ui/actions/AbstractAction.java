package eu.senla.hotel.ui.actions;

import eu.senla.hotel.dependency2.annotation.Autowired;
import eu.senla.hotel.ui.HotelController;



public abstract class AbstractAction {
    @Autowired
    protected HotelController hotelController;// = HotelController.getInstance();

}
