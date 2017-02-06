/**
 * Created by jaggu on 4/18/2015.
 */
package com.uconnect.events.model;

/**
 * UCUser is the user model (pojo) class.
 */
public class UCUser {
    private String userId;
    private String friendId;
    private String emailId;
    private String phoneNo;
    private String screenName;
    private String creationTime;
    private String profileImageUrl;
    private String updatesImageUrl;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {

        return userId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public String getFriendId() {

        return friendId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getEmailId() {

        return emailId;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPhoneNo() {

        return phoneNo;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getScreenName() {

        return screenName;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getCreationTime() {

        return creationTime;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getProfileImage() {

        return profileImageUrl;
    }

    public void setUpdatesImageUrl(String updatesImageUrl) {
        this.updatesImageUrl = updatesImageUrl;
    }

    public String getUpdatesImageUrl() {

        return updatesImageUrl;
    }
}
