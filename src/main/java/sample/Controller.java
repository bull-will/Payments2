package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

import java.io.*;
import java.util.Optional;

public class Controller {
    @FXML
    private BorderPane mainBorderPane;

    private PaymentsData paymentsData;

    private TariffsDataViaProperties tariffsData;

    private Payment payment;

    @FXML
    private ListView<Payment> paymentsListView;

    @FXML
    private TextArea paymentTextArea;

    @FXML
    private ContextMenu listContextMenu;

    private Payment toBeSelected;

    public void initialize() {
        paymentsData = new PaymentsData();
        tariffsData = new TariffsDataViaProperties();
        paymentsData.loadPayments();
        tariffsData.loadTariffs();

        /* Creating a context menu for payment itenms in the list in the window */
        listContextMenu = new ContextMenu();
        MenuItem editMenuItem = new MenuItem("Редактировать");
        editMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                editPayment(paymentsListView.getSelectionModel().getSelectedItem());
            }
        });
        MenuItem newPaymenBasedMenuItem = new MenuItem("Новый платеж на основе выделенного");
        newPaymenBasedMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                newPayment(paymentsListView.getSelectionModel().getSelectedItem(), true);
            }
        });
        MenuItem deleteMenuItem = new MenuItem("Удалить");
        deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                deletePayment(paymentsListView.getSelectionModel().getSelectedItem());
            }
        });
        listContextMenu.getItems().addAll(editMenuItem, newPaymenBasedMenuItem, deleteMenuItem);


        paymentsListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Payment>() {
            @Override
            public void changed(ObservableValue<? extends Payment> observable, Payment oldvalue, Payment newValue) {
                if (newValue != null) {
                    Payment item = paymentsListView.getSelectionModel().getSelectedItem();
                    paymentTextArea.setText(item.printContact());
                }
            }
        });

        /* Binding the list of payments to the list view in the main window*/
        paymentsListView.setItems(paymentsData.getPayments());
        paymentsListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        paymentsListView.getSelectionModel().selectFirst();

        paymentsListView.setCellFactory(new Callback<ListView<Payment>, ListCell<Payment>>() {
            @Override
            public ListCell<Payment> call(ListView<Payment> paymentListView) {
                ListCell<Payment> cell = new ListCell<Payment>() {
                    @Override
                    protected void updateItem(Payment item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            setText(item.name);
                        }
                    }
                };

                cell.emptyProperty().addListener(
                        (abs, wasEmpty, isNowEmpty) -> {
                            if (isNowEmpty) {
                                cell.setContextMenu(null);
                            } else {
                                cell.setContextMenu(listContextMenu);
                            }
                        });

                return cell;
            }
        });
    }

    @FXML
    public void handleClickListView() {
        Payment payment = paymentsListView.getSelectionModel().getSelectedItem();
        paymentTextArea.setText(payment.printContact());
    }

    @FXML
    public void handleUpdateListView() {

    }

    @FXML
    public void newPayment(ActionEvent actionEvent) {
        Payment baseForNewPayment;
        if (paymentsData.getPayments().isEmpty()) {
            /* If there were no payments before */
            baseForNewPayment = new Payment(0, 0, 0, 0, 0, 0);
        } else {
            /* let's make a new payment on the basis of the last one */
            Payment lastPayment = paymentsData.getLastPayment();

            /* Setting the month and the year for the new payment */
            int lastPaymentMonth = lastPayment.month;
            int lastPaymentYear = lastPayment.year;
            int newPaymentYear = lastPaymentYear;
            int newPaymentMonth;
            if (lastPaymentMonth < 1 || lastPaymentMonth > 12) {
                newPaymentMonth = lastPaymentMonth;
            } else if (lastPaymentMonth == 12) {
                newPaymentMonth = 1;
                newPaymentYear++;
            } else {
                newPaymentMonth = lastPaymentMonth + 1;
            }

            baseForNewPayment = new Payment(newPaymentYear, newPaymentMonth,
                    lastPayment.electroEnd /*the last end indications are the current first indications*/, lastPayment.electroEnd,
                    lastPayment.waterEnd /*the last end indications are the current first indications*/, lastPayment.waterEnd);
        }
        newPayment(baseForNewPayment, false);
    }

    @FXML
    public void newPaymentBased(ActionEvent actionEvent) {
        Payment selectedPayment = paymentsListView.getSelectionModel().getSelectedItem();
        if (selectedPayment == null) {
            Alerts.alertInfo("Нет выбранного платежа", "Вы хотите создать платеж на основании выделенного,\n" +
                    "но ничего не выделено");
            return;
        } else {
            newPayment(selectedPayment.clone(), true);
        }
    }

    private void newPayment(Payment basePayment, boolean fillSetFields) {

        /* The payment is to be processed and updated */
        Payment newPayment = processPaymentWithDialog(basePayment, false, fillSetFields);
        if (newPayment != null) {
            paymentsData.addPayment(newPayment);
            paymentsData.save();
            paymentsListView.getSelectionModel().select(newPayment);
        } else {
            System.out.println("Creating the new payment cancelled");
        }
        /* Just for info */
        paymentsData.printAll();
    }

    @FXML
    public void editPayment(ActionEvent actionEvent) {
        payment = paymentsListView.getSelectionModel().getSelectedItem();
        if (payment != null) {
            editPayment(payment);
            paymentTextArea.setText(payment.printContact());
        } else {
            Alerts.alertInfo("Нечего редактировать", "Нечего редактировать, нет выделенного платежа");
            return;
        }
    }

    private void editPayment(Payment payment) {
        Payment editedPayment = processPaymentWithDialog(payment, true, true);
        if (editedPayment != null) {
            payment = editedPayment;
        } else {
            System.out.println("Payment not edited");
        }
        paymentsListView.refresh();
        paymentsData.save();
        /* for info */
        paymentsData.printAll();
    }

    /* for both newPayment() and editPayment() */
    public Payment processPaymentWithDialog(Payment payment, boolean editPaymentOrNot, boolean fillSetFields) {
        /* for keeping the selected payment selected in a case of cancellation */
        toBeSelected = payment;

        Dialog dialog = new Dialog();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Данные платежа");
        dialog.setHeaderText(editPaymentOrNot ? "Редактирование платежа" : "Новый платеж");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getClassLoader().getResource("paymentDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Couldn't loadPayments the dialog");
            e.printStackTrace();
            return null;
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        PaymentDialogController paymentDialog = fxmlLoader.getController();

        /* let's fill dialog window's fields with numbers from the basis payment (new/being edited) */
        paymentDialog.showDialogFillFields(payment, editPaymentOrNot, fillSetFields, tariffsData);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent()) {
            if (result.get() == ButtonType.OK) {
                Payment processedPayment = paymentDialog.processPayment(payment);
                return processedPayment; /* to editPayment() as well as to newPayment() */
            }
            if (result.get() == ButtonType.CANCEL) {
//                paymentsListView.getSelectionModel().select(toBeSelected);
            }
        }
        return null;
    }

    @FXML
    public void deletePayment(ActionEvent actionEvent) {
        Payment selectedPayment = paymentsListView.getSelectionModel().getSelectedItem();
        if (selectedPayment == null) {
            if (paymentsData.getPayments().isEmpty()) {
                Alerts.alertInfo("Нечего удалить", "Платеж для удаления не выбран");
                return;
            }
        } else deletePayment(selectedPayment);
    }

    public void deletePayment(Payment paymentToDelete) {
        Alert alert = Alerts.alertConfirmation("Удаление платежа",
                "Удаление платежа: " + paymentToDelete.name,
                "Нажмите ОК чтобы удалить или Cancel чтобы отменить");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            paymentsData.deletePayment(paymentToDelete);
            paymentTextArea.clear();
            paymentsListView.refresh();
            paymentsData.save();
            /* for info */
            paymentsData.printAll();
//            paymentsListView.getSelectionModel().selectLast();
        }
    }

    public void handleKeyPressed(KeyEvent keyEvent) {
        Payment selectedPayment = paymentsListView.getSelectionModel().getSelectedItem();
        if (selectedPayment != null) {
            if (keyEvent.getCode().equals(KeyCode.DELETE)) {
                deletePayment(selectedPayment);
            }
        }
    }

    public void tariffsDialog(ActionEvent actionEvent) {
        Dialog dialog = new Dialog();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Тарифы");
        dialog.setHeaderText("Посмотрите или отредактируйте тарифы");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getClassLoader().getResource("tariffsDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Couldn't loadPayments the dialog");
            e.printStackTrace();
            return;
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        TariffsDialogController tariffsDialog = fxmlLoader.getController();

        /* let's fill dialog window's fields with numbers from the basis payment (new/being edited) */
        tariffsDialog.showDialogFillFields(tariffsData);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            tariffsData = tariffsDialog.processTariffs(tariffsData);
            tariffsData.save();
        }
    }

    public void toDoc(ActionEvent actionEvent) throws IOException {
        Payment paymentToDoc = paymentsListView.getSelectionModel().getSelectedItem();
        if (paymentToDoc == null) {
            Alerts.alertInfo("Не выбран платеж", "Вы хотите вывести выделенный платеж в .doc-файл,\n" +
                    "но ничего не выделено");
            return;
        }

        File outPutFileToPrint = makeOutPutFile(paymentToDoc.name);

        /* Now this method makes output stream writer for the .doc file */
        OutputStreamWriter dataOut = makeWriter(outPutFileToPrint);

        /* And now let's fill the file with contents */
        try {
            writeLinesToFile(paymentToDoc, dataOut);
            Alerts.alertInfo("Записано в .doc-файл", "Платеж записан в .doc-файл " + outPutFileToPrint.getPath());
        } catch (IOException e) {
            Alerts.alertInfo("Ошибка записи в .doc", "Запсиать данные платежа в .doc-файл почему-то не удалось");
        }
    }

    static File makeOutPutFile(String paymentName) throws IOException {
        /* This method sets a directory, a name and an extension for output file */
        String workingDir = System.getProperty("user.dir"); // Files will be stored in the project directory
        String dirForGeneratedFiles = workingDir + "\\Платежи\\"; // setting a directory for generated files
        if (!new File(dirForGeneratedFiles).exists()) {
            new File(dirForGeneratedFiles).mkdir();
        }
        String outPutFileName = dirForGeneratedFiles + paymentName;
        String extension = ".doc"; // set the extension of the output file

        // the following section changes the file name if a file with this name already exists:
        int counter = 0; // has to be given to the next method
        if (new File(outPutFileName + extension).exists())
            outPutFileName = changeOutPutFileName(outPutFileName, extension, counter);
        return new File(outPutFileName + extension);
    }

    static OutputStreamWriter makeWriter(File outPutFileToPrint) throws FileNotFoundException {
        BufferedOutputStream outputStream
                = new BufferedOutputStream(
                new FileOutputStream(
                        outPutFileToPrint));
        return new OutputStreamWriter(outputStream);
    }

    private void writeLinesToFile(Payment paymentToDoc, OutputStreamWriter dataOut) throws IOException {

        String writeLine = paymentToDoc.printContact();
        dataOut.write(writeLine);
        dataOut.close();
    }

    static String changeOutPutFileName(String outPutFileName, String extension, int counter) {
        counter++;
        if (new File(outPutFileName + "_" + counter + extension).exists()) {
            return changeOutPutFileName(outPutFileName, extension, counter);
        } else {
            return outPutFileName + "_" + counter;
        }
    }
}