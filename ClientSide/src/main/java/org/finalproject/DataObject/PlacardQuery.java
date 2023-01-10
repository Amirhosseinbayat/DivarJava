package org.finalproject.DataObject;

public class PlacardQuery extends DataObject {

    public static final String ORDER_BY_PRICE_ASC = "price_asc";
    public static final String ORDER_BY_PRICE_DESC = "price_desc";
    public static final String ORDER_BY_UPDATE_ASC = "update_asc";
    public static final String ORDER_BY_UPDATE_DESC = "update_desc";
    public static final String ORDER_BY_CREATION_ASC = "creation_asc";
    public static final String ORDER_BY_CREATION_DESC = "creation_desc";

    long priceGreaterThan = 0;
    long priceLessThan = Long.MAX_VALUE;
    String city;
    String searchText = "";
    String orderBy = ORDER_BY_CREATION_DESC;

    public static String getOrderByHumanReadable(String orderBy) {
        switch (orderBy) {
            case ORDER_BY_PRICE_ASC -> {
                return "sorted by price ascending.";
            }
            case ORDER_BY_PRICE_DESC -> {
                return "sorted by price descending.";
            }
            case ORDER_BY_UPDATE_ASC -> {
                return "sorted by update time ascending";
            }
            case ORDER_BY_UPDATE_DESC -> {
                return "sorted by update time descending";
            }
            case ORDER_BY_CREATION_ASC -> {
                return "sorted by creation time, oldest first";
            }
            case ORDER_BY_CREATION_DESC -> {
                return "sorted by creation time, newest first";
            }
        }
        return orderBy;
    }

    public long getPriceGreaterThan() {
        return priceGreaterThan;
    }

    public void setPriceGreaterThan(long priceGreaterThan) {
        this.priceGreaterThan = priceGreaterThan;
    }

    public long getPriceLessThan() {
        return priceLessThan;
    }

    public void setPriceLessThan(long priceLessThan) {
        this.priceLessThan = priceLessThan;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrderByHumanReadable() {
        return getOrderByHumanReadable(getOrderBy());
    }

    public String getPriceRange() {
        String priceRange = "";
        if (priceGreaterThan != 0) priceRange += "more than "+priceGreaterThan+" Rials ";
        if (priceLessThan != Long.MAX_VALUE) priceRange += " less than "+priceLessThan+" Rials";
        if (priceRange.isEmpty()) return "no price limitation";
        return priceRange;
    }
}
