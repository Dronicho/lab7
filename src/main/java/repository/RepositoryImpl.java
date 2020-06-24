package repository;

import models.Movie;
import persistance.CsvEncoder;
import persistance.MovieDecoder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

public class RepositoryImpl implements Repository {
    private HashSet<Movie> movies;
    private File in;
    private LocalDateTime initTime;
    PostgresOrm db = PostgresOrm.instance;

    public RepositoryImpl(File in) throws FileNotFoundException {
        this.movies = load();
        this.in = in;
    }

    private HashSet<Movie> load() throws FileNotFoundException {
        initTime = LocalDateTime.now();
        return db.getAllMovies();
    }

    @Override
    public void add(Movie movie) {
        this.movies.add(movie);
    }

    @Override
    public void updateById(Movie movie, int id) {
        movies.removeIf(movie1 -> movie1.getId() == id);
        movie.setId(id);
        movies.add(movie);
        System.out.println("фильм обновлен");
    }

    @Override
    public void removeById(int id) {
        movies.removeIf(movie -> movie.getId() == id);
    }

    @Override
    public void clear() {
        movies.clear();
    }

    @Override
    public void save() throws IOException, IllegalAccessException {
        CsvEncoder csvEncoder = new CsvEncoder(in);
        csvEncoder.encode(movies.toArray());
    }

    @Override
    public boolean addIfMin(Movie movie) {
        Optional<Movie> minM = movies.stream().min(Movie::compareTo);
        if (minM.isPresent() && movie.compareTo(minM.get()) < 0) {
            movies.add(movie);
            return true;
        }
        return false;
    }

    @Override
    public void removeLower(Movie movie) {
        movies.removeIf(movie1 -> movie1.compareTo(movie) < 0);
    }

    @Override
    public Optional<Movie> maxByTotalBoxOffice() {
        return movies.stream().max(Movie::compareTo);
    }

    @Override
    public Long countByTotalBoxOffice(Long target) {
        return movies.stream().filter(movie -> movie.getTotalBoxOffice() == target).count();
    }

    @Override
    public HashSet<Movie> filterContainsName(String name) {
        return movies.stream().filter(movie -> movie.getName().contains(name)).collect(Collectors.toCollection(HashSet::new));
    }

    @Override
    public String getInfoAboutCollection() {
        return String.format("размер коллекции: %s\nдата инициализации: %s", movies.size(), initTime);
    }

    @Override
    public HashSet<Movie> show() {
        return movies;
    }
}
