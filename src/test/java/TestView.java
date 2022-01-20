import com.kikichante.kikichantefx.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TestView extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(Application.class.getResource("ViewSignInSignUp.fxml"));
        Scene view = new Scene(loader.load());

        stage.setScene(view);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
