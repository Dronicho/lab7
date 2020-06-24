package repository;

import exceptions.CheckFailed;
import exceptions.StopReadingException;
import models.*;
import rules.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Function;


/**
 * класс для считывания Movie из потока
 */
public class MovieReader {
    private Scanner scanner;
    private boolean verbose;

    public MovieReader() {
    }

    /**
     * метод для считывания поля, с возможностью повторного ввода, если пользователь ошибся
     *
     * @param fieldName подсказка для пользователя
     * @param callback  метод, которым считываем поле)\
     * @return возвращает значение приведенное к нужному типу
     * @throws StopReadingException выбрасывается, если пользователь ввел break, остановка считывания
     */
    private <T> T readField(String fieldName, Function<String, T> callback, Rule... rules) throws StopReadingException {
        if (verbose) {
            System.out.println("Введите " + fieldName);
        }
        String res;
        T val;
        while (true) {
            try {
                res = scanner.nextLine();
                if (res.equals("break")) {
                    throw new StopReadingException("Отмена");
                }
                for (Rule rule : rules) {
                        rule.check(res);
                }
                val = callback.apply(res);
                break;
            } catch (CheckFailed e) {
                if (verbose) {
                    System.out.println(e.getMessage());
                }
            } catch (IllegalArgumentException | NoSuchElementException | DateTimeParseException e) {
                if (verbose) {
                    System.out.println("Попробуйте еще раз");
                }
            }
        }
        return val;
    }

    /**
     * @param scanner Сканнер с которого считывать
     * @param verbose елси true выводит подсказки для пользователя
     * @return Movie считанные из потока
     * @throws StopReadingException
     */
    public Movie read(Scanner scanner, Boolean verbose) throws StopReadingException {
        this.scanner = scanner;
        this.verbose = verbose;

        return new Movie(
                readName(),
                readCoordinates(),
                readOscars(),
                readTotalBoxOffice(),
                readUsaBoxOffice(),
                readGenre(),
                readPerson()
        );
    }
    // Далее методы для считываения конкретного поля

    public String readName() throws StopReadingException {
        return readField("название фильма", (e) -> e, new NotEmpty()); // костыль
    }

    public Coordinates readCoordinates() throws StopReadingException {
        return new Coordinates(
                readField("Х координата", Float::parseFloat, new Max(961)),
                readField("Y координата", Long::parseLong, new NotNull())
        );
    }

    public Long readOscars() throws StopReadingException {
        return readField("количество оскаров", Long::parseLong, new NotNull(), new Min(0));
    }

    public int readTotalBoxOffice() throws StopReadingException {
        return readField("сборы в мире", Integer::parseInt, new Min(0));
    }

    public int readUsaBoxOffice() throws StopReadingException {
        return readField("сборы в США", Integer::parseInt, new Min(0));
    }

    public MovieGenre readGenre() throws StopReadingException {
        String fieldName = String.format("жанр: %s", Arrays.toString(MovieGenre.values()));
        return readField(fieldName, MovieGenre::valueOf, new NotNull());
    }

    public Person readPerson() throws StopReadingException {
        String fieldName = String.format("цвет: %s", Arrays.toString(Color.values()));
        return new Person(
                readField("имя оператора", (e) -> e, new NotEmpty()),
                readField("дата рождения", LocalDateTime::parse, new NotNull()),
                readField(fieldName, Color::valueOf, new NotNull()),
                new Location(
                        readField("X страны", Integer::parseInt),
                        readField("Y страны", Long::parseLong),
                        readField("название страны", (e) -> e, new NotNull())
                )
        );
    }

    public void setScanner(Scanner fileScanner) {
        scanner = fileScanner;
    }
}
