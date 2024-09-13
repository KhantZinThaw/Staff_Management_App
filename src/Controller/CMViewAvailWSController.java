package Controller;

import Entity.WorkSlot;

public class CMViewAvailWSController {
    public Object[][] getAllWorkSlots(){return new WorkSlot().getAllWS();}
}