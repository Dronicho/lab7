package commands;

import models.Movie;

public interface Command {
    String execute(String[] args, Movie movie);
}
