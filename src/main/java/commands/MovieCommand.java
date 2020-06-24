package commands;

import repository.MovieReader;
import service.Service;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

abstract public class MovieCommand implements Command{
    protected Service service;
    protected boolean isFile = false;

    public MovieCommand(Service service) {
        this.service = service;
    }

    public boolean isFile() {
        return isFile;
    }
}
