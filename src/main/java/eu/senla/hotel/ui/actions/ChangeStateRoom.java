package main.java.eu.senla.hotel.ui.actions;

import main.java.eu.senla.hotel.model.StateRoom;

public class ChangeStateRoom extends AbstractAction implements IAction {
    @Override
    public void execute() {
        StateRoom stateRoom = StateRoom.CHECKED;
        int indexRoom = 0;
        hotelController.changeStateRoom(stateRoom, indexRoom);
    }
}
