package eu.senla.Hotel.ui.actions;

public class AddService extends AbstractAction implements IAction{
    @Override
    public void execute() {
        hotelController.addService();
    }
}
