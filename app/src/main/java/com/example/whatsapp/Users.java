package com.example.whatsapp;

public class Users {

    String profilePic , userName , userEmail , userPassword , lastMessage , status ,  userId , about;
    Long userTime;

    public Users(){

    }

    public Users(String userName, String userEmail, String userPassword,String about,Long userTime) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.about = about;
        this.userTime = userTime;
    }

    public Users(String profilePic, String userName, String userEmail, String userPassword, String lastMessage, String status , String userId , String about,Long userTime ) {
        this.profilePic = profilePic;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.lastMessage = lastMessage;
        this.status = status;
        this.userId = userId;
        this.about = about;
        this.userTime = userTime;
    }

    public Long getUserTime() {
        return userTime;
    }

    public void setUserTime(Long userTime) {
        this.userTime = userTime;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
