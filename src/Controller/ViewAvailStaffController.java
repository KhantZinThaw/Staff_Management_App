package Controller;

import Entity.WorkSlot;

import java.sql.Date;

public class ViewAvailStaffController {
    public Object[][] getAvailableStaffs(Date date){
        return new WorkSlot(date).getAvailStaffs();
    }
}
