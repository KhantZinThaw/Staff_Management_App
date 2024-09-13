package Controller;

import Entity.UserProfile;

import java.util.ArrayList;


public class ViewUserProfileController {
    private UserProfile userProfile;

    public ViewUserProfileController() {
        this.userProfile = new UserProfile();
    }
    public ArrayList<UserProfile> getProfileList() {
        return userProfile.selectAllProfile();
    }
}
