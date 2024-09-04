package germanlearningapp;

import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class UserInterface {

    private functionality functionality;
    private Scanner scanner;
    private ArrayList<String> learnedWords;
    private Map<String, String> users;
    private boolean flag;

    public UserInterface(functionality func, Scanner scanner) {
        this.functionality = func;
        this.scanner = scanner;
        this.learnedWords = new ArrayList<>();
        this.users = new HashMap<>();
        this.flag = false;
    }

    public void authentication() {
        while (true) {
            if (flag == false) {
                System.out.println("Please choose an action");
                System.out.println("1 - Register");
                System.out.println("2 - Login");
                System.out.println("3 - Quit");
                String input = scanner.nextLine();
                if (input.equals("1")) {
                    System.out.print("Enter your username:");
                    String username = scanner.nextLine();
                    username = username.toLowerCase().trim();
                    System.out.print("Enter your password:");
                    String password = scanner.nextLine();
                    password = password.toLowerCase().trim();
                    String statement = registerUser(username, password);
                    System.out.println(statement);
                } else if (input.equals("2")) {
                    System.out.print("Enter your username:");
                    String username = scanner.nextLine();
                    username = username.toLowerCase().trim();

                    String statement = loginUser(username);
                    System.out.println(statement);
                    loginUser(username);
                } else if (input.equals("3")) {
                    break;
                }
            } else {
                break;
            }
        }
    }

    public String registerUser(String uname, String pword) {
        createHashMap();
        if (users.containsKey(uname)) {
            return "Username already exists!";
        } else {
            try ( BufferedWriter writer = new BufferedWriter(new FileWriter("UserDetails.txt", true))) {
                writer.append(uname);
                writer.append(":");
                writer.append(pword);
                writer.newLine();
                writer.close();
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
            return "Registration successful.";
        }
    }

    public String loginUser(String uname) {
        createHashMap();
        if (this.users.containsKey(uname)) {
            System.out.print("Enter your password:");
            String password = scanner.nextLine();
            password = password.toLowerCase().trim();
            if (users.get(uname).equals(password)) {
                System.out.println("Login successful");
                File file = new File("/home/chaitra/NetBeansProjects/GermanLearningApp/words.txt" + uname);
                if (!file.exists()) {
                    try {
                        file.createNewFile();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                start(uname, file);
            } else {
                return "Invalid username or password!";
            }
            return "login successful!";
        } else {
            return "Username does not exist! Please register yourself!";
        }
    }

    public void createHashMap() {
        try ( Scanner scanner = new Scanner(Paths.get("UserDetails.txt"))) {
            while (scanner.hasNextLine()) {
                String details = scanner.nextLine();
                String[] parts = details.split(":");
                String u = parts[0];
                String p = parts[1];
                this.users.put(u, p);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void doExcercise(ArrayList<GermanLanguage> Alist) {
        System.out.println("Now its Excercise time!");
        System.out.println("");
        System.out.println("Enter the blank word for the given sentence");
        for (int j = 0; j < Alist.size(); j++) {
            System.out.println(Alist.get(j).getGermanWord());
            String sentence = Alist.get(j).getExample();
            String target = Alist.get(j).getAnswer();
            String replacement = "_______";
            String processed = sentence.replace(target, replacement);
            System.out.println(processed);
            String ans = scanner.nextLine();
            if (ans.equals(target)) {
                System.out.println("Your answer is correct");
            } else {
                System.out.println("Your answer is wrong");
                System.out.println("The correct answer is: " + Alist.get(j).getAnswer());
                System.out.println("");
            }
        }
    }

    public void endProgram(File file) {
        try ( Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String word = scanner.nextLine();
                learnedWords.add(word);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        if (learnedWords.isEmpty()) {
            System.out.println("You have not learned yet!");
        } else {
            System.out.println("Learned words are below:");
            for (int i = 0; i < learnedWords.size(); i++) {
                System.out.println(learnedWords.get(i));
            }
        }
    }

    public void start(String username, File file) {
        functionality.GermanWords();
        System.out.println("Welcome " + username);
        int number = 0;
        while (true) {
            if (flag == false) {
                System.out.println("How many words do you want to learn? Please enter end if you want to end");
                String str = scanner.nextLine();
                if (str.isEmpty()) {
                    System.out.println("Please give a valid input");
                    continue;
                }
                if (str.equals("end")) {
                    flag = true;
                    endProgram(file);
                    break;
                }
                try {
                    number = Integer.parseInt(str);
                    if ((number <= 0)) {
                        System.out.println("Number should be greater than 0");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please give a valid number");
                    continue;
                }
                ArrayList<GermanLanguage> newList = functionality.subListFromList(number, file);
                if (number <= newList.size()) {
                    for (int i = 0; i < newList.size(); i++) {
                        System.out.println("German word: " + newList.get(i).getGermanWord());
                        String enter1 = scanner.nextLine();
                        if (enter1.equals("end")) {
                            flag = true;
                            endProgram(file);
                            break;
                        }
                        System.out.println("Meaning in English: " + newList.get(i).getEnglishTranslation());
                        String enter2 = scanner.nextLine();
                        if (enter2.equals("end")) {
                            flag = true;
                            endProgram(file);
                            break;
                        }
                        try {
                            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
                            writer.append(newList.get(i).getGermanWord());
                            writer.newLine();
                            writer.close();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        System.out.println("Example sentence: " + newList.get(i).getExample());
                        String enter3 = scanner.nextLine();
                        if (enter3.equals("end")) {
                            flag = true;
                            endProgram(file);
                            break;
                        }
                    }
                    System.out.println("");
                    if (flag == false) {
                        doExcercise(newList);
                    }
                } else if (number > newList.size()) {
                    ArrayList<GermanLanguage> copiedList = functionality.wholeList(number, file);
                    for (int i = 0; i < copiedList.size(); i++) {
                        System.out.println("German word: " + copiedList.get(i).getGermanWord());
                        String enter4 = scanner.nextLine();
                        if (enter4.equals("end")) {
                            break;
                        }
                        System.out.println("Meaning in English: " + copiedList.get(i).getEnglishTranslation());
                        String enter5 = scanner.nextLine();
                        if (enter5.equals("end")) {
                            break;
                        }
                        try ( BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                            writer.append(copiedList.get(i).getGermanWord());
                            writer.newLine();
                        } catch (IOException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        System.out.println("Example sentence: " + copiedList.get(i).getExample());
                        String enter6 = scanner.nextLine();
                        if (enter6.equals("end")) {
                            break;
                        }
                    }
                    flag = true;
                    endProgram(file);
                    System.out.println("Thank you!!");
                    if (learnedWords.size() == 5) {
                        if (!copiedList.isEmpty()) {
                            System.out.println("You have learned everything, Thank you");
                            doExcercise(copiedList);
                        }
                    }
                    break;
                }
            } else {
                break;
            }
        }
    }
}
