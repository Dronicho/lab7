import client.Auth;
import client.Request;
import client.Response;
import commands.Command;
import repository.PostgresOrm;
import repository.Repository;
import repository.RepositoryImpl;
import server.ConsoleReader;
import server.Express;
import server.Middleware;
import server.Server;
import service.Service;
import service.ServiceImpl;

import java.io.File;
import java.io.IOException;
import java.sql.Array;
import java.util.Scanner;

public class StartServer {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        File file = new File(args[0]);
//        if (!file.exists()) {
//            file.createNewFile();
//        }
//        Repository repository = new RepositoryImpl(file);
//        Service service = new ServiceImpl(repository);
//
//
//        Server server = new Server(service);
//        server.start();
        PostgresOrm db = PostgresOrm.instance;

        Express app = new Express();
        Middleware authMiddleware = (req, res) -> {
            Auth auth = req.getHeaders().getAuth();
            if (auth.getUsername().equals("Andrey") && auth.getPassword().equals("123456")) {
                return true;
            }
            res.setStatus(403);
            res.setMessage("Wrong username or password");
            res.send();
            return false;
        };

        app.use(authMiddleware);

        app.listen(8989);
    }
}
