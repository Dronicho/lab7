package commands;

import config.Config;
import exceptions.StopReadingException;
import models.Movie;
import service.Service;

import java.util.Scanner;

public class RemoveLower extends MovieCommand {
    public RemoveLower(Service service) {
        super(service);
        isFile = true;
    }

    @Override
    public String execute(String[] args, Movie movie) {
        return service.removeLower(movie);
    }
}
