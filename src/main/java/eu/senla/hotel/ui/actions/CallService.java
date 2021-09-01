package eu.senla.hotel.ui.actions;
import org.springframework.stereotype.Component;

@Component
public class CallService extends AbstractAction implements IAction {
    @Override
    public void execute() {
        hotelController.callService();
    }
}