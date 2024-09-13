package Testing;

import Boundary.LoginGUI;
import Controller.LoginController;
import Entity.UserAccount;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import javax.swing.*;
import java.awt.*;

import static org.junit.Assert.*;

public class LoginTest {
    private LoginController testLogin;
    private JFrame frame;

    @Before
    public void setUp() throws Exception {
        this.testLogin = new LoginController();
    }

    @After
    public void tearDown() throws Exception {
        testLogin = null;
    }

    @Test
    public void testCorrectLoginDetails() {
        String correctUsername = "admin1";
        String correctPassword = "admin1";

        // testLogin.login() will return UserName Object
        assertEquals(correctPassword, testLogin.login(correctUsername, correctPassword).getUsername());
        assertEquals(correctPassword, testLogin.login(correctUsername, correctPassword).getPassword());
    }

    @Test
    public void testWrongLoginDetails() {
        String wrongUsername = "wrongAdmin";
        String wrongPassword = "wrongAdmin";

        assertEquals(null, testLogin.login(wrongUsername, wrongPassword));

    }
}