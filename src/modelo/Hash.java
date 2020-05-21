package modelo;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author daniel migales puertas
 * 
 *
 */
public class Hash {

    public static byte[] hash(String message) {
        byte[] hash = null;
        try {
            byte[] data = message.getBytes();
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            hash = md.digest(data);
        } catch (NoSuchAlgorithmException ex) {
        }
        return hash;
    }
    
     public static String byteToHex(byte[] bytes) {
        BigInteger bi = new BigInteger(1, bytes);
        return String.format("%0" + (bytes.length << 1) + "X", bi);
    }

}
