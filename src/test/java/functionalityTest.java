package germanlearningapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class functionalityTest {

    @Test
    public void testReadFile() {
        functionality func = new functionality();
        int size = func.readFile().size();
        assertEquals(5, size);
//        List<GermanLanguage> actual = new ArrayList<>();
//        GermanLanguage gl1 = new GermanLanguage("geben", "give", "Ich gebe einen Ball.", "gebe");
//        GermanLanguage gl2 = new GermanLanguage("benutzen", "use", "Die Person benutzt einen Computer.", "benutzt");
//        actual.add(gl1);
//        actual.add(gl2);
//        assertEquals("Wrong first element", gl1, actual.get(0));
    }

    @Test
    public void testSubListFromList() {
        functionality func = new functionality();
        int input = 2;
        List<GermanLanguage> actual = new ArrayList<>();
        GermanLanguage gl1 = new GermanLanguage("geben", "give", "Ich gebe einen Ball.", "gebe");
        GermanLanguage gl2 = new GermanLanguage("benutzen", "use", "Die Person benutzt einen Computer.", "benutzt");
        GermanLanguage gl3 = new GermanLanguage("kommen", "come", "Er ist gestern gekommen.", "gekommen");
        GermanLanguage gl4 = new GermanLanguage("wollen", "want", "Sie wollen hier zu bleiben.", "wollen");
        GermanLanguage gl5 = new GermanLanguage("arbeiten", "work", "Sie arbeitet in eine Firma.", "arbeitet");
        actual.add(gl1);
        actual.add(gl2);
        actual.add(gl3);
        actual.add(gl4);
        actual.add(gl5);
        actual.remove(input);
        actual.remove(input);
        int actualSize = actual.size();
        int expectedSize = func.readFile().size() - input;
        assertEquals(expectedSize, actualSize);
    }
}
