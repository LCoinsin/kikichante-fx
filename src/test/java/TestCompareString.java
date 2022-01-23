import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.*;
import org.apache.commons.text.similarity.LevenshteinDistance;

public class TestCompareString {

    public static void main(String[] args) {

        System.out.println(new LevenshteinDistance().apply("Angèle", "Engele"));

    }

}
