package eu.senla.Hotel.ui.actions;

public class ViewGuestsHotel extends AbstractAction implements IAction {
    @Override
    public void execute() {
        hotelController.viewGuests();
    }
}
