package Controller;

import Entity.Bid;

import java.util.ArrayList;

public class ViewBidStatusController {
    private Bid bid;

    public ViewBidStatusController() {
        this.bid = new Bid();
    }

    public ArrayList<Bid> viewBidStatus (String username) {
        return bid.getAllBidStatus(username);
    }

}
