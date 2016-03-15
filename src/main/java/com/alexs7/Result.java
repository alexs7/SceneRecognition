package com.alexs7;

/**
 * Created by ar1v13 on 15/03/16.
 */
public class Result {

    private String category;
    private double distance;

    public Result(String category, double distance) {
        this.category = category;
        this.distance = distance;
    }

    public String getCategory() {
        return category;
    }

    public double getDistance() {
        return distance;
    }
}
