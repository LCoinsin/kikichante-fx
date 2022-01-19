import com.kikichante.exception.ExceptionBddConnexion;
import com.kikichante.server.Bdd;

public class AddClientBdd {
    public static void main(String[] args) throws ExceptionBddConnexion {
        Bdd bdd = new Bdd();

        System.out.println(bdd.queryInscription("Mateo", "123"));

        bdd.closeBdd();
    }
}
