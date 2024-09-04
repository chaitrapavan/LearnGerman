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
         System.out.println(registerStr);
        assertEquals(registerStr, strOne);
        String un = "chaitra";
        String pw = "pavan";
        String registerationStr = ui.registerUser(un, pw);
        System.out.println(registerationStr);
        assertEquals(registerationStr, strTwo);
    }

}
