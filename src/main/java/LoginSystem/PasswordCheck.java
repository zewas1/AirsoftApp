package LoginSystem;

import java.util.regex.Pattern;

public class PasswordCheck {
    public static boolean isValid(String password){
        final String passwordRegex = "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,40})";

        Pattern pat = Pattern.compile(passwordRegex);
        if (password == null)
            return false;
        return pat.matcher(password).matches();
    }
}