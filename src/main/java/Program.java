
import java.util.Scanner;

public class Program {

    public static void main(String[] args) {
        GermanToEnglish translation = new GermanToEnglish();
        Scanner scanner = new Scanner(System.in);
        UserInterface ui = new UserInterface(translation, scanner);
        ui.start("John");
    }
}
