package Controller;

import Entity.WorkSlot;

import java.sql.Date;

public class UpdateWorkSlotController {
    private WorkSlot workSlot;

    public UpdateWorkSlotController(Date date) {this.workSlot = new WorkSlot(date);}

    public boolean updateRoleAmount(int roleID, int newAmount) {
        Object[][] ws = workSlot.getWS();

        // Get current staff assigned/approved = max amount - current slot left

        if (roleID == 1){
            if (newAmount < (workSlot.getWaiterAmount() - (int) ws[0][3])){
                return false;
            }else {
                return workSlot.updateRoleAmount(workSlot.getDate(), roleID, newAmount);
            }

        }else if (roleID == 2){
            if (newAmount < ( workSlot.getCashierAmount() - (int) ws[0][2])){
                return false;
            }else {
                return workSlot.updateRoleAmount(workSlot.getDate(), roleID, newAmount);
            }
        } else if (roleID == 3) {
            if (newAmount < (workSlot.getChefAmount() - (int) ws[0][1])){
                return false;
            }else {
                return workSlot.updateRoleAmount(workSlot.getDate(), roleID, newAmount);
            }
        }else{
            return false;
        }



    }
}
