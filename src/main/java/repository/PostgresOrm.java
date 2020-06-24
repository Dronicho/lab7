package repository;

import models.*;

import java.sql.*;
import java.util.HashSet;

public class PostgresOrm {
    final String databaseURL = "jdbc:postgresql://localhost:5432/studs";
    final String username = "dronich";
    final String password = "nv351289";

    public static PostgresOrm instance;
    private Connection connection;

    static  {
        try {
            instance = new PostgresOrm();
            instance.createMovieTable();
            instance.createUserTable();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private PostgresOrm() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection(databaseURL, username, password);
    }

    public HashSet<Movie> getAllMovies() {
        try(Statement statement = connection.createStatement()) {
            HashSet<Movie> movies = new HashSet<>();
            ResultSet rs = statement.executeQuery("SELECT * FROM movie");
            while (rs.next()) {
                Movie m = new Movie(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("name"),
                        new Coordinates(
                                rs.getInt("x"),
                                rs.getLong("y")
                        ),
                        rs.getTimestamp("createdAt"),
                        rs.getLong("oscarsCount"),
                        rs.getInt("totalBoxOffice"),
                        rs.getInt("usaBoxOffice"),
                        MovieGenre.valueOf(rs.getString("genre")),
                        new Person(
                                rs.getString("person_name"),
                                rs.getTimestamp("person_birthday"),
                                Color.valueOf(rs.getString("person_color")),
                                new Location(
                                        rs.getInt("location_x"),
                                        rs.getInt("location_y"),
                                        rs.getString("location_name")
                                )
                        )
                );
                movies.add(m);
            }
            return movies;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getUser(String username, String password) {
        
    }

    public User createUser(String username, String password) {
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate(String.format("INSERT INTO users (username, password) VALUES (%s, %s)", username, password));
            ResultSet rs = statement.executeQuery("SELECT * FROM users");
            rs.last();
            return new User(rs.getInt("id"), username, password);
        } catch (SQLException e) {
            System.out.println("юзер уже существует");
            return null;
        }
    }


    public int addMovie(Movie movie, User user) {
        try(Statement statement = connection.createStatement()) {
            movie.setUserId(user.getId());
            statement.executeUpdate(String.format("INSERT INTO movie %s VALUES %s", movie.getProperties(), movie.getValues()));
            ResultSet rs = statement.executeQuery("SELECT * FROM movie");
            rs.last();
            movie.setId(rs.getInt("id"));
            return rs.getInt("id");
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private void getMovieById(int id) {
        String getSql = String.format("SELECT * FROM Movie mv JOIN person ps ON mv.person_id=ps.id JOIN  WHERE mv.id=%s", id);

        ResultSet resultSet = executeGet(getSql);
        while (true) {
            try {
                if (!resultSet.next()) break;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println(resultSet);
        }
    }

    ResultSet executeGet(String sql) {
        try(Statement statement = connection.createStatement()) {
            return statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void createMovieTable() {
        String movieTableSql =
                "CREATE TABLE movie(" +
                "id SERIAL PRIMARY KEY," +
                "x INTEGER NOT NULL," +
                "y INTEGER NOT NULL," +
                "createdAt TIMESTAMP," +
                "oscarsCount INTEGER NOT NULL," +
                "totalBoxOffice INTEGER NOT NULL," +
                "usaBoxOffice INTEGER NOT NULL," +
                "genre VARCHAR(255) NOT NULL," +
                "person_name VARCHAR(255) NOT NULL," +
                "person_birthday TIMESTAMP NOT NULL," +
                "person_color VARCHAR(255) NOT NULL," +
                "location_x INTEGER NOT NULL," +
                "location_y INTEGER NOT NULL," +
                "location_name VARCHAR(255) NOT NULL," +
                "user_id INTEGER NOT NULL," +
                "FOREIGN KEY (user_id) REFERENCES users (id));";

        if (!execute(movieTableSql)) {
            System.out.println("Таблица movie уже существует");
        }

    }

    private void createUserTable() {

        String userTableSql =
                "CREATE TABLE users(\n" +
                "id SERIAL PRIMARY KEY,\n" +
                "username VARCHAR(255) NOT NULL,\n" +
                "password VARCHAR(255) NOT NULL);";
        if (!execute(userTableSql)) {
            System.out.println("Таблица users уже существует");
        }
    }

    private boolean execute(String sql) {
        try(Statement statement = connection.createStatement()) {
            statement.execute(sql);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean addMovie(Movie movie) {
        return true;
    }
}
