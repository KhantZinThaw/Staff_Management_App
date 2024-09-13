package Controller;

import Entity.UserAccount;

public class SuspendUserAccountController {
    private UserAccount userAccount;

    public SuspendUserAccountController() {
        this.userAccount = new UserAccount();
    }

    public boolean suspendUserAccount(String username) {
        return userAccount.suspendUserAccount(username);
    }
}
