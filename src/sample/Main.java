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
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
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
