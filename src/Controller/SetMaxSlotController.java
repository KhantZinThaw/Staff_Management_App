package Controller;

import Entity.UserAccount;

public class SetMaxSlotController {
    private UserAccount userAccount;

    public SetMaxSlotController() {
        this.userAccount = new UserAccount();
    }

    public boolean setMaxSlot(UserAccount u) {
        return userAccount.updateUserAccount(u);
    }
}
