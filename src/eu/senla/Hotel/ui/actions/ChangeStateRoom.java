package eu.senla.Hotel.ui.actions;

import eu.senla.Hotel.model.StateRoom;

public class ChangeStateRoom extends AbstractAction implements IAction {
    @Override
    public void execute() {
        StateRoom stateRoom = StateRoom.CHECKED;
        int indexRoom = 0;
        hotelController.changeStateRoom(stateRoom, indexRoom);
    }
}
