package eu.senla.hotel.ui.actions;

public class AddService extends AbstractAction implements IAction{
    @Override
    public void execute() {
        hotelController.addService();
    }
}
