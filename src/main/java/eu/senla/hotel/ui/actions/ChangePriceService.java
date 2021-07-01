package main.java.eu.senla.hotel.ui.actions;

public class ChangePriceService extends AbstractAction implements IAction {
    @Override
    public void execute() {
        int newPrice = 0;
        int indexService = 0;
        hotelController.changePriceService(newPrice, indexService);
    }
}
