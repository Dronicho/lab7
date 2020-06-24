//package controller;
//
//import commands.Command;
//import config.Config;
//import service.Help;
//import service.Service;
//import service.ServiceImpl;
//
//import javax.imageio.IIOException;
//import java.io.File;
//import java.io.FileReader;
//import java.io.IOException;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Scanner;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//public class ControllerImpl implements Controller{
//    /**
//     * ссылка на сервис, который выполняет команды
//     */
//    private final Service service;
//    /**
//     * сканнер для считывания команд(Из System.in или из файла
//     */
//    private Scanner scanner;
//    /**
//     * история)
//     */
//    private ArrayList<String> history = new ArrayList<>();
//
//    public ControllerImpl(Service service) {
//        this.service = service;
//        this.scanner = new Scanner(System.in);
//    }
//
//    @Override
//    public void startListening() {
//        if (Config.isVerbose) {
//            System.out.println("Введите команду");
//        }
//        while (scanner.hasNext()) {
//            execute(scanner.nextLine());
//            if (Config.isVerbose) {
//                System.out.println("Введите команду");
//            }
//        }
//    }
//
//    @Override
//    public void execute(String command) {
//        String[] args = command.split(" ");
//        if (args[0].equals("execute_script")) {
//            Scanner oldScanner = scanner;
//            try {
//                scanner = new Scanner(new FileReader(new File(args[1])));
//                Config.isVerbose = false;
//                startListening();
//                scanner = oldScanner;
//                Config.isVerbose = true;
//            } catch (IOException e) {
//                System.out.println("Скрипт не найден");
//            }
//            return;
//        } else if(args[0].equals("history")) {
//            if (history.size() < 7) {
//                System.out.println(history);
//            } else {
//                System.out.println(history.subList(history.size()-7, history.size()));
//            }
//            return;
//        }
//        try {
//            Command cmd = (Command) Class.forName("commands." + translateCommand(args[0]))
//                    .getConstructor(Service.class, Scanner.class).newInstance(service, scanner);
//            try {
//                cmd.execute(Arrays.copyOfRange(args, 1, args.length));
//            } catch (IllegalArgumentException e) {
//                cmd.execute(args);
//            }
//            history.add(args[0]);
//        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException e) {
//            System.out.println("Такой команды нет");
//        } catch (IllegalAccessException | InstantiationException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * преобразует команды из snake_case в camelCase
//     *
//     * @param cmd команда
//     * @return
//     */
//    private String translateCommand(String cmd) {
//        return Arrays.stream(cmd.split("_"))
//                .map(s -> s.substring(0, 1).toUpperCase() + s.substring(1))
//                .collect(Collectors.joining());
//    }
//}
