package Controller;

import Entity.UserAccount;

public class SetRoleController {
    private UserAccount userAccount;

    public SetRoleController() {
        this.userAccount = new UserAccount();
    }

    public boolean setRole(UserAccount u) {
        return userAccount.updateUserAccount(u);
    }
}
