package commands;

import models.Movie;
import service.Service;

import java.util.Scanner;

public class Exit extends MovieCommand {
    public Exit(Service service) {
        super(service);
    }

    @Override
    public String execute(String[] args, Movie movie) {
        System.out.println("Завершение работы");
        System.exit(0);
        return "";
    }
}
