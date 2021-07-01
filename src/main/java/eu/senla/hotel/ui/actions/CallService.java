package main.java.eu.senla.hotel.ui.actions;

public class CallService extends AbstractAction implements IAction {
    @Override
    public void execute() {
        hotelController.callService();
    }
}