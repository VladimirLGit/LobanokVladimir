package eu.senla.Hotel.ui.actions;

public class DeleteGuest extends AbstractAction implements IAction{
    @Override
    public void execute() {
        int indexGuest = 0;
        hotelController.deleteGuest(indexGuest);
    }
}
