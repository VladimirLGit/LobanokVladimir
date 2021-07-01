package main.java.eu.senla.hotel.ui.actions;

public class CheckOutGuest extends AbstractAction implements IAction {
    @Override
    public void execute() {
        hotelController.checkOutGuest();
    }
}
