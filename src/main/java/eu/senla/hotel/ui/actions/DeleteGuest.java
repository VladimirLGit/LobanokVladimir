package eu.senla.hotel.ui.actions;
import org.springframework.stereotype.Component;

@Component
public class DeleteGuest extends AbstractAction implements IAction{
    @Override
    public void execute() {
        int indexGuest = 0;
        hotelController.deleteGuest(indexGuest);
    }
}
