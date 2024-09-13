package Controller;

import Entity.UserProfile;

public class CreateUserProfileController {
    private UserProfile userProfile;

    public CreateUserProfileController() {
        this.userProfile = new UserProfile();
    }
    public boolean createUserProfile(String profileName, String profileDesc) {
        return userProfile.createUserProfile(profileName, profileDesc);
    }

}
