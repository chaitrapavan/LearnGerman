package germanlearningapp;

import java.util.Scanner;

public class Program {

    public static void main(String[] args) {
        functionality func = new functionality();
        Scanner scanner = new Scanner(System.in);
        UserInterface ui = new UserInterface(func, scanner);
  
        System.out.println("German learning app");
        System.out.println("If you have not registered yet, Please register yourself!");
        ui.authentication();
    }
}
