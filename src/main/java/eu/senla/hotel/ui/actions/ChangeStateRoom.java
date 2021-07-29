package eu.senla.hotel.ui.actions;

import eu.senla.hotel.model.StateRoom;

public class ChangeStateRoom extends AbstractAction implements IAction {
    @Override
    public void execute() {
        hotelController.changeStateRoom();
    }
}
