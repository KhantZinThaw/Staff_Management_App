package Controller;

import Entity.Bid;
import Entity.WorkSlot;

public class ApproveBidController {
    public Object[] processApprove(Bid bid, WorkSlot workSlot){

        // index 0 = boolean
        // index 1 = error message (If there is an issue)
        // index 2 = updated workslot (slot left)
        Object[] returnVal = new Object[2];

        // Check availability
        if(bid.getRole().equals("Chef") && workSlot.getChefAmount() == 0){
            // Chef is full
            returnVal[0] = false;
            returnVal[1] = "Chef slot is full";

        }else if(bid.getRole().equals("Cashier") && workSlot.getCashierAmount() == 0){
            // Cashier is full
            returnVal[0] = false;
            returnVal[1] = "Cashier slot is full";

        }else if(bid.getRole().equals("Waiter") && workSlot.getWaiterAmount() == 0){
            // Waiter is full
            returnVal[0] = false;
            returnVal[1] = "Waiter slot is full";

        }else{
            // Process bid approval
            returnVal[0] = new Bid(bid.getBidId(), bid.getName(), bid.getRole(), bid.getDate()).approveBid();

            if(returnVal[0].equals(false)){
                // Issue on processing bid approval
                returnVal[1] = "Issue found when approving Bid ID : " + bid.getBidId();

            }else{
                // Bid approval success
                // Reduce amount of staff's slot left
                if(bid.getRole().equals("Chef") ){
                    workSlot.setChefAmount( workSlot.getChefAmount()-1 );
                }else if(bid.getRole().equals("Cashier")){
                    workSlot.setCashierAmount( workSlot.getCashierAmount()-1 );
                }else if(bid.getRole().equals("Waiter")){
                    workSlot.setWaiterAmount( workSlot.getWaiterAmount()-1 );
                }

                returnVal[1] = null;
            }

        }
        return returnVal;
    }
}
