package sample;

import javafx.scene.control.Alert;

import javax.xml.stream.*;
import javax.xml.stream.events.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

@Deprecated
public class TariffsData {

    private static final String TARIFFS_FILE = "tariffs.xml";

    private static final String TARIFFS = "tariffs";
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

    private final double electroTariff1Original = 0.3084;
    private final int electroLimit1Original = 75;
    private final double electroTariff2Original = 0.6168;
    private final int electroLimit2Original = 150;
    private final double electroTariff3Original = 0.8388;
    private final int electroLimit3Original = 800;
    private final double electroTariff4Original = 2.6808;
    private final double heatingTariffOriginal = 270.11;
    private final double waterTariffOriginal = 14.0;
    private final double flatTariffOriginal = 197.0;
    private final double garbageTariffOriginal = 22.0;

    double electroTariff1;
    int electroLimit1;
    double electroTariff2;
    int electroLimit2;
    double electroTariff3;
    int electroLimit3;
    double electroTariff4;
    double heatingTariff;
    double waterTariff;
    double flatTariff;
    double garbageTariff;

    public TariffsData() {
        this.electroTariff1 = electroTariff1Original;
        this.electroLimit1 = electroLimit1Original;
        this.electroTariff2 = electroTariff2Original;
        this.electroLimit2 = electroLimit2Original;
        this.electroTariff3 = electroTariff3Original;
        this.electroLimit3 = electroLimit3Original;
        this.electroTariff4 = electroTariff4Original;
        this.heatingTariff = heatingTariffOriginal;
        this.waterTariff = waterTariffOriginal;
        this.flatTariff = flatTariffOriginal;
        this.garbageTariff = garbageTariffOriginal;
    }

    public TariffsData(double electroTariff1, int electroLimit1, double electroTariff2, int electroLimit2,
                       double electroTariff3, int electroLimit3, double electroTariff4,
                       double heatingTariff, double waterTariff, double flatTariff, double garbageTariff) {
        this.electroTariff1 = electroTariff1;
        this.electroLimit1 = electroLimit1;
        this.electroTariff2 = electroTariff2;
        this.electroLimit2 = electroLimit2;
        this.electroTariff3 = electroTariff3;
        this.electroLimit3 = electroLimit3;
        this.electroTariff4 = electroTariff4;
        this.heatingTariff = heatingTariff;
        this.waterTariff = waterTariff;
        this.flatTariff = flatTariff;
        this.garbageTariff = garbageTariff;
    }

