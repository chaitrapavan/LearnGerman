package germanlearningapp;

import java.util.ArrayList;
import java.util.Random;
import java.sql.*;

public class functionality {

    private ArrayList<GermanLanguage> list;

    public functionality() {
        this.list = new ArrayList<>();
    }
//reading the file "GermanWords.txt", adding words to an array and returning them
    public ArrayList<GermanLanguage> readFile() {
        ArrayList<GermanLanguage> words = new ArrayList<>();
        try {
          Connection myConn= DriverManager.getConnection("jdbc:mysql://localhost:3306/learnGerman", "root", "root");

          Statement myStmt = myConn.createStatement();

          ResultSet myRs = myStmt.executeQuery("select * from germanWords");

          while (myRs.next()){
              GermanLanguage glan = new GermanLanguage();
              String germanWord = myRs.getString("germanWord");
              String englishTranslation = myRs.getString("englishTranslation");
              String example = myRs.getString("example");
              String answer = myRs.getString("answer");

              glan.setGermanWord(germanWord);
              glan.setEnglishTranslation(englishTranslation);
              glan.setExample(example);
              glan.setAnswer(answer);

              words.add(glan);
          }
          myRs.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return words;
    }

    public void GermanWords() {
        this.list = this.readFile();
    }

    //returning random words from a list
    public ArrayList<GermanLanguage> subListFromList(int input, String tablename) {
        ArrayList<String> words = new ArrayList<>();
        try {
            Connection myConn= DriverManager.getConnection("jdbc:mysql://localhost:3306/learnGerman", "root", "root");

            Statement myStmt = myConn.createStatement();

            ResultSet myRs = myStmt.executeQuery("select * from " + tablename);
            while (myRs.next()) {
               String word = (myRs.getString(1));
                for (int i = 0; i < this.list.size(); i++) {
                    if (this.list.get(i).getGermanWord().equals(word)) {
                        this.list.remove(i);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        ArrayList<GermanLanguage> subList = new ArrayList<>();
        Random r = new Random();
        int size = this.list.size();
        if (!this.list.isEmpty() && input <= this.list.size()) {
            for (int i = 0; i < input; i++) {
                int selection = r.nextInt(size);
                subList.add(this.list.get(selection));
                this.list.set(selection, this.list.get(--size));
                this.list.remove(size);
            }
        }
        return subList;
    }

    public ArrayList<GermanLanguage> wholeList(int input) {
        ArrayList<GermanLanguage> copyList = new ArrayList<>();
        if (input > this.list.size()) {
            copyList = this.list;
        }
        return copyList;
    }
}
