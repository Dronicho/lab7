package server;

import client.Request;
import client.Response;

@FunctionalInterface
public interface Middleware {
    boolean apply(Request req, Response res);
}
