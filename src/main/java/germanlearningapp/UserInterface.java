package germanlearningapp;

import java.sql.*;
import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.BufferedWriter;
//import java.nio.file.Paths;
//import java.util.HashMap;
//import java.util.Map;

public class UserInterface {

    private functionality functionality;
    private Scanner scanner;
    private ArrayList<String> learnedWords;
    private boolean flag;

    public UserInterface(functionality func, Scanner scanner) {
        this.functionality = func;
        this.scanner = scanner;
        this.learnedWords = new ArrayList<>();
//        this.users = new HashMap<>();
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
                    registerUser(username, password);
                } else if (input.equals("2")) {
                    String password = "";
                    System.out.print("Enter your username:");
                    String username = scanner.nextLine();
                    username = username.toLowerCase().trim();
                        System.out.print("Enter your password:");
                        password = scanner.nextLine();
                        password = password.toLowerCase().trim();
                        loginUser(username, password);
                } else if (input.equals("3")) {
                    break;
                }
            } else {
                break;
            }
        }
    }

    //user registration with username, password
    public void registerUser(String username, String password) {
        Connection conn = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet  = null;
        try{
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/learnGerman", "root", "root");

            psCheckUserExists =  conn.prepareStatement("select * from users where username = ?");
            psCheckUserExists.setString(1, username);
            resultSet = psCheckUserExists.executeQuery();

            if(resultSet.isBeforeFirst()){
                System.out.println("User already exists");
            }else{
                psInsert = conn.prepareStatement("INSERT INTO users (username, password) VALUES (?,?)");
                psInsert.setString(1, username);
                psInsert.setString(2, password);
                psInsert.executeUpdate();
                System.out.println("Registration successful");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        finally {
            if(resultSet != null){
                try{
                    resultSet.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if(psInsert != null){
                try{
                    psInsert.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if(psCheckUserExists != null){
                try{
                    psCheckUserExists.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if(conn != null){
                try{
                    conn.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void loginUser(String username, String password){
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/learnGerman", "root", "root");
            preparedStatement = conn.prepareStatement("SELECT password FROM users WHERE username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if(!resultSet.isBeforeFirst()){
                System.out.println("User not found");
            }else{
                while(resultSet.next()){
                    String retrievedPassword = resultSet.getString("password");
                    if(retrievedPassword.equals(password)){
                        String tableName = "learnedWords"+username;
                        String column = "learnedWords varchar(50)";
                        DatabaseMetaData databaseMetaData = conn.getMetaData();
                        ResultSet tables = databaseMetaData.getTables(null, null, tableName, null);
                        if(!tables.next()){
                           StringBuilder sql = new StringBuilder("create table " + tableName + " (");
                            sql.append(column).append(", ");
                            sql.setLength(sql.length() - 2); // Remove the last comma and space
                            sql.append(");");
                            try{
                                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/learnGerman", "root", "root");
                                Statement statement = conn.createStatement();
                                statement.executeUpdate(sql.toString());
                            }catch (SQLException e){
                                e.printStackTrace();
                            }
                        }
                        start(username, tableName);

                    }else{
                        System.out.println("Password did not match!");
                    }
                }
            }
        }catch(SQLException e){
            System.out.println("Error:" + e.getMessage());
        }
    }

    //a method to give excercise to user
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

    //ending the program if users enters "end"
    public void endProgram(String tablename) {
        try {
            Connection myConn= DriverManager.getConnection("jdbc:mysql://localhost:3306/learnGerman", "root", "root");

            Statement myStmt = myConn.createStatement();

            ResultSet myRs = myStmt.executeQuery("select * from " + tablename);
            while (myRs.next()) {
                learnedWords.add(myRs.getString(1));
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

    public void start(String username, String tablename) {
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
                    endProgram(tablename);
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
                ArrayList<GermanLanguage> newList = functionality.subListFromList(number, tablename);
                if (number <= newList.size()) {
                    for (int i = 0; i < newList.size(); i++) {
                        System.out.println("German word: " + newList.get(i).getGermanWord());
                        String enter1 = scanner.nextLine();
                        if (enter1.equals("end")) {
                            flag = true;
                            endProgram(tablename);
                            break;
                        }
                        System.out.println("Meaning in English: " + newList.get(i).getEnglishTranslation());
                        String enter2 = scanner.nextLine();
                        if (enter2.equals("end")) {
                            flag = true;
                            endProgram(tablename);
                            break;
                        }
                        try {
                            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/learnGerman", "root", "root");
                            String sql = "insert into " + tablename + "(learnedWords) values (?)";
                            PreparedStatement preparedStatement = conn.prepareStatement(sql);
                            preparedStatement.setString(1, newList.get(i).getGermanWord());
                            preparedStatement.executeUpdate();
                            System.out.println("word inserted correctly");

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        System.out.println("Example sentence: " + newList.get(i).getExample());
                        String enter3 = scanner.nextLine();
                        if (enter3.equals("end")) {
                            flag = true;
                            endProgram(tablename);
                            break;
                        }
                    }
                    System.out.println("");
                    if (flag == false) {
                        doExcercise(newList);
                    }
                } else if (number > newList.size()) {
                    ArrayList<GermanLanguage> copiedList = functionality.wholeList(number);
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
                        try {
                            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/learnGerman", "root", "root");
                            String sql = "insert into " + tablename + "(learnedWords) values (?)";
                            PreparedStatement preparedStatement = conn.prepareStatement(sql);
                            preparedStatement.setString(1, copiedList.get(i).getGermanWord());
                            preparedStatement.executeUpdate();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        System.out.println("Example sentence: " + copiedList.get(i).getExample());
                        String enter6 = scanner.nextLine();
                        if (enter6.equals("end")) {
                            break;
                        }
                    }
                    flag = true;
                    endProgram(tablename);
                    System.out.println("Thank you!!");
                    if (learnedWords.size() == 5) {
                        if (!copiedList.isEmpty()) {
                            System.out.println("You have learned everything..");
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
