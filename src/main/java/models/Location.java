package models;

import persistance.CsvSerilizable;

import java.io.Serializable;

public class Location implements CsvSerilizable, Serializable {
    private Integer x;
    private long y;
    private String name;

    public Integer getX() {
        return x;
    }

    public long getY() {
        return y;
    }

    public String getName() {
        return name;
    }

    public Location(int x, long y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }
}
