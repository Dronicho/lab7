package models;

import persistance.CsvSerilizable;

import java.io.Serializable;

public class Coordinates implements CsvSerilizable, Serializable {
    private double x;
    private Long y;

    public double getX() {
        return x;
    }

    public Long getY() {
        return y;
    }

    public Coordinates(double x, Long y) {
        this.x = x;
        this.y = y;
    }
}
