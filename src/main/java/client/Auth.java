package client;

import java.io.Serializable;

public class Auth implements Serializable {
    final String username;
    final String password;

    public Auth(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
