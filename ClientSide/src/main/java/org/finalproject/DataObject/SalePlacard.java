package org.finalproject.DataObject;

import java.util.HashSet;

public class SalePlacard extends DataObject {

    //should be increased when serialized versions of older objects can not be deserialized to the new class...
    static final long serialVersionUID = 1L;

    private final HashSet<String> imagesUrl = new HashSet<>();
    private String title;
    private String description = "";
    private long priceInRials;
    private String city = "";
    private String address = "";
    private String phoneNumber = "";

    public SalePlacard(String title) {
        this.title = title;
    }

    public HashSet<String> getImagesUrl() {
        return imagesUrl;
    }

    public void addImageUrl(String imageUrl) {
        this.imagesUrl.add(imageUrl);
    }

    public void removeImageUrl(String imageUrl) {
        this.imagesUrl.remove(imageUrl);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getPriceInRials() {
        //TODO: add a method to get formatted price, like 410,000 rials. maybe user should set preference of rial/tooman
        return priceInRials;
    }

    public void setPriceInRials(long priceInRials) {
        this.priceInRials = priceInRials;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
