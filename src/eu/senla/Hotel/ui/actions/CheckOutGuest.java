package eu.senla.Hotel.ui.actions;

public class CheckOutGuest extends AbstractAction implements IAction {
    @Override
    public void execute() {
        hotelController.checkOutGuest();
    }
}
