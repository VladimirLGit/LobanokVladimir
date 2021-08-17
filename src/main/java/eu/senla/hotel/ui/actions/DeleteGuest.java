package eu.senla.hotel.ui.actions;

import eu.senla.hotel.dependency2.annotation.Component;

@Component
public class DeleteGuest extends AbstractAction implements IAction{
    @Override
    public void execute() {
        int indexGuest = 0;
        hotelController.deleteGuest(indexGuest);
    }
}
