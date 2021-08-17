package eu.senla.hotel.ui.actions;

import eu.senla.hotel.dependency2.annotation.Component;

@Component
public class DeleteRoom extends AbstractAction implements IAction{
    @Override
    public void execute() {
        int indexRoom = 0;
        hotelController.deleteRoom(indexRoom);
    }
}
