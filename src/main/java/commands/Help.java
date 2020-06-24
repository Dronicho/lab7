package commands;

import models.Movie;
import service.Service;

import java.util.Scanner;

public class Help extends MovieCommand {

    public Help(Service service) {
        super(service);
    }

    @Override
    public String execute(String[] args, Movie movie) {
        return service.help();
    }
}
