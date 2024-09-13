package Controller;

import Entity.Bid;

import java.sql.Date;

public class UpdateBidController {
    private Bid bid;

    public UpdateBidController() {
        this.bid = new Bid();
    }

    public boolean updateBid(int bidId, String username, Date date) {
        return bid.updateBid(bidId, username, date);
    }
}
