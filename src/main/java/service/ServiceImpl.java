package service;

import models.Movie;
import repository.Repository;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ServiceImpl implements Service{
    private final Repository repository;
    private String helpInfo = "history : вывести последние 7 команд (без их аргументов)\n" +
            "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n";
    private boolean verbose = true;

    {
        Class<Service> cls = Service.class;
        helpInfo += Stream.of(cls.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(Help.class))
                .map(method -> method.getAnnotation(Help.class).help())
                .collect(Collectors.joining("\n"));
    }

    public ServiceImpl(Repository repository) {
        this.repository = repository;
    }

    @Override
    public String help() {
        return helpInfo;
    }

    @Override
    public String add(Movie movie) {
        repository.add(movie);
        return "фильм добавлен";
    }

    @Override
    public String info() {
        return repository.getInfoAboutCollection();
    }

    @Override
    public String updateById(Movie movie, int id) {
        repository.updateById(movie, id);
        return String.format("фильм с id=%s обновлен", id);
    }

    @Override
    @Help(help = "remove_by_id id : удалить элемент из коллекции по его id")
    public String removeById(int id) {
        repository.removeById(id);
        return String.format("фильм с id=%s удален", id);
    }

    @Override
    public String clear() {
        repository.clear();
        return "коллекция очищена";
    }

    @Override
    public String save() {
        try {
            repository.save();
        } catch (IOException e) {
            return "Невозможно сохранить коллекцию";
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public String addIfMin(Movie movie) {
        if (repository.addIfMin(movie)) {
            return "фильм добавлен";
        }
        return "Фильм не был добавлен";
    }

    @Override
    public String removeLower(Movie movie) {
        repository.removeLower(movie);
        return "фильмы удалены";
    }

    @Override
    public String maxByTotalBoxOffice() {
        Optional<Movie> movie = repository.maxByTotalBoxOffice();
        return movie.map(Movie::toString).orElse(null);
    }

    @Override
    public String countByTotalBoxOffice(Long target) {
        return repository.countByTotalBoxOffice(target).toString();
    }

    @Override
    public String filterContainsName(String name)
    {
        return repository.filterContainsName(name).toString();
    }

    @Override
    public String show() {
        return repository.show().toString();
    }

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }
}
