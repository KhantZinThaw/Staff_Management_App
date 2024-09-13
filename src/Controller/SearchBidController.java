package Controller;

import Entity.Bid;

import java.sql.Date;
import java.util.ArrayList;

public class SearchBidController {
    private Bid bid;

    public SearchBidController() {
        this.bid = new Bid();
    }

    public ArrayList<Bid> searchBid(String username, Date selectedDate) {
        return bid.getBidByDate(username, selectedDate);
    }
}
