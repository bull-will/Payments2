package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class PaymentDialogController {

    @FXML
    private DialogPane dialogPane; /* do I even need this? */

    @FXML
    private TextField monthField;
    @FXML
    private TextField yearField;
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
    private TextField electroStartField;
    @FXML
    private TextField electroEndField;
    @FXML
    private TextField electroMustPayField;
    @FXML
    private TextField waterTariffField;
    @FXML
    private TextField waterStartField;
    @FXML
    private TextField waterEndField;
    @FXML
    private TextField waterMustPayField;
    @FXML
    private TextField flatTariffField;
    @FXML
    private TextField flatMustPayField;
    @FXML
    private TextField heatingTariffField;
    @FXML
    private TextField heatingMustPayField;
    @FXML
    private TextField garbageTariffField;
    @FXML
    private TextField garbageMustPayField;

    @FXML
    RadioButton electroByCounter;
    @FXML
    RadioButton electroBySet;
    @FXML
    RadioButton waterByCounter;
    @FXML
    RadioButton waterBySet;
    @FXML
    RadioButton flatByTariff;
    @FXML
    RadioButton flatBySet;
    @FXML
    RadioButton heatingByTariff;
    @FXML
    RadioButton heatingBySet;
    @FXML
    RadioButton garbageByTariff;
    @FXML
    RadioButton garbageBySet;

    /* These are going to be collected from the basis payment,
    then changed(or not) in the dialog, and then put into new/edited payment */
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

    private double electroMustPay;
    private double heatingMustPay;
    private double waterMustPay;
    private double flatMustPay;
    private double garbageMustPay;

    private int year;
    private int month;

    private int electroStart;
    private int electroEnd;
    private int waterStart;
    private int waterEnd;

    /* switching radio buttons in the dialog window
    according to whether the stuff is supposed to be payd by the tariff or by the random set amount of money*/
    public void electroSetInField(MouseEvent mouseEvent) {
        electroBySet.setSelected(true);
    }

    public void waterSetInField(MouseEvent mouseEvent) {
        waterBySet.setSelected(true);
    }

    @FXML
    public void flatSetInField(MouseEvent mouseEvent) {
        flatBySet.setSelected(true);
    }

    @FXML
    public void heatingSetInField(MouseEvent mouseEvent) {
        heatingBySet.setSelected(true);
    }

    @FXML
    public void garbageSetInField(MouseEvent mouseEvent) {
        garbageBySet.setSelected(true);
    }

    /* Obtaining what was in the textfields of the payment dialog, either pre-filled or manually entered */
    public void obtainNumbersFromTextFields() {
        try {
            year = Integer.parseInt(yearField.getText());
        } catch (Exception e) {
            year = 0;
        }
        try {
            month = Integer.parseInt(monthField.getText());
        } catch (Exception e) {
            month = 0;
        }
        try {
            electroTariff1 = Double.parseDouble(electroTariff1Field.getText().replace(',', '.'));
        } catch (Exception e) {
            electroTariff1 = 0;
        }
        try {
            electroLimit1 = Integer.parseInt(electroLimit1Field.getText().replace(',', '.'));
        } catch (Exception e) {
            electroLimit1 = 0;
        }
        try {
            electroTariff2 = Double.parseDouble(electroTariff2Field.getText().replace(',', '.'));
        } catch (Exception e) {
            electroTariff2 = 0;
        }
        try {
            electroLimit2 = Integer.parseInt(electroLimit2Field.getText().replace(',', '.'));
        } catch (Exception e) {
            electroLimit2 = 0;
        }
        try {
            electroTariff3 = Double.parseDouble(electroTariff3Field.getText().replace(',', '.'));
        } catch (Exception e) {
            electroTariff3 = 0;
        }
        try {
            electroLimit3 = Integer.parseInt(electroLimit3Field.getText().replace(',', '.'));
        } catch (Exception e) {
            electroLimit3 = 0;
        }
        try {
            electroTariff4 = Double.parseDouble(electroTariff4Field.getText().replace(',', '.'));
        } catch (Exception e) {
            electroTariff4 = 0;
        }
        try {
            electroStart = Integer.parseInt(electroStartField.getText().replace(',', '.'));
        } catch (Exception e) {
            electroStart = 0;
        }
        try {
            electroEnd = Integer.parseInt(electroEndField.getText().replace(',', '.'));
        } catch (Exception e) {
            electroEnd = 0;
        }
        try {
            waterTariff = Double.parseDouble(waterTariffField.getText().replace(',', '.'));
        } catch (Exception e) {
            waterTariff = 0;
        }
        try {
            waterStart = Integer.parseInt(waterStartField.getText().replace(',', '.'));
        } catch (Exception e) {
            waterStart = 0;
        }
        try {
            waterEnd = Integer.parseInt(waterEndField.getText().replace(',', '.'));
        } catch (Exception e) {
            waterEnd = 0;
        }
        try {
            heatingTariff = Double.parseDouble(heatingTariffField.getText().replace(',', '.'));
        } catch (Exception e) {
            heatingTariff = 0;
        }
        try {
            flatTariff = Double.parseDouble(flatTariffField.getText().replace(',', '.'));
        } catch (Exception e) {
            flatTariff = 0;
        }
        try {
            garbageTariff = Double.parseDouble(garbageTariffField.getText().replace(',', '.'));
        } catch (Exception e) {
            garbageTariff = 0;
        }


        try {
            electroMustPay = Double.parseDouble(electroMustPayField.getText().replace(',', '.'));
        } catch (Exception e) {
            electroMustPay = 0;
        }
        try {
            waterMustPay = Double.parseDouble(waterMustPayField.getText().replace(',', '.'));
        } catch (Exception e) {
            waterMustPay = 0;
        }
        try {
            flatMustPay = Double.parseDouble(flatMustPayField.getText().replace(',', '.'));
        } catch (Exception e) {
            flatMustPay = 0;
        }
        try {
            heatingMustPay = Double.parseDouble(heatingMustPayField.getText().replace(',', '.'));
        } catch (Exception e) {
            heatingMustPay = 0;
        }
        try {
            flatMustPay = Double.parseDouble(flatMustPayField.getText().replace(',', '.'));
        } catch (Exception e) {
            flatMustPay = 0;
        }
        try {
            garbageMustPay = Double.parseDouble(garbageMustPayField.getText().replace(',', '.'));
        } catch (Exception e) {
            garbageMustPay = 0;
        }

    }

    public Payment processPayment(Payment payment) {

        obtainNumbersFromTextFields();

        payment.year = year;
        payment.month = month;
        payment.buildName();

        payment.electroTariff1 = electroTariff1;
        payment.electroLimit1 = electroLimit1;
        payment.electroTariff2 = electroTariff2;
        payment.electroLimit2 = electroLimit2;
        payment.electroTariff3 = electroTariff3;
        payment.electroLimit3 = electroLimit3;
        payment.electroTariff4 = electroTariff4;
        payment.heatingTariff = heatingTariff;
        payment.waterTariff = waterTariff;
        payment.flatTariff = flatTariff;
        payment.garbageTariff = garbageTariff;
        payment.electroStart = electroStart;
        payment.electroEnd = electroEnd;
        payment.waterStart = waterStart;
        payment.waterEnd = waterEnd;

        /* If a user set one or another payment to be paid not by the tariff but set the sum himself */
        if (electroBySet.isSelected() && !electroMustPayField.getText().equals("")) {
            payment.setElectroPayment(electroMustPay);
        } else {
            payment.setDefaultElectroPayment();
        }
        if (waterBySet.isSelected() && !waterMustPayField.getText().equals("")) {
            payment.setWaterPayment(waterMustPay);
        } else {
            payment.setDefaultWaterPayment();
        }
        if (flatBySet.isSelected() && !flatMustPayField.getText().equals("")) {
            payment.setFlatPayment(flatMustPay);
        } else {
            payment.setDefaultFlatPayment();
        }
        if (heatingBySet.isSelected() && !heatingMustPayField.getText().equals("")) {
            payment.setHeatingPayment(heatingMustPay);
        } else {
            payment.setDefaultHeatingPayment();
        }
        if (garbageBySet.isSelected() && !garbageMustPayField.getText().equals("")) {
            payment.setGarbagePayment(garbageMustPay);
        } else {
            payment.setDefaultGarbagePayment();
        }

        payment.payForEverything();
        return payment;
    }

    public void showDialogFillFields(Payment payment, boolean refillAllFieldsForEditing, boolean fillSetFields,
                                     TariffsData tariffsData) {
        monthField.setText(String.valueOf(payment.month));
        yearField.setText(String.valueOf(payment.year));

        if (fillSetFields) {
            electroTariff1Field.setText(String.valueOf(payment.electroTariff1));
            electroTariff2Field.setText(String.valueOf(payment.electroTariff2));
            electroTariff3Field.setText(String.valueOf(payment.electroTariff3));
            electroTariff4Field.setText(String.valueOf(payment.electroTariff4));
            electroLimit1Field.setText(String.valueOf(payment.electroLimit1));
            electroLimit2Field.setText(String.valueOf(payment.electroLimit2));
            electroLimit3Field.setText(String.valueOf(payment.electroLimit3));
            waterTariffField.setText(String.valueOf(payment.waterTariff));
            flatTariffField.setText(String.valueOf(payment.flatTariff));
            heatingTariffField.setText(String.valueOf(payment.heatingTariff));
            garbageTariffField.setText(String.valueOf(payment.garbageTariff));
        }
        else {
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

        electroStartField.setText(String.valueOf(payment.electroStart));
        electroEndField.setText(String.valueOf(payment.electroEnd));

        waterStartField.setText(String.valueOf(payment.waterStart));
        waterEndField.setText(String.valueOf(payment.waterEnd));


        /* these fields' data is collected from the basis payment only if it's being edited */
        if (refillAllFieldsForEditing || fillSetFields) {

            if (payment.electroPaymentSet) {
                electroBySet.setSelected(true);
                electroMustPayField.setText(String.valueOf(payment.electroMustPay));
            }
            if (payment.waterPaymentSet) {
                waterBySet.setSelected(true);
                waterMustPayField.setText(String.valueOf(payment.waterMustPay));
            }
            if (payment.flatPaymentSet) {
                flatBySet.setSelected(true);
                flatMustPayField.setText(String.valueOf(payment.flatMustPay));
            }
            if (payment.heatingPaymentSet) {
                heatingBySet.setSelected(true);
                heatingMustPayField.setText(String.valueOf(payment.heatingMustPay));
            }
            if (payment.garbagePaymentSet) {
                garbageBySet.setSelected(true);
                garbageMustPayField.setText(String.valueOf(payment.garbageMustPay));
            }

            if (refillAllFieldsForEditing) {
                electroStartField.setText(String.valueOf(payment.electroStart));
                electroEndField.setText(String.valueOf(payment.electroEnd));
                waterStartField.setText(String.valueOf(payment.waterStart));
                waterEndField.setText(String.valueOf(payment.waterEnd));
            }
        }
    }

}
