package eu.senla.Hotel.ui.actions;

public class SerializationsObjects extends AbstractAction implements IAction {
    @Override
    public void execute() {
        //hotelController.serializationsObjects();
        hotelController.serializationMarshal();
    }
}
