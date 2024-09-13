package Controller;

import Entity.Bid;

import java.sql.Date;

public class CancelBidController {
    private Bid bid;

    public CancelBidController() {
        this.bid = new Bid();
    }

    public boolean cancelBid(Date date) {
        return bid.cancelBid(date);
    }
}
