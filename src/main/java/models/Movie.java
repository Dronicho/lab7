package models;

import persistance.CsvSerilizable;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Movie implements CsvSerilizable, Comparable<Movie>, Serializable, Entity {
    public static int maxId = 0;
    private int id;
    private int userId;
    private String name;
    private Coordinates coordinates;
    private LocalDateTime creationDate;
    private Long oscarsCount;
    private long totalBoxOffice;
    private long usaBoxOffice;
    private MovieGenre genre;
    private Person person;


    public Movie(int id, int userId, String name, Coordinates coordinates, Timestamp creationDate, Long oscarsCount, long totalBoxOffice, long usaBoxOffice, MovieGenre genre, Person person) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate.toLocalDateTime();
        this.oscarsCount = oscarsCount;
        this.totalBoxOffice = totalBoxOffice;
        this.usaBoxOffice = usaBoxOffice;
        this.genre = genre;
        this.person = person;
    }

    public Movie(int userId, String name, Coordinates coordinates, Long oscarsCount, long totalBoxOffice, long usaBoxOffice, MovieGenre genre, Person person) {
        this.userId = userId;
        this.creationDate = LocalDateTime.now();
        this.name = name;
        this.coordinates = coordinates;
        this.oscarsCount = oscarsCount;
        this.totalBoxOffice = totalBoxOffice;
        this.usaBoxOffice = usaBoxOffice;
        this.genre = genre;
        this.person = person;
    }

    public Movie() {
        this.id = ++maxId;
    }

    @Override
    public String getProperties() {
        return "(name, x, y, createdat, oscarscount, totalboxoffice, usaboxoffice, genre, person_name, person_birthday, person_color, location_x, location_y, location_name, user_id)";
    }

    @Override
    public String getValues() {
        return String.format("(%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)",
                getName(),
                getCoordinates().getX(),
                getCoordinates().getY(),
                getCreationDate(),
                getOscarsCount(),
                getTotalBoxOffice(),
                getUsaBoxOffice(),
                getGenre().toString(),
                getPerson().getName(),
                getPerson().getBirthday(),
                getPerson().getHairColor(),
                getPerson().getLocation().getName(),
                getPerson().getLocation().getX(),
                getPerson().getLocation().getY(),
                getUserId()
            );
    }

    @Override
    public String toString() {
        return String.format("Movie(id=%s, name=%s, totalBoxOffice=%s)", id, name, totalBoxOffice);
    }

    @Override
    public int compareTo(Movie o) {
        return (int) (totalBoxOffice - o.getTotalBoxOffice());
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Long getOscarsCount() {
        return oscarsCount;
    }

    public void setOscarsCount(Long oscarsCount) {
        this.oscarsCount = oscarsCount;
    }

    public long getTotalBoxOffice() {
        return totalBoxOffice;
    }

    public void setTotalBoxOffice(long totalBoxOffice) {
        this.totalBoxOffice = totalBoxOffice;
    }

    public long getUsaBoxOffice() {
        return usaBoxOffice;
    }

    public void setUsaBoxOffice(long usaBoxOffice) {
        this.usaBoxOffice = usaBoxOffice;
    }

    public MovieGenre getGenre() {
        return genre;
    }

    public void setGenre(MovieGenre genre) {
        this.genre = genre;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int id) {
        this.userId = id;
    }
}
