package client;

import java.io.Serializable;

public class Headers implements Serializable {
    Auth auth;
    String host;

    public Headers(Auth auth, String host) {
        this.auth = auth;
        this.host = host;
    }

    public Auth getAuth() {
        return auth;
    }

    public String getHost() {
        return host;
    }
}
