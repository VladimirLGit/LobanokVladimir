package eu.senla.hotel.ui.actions;

public class CheckInGuest extends AbstractAction implements IAction {
    @Override
    public void execute() {
        hotelController.checkInGuest();
    }
}
