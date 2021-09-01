package eu.senla.hotel.ui.actions;
import org.springframework.stereotype.Component;

@Component
public class DeleteService extends AbstractAction implements IAction{
    @Override
    public void execute() {
        int indexService = 0;
        hotelController.deleteService(indexService);
    }
}
