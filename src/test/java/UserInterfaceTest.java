package germanlearningapp;

import java.util.Scanner;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserInterfaceTest {
    @Test
    public void testAuthentication() {
        functionality func = new functionality();
        Scanner scanner = new Scanner(System.in);
        UserInterface ui = new UserInterface(func, scanner);
        
        
    }

}
