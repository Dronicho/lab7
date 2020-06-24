package commands;

import config.Config;
import exceptions.StopReadingException;
import models.Movie;
import repository.MovieReader;
import service.Service;

import java.util.Scanner;

public class AddIfMin extends MovieCommand{
    public AddIfMin(Service service) {
        super(service);
        this.isFile = true;
    }

    @Override
    public String execute(String[] args, Movie movie) {
        return service.addIfMin(movie);
    }
}
