import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

        @Override
        public void start(Stage myStage) throws Exception{
            Parent root = FXMLLoader.load(getClass().getResource("resources/Main.fxml"));
            myStage.setTitle("Java Data Access - Edin Saračević");
            myStage.setScene(new Scene(root, 800, 600));
            myStage.resizableProperty().setValue(false);
            myStage.initStyle(StageStyle.TRANSPARENT);
            myStage.show();
        }

        public static void main(String[] args) {
            launch(args);
        }
}
