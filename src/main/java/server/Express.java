package server;

import client.Message;
import client.Request;
import client.Response;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.logging.Logger;

public class Express {
    ArrayList<Middleware> middlewares = new ArrayList<>();
    ServerSocketChannel channel;
    public static final Logger log = Logger.getLogger(Express.class.getName());
    RequestHandler requestHandler = new RequestHandler(10);
    final int SIZE = 1024;
    ByteBuffer buf = ByteBuffer.allocate(SIZE);


    public Express() {
        try {
            channel = ServerSocketChannel.open();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void use(Middleware middleware) {
        assert middleware != null;
        middlewares.add(middleware);
    }

    public void listen(int port) throws IOException, ClassNotFoundException {
        start(port);
    }

    public void listen() throws IOException, ClassNotFoundException {
        start(8888);
    }

    private void start(int port) throws IOException, ClassNotFoundException {
        try {
            channel.bind(new InetSocketAddress("localhost", port));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        System.out.println(String.format("Server started on port %s", port));
        while (true) {
            SocketChannel client = channel.accept();
            boolean skip = false;
            if (client != null) {
                client.read(buf);
                Request req = Message.deserialize(buf);
                Response res = new Response(client);
                requestHandler.add()
                for (int i = 0; i < middlewares.size(); i++) {
                    boolean result = middlewares.get(i).apply(req, res);
                    if (!result) {
                        skip = true;
                        break;
                    };
                }
                if (!skip) {
                    res.setStatus(200);
                    res.setMessage("Hello Andrey!");
                    res.send();
                }
            }
        }
    }
}
