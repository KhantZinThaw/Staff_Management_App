package Controller;

import Entity.Bid;

import java.sql.Date;

public class ViewBidDataController {
    public Object[][] viewBidData(Date date){
        return new Bid(date).getBids();
    }
}
