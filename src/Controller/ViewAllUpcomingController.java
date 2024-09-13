package Controller;

import Entity.Bid;
import Entity.UserAccount;

public class ViewAllUpcomingController {
    public Object[][] getAllUpcoming(String select, UserAccount userAccount){
        return new Bid(userAccount.getUsername(), null).filterSearchWorkSched(select);
    }
}
