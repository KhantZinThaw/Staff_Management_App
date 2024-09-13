package Controller;

import Entity.Bid;

import java.util.ArrayList;

public class FilterBidStatusController {
    private Bid bid;

    public FilterBidStatusController() {
        this.bid = new Bid();
    }

    public ArrayList<Bid> filterBidStatus(String username, String selectedBidStatus) {
        return bid.getBidByStatus(username, selectedBidStatus);
    }
}
