package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private PaymentsData paymentsData;

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Original:
//        String workingDir = System.getProperty("user.dir");
//        System.out.println(workingDir);
//        String fxmlPath = workingDir + "\\src\\main\\resources\\mainWindow.fxml";
//        System.out.println(fxmlPath);
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("mainWindow.fxml"));
        primaryStage.setTitle("Платежи");
        primaryStage.setScene(new Scene(root, 900, 475));
        primaryStage.show();

//        Mine:
//        Parent root = FXMLLoader.loadPayments(getClass().getResource("paymentDialog.fxml"));
//        primaryStage.setTitle("Данные платежа");
//        primaryStage.setScene(new Scene(root/*, 900,275*/));
//        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
