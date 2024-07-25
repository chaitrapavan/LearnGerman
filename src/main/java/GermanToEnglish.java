
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
import java.util.List;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;

public class GermanToEnglish {

    private List<Dictionary> translatedList;

    public GermanToEnglish() {
        this.translatedList = new ArrayList<>();

    }
//reading germanWords.txt file, adding lines to a ArrayList and returning it.
    public ArrayList<Dictionary> readFile() {
        ArrayList<Dictionary> list = new ArrayList<>();
        try ( Scanner scanner = new Scanner(Paths.get("germanwords.txt"))) {
            while (scanner.hasNextLine()) {
                String germanWord = scanner.nextLine();
                String englishTran = scanner.nextLine();
                String example = scanner.nextLine();

                list.add(new Dictionary(germanWord, englishTran, example));
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return list;

    }

    //calling readFile() function 
    public void germanWords() {
        this.translatedList = this.readFile();
    }

    //getting newList on the basis of user input and excluding newLists's elements from main list
    public List<Dictionary> getNewList(int input) {
        List<Dictionary> newList = new ArrayList<>();
        Random r = new Random();
        try ( Scanner scanner = new Scanner(Paths.get("learnedWords.txt"))) {
            while (scanner.hasNextLine()) {
                String storedWord = scanner.nextLine();
                for (int i = 0; i < this.translatedList.size(); i++) {
                    if (this.translatedList.get(i).getGermanword().equals(storedWord)) {
                        this.translatedList.remove(i);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        int size = this.translatedList.size();
        if (!this.translatedList.isEmpty() && input <= this.translatedList.size()) {
            for (int i = 0; i < input; i++) {
                int selection = r.nextInt(size);
                newList.add(this.translatedList.get(selection));
                this.translatedList.set(selection, this.translatedList.get(--size));
                this.translatedList.remove(size);
            }

        } else if (input > this.translatedList.size()) {
            newList = this.translatedList;
        }
        return newList;
    }

    public List<Dictionary> getCopiedList(int input) {
        List<Dictionary> newList = new ArrayList<>();
        if (input > this.translatedList.size()) {
            newList = this.translatedList;
        }
        return newList;
    }
}
