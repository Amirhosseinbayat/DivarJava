package org.finalproject.DataObject;

import java.util.HashSet;

/**
 * represents a user and his/her profile information.
 */
public class User extends DataObject {

    static final long serialVersionUID = 1L;
    private String username;
    private String firstName = "";
    private String lastName = "";
    private String emailAddress = "";
    private String phoneNumber = "";
    private String password;
    private String newPassword;

    private String profilePictureUrl = "";
    private String city = "";
    private String address = "";
    private HashSet<Long> likedPlacards;
    private HashSet<Long> createdPlacards;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public HashSet<Long> getLikedPlacards() {
        if (likedPlacards == null) likedPlacards = new HashSet<>();
        return likedPlacards;
    }

    public void addToLikedPlacards(long objectId) {
        getLikedPlacards().add(objectId);
    }

    public void removeFromLikedPlacards(long objectId) {
        getLikedPlacards().remove(objectId);
    }

    public HashSet<Long> getCreatedPlacards() {
        if (createdPlacards == null) createdPlacards = new HashSet<>();
        return createdPlacards;
    }

    public void addToCreatedPlacards(long objectId) {
        getCreatedPlacards().add(objectId);
    }

    public void removeFromCreatedPlacards(long objectId) {
        getCreatedPlacards().remove(objectId);
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public String toString() {
        return "User{"+
                "username='"+username+'\''+
                ", password='"+password+'\''+
                ", objectId="+objectId+
                '}';
    }

    @Override
    public User clone() {
        return (User) super.clone();
    }
}
