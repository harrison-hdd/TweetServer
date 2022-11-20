package service.utils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.UUID;

public class PasswordHash {
    private static final String algorithm = "PBKDF2WithHmacSHA1";
    private static final int numIterations = 8;

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {
        System.out.println(hash(UUID.randomUUID().toString(), "password"));
    }
    public static String hash(String salt, String password){
        try {
            return generateStrongPasswordHash(salt, password);
        }catch (Exception e){
            return null;
        }
    }
    private static String generateStrongPasswordHash(String saltStr, String password) throws NoSuchAlgorithmException, InvalidKeySpecException
    {

        char[] chars = password.toCharArray();
        byte[] salt = saltStr.getBytes(StandardCharsets.UTF_8);

        PBEKeySpec spec = new PBEKeySpec(chars, salt, numIterations, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance(algorithm);
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return toHex(hash);
    }

    private static String toHex(byte[] array) throws NoSuchAlgorithmException
    {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
        {
            return String.format("%0"  +paddingLength + "d", 0) + hex;
        }else{
            return hex;
        }
    }
}
