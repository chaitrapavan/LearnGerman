
public class Dictionary {

    private String germanWord;
    private String translatedWord;
    private String example;

    public Dictionary(String germanWord, String translatedWord, String example) {
        this.germanWord = germanWord;
        this.translatedWord = translatedWord;
        this.example = example;
    }

    public String getGermanword() {
        return this.germanWord;
    }

    public String gettranslatedWord() {
        return this.translatedWord;
    }

    public String getExample() {
        return this.example;
    }
}
