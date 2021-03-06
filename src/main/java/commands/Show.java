package commands;

import models.Movie;
import service.Service;

import java.util.Scanner;

public class Show extends MovieCommand {
    public Show(Service service) {
        super(service);
    }

    @Override
    public String execute(String[] args, Movie movie) {
        return service.show();
    }
}
