package client;

import models.Movie;

import java.io.Serializable;

public class Request implements Serializable {
    String[] args;
    Movie movie;
    String commandName;
    Headers headers;

    public Request(Headers headers, String[] args, Movie movie, String commandName) {
        this.args = args;
        this.movie = movie;
        this.commandName = commandName;
        this.headers = headers;
    }


    public String[] getArgs() {
        return args;
    }
    public String getCommandName() {
        return commandName;
    }
    public Movie getMovie() {
        return movie;
    }
    public Headers getHeaders() {
        return headers;
    }
}
