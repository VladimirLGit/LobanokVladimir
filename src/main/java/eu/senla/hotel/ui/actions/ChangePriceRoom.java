package eu.senla.hotel.ui.actions;

public class ChangePriceRoom extends AbstractAction implements IAction {
    @Override
    public void execute() {
        hotelController.changePriceRoom();
    }
}
