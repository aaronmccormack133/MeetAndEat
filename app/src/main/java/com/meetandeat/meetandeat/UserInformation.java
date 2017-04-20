package com.meetandeat.meetandeat;

/**
 * Created by Aaron on 12/04/2017.
 */

public class UserInformation {

    public String userInfoAge;
    public String userInfoBio;
    public String userInfoName;

    public UserInformation(){

    }

    public UserInformation(String userInfoAge, String userInfoBio, String userInfoName) {
        this.userInfoAge = userInfoAge;
        this.userInfoBio = userInfoBio;
        this.userInfoName = userInfoName;
    }

    public String getUserInfoAge() {
        return userInfoAge;
    }

    public void setUserInfoAge(String userInfoAge) {
        this.userInfoAge = userInfoAge;
    }

    public String getUserInfoBio() {
        return userInfoBio;
    }

    public void setUserInfoBio(String userInfoBio) {
        this.userInfoBio = userInfoBio;
    }

    public String getUserInfoName() {
        return userInfoName;
    }

    public void setUserInfoName(String userInfoName) {
        this.userInfoName = userInfoName;
    }
}
