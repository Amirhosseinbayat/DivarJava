package org.finalproject.DataObject;

import java.util.HashSet;

/**
 * represents a user and his/her profile information.
 */
public class User extends DataObject {

    static final long serialVersionUID = 1L;
    private String username;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String phoneNumber;
    private String password;
    private String newPassword;

    private String profilePictureUrl;
    private String city;
    private String address;
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
                ", firstName='"+firstName+'\''+
                ", lastName='"+lastName+'\''+
                ", emailAddress='"+emailAddress+'\''+
                ", phoneNumber='"+phoneNumber+'\''+
                ", password='"+password+'\''+
                ", newPassword='"+newPassword+'\''+
                ", profilePictureUrl='"+profilePictureUrl+'\''+
                ", city='"+city+'\''+
                ", address='"+address+'\''+
                ", likedPlacards="+likedPlacards+
                ", createdPlacards="+createdPlacards+
                ", objectId="+objectId+
                '}';
    }

    @Override
    public User clone() {
        return (User) super.clone();
    }

    public void setLikedPlacards(HashSet<Long> likedPlacards) {
        this.likedPlacards = likedPlacards;
    }

    public void setCreatedPlacards(HashSet<Long> createdPlacards) {
        this.createdPlacards = createdPlacards;
    }

    @Override
    public void copyData(DataObject dataObject) {
        super.copyData(dataObject);
        User that = ((User) dataObject);
        this.setUsername(that.getUsername());
        this.setFirstName(that.getFirstName());
        this.setLastName(that.getLastName());
        this.setPassword(that.getPassword());
        this.setNewPassword(that.getNewPassword());
        this.setPhoneNumber(that.getPhoneNumber());
        this.setEmailAddress(that.getEmailAddress());
        this.setProfilePictureUrl(that.getProfilePictureUrl());
        this.setAddress(that.getAddress());
        this.setCity(that.getCity());
        this.setLikedPlacards(new HashSet<>());
        this.setCreatedPlacards(new HashSet<>());
        for (Long objectId : that.getLikedPlacards()) {
            this.addToLikedPlacards(objectId);
        }
        for (Long objectId : that.getCreatedPlacards()) {
            this.addToCreatedPlacards(objectId);
        }
    }
}
