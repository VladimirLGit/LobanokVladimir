package main.java.eu.senla.hotel.ui.actions;

public class DeleteService extends AbstractAction implements IAction{
    @Override
    public void execute() {
        int indexService = 0;
        hotelController.deleteService(indexService);
    }
}
