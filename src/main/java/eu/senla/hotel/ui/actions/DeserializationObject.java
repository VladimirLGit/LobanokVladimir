package eu.senla.hotel.ui.actions;

public class DeserializationObject extends AbstractAction implements IAction {
    @Override
    public void execute() {
        hotelController.deserializationObjects();
    }
}
