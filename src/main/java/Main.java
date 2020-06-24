import client.Client;
import repository.Repository;
import repository.RepositoryImpl;
import server.Server;
import service.Service;
import service.ServiceImpl;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        File file = new File("test.txt");
        Repository repository = new RepositoryImpl(file);
        Service service = new ServiceImpl(repository);


        new Server(service).start();
        Client client = new Client();
        client.startListening();
    }

}
