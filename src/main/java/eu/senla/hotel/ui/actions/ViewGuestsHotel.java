package eu.senla.hotel.ui.actions;

public class ViewGuestsHotel extends AbstractAction implements IAction {
    @Override
    public void execute() {
        hotelController.viewGuests();
    }
}
