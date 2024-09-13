package Controller;

import Entity.UserProfile;

import java.util.ArrayList;


public class SearchUserProfileController {
    private UserProfile userProfile;

    public SearchUserProfileController() {
        this.userProfile = new UserProfile();
    }
    public ArrayList<UserProfile> SearchUserProfile(String search) {
        return userProfile.getProfileName(search);
    }
}
