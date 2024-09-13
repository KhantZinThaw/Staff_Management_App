package Controller;

import Entity.WorkSlot;

public class CSViewAvailWSController {
    public Object[][] getAllWorkSlots(){return new WorkSlot().getAllWS();}
}
