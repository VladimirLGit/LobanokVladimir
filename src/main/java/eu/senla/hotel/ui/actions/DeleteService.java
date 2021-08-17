package eu.senla.hotel.ui.actions;

import eu.senla.hotel.dependency2.annotation.Component;

@Component
public class DeleteService extends AbstractAction implements IAction{
    @Override
    public void execute() {
        int indexService = 0;
        hotelController.deleteService(indexService);
    }
}
