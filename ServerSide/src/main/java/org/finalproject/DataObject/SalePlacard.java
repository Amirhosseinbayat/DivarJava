package org.finalproject.DataObject;

import java.util.LinkedHashSet;
import java.util.Set;

public class SalePlacard extends DataObject {

    //should be increased when serialized versions of older objects can not be deserialized to the new class...
    static final long serialVersionUID = 1L;

    private Set<String> imageUrlSet;
    private String title;
    private String description;
    private long priceInRials;

    private long createdBy;
    private String city;
    private String address;
    private String phoneNumber;

    private long promotionExpireData = -1;

    public long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(long createdBy) {
        this.createdBy = createdBy;
    }

    public SalePlacard(String title) {
        this.title = title;
    }

    public Set<String> getImageUrlSet() {
        if (imageUrlSet == null) imageUrlSet = new LinkedHashSet<>();
        return imageUrlSet;
    }

    public void addImageUrl(String imageUrl) {
        this.getImageUrlSet().add(imageUrl);
    }

    public void removeImageUrl(String imageUrl) {
        this.imageUrlSet.remove(imageUrl);
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


    @Override
    public String toString() {
        return "SalePlacard{"+
                "title='"+title+'\''+
                ", priceInRials="+priceInRials+
                ", city='"+city+'\''+
                '}';
    }

    public String getFirstImageUrl() {
        if (getImageUrlSet().isEmpty()) return "";
        return getImageUrlSet().toArray(new String[0])[0];
    }

    public String getShortenedDescription() {
        return  getDescription().substring(0, Math.min(getDescription().length(), 60));
    }

    public boolean isCreatedBy(User user) {
        return getCreatedBy() == user.getObjectId();
    }


    public long getPromotionExpireData() {
        return promotionExpireData;
    }

    public void setPromotionExpireData(long promotionExpireData) {
        this.promotionExpireData = promotionExpireData;
    }

    public boolean isStillPromoted() {
        return this.getPromotionExpireData()>System.currentTimeMillis();
    }

    @Override
    public SalePlacard clone() {
        SalePlacard clone = (SalePlacard) super.clone();
        clone.imageUrlSet = new LinkedHashSet<>();
        //current imageUrlSet is in use by this object. clone needs to have another separated one.
        for (String imgUrl : getImageUrlSet()) {
            clone.addImageUrl(imgUrl);
        }
        return clone;
    }
}
