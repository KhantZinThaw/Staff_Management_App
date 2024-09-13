package Controller;

import Entity.UserAccount;

import java.util.ArrayList;

public class UpdateUserAccountController {
    private UserAccount userAccount;

    public UpdateUserAccountController() {
        this.userAccount = new UserAccount();
    }

    public UserAccount getSelectedAccount(String username) {
        return userAccount.getSelectedAccount(username);
    }

    public ArrayList<String> getProfileList() {
        return userAccount.getProfileByName();
    }

    public boolean UpdateUserAccount(UserAccount updatedUser) {
        return userAccount.updateUserAccount(updatedUser);
    }
}
