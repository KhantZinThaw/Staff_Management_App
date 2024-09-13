package Controller;

import Entity.WorkSlot;

import java.util.ArrayList;
import java.sql.Date;

public class SearchWorkSlotController {
    private WorkSlot workSlot;

    public SearchWorkSlotController() {this.workSlot = new WorkSlot();}

    public WorkSlot searchDate(Date selectedDate) {
        return workSlot.getWorkSlotByDate(selectedDate);
    }
}
