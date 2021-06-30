package eu.senla.hotel.ui.actions;

public class ViewServices extends AbstractAction implements IAction {
    @Override
    public void execute() {
        hotelController.viewService();
    }
}
