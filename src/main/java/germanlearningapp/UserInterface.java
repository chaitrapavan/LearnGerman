package germanlearningapp;

import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;

public class UserInterface {

    private functionality functionality;
    private Scanner scanner;
    private ArrayList<String> learnedWords;
    private  Map<String, String> users;
 

    public UserInterface(functionality func, Scanner scanner) {
        this.functionality = func;
        this.scanner = scanner;
        this.learnedWords = new ArrayList<>();
        this.users = new HashMap<>();
       
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

    boolean flag = false;
    boolean success = false;

     public void auth(){
        int choice = 0;
        try{
            while (choice != 2) {
            System.out.println("Select an option: ");
            System.out.println("1. Register");
            System.out.println("2. Login");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch(choice){
                case 1:
                    registerUser();
                    break;
                case 2:
                    loginUser();
                    break; 
                    default:
                    System.out.println("Invalid choice"); 
            }
        }    
   
    }catch (InputMismatchException ex) {  
            System.out.println("Please select 1 or 2");
           }
    }
        public void registerUser(){
            System.out.print("Enter your username:");
            String username = scanner.nextLine();
            username = username.toLowerCase().trim();
            try ( Scanner scanner = new Scanner(Paths.get("UserDetails.txt"))) {
             while(scanner.hasNextLine()){
                 String details = scanner.nextLine();
                 String[] parts = details.split(":");
                 String u = parts[0];
                 String p = parts[1];
                 this.users.put(u, p);                
             }          
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
            if (users.containsKey(username)) {
                System.out.println("Username already exists!");
            } else {               
                System.out.print("Enter your password:");
                String password = scanner.nextLine();
                password = password.toLowerCase().trim();
                try ( BufferedWriter writer = new BufferedWriter(new FileWriter("UserDetails.txt", true))) {
                    writer.append(username);
                    writer.append(":");
                    writer.append(password);
                    writer.newLine();
                    writer.close();
                } catch (IOException e) {
                    System.out.println("Error: " + e.getMessage());
                }

                System.out.println("Registration successful.");
            }            
    }

       public void loginUser(){
           System.out.print("Enter your Username:");
           String username = scanner.nextLine();
           username = username.toLowerCase().trim();
                if(this.users.containsKey(username)){
                     System.out.print("Enter your password:");
                     String password = scanner.nextLine();
                     password = password.toLowerCase().trim();
                     if(users.get(username).equals(password)){
                         System.out.println("Login successful");
                         File file = new File( "/home/chaitra/NetBeansProjects/GermanLearningApp/words.txt" + username);  
                         if(!file.exists()){
                             try{
                                  file.createNewFile();
                             }catch(Exception e){
                                 e.printStackTrace();
                             }             
                         }
                         this.start(username, file);
                     }else{
                         System.out.println("Invalid username or password!");
                     }
                 }else{
                         System.out.println("Username does not exist! Please register yourself!" );
                         this.auth();
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
                if(str.isEmpty()){
                    System.out.println("Please give a valid input");
                    continue;
                }
                if (str.equals("end")) {
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
                }

                ArrayList<GermanLanguage> newList = functionality.subListFromList(number, file);
                if (number <= newList.size()) {
                    for (int i = 0; i < newList.size(); i++) {
                        System.out.println("German word: " + newList.get(i).getGermanWord());
                        String enter = scanner.nextLine();
                        if (str.equals("end")) {
                            flag = true;    
                            endProgram(file);
                            break;
                        }

                        System.out.println("Meaning in English: " + newList.get(i).getEnglishTranslation());
                        String enter1 = scanner.nextLine();
                        if (enter1.equals("end")) {
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
                        String enter2 = scanner.nextLine();
                        if (enter2.equals("end")) {
                            flag = true;
                            endProgram(file);
                            break;
                        }
                    }
                    System.out.println("");
                    System.out.println("Now its Excercise time!");
                    System.out.println("");
                    System.out.println("Enter the blank word for the given sentence");
                    for (int i = 0; i < newList.size(); i++) {
                        System.out.println(newList.get(i).getGermanWord());
                        String sentence = newList.get(i).getExample();
                        String target = newList.get(i).getAnswer();
                        String replacement = "_______";
                        String processed = sentence.replace(target, replacement);
                        System.out.println(processed);
                        String ans = scanner.nextLine();
                        if (ans.equals(target)) {
                            System.out.println("Your answer is correct");
                        } else {
                            System.out.println("Your answer is wrong");
                            System.out.println("The correct answer is: " + newList.get(i).getAnswer());
                        }
                        System.out.println("");
                    }
                } else if (number > newList.size()) {
                    System.out.println("The app does not contain that much of words!");
                    ArrayList<GermanLanguage> copiedList = functionality.wholeList(number, file);
                    for (int i = 0; i < copiedList.size(); i++) {
                        System.out.println("German word: " + copiedList.get(i).getGermanWord());
                        String enter = scanner.nextLine();
                        if (enter.equals("end")) {
                            flag = true;
                            endProgram(file);
                            break;
                        }
                        System.out.println("Meaning in English: " + copiedList.get(i).getEnglishTranslation());
                        String enter1 = scanner.nextLine();
                        if (enter1.equals("end")) {
                            flag = true;
                            endProgram(file);
                            break;
                        }
                        try ( BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                            writer.append(copiedList.get(i).getGermanWord());
                            writer.newLine();
//                        writer.close();
                        } catch (IOException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        System.out.println("Example sentence: " + copiedList.get(i).getExample());
                        String enter2 = scanner.nextLine();
                        if (enter.equals("end")) {
                            flag = true;
                            endProgram(file);
                            break;
                        }
                    }
                    this.endProgram(file);
                    System.out.println("You have learned everything, Thank you");
                    
                    System.out.println("");
                    if(!copiedList.isEmpty()){
                             System.out.println("Now its Excercise time!");
                    System.out.println("");
                    System.out.println("Enter the blank word for the given sentence");
                    for (int i = 0; i < copiedList.size(); i++) {
                        System.out.println(copiedList.get(i).getGermanWord());
                        String sentence = copiedList.get(i).getExample();
                        String target = copiedList.get(i).getAnswer();
                        String replacement = "_______";
                        String processed = sentence.replace(target, replacement);
                        System.out.println(processed);
                        String ans = scanner.nextLine();
                        if (ans.equals(target)) {
                            System.out.println("Your answer is correct");
                        } else {
                            System.out.println("Your answer is wrong");
                            System.out.println("The correct answer is: " + copiedList.get(i).getAnswer());
                            System.out.println("");
                        }
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
