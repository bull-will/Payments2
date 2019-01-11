package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.stream.*;
import javax.xml.stream.events.*;
import java.io.*;

public class PaymentsData {

    private static String PAYMENTS_FILE;
    private static final String PAYMENTS = "payments";
    private static final String PAYMENT = "payment";

    private static final String YEAR = "year";
    private static final String MONTH = "month";
    private static final String NAME = "name";
    private static final String NAME_TO_PRINT_IS_SET = "name_to_print_is_set";
    private static final String ELECTRO_TARIFF_1 = "electro_tariff_1";
    private static final String ELECTRO_LIMIT_1 = "electro_limit_1";
    private static final String ELECTRO_TARIFF_2 = "electro_tariff_2";
    private static final String ELECTRO_LIMIT_2 = "electro_limit_2";
    private static final String ELECTRO_TARIFF_3 = "electro_tariff_3";
    private static final String ELECTRO_LIMIT_3 = "electro_limit_3";
    private static final String ELECTRO_TARIFF_4 = "electro_tariff_4";
    private static final String HEATING_TARIFF = "heating_tariff";
    private static final String WATER_TARIFF = "water_tariff";
    private static final String FLAT_TARIFF = "flat_tariff";
    private static final String GARBAGE_TARIFF = "garbage_tariff";

    private static final String HEATING_TARIFF_TO_PRINT = "heating_tariff_to_print";
    private static final String WATER_TARIFF_TO_PRINT = "water_tariff_to_print";
    private static final String FLAT_TARIFF_TO_PRINT = "flat_tariff_to_print";
    private static final String GARBAGE_TARIFF_TO_PRINT = "garbage_tariff_to_print";

    private static final String ELECTRO_MUST_PAY = "electro_must_pay";
    private static final String ELECTRO_PAYMENT_SET = "electro_payment_set";
    private static final String HEATING_MUST_PAY = "heating_must_pay";
    private static final String HEATING_PAYMENT_SET = "heating_payment_set";
    private static final String WATER_MUST_PAY = "water_must_pay";
    private static final String WATER_PAYMENT_SET = "water_payment_set";
    private static final String FLAT_MUST_PAY = "flat_must_pay";
    private static final String FLAT_PAYMENT_SET = "flat_payment_set";
    private static final String GARBAGE_MUST_PAY = "garbage_must_pay";
    private static final String GARBAGE_PAYMENT_SET = "garbage_payment_set";

    private static final String ELECTRO_START = "electro_start";
    private static final String ELECTRO_END = "electro_end";
    private static final String KWATT_CONSUMED = "kwatt_consumed";
    private static final String WATER_START = "water_start";
    private static final String WATER_END = "water_end";
    private static final String M3_CONSUMED = "m3_consumed";

    private static final String PAYMENT_FOR_ELECTRICITY = "payment_for_electricity";
    private static final String PAYMENT_FOR_HEATING = "payment_for_heating";
    private static final String PAYMENT_FOR_WATER = "payment_for_water";
    private static final String PAYMENT_FOR_FLAT = "payment_for_flat";
    private static final String PAYMENT_FOR_GARBAGE = "payment_for_garbage";
    private static final String TOTAL = "total";


    private ObservableList<Payment> payments;

    public PaymentsData() {
        try {
            /* looks like not so beautiful */
            PAYMENTS_FILE =  (new File(".").getCanonicalPath().endsWith("target") ?
                    "classes/payments.xml" : ("src/main/resources/payments.xml"));
        } catch (IOException e) {
            Alerts.alertInfo("Ошибка обработки файла тарифов",
                    "Не удалось получить путь файла тарифов\n" +
                            "Загружены тарифы по умолчанию");
            e.printStackTrace();
        }
        payments = FXCollections.observableArrayList();
    }

    public ObservableList<Payment> getPayments() {
        return payments;
    }

    public void addPayment(Payment payment) {
        if (payment == null) {
//            System.out.println("Trying to add null was futile");
            Alerts.alertInfo("Null", "Попытка добавить null " +
                    "вместо нормального платежа безуспешна");
            return;
        }
        payments.add(payment);
    }

