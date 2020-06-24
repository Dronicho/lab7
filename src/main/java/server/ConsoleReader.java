package server;

import service.Service;

import java.util.Scanner;

public class ConsoleReader extends Thread {
    Service service;

    public ConsoleReader(Service service) {
        this.service = service;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if (scanner.nextLine().equals("save")) {
                service.save();
                System.out.println("коллекция сохранена");
            }
        }
    }
}
