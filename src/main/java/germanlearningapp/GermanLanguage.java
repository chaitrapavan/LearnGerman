package germanlearningapp;

public class GermanLanguage {

    private String germanWord;
    private String englishTranslation;
    private String example;
    private String answer;

    public GermanLanguage(){

    }
    public GermanLanguage(String germanWord, String englishTranslation, String example, String answer) {
        this.germanWord = germanWord;
        this.englishTranslation = englishTranslation;
        this.example = example;
        this.answer = answer;
    }

    public GermanLanguage(String GermanWord){
        this.germanWord = germanWord;
    }
    public String getGermanWord() {
        return this.germanWord;
    }

    public String getEnglishTranslation() {
        return this.englishTranslation;
    }

    public String getExample() {
        return this.example;
    }
    public String getAnswer(){
        return this.answer;
    }

    public void setGermanWord(String newGermanWord){
        this.germanWord = newGermanWord;
    }
    public void setEnglishTranslation(String newEnglishTranslation){
        this.englishTranslation = newEnglishTranslation;
    }
    public void setExample(String newExample){
        this.example = newExample;
    }
    public void setAnswer(String newAnswer){
        this.answer = newAnswer;
    }
}
