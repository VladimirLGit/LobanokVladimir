package eu.senla.Hotel.ui.actions;

public class ChangePriceRoom extends AbstractAction implements IAction {
    @Override
    public void execute() {
        int newPrice = 0;
        int indexRoom = 0;
        hotelController.changePriceRoom(newPrice, indexRoom);
    }
}
