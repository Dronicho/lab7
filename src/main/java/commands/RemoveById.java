package commands;

import models.Movie;
import service.Service;

import java.util.Scanner;

public class RemoveById extends MovieCommand {
    public RemoveById(Service service) {
        super(service);
    }

    @Override
    public String execute(String[] args, Movie movie) {
        return service.removeById(Integer.parseInt(args[0]));
    }
}
