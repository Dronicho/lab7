package commands;

import models.Movie;
import service.Service;

import java.util.Scanner;

public class Info extends MovieCommand {
    public Info(Service service) {
        super(service);
    }

    @Override
    public String execute(String[] args, Movie movie) {
        return service.info();
    }
}
