package germanlearningapp;

import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserInterfaceTest {

    functionality func = new functionality();
    Scanner scanner = new Scanner(System.in);
    UserInterface ui = new UserInterface(func, scanner);
    Map<String, String> users = new HashMap<>();

    @Test
    public void testRegisterUser() {
        ui.createHashMap();
        String strOne = "Username already exists!";
        String strTwo = "Registration successful.";
        String u = "pp";
        String p = "pandappa";
        String registerStr = ui.registerUser(u, p);
        assertEquals(registerStr, strOne);
//        String un = "chai";
//        String pw = "pavan";
//        String registerationStr = ui.registerUser(un, pw);
//        assertEquals(registerationStr, strTwo);
    }

    @Test
    public void testLoginUser() {
        ui.createHashMap();
        String strOne = "Invalid password!";
        String u = "pp";
        String p = "pa";
        String loginStr = ui.loginUser(u, p);
        assertEquals(loginStr, strOne);
    }
}
