package Controller;

import Entity.UserAccount;

import java.util.ArrayList;

public class CreateUserAccountController {
    private UserAccount userAccount;

    public CreateUserAccountController() {
        this.userAccount = new UserAccount();
    }
    public ArrayList<String> getProfileList() {
        return userAccount.getProfileByName();
    }

    public ArrayList<String> getRoleList() {
        return userAccount.getRoleByName();
    }

    public boolean addAccount(UserAccount newUser) {
        return userAccount.insertAccount(newUser);
    }
}
