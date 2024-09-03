package germanlearningapp;

import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;
import java.io.File;

public class functionality {

    private ArrayList<GermanLanguage> list;

    public functionality() {
        this.list = new ArrayList<>();
    }

    public ArrayList<GermanLanguage> readFile() {
        ArrayList<GermanLanguage> words = new ArrayList<>();
        try ( Scanner scanner = new Scanner(Paths.get("GermanWords.txt"))) {
            while (scanner.hasNextLine()) {
                String germanWord = scanner.nextLine();
                String englishWord = scanner.nextLine();
                String example = scanner.nextLine();
                String answer = scanner.nextLine();
                words.add(new GermanLanguage(germanWord, englishWord, example, answer));
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return words;
    }

    public void GermanWords() {
        this.list = this.readFile();
    }

    public ArrayList<GermanLanguage> subListFromList(int input, File file) {
        try ( Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String word = scanner.nextLine();
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

    public ArrayList<GermanLanguage> wholeList(int input, File file) {
        ArrayList<GermanLanguage> copyList = new ArrayList<>();
        if (input > this.list.size()) {
            copyList = this.list;
        }
        return copyList;
    }
}
