package persistance;

import models.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.*;

/**
 * класс для восстановления коллекции movies из файла
 */
public class MovieDecoder {
    /**
     * файл для считывания
     */
    private final File in;

    public MovieDecoder(File in) {
        this.in = in;
    }

    /**
     * декодирование файла
     * @return коллекция movies
     * @throws FileNotFoundException
     */
    public HashSet<Movie> decode() throws FileNotFoundException {
        FileReader reader = new FileReader(in);
        Scanner scanner = new Scanner(reader);
        HashSet<Movie> movies = new HashSet<>();
        int broken = 0;
        int maxId = 0;
        try {
            scanner.nextLine(); // skip header
        } catch (NoSuchElementException e) {
            Movie.maxId = maxId;
            scanner.close();
            return movies;
        }
        while (scanner.hasNext()) {
            String[] args = scanner.nextLine().split(";");
            try {
                Movie movie = new Movie(
                        Integer.parseInt(args[0]),
                        args[1],
                        new Coordinates(Float.parseFloat(args[2]), Long.parseLong(args[3])),
                        LocalDateTime.parse(args[4]),
                        Long.parseLong(args[5]),
                        Long.parseLong(args[6]),
                        Long.parseLong(args[7]),
                        MovieGenre.valueOf(args[8]),
                        new Person(
                                args[9],
                                LocalDateTime.parse(args[10]),
                                Color.valueOf(args[11]),
                                new Location(Integer.parseInt(args[12]), Long.parseLong(args[13]), args[14])
                        )
                );
                maxId = Math.max(maxId, movie.getId());
                movies.add(movie);
            } catch (Exception e) {
                broken++;
            }

        }
        Movie.maxId = maxId;
        scanner.close();
        if (broken > 0) {
            System.out.println("Поврежденных записей:" + broken);
        }
        return movies;
    }
}
