package eu.senla.hotel.ui.actions;

public class DeleteRoom extends AbstractAction implements IAction{
    @Override
    public void execute() {
        int indexRoom = 0;
        hotelController.deleteRoom(indexRoom);
    }
}
