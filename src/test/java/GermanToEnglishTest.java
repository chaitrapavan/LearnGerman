
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class GermanToEnglishTest {

    @Test
    public void testcheckFile() {
        System.out.println("running test checkFile");
        GermanToEnglish gte = new GermanToEnglish();
        ArrayList<Dictionary> list = new ArrayList<>();
        list = gte.readFile();
        int size = list.size();
        assertEquals(size, 8);

    }
}
