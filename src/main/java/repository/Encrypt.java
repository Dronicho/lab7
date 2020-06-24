package repository;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encrypt {
    static MessageDigest md;

    static {
        try {
            md = MessageDigest.getInstance("SHA-384");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public Encrypt() throws NoSuchAlgorithmException {
    }

    static String encrypt(String value) {
        byte[] bytes = md.digest(value.getBytes());
        BigInteger no = new BigInteger(1, bytes);
        StringBuilder hashtext = new StringBuilder(no.toString(16));

        while (hashtext.length() < 32) {
            hashtext.insert(0, "0");
        }
        return hashtext.toString();
    }
}
