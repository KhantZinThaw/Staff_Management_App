package Controller;

import Entity.Bid;
import Entity.WorkSlot;

import java.sql.Date;

public class AssignAvailStaffController {
    public Object[] assignAvailStaff(String name, String username, String role, WorkSlot workSlot){
        // index 0 = boolean
        // index 1 = error message (If there is an issue)
        // index 2 = updated workslot (slot left)
        Object[] returnVal = new Object[2];

        // Check availability
        if(role.equals("Chef") && workSlot.getChefAmount() == 0){
            // Chef is full
            returnVal[0] = false;
            returnVal[1] = "Chef slot is full";

        }else if(role.equals("Cashier") && workSlot.getCashierAmount() == 0){
            // Cashier is full
            returnVal[0] = false;
            returnVal[1] = "Cashier slot is full";

        }else if(role.equals("Waiter") && workSlot.getWaiterAmount() == 0){
            // Waiter is full
            returnVal[0] = false;
            returnVal[1] = "Waiter slot is full";

        }else{
            // Process assign staff
            Date ws = new WorkSlot(workSlot.getDate()).getDate();
            returnVal[0] = new Bid(ws).assignAvailableStaff(username);


            if(returnVal[0].equals(false)){
                // Issue on assigning staff
                returnVal[1] = String.format("Issue while assigning %s (Role: %s) to %s", name, role, workSlot.getDate());

            }else{
                // Assign staff success
                // Reduce amount of staff's slot left
                if(role.equals("Chef") ){
                    workSlot.setChefAmount( workSlot.getChefAmount()-1 );
                }else if(role.equals("Cashier")){
                    workSlot.setCashierAmount( workSlot.getCashierAmount()-1 );
                }else if(role.equals("Waiter")){
                    workSlot.setWaiterAmount( workSlot.getWaiterAmount()-1 );
                }

                returnVal[1] = null;
            }

        }
        return returnVal;
    }
}
