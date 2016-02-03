package restaurant.externals;

import java.util.Arrays;

/**
 * Created by Misa on 03.02.2016..
 */
public class HashPasswordMainTest {

    public static void main(String[] args){
         HashPassword hshPass;
        byte[] salt;
         byte[] hashedPassword;
        salt = new byte[16];
        hashedPassword = new byte[256];
        hshPass = new HashPassword();
        salt = hshPass.getNextSalt();
        char[] pass = new char[7];
        pass[0] = 'l';
        pass[1] = 'o';
        pass[2] = 'z';
        pass[3] = 'i';
        pass[4] = 'n';
        pass[5] = 'k';
        pass[6] = 'a';
        System.out.println("Password: " + pass);
        try {
            hashedPassword = hshPass.hashPassword(pass, salt);
            System.out.println("Salt: " + salt);
            System.out.println("Hashed password: " + hashedPassword);
            System.out.println("Password: " + pass);
            salt = hshPass.getNextSalt();
            System.out.println("Salt: " + salt);
            pass[0] = 'l';
            pass[1] = 'o';
            pass[2] = 'z';
            pass[3] = 'i';
            pass[4] = 'n';
            pass[5] = 'k';
            pass[6] = 'a';
            System.out.println("Password: " + pass);
            boolean isSame = hshPass.isPassword(pass, salt, hashedPassword);
            System.out.println("Are those same: " + isSame);
        }catch(AssertionError e){
            System.out.println(e.getMessage());
        }


    }
}