    public void loadTariffs() {

        TariffsData loadedTariffs = new TariffsData();
        try {
            // First, create a new XMLInputFactory
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            // Setup a new eventReader
            InputStream in = new FileInputStream(TARIFFS_FILE);
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
            // read the XML document

            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();


                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    if (startElement.getName().getLocalPart().equals(ELECTRO_TARIFF_1)) {
                        event = eventReader.nextEvent();
                        loadedTariffs.electroTariff1 = Double.parseDouble(event.asCharacters().getData());
                        continue;
                    }
                    if (startElement.getName().getLocalPart().equals(ELECTRO_LIMIT_1)) {
                        event = eventReader.nextEvent();
                        loadedTariffs.electroLimit1 = Integer.parseInt(event.asCharacters().getData());
                        continue;
                    }
                    if (startElement.getName().getLocalPart().equals(ELECTRO_TARIFF_2)) {
                        event = eventReader.nextEvent();
                        loadedTariffs.electroTariff2 = Double.parseDouble(event.asCharacters().getData());
                        continue;
                    }
                    if (startElement.getName().getLocalPart().equals(ELECTRO_LIMIT_2)) {
                        event = eventReader.nextEvent();
                        loadedTariffs.electroLimit2 = Integer.parseInt(event.asCharacters().getData());
                        continue;
                    }
                    if (startElement.getName().getLocalPart().equals(ELECTRO_TARIFF_3)) {
                        event = eventReader.nextEvent();
                        loadedTariffs.electroTariff3 = Double.parseDouble(event.asCharacters().getData());
                        continue;
                    }
                    if (startElement.getName().getLocalPart().equals(ELECTRO_LIMIT_3)) {
                        event = eventReader.nextEvent();
                        loadedTariffs.electroLimit3 = Integer.parseInt(event.asCharacters().getData());
                        continue;
                    }
                    if (startElement.getName().getLocalPart().equals(ELECTRO_TARIFF_4)) {
                        event = eventReader.nextEvent();
                        loadedTariffs.electroTariff4 = Double.parseDouble(event.asCharacters().getData());
                        continue;
                    }
                    if (startElement.getName().getLocalPart().equals(WATER_TARIFF)) {
                        event = eventReader.nextEvent();
                        loadedTariffs.waterTariff = Double.parseDouble(event.asCharacters().getData());
                        continue;
                    }
                    if (startElement.getName().getLocalPart().equals(HEATING_TARIFF)) {
                        event = eventReader.nextEvent();
                        loadedTariffs.heatingTariff = Double.parseDouble(event.asCharacters().getData());
                        continue;
                    }
                    if (startElement.getName().getLocalPart().equals(FLAT_TARIFF)) {
                        event = eventReader.nextEvent();
                        loadedTariffs.flatTariff = Double.parseDouble(event.asCharacters().getData());
                        continue;
                    }
                    if (startElement.getName().getLocalPart().equals(GARBAGE_TARIFF)) {
                        event = eventReader.nextEvent();
                        loadedTariffs.garbageTariff = Double.parseDouble(event.asCharacters().getData());
                        continue;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
            Alerts.alertInfo("Тарифы не найдены", "Файл тарифов не найден.");
        } catch (XMLStreamException e) {
//            e.printStackTrace();
            Alerts.alertInfo("Ошибка загрузки", "Ошибка в потоке загрузки тарифов.");
        } catch (NumberFormatException e) {
//            e.printStackTrace();
            Alerts.alertInfo("Ошибка загрузки", "Ошибка формата данных сохраненных тарифов.\n" +
                    "Поэтому последние тарифы загружены не вполне/");
        }
        this.electroTariff1 = loadedTariffs.electroTariff1;
        this.electroLimit1 = loadedTariffs.electroLimit1;
        this.electroTariff2 = loadedTariffs.electroTariff2;
        this.electroLimit2 = loadedTariffs.electroLimit2;
        this.electroTariff3 = loadedTariffs.electroTariff3;
        this.electroLimit3 = loadedTariffs.electroLimit3;
        this.electroTariff4 = loadedTariffs.electroTariff4;
        this.heatingTariff = loadedTariffs.heatingTariff;
        this.waterTariff = loadedTariffs.waterTariff;
        this.flatTariff = loadedTariffs.flatTariff;
        this.garbageTariff = loadedTariffs.garbageTariff;
    }

    public void save() {
        try {
            // create an XMLOutputFactory
            XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
            // create XMLEventWriter
            XMLEventWriter eventWriter = outputFactory
                    .createXMLEventWriter(new FileOutputStream(TARIFFS_FILE));
            // create an EventFactory
            XMLEventFactory eventFactory = XMLEventFactory.newInstance();
            XMLEvent end = eventFactory.createDTD("\n");
            // create and write Start Tag
            StartDocument startDocument = eventFactory.createStartDocument();
            eventWriter.add(startDocument);
            eventWriter.add(end);

            StartElement contactsStartElement = eventFactory.createStartElement("",
                    "", TARIFFS);
            eventWriter.add(contactsStartElement);
            eventWriter.add(end);

            NodeCreator.createNode(eventWriter, ELECTRO_TARIFF_1, String.valueOf(electroTariff1));
            NodeCreator.createNode(eventWriter, ELECTRO_LIMIT_1, String.valueOf(electroLimit1));
            NodeCreator.createNode(eventWriter, ELECTRO_TARIFF_2, String.valueOf(electroTariff2));
            NodeCreator.createNode(eventWriter, ELECTRO_LIMIT_2, String.valueOf(electroLimit2));
            NodeCreator.createNode(eventWriter, ELECTRO_TARIFF_3, String.valueOf(electroTariff3));
            NodeCreator.createNode(eventWriter, ELECTRO_LIMIT_3, String.valueOf(electroLimit3));
            NodeCreator.createNode(eventWriter, ELECTRO_TARIFF_4, String.valueOf(electroTariff4));
            NodeCreator.createNode(eventWriter, HEATING_TARIFF, String.valueOf(heatingTariff));
            NodeCreator.createNode(eventWriter, WATER_TARIFF, String.valueOf(waterTariff));
            NodeCreator.createNode(eventWriter, FLAT_TARIFF, String.valueOf(flatTariff));
            NodeCreator.createNode(eventWriter, GARBAGE_TARIFF, String.valueOf(garbageTariff));

            eventWriter.add(eventFactory.createEndElement("", "", TARIFFS));
            eventWriter.add(end);
            eventWriter.add(eventFactory.createEndDocument());
            eventWriter.close();
        } catch (FileNotFoundException e) {
            Alerts.alertInfo("Проблема записи в файл тарифов", "Возможно, файл не существует");
        } catch (XMLStreamException e) {
            Alerts.alertInfo("Проблема записи в файл тарифоы", "Что-то пошло не так в потоке записи");
        }
    }


    /* I'll probably won't need this*/
    public void backToDefaults() {
        electroTariff1 = electroTariff1Original;
        electroLimit1 = electroLimit1Original;
        electroTariff2 = electroTariff2Original;
        electroLimit2 = electroLimit2Original;
        electroTariff3 = electroTariff3Original;
        electroLimit3 = electroLimit3Original;
        electroTariff4 = electroTariff4Original;
        heatingTariff = heatingTariffOriginal;
        waterTariff = waterTariffOriginal;
        flatTariff = flatTariffOriginal;
        garbageTariff = garbageTariffOriginal;
    }
}
