package Controller;

import Entity.UserAccount;

public class UnsuspendUserAccountController {
    private UserAccount userAccount;

    public UnsuspendUserAccountController() {
        this.userAccount = new UserAccount();
    }

    public boolean unsuspendUserAccount(String username) {
        return userAccount.unsuspendUserAccount(username);
    }
}
