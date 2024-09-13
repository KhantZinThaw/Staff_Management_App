package Controller;

import Entity.WorkSlot;
public class CreateWorkSlotController {
    private WorkSlot workSlot;

    public CreateWorkSlotController() {
        this.workSlot = new WorkSlot();
    }

    public WorkSlot createWorkSlot(String date, int chefAmount, int cashierAmount, int waiterAmount) {
        return workSlot.createWorkSlot(date, chefAmount, cashierAmount, waiterAmount);
    }
}
