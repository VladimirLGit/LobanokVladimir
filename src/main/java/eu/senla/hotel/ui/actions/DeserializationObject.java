package eu.senla.hotel.ui.actions;
import org.springframework.stereotype.Component;

@Component
public class DeserializationObject extends AbstractAction implements IAction {
    @Override
    public void execute() {
        hotelController.deserializationObjects();
    }
}
