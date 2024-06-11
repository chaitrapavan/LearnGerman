
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

public class UserInterface {

    private GermanToEnglish germanToEnglish;
    private Scanner scanner;
    private ArrayList<String> readFromStoredfile;
    private ArrayList<String> readLearnedList;

    public UserInterface(GermanToEnglish germanToEnglish, Scanner scanner) {
        this.germanToEnglish = germanToEnglish;
        this.scanner = scanner;
        this.readFromStoredfile = new ArrayList<>();
        this.readLearnedList = new ArrayList<>();
    }
    boolean flag = false;
    int number = 0;

    public void endProgram() {
        try ( Scanner scanner = new Scanner(Paths.get("learnedWords.txt"))) {
            while (scanner.hasNextLine()) {
                String storedWord = scanner.nextLine();
                this.readLearnedList.add(storedWord);
            }
            if (this.readLearnedList.isEmpty()) {
                System.out.println("You have not learned any words");
            } else {
                System.out.println("Learned words are: " + this.readLearnedList);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start(String name) {
        this.germanToEnglish.germanWords();

        while (true) {
            if (flag == false) {
                System.out.println("Learn German");
                System.out.println("How many words do you want to learn " + name + "?" + " Type end if you want to end");
                String input = scanner.nextLine();
                input = input.trim();
                //getting a valid integer input
                try {
                    if (input.equals("end")) {
                        this.endProgram();
                        break;
                    }
                    number = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println("Please give a valid number");
                }

                //calling getNewList(number) method to get results
                List<Dictionary> list = this.germanToEnglish.getNewList(number);

                try ( Scanner scanner = new Scanner(Paths.get("learnedWords.txt"))) {
                    this.readFromStoredfile.clear();
                    while (scanner.hasNextLine()) {
                        String storedWord = scanner.nextLine();
                        this.readFromStoredfile.add(storedWord);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (number > 0 && number <= list.size()) {
                    //checking the list is not empty, if it is not empty, get objects
                    if (!list.isEmpty()) {

                        for (int i = 0; i < list.size(); i++) {
//                        System.out.println("word to search: " + list.get(i).getGermanword());
//                        System.out.println(this.readFromStoredfile);
                            if (!this.readFromStoredfile.contains(list.get(i).getGermanword())) {
                                System.out.println("German word: " + list.get(i).getGermanword());

                                String enter = scanner.nextLine();
                                if (enter.equals("end")) {
                                    flag = true;
                                    this.endProgram();
                                    break;
                                }
                                System.out.println("Meaning in english: " + list.get(i).gettranslatedWord());
                                System.out.println("Example: " + list.get(i).getExample());
                                String enter1 = scanner.nextLine();
                                if (enter1.equals("end")) {
                                    flag = true;
                                    this.endProgram();
                                    break;
                                }

                                try ( BufferedWriter writer = new BufferedWriter(new FileWriter("learnedWords.txt", true))) {
                                    writer.append(list.get(i).getGermanword());
                                    writer.newLine();

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                System.out.println("You have learned this word: " + list.get(i).getGermanword());
                                System.out.println("");
                            }
                        }
                    }

                } else if (number > 0 && number > list.size()) {
                    List<Dictionary> copiedList = this.germanToEnglish.getCopiedList(number);
                    System.out.println("The App does not contain that much of words!, below are the words in the list.");
                    for (int i = 0; i < copiedList.size(); i++) {
                        if (!this.readFromStoredfile.contains(list.get(i).getGermanword())) {
                            System.out.println("German word: " + list.get(i).getGermanword());
                            String enter = scanner.nextLine();
                            System.out.println("Meaning in english: " + list.get(i).gettranslatedWord());
                            System.out.println("Example: " + list.get(i).getExample());
                            String enter1 = scanner.nextLine();
                            this.readFromStoredfile.add(list.get(i).getGermanword());
                            try ( BufferedWriter writer = new BufferedWriter(new FileWriter("learnedWords.txt", true))) {
                                //writer.append(' ');
                                writer.append(list.get(i).getGermanword());
                                writer.newLine();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            System.out.println("You have learned this word: " + list.get(i).getGermanword());
                        }
                    }
                    System.out.println("You have learned everything");
                    break;
                }

            }
            if (flag == true) {
                break;
            }
        }
    }
}
