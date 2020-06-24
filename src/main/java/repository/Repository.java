package repository;

import models.Movie;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;

/**
 * слой работы с хранилищем данных
 */
public interface Repository {
    /**
     * добвляет Movie в коллекцию
     * @param movie
     */
    void add(Movie movie);

    /**
     * Обновляет movie по заданному id
     * @param movie
     * @param id
     */
    void updateById(Movie movie, int id);

    /**
     * Удаляет элемент по id
     * @param id
     */
    void removeById(int id);

    /**
     * очищает коллекцию
     */
    void clear();

    /**
     * сохряняет колекцию в файл
     * @throws IOException
     * @throws IllegalAccessException
     */
    void save() throws IOException, IllegalAccessException;

    /**
     * добавляет movie, если его значение totalBoxOffice меньше наименьшего в коллекции
     * @param movie
     * @return
     */
    boolean addIfMin(Movie movie);

    /**
     * Удаляет все объекты, totalBoxOffice которых меньше заданного
     * @param movie
     */
    void removeLower(Movie movie);

    /**
     * Возвращает объект с наибольшим totalBoxOffice
     * @return
     */
    Optional<Movie> maxByTotalBoxOffice();

    /**
     * считает количество объектов, totalBoxOffice которых равен заданному
     * @return
     */
    Long countByTotalBoxOffice(Long totalBoxOffice);

    /**
     * Возвращает объекты, name которых содержит заданную строку
     * @param name
     * @return
     */
    HashSet<Movie> filterContainsName(String name);

    /**
     * Возвращает информацию о коллекции(кол-во объектов, дату инициализации)
     * @return
     */
    String getInfoAboutCollection();

    /**
     * Возвращаяет всю коллекцию
     */
    HashSet<Movie> show();
}
