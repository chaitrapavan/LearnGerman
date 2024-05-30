
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class UserInterface {

    private GermanToEnglish germanToEnglish;
    private Scanner scanner;

    public UserInterface(GermanToEnglish germanToEnglish, Scanner scanner) {
        this.germanToEnglish = germanToEnglish;
        this.scanner = scanner;
    }

    public void start() {
        this.germanToEnglish.germanWords();
        List<Dictionary> mainList = this.germanToEnglish.readFile();

        while (true) {
            System.out.println("Learn German");
            System.out.println("How many words do you want to learn?");
            String input = scanner.nextLine();
            input = input.trim();
            int number = 0;
            //getting a valid integer input
            try {
                number = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please give a valid number");
            }

            //calling getNewList(number) method to get results
            List<Dictionary> list = this.germanToEnglish.getNewList(number);

            if (number > 0 && number <= list.size()) {
                //checking the list is not empty, if it is not empty, get objects
                if (!list.isEmpty()) {
                    for (int i = 0; i < list.size(); i++) {
                        System.out.println("German word: " + list.get(i).getGermanword());
                        String enter = scanner.nextLine();
                        System.out.println("Meaning in english: " + list.get(i).gettranslatedWord());
                        System.out.println("Example: " + list.get(i).getExample());
                        String enter1 = scanner.nextLine();
                    }
                } else {
                    System.out.println("You have learned everything");
                    break;
                }

            } else if (number > 0 && number > list.size()) {
                List<Dictionary> copiedList = this.germanToEnglish.getCopiesList(number);
                System.out.println("The App does not contain that much of words!, below are the words in the list.");
                for (int i = 0; i < copiedList.size(); i++) {
                    System.out.println("German word: " + list.get(i).getGermanword());
                    String enter = scanner.nextLine();
                    System.out.println("Meaning in english: " + list.get(i).gettranslatedWord());
                    System.out.println("Example: " + list.get(i).getExample());
                    String enter1 = scanner.nextLine();
                }
                System.out.println("You have learned everything");
                break;
            }

        }

    }
}
