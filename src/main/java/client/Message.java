package client;

import java.io.*;
import java.nio.ByteBuffer;

public class Message {
    static public <T> ByteBuffer serialize(T obj) {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();

        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(out)) {
            objectOutputStream.writeObject(obj);
            objectOutputStream.flush();
            return ByteBuffer.wrap(out.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            return ByteBuffer.allocate(256);
        }
    }

    static public <T> T deserialize(ByteBuffer ms) throws IOException, ClassNotFoundException {
        ms.flip();
        int limits = ms.limit();
        byte[] bytes = new byte[limits];
        ms.get(bytes, 0, limits);
        final ByteArrayInputStream in = new ByteArrayInputStream(bytes);

        ObjectInputStream objectIn = new ObjectInputStream(in);
        return (T) objectIn.readObject();
    }
}
