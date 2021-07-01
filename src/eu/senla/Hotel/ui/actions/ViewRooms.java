package eu.senla.hotel.ui.actions;

public class ViewRooms extends AbstractAction implements IAction {
    @Override
    public void execute() {
        hotelController.viewRooms();
    }
}
