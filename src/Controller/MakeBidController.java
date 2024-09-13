package Controller;

import Entity.Bid;
import java.sql.Date;

public class MakeBidController {
    private Bid bid;

    public MakeBidController() {this.bid = new Bid();}

    public boolean makeBid(String username, Date date) {
        return bid.makeBid(username, date);
    }
}
