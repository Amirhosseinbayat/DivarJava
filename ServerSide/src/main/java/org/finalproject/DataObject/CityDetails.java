package org.finalproject.DataObject;

public class CityDetails extends DataObject{

    String name;
    int countOfPlacards;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCountOfPlacards() {
        return countOfPlacards;
    }

    public void setCountOfPlacards(int countOfPlacards) {
        this.countOfPlacards = countOfPlacards;
    }

    public void incrementCount() {
        this.countOfPlacards++;
    }
}
