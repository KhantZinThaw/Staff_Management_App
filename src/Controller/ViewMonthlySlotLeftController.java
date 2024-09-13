package Controller;

import Entity.Bid;
import Entity.UserAccount;

import java.sql.Date;

public class ViewMonthlySlotLeftController {
    public int viewMonthlySlotLeft(UserAccount u, Date date){
        return new Bid(u.getUsername(), date).getMonthlySlotLeft();
    }
}