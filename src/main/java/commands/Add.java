package commands;

import config.Config;
import exceptions.StopReadingException;
import models.Movie;
import service.Service;

import java.util.Scanner;

public class Add extends MovieCommand {

    public Add(Service service) {
        super(service);
        this.isFile = true;
    }

    @Override
    public String execute(String[] args, Movie movie) {
        return service.add(movie);
    }
}
