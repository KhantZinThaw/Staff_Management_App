package Controller;

import Entity.WorkSlot;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DeleteWorkSlotController {
    private WorkSlot workSlot;

    public DeleteWorkSlotController() {this.workSlot = new WorkSlot();}

    public boolean deleteWorkSlot(Date date) {
        return workSlot.deleteWorkSlot(date);
    }
}
