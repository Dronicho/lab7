package commands;

import models.Movie;
import service.Service;

import java.util.Scanner;

public class MaxByTotalBoxOffice extends MovieCommand {
    public MaxByTotalBoxOffice(Service service) {
        super(service);
    }

    @Override
    public String execute(String[] args, Movie movie) {
        return service.maxByTotalBoxOffice();
    }
}
