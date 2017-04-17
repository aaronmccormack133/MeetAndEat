package com.meetandeat.meetandeat;

/**
 * Created by Aaron on 12/04/2017.
 */

public class UserInformation {
    public String name;
    public String age;
    public String bio;

    public UserInformation(){

    }

    public UserInformation(String name, String age, String bio) {
        this.name = name;
        this.age = age;
        this.bio = bio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
