package Controller;

import Entity.Bid;
import Entity.UserAccount;

public class FilterWorkScheduleController {
    public Object[][] getFilterWorkSchedule(String select, UserAccount userAccount){
        return new Bid(userAccount.getUsername(), null).filterSearchWorkSched(select);
    }
}
