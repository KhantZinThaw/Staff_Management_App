package Controller;

import Entity.UserAccount;
import java.util.ArrayList;

public class ViewUserAccountController {
    private UserAccount userAccount;

    public ViewUserAccountController() {
        this.userAccount = new UserAccount();
    }
    public ArrayList<UserAccount> getAccountList() {
        return userAccount.selectAll();
    }


}
