package restaurant.externals;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Random;

public class HashPassword {

    private static final Random RANDOM = new SecureRandom();
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;

    public HashPassword(){}

    /**
     * Salt generator that generates random data whcih is then used
     * as additional input to a function that "hashes" a password.
     * @return 16-bit salt
     */
    public static byte[] getNextSalt(){
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        return salt;
    }

    /**
     * Hashing function that returns salted and hashed password.
     * Drawback - Password is destroyed, char[] is filled with zeros.
     * @param password the password to be hashed
     * @param salt     a 16 bytes salt, obtained with getNextSalt() method.
     * @return 16-bit salt
     */
    public static byte[] hashPassword(char[] password, byte[] salt){

        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        Arrays.fill(password, Character.MIN_VALUE);

        try{
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        }catch(NoSuchAlgorithmException | InvalidKeySpecException e){
            throw new AssertionError("Error while hashing a password: "+ e.getMessage(), e);
        }finally {
            spec.clearPassword();
        }
    }

    /**
     * Function that compares password and expectedHash or stored password,
     * and returns true if they are identical.
     * Drawback - Password is destroyed, char[] is filled with zeros.
     * @param password the password that is inserted
     * @param salt     a 16 bytes salt, obtained with getNextSalt() method.
     * @param expectedHash password that is stored in database.
     * @return 16-bit salt
     */
    public static boolean isPassword(char[] password, byte[] salt, byte[] expectedHash){
        byte[] pwdHash = hashPassword(password, salt);
        Arrays.fill(password, Character.MIN_VALUE);
        if (pwdHash.length != expectedHash.length) return false;
        for (int i = 0; i < pwdHash.length; i++) {
            if (pwdHash[i] != expectedHash[i]) return false;
        }
        return true;
    }

    /**
     * Method converts string to array of chars.
     * @param str string that needs to be converted.
     * @return array of chars
     */
    public char[] strToChar(String str){
        char[] chArray = new char[str.length()];
        for(int i = 0; i < str.length(); i++){
            chArray[i] = str.charAt(i);
        }
        return chArray;
    }
}
