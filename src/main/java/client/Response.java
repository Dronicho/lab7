package client;

import java.io.IOException;
import java.io.Serializable;
import java.nio.channels.SocketChannel;

public class Response implements Serializable {
    int status;
    String message;
    private final transient SocketChannel client;

    public Response(SocketChannel client) {
        this.client = client;
        status = 0;
        message = "";
    }

    public void send() {
        assert status != 0;
        assert !message.equals("");

        try {
            client.write(Message.serialize(this));
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
