import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.*;
import org.apache.commons.text.similarity.LevenshteinDistance;

public class TestCompareString {

    public static void main(String[] args) {

        String author = "test";
        String answer = "tet";

        LevenshteinDistance levenshteinDistance = new LevenshteinDistance();

        if (levenshteinDistance.apply(author, answer) < 2) {
            System.out.println("ok");
        }

    }

}
