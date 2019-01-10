package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class TariffsDialogController {

    @FXML
    private TextField electroTariff1Field;
    @FXML
    private TextField electroLimit1Field;
    @FXML
    private TextField electroTariff2Field;
    @FXML
    private TextField electroLimit2Field;
    @FXML
    private TextField electroTariff3Field;
    @FXML
    private TextField electroLimit3Field;
    @FXML
    private TextField electroTariff4Field;

    @FXML
    private TextField waterTariffField;
    @FXML
    private TextField flatTariffField;
    @FXML
    private TextField heatingTariffField;
    @FXML
    private TextField garbageTariffField;

    private double electroTariff1;
    private int electroLimit1;
    private double electroTariff2;
    private int electroLimit2;
    private double electroTariff3;
    private int electroLimit3;
    private double electroTariff4;

    private double heatingTariff;
    private double waterTariff;
    private double flatTariff;
    private double garbageTariff;

    private boolean someFieldsProcessedWrong = false;

    public void showDialogFillFields(TariffsDataViaProperties tariffsData) {
        electroTariff1Field.setText(String.valueOf(tariffsData.electroTariff1));
        electroTariff2Field.setText(String.valueOf(tariffsData.electroTariff2));
        electroTariff3Field.setText(String.valueOf(tariffsData.electroTariff3));
        electroTariff4Field.setText(String.valueOf(tariffsData.electroTariff4));
        electroLimit1Field.setText(String.valueOf(tariffsData.electroLimit1));
        electroLimit2Field.setText(String.valueOf(tariffsData.electroLimit2));
        electroLimit3Field.setText(String.valueOf(tariffsData.electroLimit3));

        waterTariffField.setText(String.valueOf(tariffsData.waterTariff));
        flatTariffField.setText(String.valueOf(tariffsData.flatTariff));
        heatingTariffField.setText(String.valueOf(tariffsData.heatingTariff));
        garbageTariffField.setText(String.valueOf(tariffsData.garbageTariff));
    }

    public void obtainNumbersFromTextFields() {
        try {
            electroTariff1 = Double.parseDouble(electroTariff1Field.getText());
        } catch (Exception e) {
            electroTariff1 = 0;
            someFieldsProcessedWrong = true;
        }
        try {
            int trial = (int) 0.5;
            electroLimit1 = Integer.parseInt(electroLimit1Field.getText());
        } catch (Exception e) {
            electroLimit1 = 0;
            someFieldsProcessedWrong = true;
        }
        try {
            electroTariff2 = Double.parseDouble(electroTariff2Field.getText());
        } catch (Exception e) {
            electroTariff2 = 0;
            someFieldsProcessedWrong = true;
        }
        try {
            electroLimit2 = Integer.parseInt(electroLimit2Field.getText());
        } catch (Exception e) {
            electroLimit2 = 0;
            someFieldsProcessedWrong = true;
        }
        try {
            electroTariff3 = Double.parseDouble(electroTariff3Field.getText());
        } catch (Exception e) {
            electroTariff3 = 0;
            someFieldsProcessedWrong = true;
        }
        try {
            electroLimit3 = Integer.parseInt(electroLimit3Field.getText());
        } catch (Exception e) {
            electroLimit3 = 0;
            someFieldsProcessedWrong = true;
        }
        try {
            electroTariff4 = Double.parseDouble(electroTariff4Field.getText());
        } catch (Exception e) {
            electroTariff4 = 0;
            someFieldsProcessedWrong = true;
        }
        try {
            waterTariff = Double.parseDouble(waterTariffField.getText());
        } catch (Exception e) {
            waterTariff = 0;
            someFieldsProcessedWrong = true;
        }
        try {
            heatingTariff = Double.parseDouble(heatingTariffField.getText());
        } catch (Exception e) {
            heatingTariff = 0;
            someFieldsProcessedWrong = true;
        }
        try {
            flatTariff = Double.parseDouble(flatTariffField.getText());
        } catch (Exception e) {
            flatTariff = 0;
            someFieldsProcessedWrong = true;
        }
        try {
            garbageTariff = Double.parseDouble(garbageTariffField.getText());
        } catch (Exception e) {
            garbageTariff = 0;
            someFieldsProcessedWrong = true;
        }

        if (someFieldsProcessedWrong) {
            Alerts.alertInfo("Тариф(ы) обработан(ы) неправильно",
                    "Значения в одном (или нескольких) полях ввода тарифов некорректны.\n" +
                    "Все неправильные значения заменены значением 0");
        }
    }

    public TariffsDataViaProperties processTariffs(TariffsDataViaProperties tariffsData) {
        obtainNumbersFromTextFields();

        tariffsData.electroTariff1 = electroTariff1;
        tariffsData.electroLimit1 = electroLimit1;
        tariffsData.electroTariff2 = electroTariff2;
        tariffsData.electroLimit2 = electroLimit2;
        tariffsData.electroTariff3 = electroTariff3;
        tariffsData.electroLimit3 = electroLimit3;
        tariffsData.electroTariff4 = electroTariff4;
        tariffsData.heatingTariff = heatingTariff;
        tariffsData.waterTariff = waterTariff;
        tariffsData.flatTariff = flatTariff;
        tariffsData.garbageTariff = garbageTariff;

        return tariffsData;
    }

    public void backToDefaults(ActionEvent actionEvent) {
        TariffsDataViaProperties newDefaultTariffsDataViaProperties = new TariffsDataViaProperties();
        showDialogFillFields(newDefaultTariffsDataViaProperties);
//        obtainNumbersFromTextFields();
    }
}
