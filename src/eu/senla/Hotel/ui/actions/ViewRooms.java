package eu.senla.Hotel.ui.actions;

public class ViewRooms extends AbstractAction implements IAction {
    @Override
    public void execute() {
        hotelController.viewRooms();
    }
}
