package commands;

import models.Movie;
import service.Service;

import java.util.Scanner;

public class FilterContainsName extends MovieCommand{

    public FilterContainsName(Service service) {
        super(service);
    }

    @Override
    public String execute(String[] args, Movie movie) {
        return service.filterContainsName(args[0]);
    }
}
