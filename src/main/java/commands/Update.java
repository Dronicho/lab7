package commands;

import config.Config;
import exceptions.StopReadingException;
import models.Movie;
import service.Service;

import java.util.Arrays;
import java.util.Scanner;

public class Update extends MovieCommand {
    public Update(Service service) {
        super(service);
        isFile = true;
    }

    @Override
    public String execute(String[] args, Movie movie) {
        return service.updateById(movie, Integer.parseInt(args[0]));
    }
}
