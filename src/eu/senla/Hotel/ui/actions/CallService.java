package eu.senla.Hotel.ui.actions;

public class CallService extends AbstractAction implements IAction {
    @Override
    public void execute() {
        hotelController.callService();
    }
}