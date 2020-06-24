package client;

import commands.Exit;
import commands.MovieCommand;
import config.Config;
import exceptions.StopReadingException;
import models.Movie;
import repository.MovieReader;
import service.Service;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Client {
    private final InetAddress address = InetAddress.getByName("localhost");
    private Socket channel;
    private ByteBuffer recieveBuf = ByteBuffer.allocate(1024 * 5);
    private Scanner scanner = new Scanner(System.in);
    private ArrayList<String> history = new ArrayList<>();
    protected MovieReader movieReader = new MovieReader();
    private Service service;
    private final Request handshake = new Request(null, null, null, "handshake");


    public Client() throws IOException, InterruptedException {
        try {
            channel = new Socket(address, 8989);
            channel.getOutputStream().write(Message.serialize(handshake).array());
            startListening();
        } catch (ConnectException e) {
            System.out.println("Server cannot be reached");
        }
    }

    public void startListening() {
        if (Config.isVerbose) {
            System.out.println("Введите команду");
        }
        while (scanner.hasNext()) {
            String cmd = scanner.nextLine();
            execute(cmd);
            if (Config.isVerbose) {
                System.out.println("Введите команду");
            }
        }
    }

    private void execute(String command) {
        String[] args = command.split(" ");
        if (args[0].equals("execute_script")) {
            Scanner oldScanner = scanner;
            try {
                scanner = new Scanner(new FileReader(new File(args[1])));
                Config.isVerbose = false;
                startListening();
                scanner = oldScanner;
                Config.isVerbose = true;
            } catch (IOException e) {
                System.out.println("Скрипт не найден");
            }
            return;
        } else if (args[0].equals("history")) {
            if (history.size() < 7) {
                System.out.println(history);
            } else {
                System.out.println(history.subList(history.size() - 7, history.size()));
            }
            return;
        }
        try {
            MovieCommand cmd = (MovieCommand) Class.forName("commands." + translateCommand(args[0]))
                    .getConstructor(Service.class).newInstance(service);
            Movie movie = null;

            if (cmd instanceof Exit) {
                System.exit(0);
            }

            if (cmd.isFile()) {
                movie = movieReader.read(scanner, Config.isVerbose);
            }
            Auth auth = new Auth("Andrey", "123456");
            Headers headers = new Headers(auth, channel.getInetAddress().toString());
            Request request = new Request(headers, args, movie, args[0]);
            this.send(request);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException e) {
            System.out.println("Такой команды нет");
        } catch (IllegalAccessException | InstantiationException | StopReadingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(Request request) throws IOException, ClassNotFoundException {
        final ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        try {
            channel = new Socket(address, 8989);
        } catch (ConnectException e) {
            System.out.println("Server cannot be reached");
            return;
        }

        try (ObjectOutputStream out = new ObjectOutputStream(byteOut)) {
            out.writeObject(request);
        }
        SocketChannel socketChannel = channel.getChannel();
        channel.getOutputStream().write(Message.serialize(request).array());
//        channel.getOutputStream().flush();

        byte[] responseBytes = new byte[1024 * 5];
        int respLength = channel.getInputStream().read(responseBytes);
        recieveBuf.put(responseBytes);
        Response response = Message.deserialize(recieveBuf);
        System.out.println("Server response: \n" + response.message);
        recieveBuf.clear();
    }

    private String translateCommand(String cmd) {
        return Arrays.stream(cmd.split("_"))
                .map(s -> s.substring(0, 1).toUpperCase() + s.substring(1))
                .collect(Collectors.joining());
    }
}
