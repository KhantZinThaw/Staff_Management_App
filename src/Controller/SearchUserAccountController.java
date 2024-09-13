package Controller;

import Entity.UserAccount;

import java.util.ArrayList;

public class SearchUserAccountController {
    private UserAccount userAccount;

    public SearchUserAccountController() {
        this.userAccount = new UserAccount();
    }
    public ArrayList<UserAccount> searchUserAccount(String search) {
        return userAccount.getUserAccountByUsername(search);
    }
}
