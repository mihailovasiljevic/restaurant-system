package restaurant.externals;

import java.util.Arrays;

import restaurant.server.entity.User;

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
        
        
        User systemMenager1 = new User();
        
        salt = new byte[16];
        hashedPassword = new byte[256];
        salt = HashPassword.getNextSalt();
        systemMenager1.setSalt(salt);
    	hashedPassword = new byte[256];
    	systemMenager1.setPassword(hashedPassword);

        boolean equal = true;
        for(int i = 0; i < salt.length; i++){
        	if(salt[i] != systemMenager1.getSalt()[i]){
        		equal = false;
        		break;
        	}
        }
        System.out.println("[SALTS ARE EQUAL ? ]" + equal);
        char[] pass2 = {'m','a','r','k','o'};
        hashedPassword = HashPassword.hashPassword(pass, systemMenager1.getSalt());
        systemMenager1.setPassword(hashedPassword);
        System.out.println("[SALT]: " + salt);
        System.out.println("[SALT-USER]: " + systemMenager1.getSalt());
        System.out.println("[HASHEDPASWORD]: " + hashedPassword);
        System.out.println("[HASHEDPASWORD-USER]: " + systemMenager1.getPassword()); 
        equal = true;
        for(int i = 0; i < hashedPassword.length; i++){
        	if(hashedPassword[i] != systemMenager1.getPassword()[i]){
        		equal = false;
        		break;
        	}
        }
        
        System.out.println("[PASSWORDS ARE EQUAL ? ]" + equal);
        if(HashPassword.isPassword(pass, systemMenager1.getSalt(), systemMenager1.getPassword())){
        	System.out.println("[PASSWORD SUCCESS ]: "+systemMenager1.getPassword());
        }
        else{
        	hashedPassword = new byte[256];
        	systemMenager1.setPassword(hashedPassword);
        }

    }
}
