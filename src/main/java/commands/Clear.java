package commands;

import models.Movie;
import service.Service;

import java.util.Scanner;

public class Clear extends MovieCommand{
    public Clear(Service service) {
        super(service);
    }

    @Override
    public String execute(String[] args, Movie movie) {
        return service.clear();
    }
}
