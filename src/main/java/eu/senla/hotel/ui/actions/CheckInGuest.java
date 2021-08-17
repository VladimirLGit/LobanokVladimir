package eu.senla.hotel.ui.actions;

import eu.senla.hotel.dependency2.annotation.Component;

@Component
public class CheckInGuest extends AbstractAction implements IAction {
    @Override
    public void execute() {
        hotelController.checkInGuest();
    }
}
