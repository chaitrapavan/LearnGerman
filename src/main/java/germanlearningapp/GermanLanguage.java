package germanlearningapp;

public class GermanLanguage {

    private String GermanWord;
    private String EnglishTranslation;
    private String example;
    private String answer;

    public GermanLanguage(String GermanWord, String EnglishTranslation, String example, String answer) {
        this.GermanWord = GermanWord;
        this.EnglishTranslation = EnglishTranslation;
        this.example = example;
        this.answer = answer;
    }

    public GermanLanguage(String GermanWord){
        this.GermanWord = GermanWord;
    }
    public String getGermanWord() {
        return this.GermanWord;
    }

    public String getEnglishTranslation() {
        return this.EnglishTranslation;
    }

    public String getExample() {
        return this.example;
    }
    public String getAnswer(){
        return this.answer;
    }
   
}
