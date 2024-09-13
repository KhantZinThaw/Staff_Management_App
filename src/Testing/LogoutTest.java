package Testing;

import Boundary.SystemAdminGUI;
import Boundary.CafeOwnerGUI;
import Boundary.CafeManagerGUI;
import Boundary.CafeStaffGUI;

import Entity.UserAccount;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LogoutTest {
    private SystemAdminGUI adminLogout;
    private CafeOwnerGUI ownerLogout;
    private CafeManagerGUI managerLogout;
    private CafeStaffGUI staffLogout;

    @Before
    public void setUp() throws Exception {
        UserAccount sa = new UserAccount("SystemAdmin", "SystemAdmin", "System", "Admin", "SystemAdmin@Gmail.com", null, true);
        UserAccount o = new UserAccount("CafeOwner", "CafeOwner", "Cafe", "Owner", "CafeOwner@Gmail.com", null, true);
        UserAccount m = new UserAccount("CafeManager", "CafeManager", "Cafe", "Manager", "CafeManager@Gmail.com", null, true);
        UserAccount s = new UserAccount("CafeStaff", "CafeStaff", "Cafe", "Staff", "CafeStaff@gmail.com", null, true, 5, 2);

        this.adminLogout = new SystemAdminGUI(sa);
        this.ownerLogout = new CafeOwnerGUI(o);
        this.managerLogout = new CafeManagerGUI(m);
        this.staffLogout = new CafeStaffGUI(s);

    }

    @After
    public void tearDown() {
        adminLogout = null;
        ownerLogout = null;
        managerLogout = null;
        staffLogout = null;
    }

    @Test
    public void testAdminLogout() {
        adminLogout.logout();
        assertFalse(adminLogout.getFrame().isVisible());
    }

    @Test
    public void testOwnerLogout() {
        ownerLogout.logout();
        assertFalse(ownerLogout.getFrame().isVisible());
    }

    @Test
    public void testManagerLogout() {
        managerLogout.logout();
        assertFalse(managerLogout.getFrame().isVisible());
    }

    @Test
    public void testStaffLogout() {
        staffLogout.logout();
        assertFalse(staffLogout.getFrame().isVisible());
    }
}
