package models;

import persistance.CsvSerilizable;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class
Person implements CsvSerilizable, Serializable {
    private String name;
    private LocalDateTime birthday;
    private Color hairColor;
    private Location location;

    public String getName() {
        return name;
    }

    public LocalDateTime getBirthday() {
        return birthday;
    }

    public Color getHairColor() {
        return hairColor;
    }

    public Location getLocation() {
        return location;
    }

    public Person(String name, Timestamp birthday, Color hairColor, Location location) {
        this.name = name;
        this.birthday = birthday.toLocalDateTime();
        this.hairColor = hairColor;
        this.location = location;
    }


}
