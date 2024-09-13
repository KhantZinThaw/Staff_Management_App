package Controller;

import Entity.UserProfile;

public class UpdateUserProfileController {
    private UserProfile userProfile;

    public UpdateUserProfileController() {
        this.userProfile = new UserProfile();
    }

    public boolean UpdateUserProfile(String profileName, String newProfileDesc) {
        return userProfile.updateUserProfile(profileName, newProfileDesc);
    }
}
