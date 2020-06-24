package service;

import models.Movie;

/**
 * Вообще был задуман для реализации логики работы команд, но не получилось,
 * поэтому просто дублирует методы из Repository и добавляет описание:)
 * Здесь можно добавить дополнительную логику например кэширование или проверки
 */
public interface Service {
    @Help(help = "add {element} : добавить новый элемент в коллекцию")
    String add(Movie movie);
    @Help(help = "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)")
    String info();
    @Help(help = "update id {element} : обновить значение элемента коллекции, id которого равен заданному")
    String updateById(Movie movie, int id);
    @Help(help = "remove_by_id id : удалить элемент из коллекции по его id")
    String removeById(int id);
    @Help(help = "clear : очистить коллекцию")
    String clear();
    String save();
    @Help(help = "add_if_min {element} : добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции")
    String addIfMin(Movie movie);
    @Help(help = "remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный")
    String removeLower(Movie movie);
    @Help(help = "max_by_total_box_office : вывести любой объект из коллекции, значение поля totalBoxOffice которого является максимальным")
    String maxByTotalBoxOffice();
    @Help(help = "count_by_total_box_office totalBoxOffice : вывести количество элементов, значение поля totalBoxOffice которых равно заданному")
    String countByTotalBoxOffice(Long target);
    @Help(help = "filter_contains_name name : вывести элементы, значение поля name которых содержит заданную подстроку")
    String filterContainsName(String name);
    @Help(help = "help : вывести справку по доступным командам")
    String help();
    @Help(help = "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении")
    String show();
}
