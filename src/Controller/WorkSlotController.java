package Controller;

import Entity.WorkSlot;

import java.sql.Date;
import java.util.ArrayList;

public class WorkSlotController {
    private WorkSlot workSlot;
    public WorkSlotController() {
        this.workSlot = new WorkSlot();
    }

    public ArrayList<WorkSlot> getWorkSlotsByDate(Date selectedDate) {
        ArrayList<WorkSlot> workSlots = new ArrayList<>();
        ArrayList<WorkSlot> allWorkSlots = getAllWorkSlots();

        for (WorkSlot workSlot : allWorkSlots) {
            if (workSlot.getDate().equals(selectedDate)) {
                workSlots.add(workSlot);
            }
        }

        return workSlots;
    }

    public ArrayList<WorkSlot> getAllWorkSlots() {
        WorkSlot workSlot = new WorkSlot();
        return workSlot.getAllWorkSlotsMax();
    }
}


