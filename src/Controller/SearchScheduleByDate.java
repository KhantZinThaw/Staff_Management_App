package Controller;

import Entity.Bid;
import Entity.UserAccount;

import java.sql.Date;

public class SearchScheduleByDate {
    public Object[][] getScheduleByDate(String select, UserAccount userAccount, Date date){
        return new Bid(userAccount.getUsername(), date).searchWorkScheduleByDate(select);
    }
}
