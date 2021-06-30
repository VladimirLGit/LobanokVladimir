package eu.senla.hotel.ui.actions;

public class AddRoom extends AbstractAction implements IAction{
    @Override
    public void execute() {
        hotelController.addRoom();
    }
}
