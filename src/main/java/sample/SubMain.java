package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/* A Main class to be packed into executable jar must not extends another classes
 * so I invoke the SubMain extending Application from Main */
public class SubMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("mainWindow.fxml"));
        primaryStage.setTitle("Платежи");
        primaryStage.setScene(new Scene(root, 900, 475));
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
