package eu.senla.Hotel.ui.actions;

public class CheckInGuest extends AbstractAction implements IAction {
    @Override
    public void execute() {
        hotelController.checkInGuest();
    }
}
