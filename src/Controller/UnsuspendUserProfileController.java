package Controller;

import Entity.UserProfile;

public class UnsuspendUserProfileController {
    private UserProfile userProfile;

    public UnsuspendUserProfileController() {
        this.userProfile = new UserProfile();
    }

    public boolean unsuspendUserProfile(String profileName) {
        return userProfile.unsuspendUserProfile(profileName);
    }
}
