package eu.senla.Hotel.ui.actions;

public class AddRoom extends AbstractAction implements IAction{
    @Override
    public void execute() {
        hotelController.addRoom();
    }
}
