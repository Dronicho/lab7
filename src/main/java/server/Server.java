package server;

import client.Message;
import client.Request;
import client.Response;
import commands.Command;
import commands.MovieCommand;
import models.Movie;
import service.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Server extends Thread{
    InetSocketAddress address = new InetSocketAddress("localhost", 8989);
    ServerSocketChannel channel = ServerSocketChannel.open();
    Service service;
        final int SIZE = 1024 * 5;

    public Server(Service service) throws IOException {
        channel.bind(address);
        channel.configureBlocking(false);
        this.service = service;
    }

    public String save() {
        try {
            service.save();
            return "коллекция сохранена";
        } catch (Exception e) {
            return "произошла ошибка";
        }
    }

    @Override
    public void run() {
        boolean running = true;
        try {
            System.out.println(String.format("server started on %s", channel.getLocalAddress()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);
        while (channel.isOpen()) {
            try {
                ByteBuffer buf = ByteBuffer.allocate(SIZE);
                SocketChannel socketAddress = channel.accept();
                if (socketAddress != null) {
                    socketAddress.read(buf);
                    Request req = Message.deserialize(buf);
                    System.out.println(req.getCommandName());
                    buf.clear();
                    if (req.getCommandName().equals("handshake")) {
                        System.out.println(String.format("User %s connected", socketAddress));
                    } else {
                        System.out.println(String.format("Request from %s: \n\tcommand: %s", socketAddress, req.getCommandName()));
                    }
                    Response response = processRequest(req);
                    socketAddress.write(Message.serialize(response));
                    socketAddress.close();
                }
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                long end = System.currentTimeMillis() + 500;

                while((System.currentTimeMillis()) <= end) {
                    if (bufferedReader.ready()) {
                        String cmd = bufferedReader.readLine();
                        if (cmd.equals("save")) {
                            try {
                                service.save();
                                System.out.println("Колекция сохранена");
                            } catch (Exception e) {
                                System.out.println("ошибка");
                            }
                        }
                    }
                }

            } catch (IOException | ClassNotFoundException | InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
                e.printStackTrace();
            }

        }
    }

    private Response processRequest(Request request) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        if (request.getCommandName().equals("handshake")) {
            return new Response(null);
        }

        Command cmd = (Command) Class.forName("commands." + translateCommand(request.getCommandName()))
                .getConstructor(Service.class).newInstance(service);
        Movie movie = request.getMovie();
        if (movie != null) {
            movie.setId(++Movie.maxId);
        }
        String msg = cmd.execute(request.getArgs(), request.getMovie());
        return new Response(null);
    }

    /**
     * преобразует команды из snake_case в camelCase
     *
     * @param cmd команда
     * @return
     */
    private String translateCommand(String cmd) {
        return Arrays.stream(cmd.split("_"))
                .map(s -> s.substring(0, 1).toUpperCase() + s.substring(1))
                .collect(Collectors.joining());
    }
}
