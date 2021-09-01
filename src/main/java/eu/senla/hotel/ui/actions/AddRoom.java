package eu.senla.hotel.ui.actions;
import org.springframework.stereotype.Component;

@Component
public class AddRoom extends AbstractAction implements IAction{
    @Override
    public void execute() {
        hotelController.addRoom();
    }
}
