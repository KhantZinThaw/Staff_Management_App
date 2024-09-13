package Controller;

import Entity.UserAccount;

public class LoginController {
    public UserAccount userAccount;

    public LoginController() {
        this.userAccount = new UserAccount();
    }

    // Checking username and password
    public UserAccount login(String username, String password) {
        // Return user account object if correct
        // else, return null
        return userAccount.validateLogin(username, password);
    }
}
