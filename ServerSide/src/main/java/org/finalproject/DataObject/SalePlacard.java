package org.finalproject.DataObject;

import java.util.HashSet;
import java.util.Set;

public class SalePlacard extends DataObject {

    String title;
    String description;
    String category;
    String city;
    long userId;
    long price;

    Set<String> imageUrlSet = new HashSet<>();

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public Set<String> getImageUrlSet() {
        return imageUrlSet;
    }

    public void addImageUrl(String imageUrl) {
        this.imageUrlSet.add(imageUrl);
    }

    public void removeImageUrl(String imageUrl){
        this.imageUrlSet.remove(imageUrl);
    }
}
