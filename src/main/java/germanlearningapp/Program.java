package germanlearningapp;

import java.nio.file.Paths;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;

public class Program {

    public static void main(String[] args) {
        functionality func = new functionality();
        Scanner scanner = new Scanner(System.in);
        UserInterface ui = new UserInterface(func, scanner);

        System.out.println("German learning app");
        System.out.println("If you dont have an account, please create an account first");
        ui.auth();
//        ui.start();
    }
}
