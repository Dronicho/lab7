package commands;

import models.Movie;
import service.Service;

import java.util.Scanner;

public class CountByTotalBoxOffice extends MovieCommand {
    public CountByTotalBoxOffice(Service service) {
        super(service);
    }

    @Override
    public String execute(String[] args, Movie movie) { 
        return service.countByTotalBoxOffice(Long.parseLong(args[0]));
    }
}