    public Payment getLastPayment() {
        if (payments.isEmpty()) return null;
        return payments.get(payments.size() - 1);
    }

    public void deletePayment(Payment payment) {
        payments.remove(payment);
    }

    public void printAll() {
        if (payments.size() == 0) {
            System.out.println("List of payments is empty");
            return;
        }
        System.out.println(" - - List of payments: - - ");
        for (Payment payment : payments) {
            System.out.println(payment.printPayment());
        }
    }

    public void loadPayments() {
        InputStream in = null;
        try {
            // First, create a new XMLInputFactory

            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            // Setup a new eventReader
            in = new FileInputStream(PAYMENTS_FILE);
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in, "UTF-8");

            // read the XML document
            Payment payment = null;


            while (eventReader.hasNext()) {

                XMLEvent event = eventReader.nextEvent();
                try {
                    //for testing and info
//                try {
//                    currentDir = new java.io.File( "." ).getCanonicalPath();
//                    Alerts.alertInfo("Обрабатываемое занчение xml:", String.valueOf(event));
////            PAYMENTS_FILE = currentDir + "\\" + PAYMENTS_FILE;
//                } catch (IOException e) {
//                    Alerts.alertInfo("Ошибка", "Ошибка получения адреса рабочей директории");
//                }
                    // for testing and info


                    if (event.isStartElement()) {
                        StartElement startElement = event.asStartElement();
                        // If we have a contact item, we create a new contact
                        if (startElement.getName().getLocalPart().equals(PAYMENT)) {
                            payment = new Payment(0, 0, 0, 0, 0, 0);
                            continue;
                        }

                        /* ↓↓↓ 320 lines of 40 "if's" for parsing all the fields of the payment ↓↓↓ */

                        if (event.isStartElement()) {
                            if (event.asStartElement().getName().getLocalPart()
                                    .equals(YEAR)) {
                                event = eventReader.nextEvent();
                                payment.year = Integer.parseInt(event.asCharacters().getData());
                                continue;
                            }
                        }
                        if (event.asStartElement().getName().getLocalPart()
                                .equals(MONTH)) {
                            event = eventReader.nextEvent();
                            payment.month = Integer.parseInt(event.asCharacters().getData());
                            continue;
                        }

                        if (event.asStartElement().getName().getLocalPart()
                                .equals(NAME)) {
                            event = eventReader.nextEvent();
                            payment.name.set(event.asCharacters().getData());
                            continue;
                        }

                        if (event.asStartElement().getName().getLocalPart()
                                .equals(ELECTRO_TARIFF_1)) {
                            event = eventReader.nextEvent();
                            payment.electroTariff1 = Double.parseDouble(event.asCharacters().getData());
                            continue;
                        }

                        if (event.asStartElement().getName().getLocalPart()
                                .equals(ELECTRO_LIMIT_1)) {
                            event = eventReader.nextEvent();
                            payment.electroLimit1 = Integer.parseInt(event.asCharacters().getData());
                            continue;
                        }

                        if (event.asStartElement().getName().getLocalPart()
                                .equals(ELECTRO_TARIFF_2)) {
                            event = eventReader.nextEvent();
                            payment.electroTariff2 = Double.parseDouble(event.asCharacters().getData());
                            continue;
                        }

                        if (event.asStartElement().getName().getLocalPart()
                                .equals(ELECTRO_LIMIT_2)) {
                            event = eventReader.nextEvent();
                            payment.electroLimit2 = Integer.parseInt(event.asCharacters().getData());
                            continue;
                        }

                        if (event.asStartElement().getName().getLocalPart()
                                .equals(ELECTRO_TARIFF_3)) {
                            event = eventReader.nextEvent();
                            payment.electroTariff3 = Double.parseDouble(event.asCharacters().getData());
                            continue;
                        }

                        if (event.asStartElement().getName().getLocalPart()
                                .equals(ELECTRO_LIMIT_3)) {
                            event = eventReader.nextEvent();
                            payment.electroLimit3 = Integer.parseInt(event.asCharacters().getData());
                            continue;
                        }

                        if (event.asStartElement().getName().getLocalPart()
                                .equals(ELECTRO_TARIFF_4)) {
                            event = eventReader.nextEvent();
                            payment.electroTariff4 = Double.parseDouble(event.asCharacters().getData());
                            continue;
                        }

                        if (event.asStartElement().getName().getLocalPart()
                                .equals(HEATING_TARIFF)) {
                            event = eventReader.nextEvent();
                            payment.heatingTariff = Double.parseDouble(event.asCharacters().getData());
                            continue;
                        }

                        if (event.asStartElement().getName().getLocalPart()
                                .equals(WATER_TARIFF)) {
                            event = eventReader.nextEvent();
                            payment.waterTariff = Double.parseDouble(event.asCharacters().getData());
                            continue;
                        }

                        if (event.asStartElement().getName().getLocalPart()
                                .equals(FLAT_TARIFF)) {
                            event = eventReader.nextEvent();
                            payment.flatTariff = Double.parseDouble(event.asCharacters().getData());
                            continue;
                        }

                        if (event.asStartElement().getName().getLocalPart()
                                .equals(GARBAGE_TARIFF)) {
                            event = eventReader.nextEvent();
                            payment.garbageTariff = Double.parseDouble(event.asCharacters().getData());
                            continue;
                        }

                        if (event.asStartElement().getName().getLocalPart()
                                .equals(HEATING_TARIFF_TO_PRINT)) {
                            event = eventReader.nextEvent();
                            payment.heatingTariffToPrint = Double.parseDouble(event.asCharacters().getData());
                            continue;
                        }

                        if (event.asStartElement().getName().getLocalPart()
                                .equals(WATER_TARIFF_TO_PRINT)) {
                            event = eventReader.nextEvent();
                            payment.waterTariffToPrint = Double.parseDouble(event.asCharacters().getData());
                            continue;
                        }

                        if (event.asStartElement().getName().getLocalPart()
                                .equals(FLAT_TARIFF_TO_PRINT)) {
                            event = eventReader.nextEvent();
                            payment.flatTariffToPrint = Double.parseDouble(event.asCharacters().getData());
                            continue;
                        }

                        if (event.asStartElement().getName().getLocalPart()
                                .equals(GARBAGE_TARIFF_TO_PRINT)) {
                            event = eventReader.nextEvent();
                            payment.garbageTariffToPrint = Double.parseDouble(event.asCharacters().getData());
                            continue;
                        }

                        if (event.asStartElement().getName().getLocalPart()
                                .equals(ELECTRO_MUST_PAY)) {
                            event = eventReader.nextEvent();
                            payment.electroMustPay = Double.parseDouble(event.asCharacters().getData());
                            continue;
                        }

                        if (event.asStartElement().getName().getLocalPart()
                                .equals(ELECTRO_PAYMENT_SET)) {
                            event = eventReader.nextEvent();
                            payment.electroPaymentSet = Boolean.parseBoolean(event.asCharacters().getData());
                            continue;
                        }

                        if (event.asStartElement().getName().getLocalPart()
                                .equals(HEATING_MUST_PAY)) {
                            event = eventReader.nextEvent();
                            payment.heatingMustPay = Double.parseDouble(event.asCharacters().getData());
                            continue;
                        }

                        if (event.asStartElement().getName().getLocalPart()
                                .equals(HEATING_PAYMENT_SET)) {
                            event = eventReader.nextEvent();
                            payment.heatingPaymentSet = Boolean.parseBoolean(event.asCharacters().getData());
                            continue;
                        }

                        if (event.asStartElement().getName().getLocalPart()
                                .equals(WATER_MUST_PAY)) {
                            event = eventReader.nextEvent();
                            payment.waterMustPay = Double.parseDouble(event.asCharacters().getData());
                            continue;
                        }

                        if (event.asStartElement().getName().getLocalPart()
                                .equals(WATER_PAYMENT_SET)) {
                            event = eventReader.nextEvent();
                            payment.waterPaymentSet = Boolean.parseBoolean(event.asCharacters().getData());
                            continue;
                        }

                        if (event.asStartElement().getName().getLocalPart()
                                .equals(FLAT_MUST_PAY)) {
                            event = eventReader.nextEvent();
                            payment.flatMustPay = Double.parseDouble(event.asCharacters().getData());
                            continue;
                        }

                        if (event.asStartElement().getName().getLocalPart()
                                .equals(FLAT_PAYMENT_SET)) {
                            event = eventReader.nextEvent();
                            payment.flatPaymentSet = Boolean.parseBoolean(event.asCharacters().getData());
                            continue;
                        }

                        if (event.asStartElement().getName().getLocalPart()
                                .equals(GARBAGE_MUST_PAY)) {
                            event = eventReader.nextEvent();
                            payment.garbageMustPay = Double.parseDouble(event.asCharacters().getData());
                            continue;
                        }

                        if (event.asStartElement().getName().getLocalPart()
                                .equals(GARBAGE_PAYMENT_SET)) {
                            event = eventReader.nextEvent();
                            payment.garbagePaymentSet = Boolean.parseBoolean(event.asCharacters().getData());
                            continue;
                        }

                        if (event.asStartElement().getName().getLocalPart()
                                .equals(ELECTRO_START)) {
                            event = eventReader.nextEvent();
                            payment.electroStart = Integer.parseInt(event.asCharacters().getData());
                            continue;
                        }

                        if (event.asStartElement().getName().getLocalPart()
                                .equals(ELECTRO_END)) {
                            event = eventReader.nextEvent();
                            payment.electroEnd = Integer.parseInt(event.asCharacters().getData());
                            continue;
                        }

                        if (event.asStartElement().getName().getLocalPart()
                                .equals(KWATT_CONSUMED)) {
                            event = eventReader.nextEvent();
                            payment.kWattConsumed = Integer.parseInt(event.asCharacters().getData());
                            continue;
                        }

                        if (event.asStartElement().getName().getLocalPart()
                                .equals(WATER_START)) {
                            event = eventReader.nextEvent();
                            payment.waterStart = Integer.parseInt(event.asCharacters().getData());
                            continue;
                        }

                        if (event.asStartElement().getName().getLocalPart()
                                .equals(WATER_END)) {
                            event = eventReader.nextEvent();
                            payment.waterEnd = Integer.parseInt(event.asCharacters().getData());
                            continue;
                        }

                        if (event.asStartElement().getName().getLocalPart()
                                .equals(M3_CONSUMED)) {
                            event = eventReader.nextEvent();
                            payment.m3consumed = Integer.parseInt(event.asCharacters().getData());
                            continue;
                        }

                        if (event.asStartElement().getName().getLocalPart()
                                .equals(PAYMENT_FOR_ELECTRICITY)) {
                            event = eventReader.nextEvent();
                            payment.paymentForElectricity = Integer.parseInt(event.asCharacters().getData());
                            continue;
                        }

                        if (event.asStartElement().getName().getLocalPart()
                                .equals(PAYMENT_FOR_WATER)) {
                            event = eventReader.nextEvent();
                            payment.paymentForWater = Integer.parseInt(event.asCharacters().getData());
                            continue;
                        }

                        if (event.asStartElement().getName().getLocalPart()
                                .equals(PAYMENT_FOR_HEATING)) {
                            event = eventReader.nextEvent();
                            payment.paymentForHeating = Integer.parseInt(event.asCharacters().getData());
                            continue;
                        }

                        if (event.asStartElement().getName().getLocalPart()
                                .equals(PAYMENT_FOR_FLAT)) {
                            event = eventReader.nextEvent();
                            payment.paymentForFlat = Integer.parseInt(event.asCharacters().getData());
                            continue;
                        }

                        if (event.asStartElement().getName().getLocalPart()
                                .equals(PAYMENT_FOR_GARBAGE)) {
                            event = eventReader.nextEvent();
                            payment.paymentForGarbage = Integer.parseInt(event.asCharacters().getData());
                            continue;
                        }

                        if (event.asStartElement().getName().getLocalPart()
                                .equals(TOTAL)) {
                            event = eventReader.nextEvent();
                            payment.total = Integer.parseInt(event.asCharacters().getData());
                            continue;
                        }
                    }

                    // If we reach the end of a contact element, we add it to the list
                    if (event.isEndElement()) {
                        EndElement endElement = event.asEndElement();
                        if (endElement.getName().getLocalPart().equals(PAYMENT)) {
                            payments.add(payment);
                        }
                    }

                } catch (XMLStreamException xlmse) {
                    Alerts.alertInfo("Проблема потока чтения",
                            "В потоке чтения платежей произошла какая-то ошибка\n" +
                                    "Часть одного из платежей считана неправильно.\n" +
                                    "Можете изучить платежи и найти, что не так\n" +
                                    xlmse.getMessage()
                    );
                }
            }
        } catch (FileNotFoundException fnfe) {
            Alerts.alertInfo("Ошибка чтения",
                    "Проблема чтения файла сохраненных платежей." +
                            "\nФайл не существует, а может быть, поврежден\n" +
                            fnfe.getMessage()
            );
        } catch (XMLStreamException xlmse) {
            Alerts.alertInfo("Проблема чтения",
                    "Проблема при чтении файла сохраненных платежей" +
                            "\nЧто-то пошло не так в потоке чтения xml\n" +
                            xlmse.getMessage()
            );
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ioe) {
                    Alerts.alertInfo("Проблема чтения",
                            "Проблема при чтении файла сохраненных платежей" +
                                    "\nНе удалось закрыть поток чтения\n" +
                                    ioe.getMessage()
                    );
                }
            }
        }
    }

    public void save() {

        try {
            FileOutputStream out = new FileOutputStream(PAYMENTS_FILE);

            // create an XMLOutputFactory
            XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
            // create XMLEventWriter
            XMLEventWriter eventWriter = outputFactory
                    .createXMLEventWriter(out, "UTF-8");
            // create an EventFactory
            XMLEventFactory eventFactory = XMLEventFactory.newInstance();
            XMLEvent end = eventFactory.createDTD("\n");
            // create and write Start Tag
            StartDocument startDocument = eventFactory.createStartDocument();
            eventWriter.add(startDocument);
            eventWriter.add(end);

            StartElement contactsStartElement = eventFactory.createStartElement("",
                    "", PAYMENTS);
            eventWriter.add(contactsStartElement);
            eventWriter.add(end);

            for (Payment payment : payments) {
                savePayment(eventWriter, eventFactory, payment);
            }

            eventWriter.add(eventFactory.createEndElement("", "", "contacts"));
            eventWriter.add(end);
            eventWriter.add(eventFactory.createEndDocument());
            eventWriter.close();
        } catch (FileNotFoundException e) {
            Alerts.alertInfo("Проблема записи",
                    "Проблема записи в файл сохраненных платежей.\nВозможно, файл не существует");
        } catch (XMLStreamException e) {
            Alerts.alertInfo("Проблема записи",
                    "Проблема записи в файл сохраненных платежей\nЧто-то пошло не так в потоке записи");
        }
    }

    private void savePayment(XMLEventWriter eventWriter, XMLEventFactory eventFactory, Payment payment)
            throws FileNotFoundException, XMLStreamException {

        XMLEvent end = eventFactory.createDTD("\n");

        // create contact open tag
        StartElement configStartElement = eventFactory.createStartElement("",
                "", PAYMENT);
        eventWriter.add(configStartElement);
        eventWriter.add(end);
        // Write the different nodes
        NodeCreator.createNode(eventWriter, YEAR, String.valueOf(payment.year));
        NodeCreator.createNode(eventWriter, MONTH, String.valueOf(payment.month));
        NodeCreator.createNode(eventWriter, NAME, payment.name.get());
        NodeCreator.createNode(eventWriter, ELECTRO_TARIFF_1, String.valueOf(payment.electroTariff1));
        NodeCreator.createNode(eventWriter, ELECTRO_LIMIT_1, String.valueOf(payment.electroLimit1));
        NodeCreator.createNode(eventWriter, ELECTRO_TARIFF_2, String.valueOf(payment.electroTariff2));
        NodeCreator.createNode(eventWriter, ELECTRO_LIMIT_2, String.valueOf(payment.electroLimit2));
        NodeCreator.createNode(eventWriter, ELECTRO_TARIFF_3, String.valueOf(payment.electroTariff3));
        NodeCreator.createNode(eventWriter, ELECTRO_LIMIT_3, String.valueOf(payment.electroLimit3));
        NodeCreator.createNode(eventWriter, ELECTRO_TARIFF_4, String.valueOf(payment.electroTariff4));
        NodeCreator.createNode(eventWriter, HEATING_TARIFF, String.valueOf(payment.heatingTariff));
        NodeCreator.createNode(eventWriter, WATER_TARIFF, String.valueOf(payment.waterTariff));
        NodeCreator.createNode(eventWriter, FLAT_TARIFF, String.valueOf(payment.flatTariff));
        NodeCreator.createNode(eventWriter, GARBAGE_TARIFF, String.valueOf(payment.garbageTariff));
        NodeCreator.createNode(eventWriter, HEATING_TARIFF_TO_PRINT, String.valueOf(payment.heatingTariffToPrint));
        NodeCreator.createNode(eventWriter, WATER_TARIFF_TO_PRINT, String.valueOf(payment.waterTariffToPrint));
        NodeCreator.createNode(eventWriter, FLAT_TARIFF_TO_PRINT, String.valueOf(payment.flatTariffToPrint));
        NodeCreator.createNode(eventWriter, GARBAGE_TARIFF_TO_PRINT, String.valueOf(payment.garbageTariffToPrint));
        NodeCreator.createNode(eventWriter, ELECTRO_MUST_PAY, String.valueOf(payment.electroMustPay));
        NodeCreator.createNode(eventWriter, ELECTRO_PAYMENT_SET, String.valueOf(payment.electroPaymentSet));
        NodeCreator.createNode(eventWriter, HEATING_MUST_PAY, String.valueOf(payment.heatingMustPay));
        NodeCreator.createNode(eventWriter, HEATING_PAYMENT_SET, String.valueOf(payment.heatingPaymentSet));
        NodeCreator.createNode(eventWriter, WATER_MUST_PAY, String.valueOf(payment.waterMustPay));
        NodeCreator.createNode(eventWriter, WATER_PAYMENT_SET, String.valueOf(payment.waterPaymentSet));
        NodeCreator.createNode(eventWriter, FLAT_MUST_PAY, String.valueOf(payment.flatMustPay));
        NodeCreator.createNode(eventWriter, FLAT_PAYMENT_SET, String.valueOf(payment.flatPaymentSet));
        NodeCreator.createNode(eventWriter, GARBAGE_MUST_PAY, String.valueOf(payment.garbageMustPay));
        NodeCreator.createNode(eventWriter, GARBAGE_PAYMENT_SET, String.valueOf(payment.garbagePaymentSet));
        NodeCreator.createNode(eventWriter, ELECTRO_START, String.valueOf(payment.electroStart));
        NodeCreator.createNode(eventWriter, ELECTRO_END, String.valueOf(payment.electroEnd));
        NodeCreator.createNode(eventWriter, KWATT_CONSUMED, String.valueOf(payment.kWattConsumed));
        NodeCreator.createNode(eventWriter, WATER_START, String.valueOf(payment.waterStart));
        NodeCreator.createNode(eventWriter, WATER_END, String.valueOf(payment.waterEnd));
        NodeCreator.createNode(eventWriter, M3_CONSUMED, String.valueOf(payment.m3consumed));
        NodeCreator.createNode(eventWriter, PAYMENT_FOR_ELECTRICITY, String.valueOf(payment.paymentForElectricity));
        NodeCreator.createNode(eventWriter, PAYMENT_FOR_WATER, String.valueOf(payment.paymentForWater));
        NodeCreator.createNode(eventWriter, PAYMENT_FOR_HEATING, String.valueOf(payment.paymentForHeating));
        NodeCreator.createNode(eventWriter, PAYMENT_FOR_FLAT, String.valueOf(payment.paymentForFlat));
        NodeCreator.createNode(eventWriter, PAYMENT_FOR_GARBAGE, String.valueOf(payment.paymentForGarbage));
        NodeCreator.createNode(eventWriter, TOTAL, String.valueOf(payment.total));

        eventWriter.add(eventFactory.createEndElement("", "", PAYMENT));
        eventWriter.add(end);
    }


}
