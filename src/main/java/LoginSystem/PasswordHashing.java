package LoginSystem;
import LoginSystem.BCrypt.BCrypt;


public class PasswordHashing {
    public static String hashPassword(String plainPassword){
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }
    public static boolean checkPassword (String plainPassword, String hashedPassword){
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}