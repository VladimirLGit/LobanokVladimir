package main.java.eu.senla.hotel.ui.actions;

public class SerializationsObjects extends AbstractAction implements IAction {
    @Override
    public void execute() {
        //hotelController.serializationsObjects();
        hotelController.serializationMarshal();
    }
}
