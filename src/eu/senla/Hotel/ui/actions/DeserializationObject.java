package eu.senla.Hotel.ui.actions;

public class DeserializationObject extends AbstractAction implements IAction {
    @Override
    public void execute() {
        hotelController.deserializationObjects();
    }
}
