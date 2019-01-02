package sample;

import javafx.beans.property.SimpleStringProperty;

public class Payment {

    static final String[] NAMES_OF_MONTHS = {"Без Месяца", "Январь", "Февраль", "Март", "Апрель",
            "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};

    double electroTariff1 = 0.3084;
    int electroLimit1 = 75;
    double electroTariff2 = 0.6168;
    int electroLimit2 = 150;
    double electroTariff3 = 0.8388;
    int electroLimit3 = 800;
    double electroTariff4 = 2.6808;

    double heatingTariff = 270.11;
    double heatingTariffToPrint = heatingTariff;
    double waterTariff = 14.0;
    double waterTariffToPrint = waterTariff;
    double flatTariff = 197.0;
    double flatTariffToPrint = flatTariff;
    double garbageTariff = 22.0;
    double garbageTariffToPrint = garbageTariff;

    double electroMustPay = 0.0;
    boolean electroPaymentSet = false;
    double heatingMustPay = 0.0;
    boolean heatingPaymentSet = false;
    double waterMustPay = 0.0;
    boolean waterPaymentSet = false;
    double flatMustPay = 0.0;
    boolean flatPaymentSet = false;
    double garbageMustPay = 0.0;
    boolean garbagePaymentSet = false;

    SimpleStringProperty name; //year and month represent the name for a payment

    int year;
    int month;

    int electroStart;
    int electroEnd;
    int kWattConsumed;
    int waterStart;
    int waterEnd;
    int m3consumed;

    int paymentForElectricity;
    int paymentForHeating;
    int paymentForWater;
    int paymentForFlat;
    int paymentForGarbage;
    int total;

    /*
    This constructor generates an instance of a monthly payment by the specified values:
    the number of the month (1 - 12), the start readings and the end readings of the electricity counter
    and the water counter.
    The methods of this class must be run to calculate the payments for all services and the total payment.
    Method payForEverything() (at the bottom) runs them all.
    */
    public Payment(int year, int month, int electroStart, int electroEnd, int waterStart, int waterEnd) {
        this.name = new SimpleStringProperty();
        this.year = year;
        this.month = month;
        buildName();
        this.electroStart = electroStart;
        this.electroEnd = electroEnd;
        this.waterStart = waterStart;
        this.waterEnd = waterEnd;

    }

    void buildName() {
        if (month > 0 & month < 13) {
            name.set(year + "." + month + " (" + NAMES_OF_MONTHS[month] + ")");
        } else {
            name.set(year + "." + month + " (" + NAMES_OF_MONTHS[0] + ")");
        }
    }

    void setElectroPayment(double electroMustPay) {
        this.electroMustPay = electroMustPay;
        electroPaymentSet = true;
    }

    void setDefaultElectroPayment() {
        this.electroPaymentSet = false;
    }

    void payForElectricity() {
        kWattConsumed = electroEnd - electroStart;
        if (electroPaymentSet == true) {
            paymentForElectricity = (int) Math.round(electroMustPay);
            return;
        }
        double paymentForElectricityCopeek = 0;
        if (kWattConsumed >= 0) {
            if (kWattConsumed <= electroLimit1) {
                paymentForElectricityCopeek += kWattConsumed * electroTariff1;
            } else if (kWattConsumed <= electroLimit2) {
                paymentForElectricityCopeek += electroLimit1 * electroTariff1 +
                        (kWattConsumed - electroLimit1) * electroTariff2;
            } else if (kWattConsumed <= electroLimit3) {
                paymentForElectricityCopeek += electroLimit1 * electroTariff1 +
                        (electroLimit2 - electroLimit1) * electroTariff2 +
                        (kWattConsumed - electroLimit2) * electroTariff3;
            } else {
                paymentForElectricityCopeek += electroLimit1 * electroTariff1 +
                        (electroLimit2 - electroLimit1) * electroTariff2 +
                        (electroLimit3 - electroLimit2) * electroTariff3 +
                        (kWattConsumed - electroLimit3) * electroTariff4;

            }
        }
        paymentForElectricity = (int) Math.round(paymentForElectricityCopeek);
    }

    void setHeatingPayment(double heatingMustPay) {
        this.heatingMustPay = heatingMustPay;
        heatingPaymentSet = true;
    }

    void setDefaultHeatingPayment() {
        this.heatingPaymentSet = false;
        this.heatingTariffToPrint = this.heatingTariff;
    }

    void payForHeating() {
        if (heatingPaymentSet == true) {
            heatingTariffToPrint = 0;
            paymentForHeating = (int) Math.round(heatingMustPay);
            return;
        }
        paymentForHeating = (int) Math.round(heatingTariff);
    }

    void setWaterPayment(double waterMustPay) {
        this.waterMustPay = waterMustPay;
        waterPaymentSet = true;
    }

    void setDefaultWaterPayment() {
        this.waterPaymentSet = false;
        this.waterTariffToPrint = this.waterTariff;
    }

    void payForWater() {
        m3consumed = waterEnd - waterStart;
        if (waterPaymentSet == true) {
            waterTariffToPrint = 0;
            paymentForWater = (int) Math.round(waterMustPay);
            return;
        }
        if (m3consumed >= 0) {
            paymentForWater = (int) Math.round(m3consumed * waterTariff);
        }
    }

    void setFlatPayment(double flatMustPay) {
        this.flatMustPay = flatMustPay;
        flatPaymentSet = true;
    }

    void setDefaultFlatPayment() {
        this.flatPaymentSet = false;
        this.flatTariffToPrint = this.flatTariff;
    }

    void payForFlat() {
        if (flatPaymentSet == true) {
            flatTariffToPrint = 0;
            paymentForFlat = (int) Math.round(flatMustPay);
            return;
        }
        paymentForFlat = (int) Math.round(flatTariff);
    }

    void setGarbagePayment(double garbageMustPay) {
        this.garbageMustPay = garbageMustPay;
        garbagePaymentSet = true;
    }

    void setDefaultGarbagePayment() {
        this.garbagePaymentSet = false;
        this.garbageTariffToPrint = this.garbageTariff;
    }

    void payForGarbage() {
        if (garbagePaymentSet == true) {
            garbageTariffToPrint = 0;
            paymentForGarbage = (int) Math.round(garbageMustPay);
            return;
        }
        paymentForGarbage = (int) Math.round(garbageTariff);
    }

    void getTotal() {
        total = paymentForElectricity
                + paymentForHeating
                + paymentForWater
                + paymentForFlat
                + paymentForGarbage;
    }

    void payForEverything() {
        payForElectricity();
        payForHeating();
        payForWater();
        payForFlat();
        payForGarbage();
        getTotal();
    }


    public String printPayment() {
        String writeLine = "";

        writeLine = writeLine.concat(name.get() + ":\n\n")
                .concat("Квартплата\t\t\tТариф " + Math.round(flatTariffToPrint) + " руб.\n")
                .concat("\t\t\t\tПлатеж " + paymentForFlat + " руб.\n\n")
                .concat("Электричество\n")
                .concat("(Нач. пок. счетчика:\t" + electroStart + ")\n")
                .concat("(Конеч. пок. счетчика:\t" + electroEnd + ")\n")
                .concat("(Потреблено кВт:\t\t" + kWattConsumed + ")\n")
                .concat("\t\t\t\tПлатеж " + paymentForElectricity + " руб.\n\n")
                .concat("Вода\t\t\t\tТариф " + Math.round(waterTariffToPrint) + " руб.\n")
                .concat("(Нач. пок. счетчика:\t" + waterStart + ")\n")
                .concat("(Конеч. пок. счетчика:\t" + waterEnd + ")\n")
                .concat("(Потреблено куб.м:\t" + m3consumed + ")\n")
                .concat("\t\t\t\tПлатеж " + paymentForWater + " руб.\n\n")
                .concat("Отопление\t\t\tТариф " + Math.round(heatingTariffToPrint) + " руб.\n")
                .concat("\t\t\t\tПлатеж " + paymentForHeating + " руб.\n\n")
                .concat("Вывоз мусора\t\tТариф " + Math.round(garbageTariffToPrint) + " руб.\n")
                .concat("\t\t\t\tПлатеж " + paymentForGarbage + " руб.\n\n")
                .concat("\t\t\t\tВсего: " + total + " руб.\n");
        return writeLine;
    }


    public Payment clone() {
        Payment clone = new Payment(year, month, electroStart, electroEnd, waterStart, waterEnd);

        clone.electroTariff1 = electroTariff1;
        clone.electroLimit1 = electroLimit1;
        clone.electroTariff2 = electroTariff2;
        clone.electroLimit2 = electroLimit2;
        clone.electroTariff3 = electroTariff3;
        clone.electroLimit3 = electroLimit3;
        clone.electroTariff4 = electroTariff4;

        clone.heatingTariff = heatingTariff;
        clone.heatingTariffToPrint = heatingTariffToPrint;
        clone.waterTariff = waterTariff;
        clone.waterTariffToPrint = waterTariffToPrint;
        clone.flatTariff = flatTariff;
        clone.flatTariffToPrint = flatTariffToPrint;
        clone.garbageTariff = garbageTariff;
        clone.garbageTariffToPrint = garbageTariffToPrint;

        clone.electroMustPay = electroMustPay;
        clone.electroPaymentSet = electroPaymentSet;
        clone.heatingMustPay = heatingMustPay;
        clone.heatingPaymentSet = heatingPaymentSet;
        clone.waterMustPay = waterMustPay;
        clone.waterPaymentSet = waterPaymentSet;
        clone.flatMustPay = flatMustPay;
        clone.flatPaymentSet = flatPaymentSet;
        clone.garbageMustPay = garbageMustPay;
        clone.garbagePaymentSet = garbagePaymentSet;

        clone.name.set(name.get());

        clone.year = year;
        clone.year = year;
        clone.electroStart = electroStart;
        clone.electroEnd = electroEnd;
        clone.kWattConsumed = kWattConsumed;
        clone.waterStart = waterStart;
        clone.waterEnd = waterEnd;
        clone.m3consumed = m3consumed;
        clone.paymentForElectricity = paymentForElectricity;
        clone.paymentForHeating = paymentForHeating;
        clone.paymentForWater = paymentForWater;
        clone.paymentForFlat = paymentForFlat;
        clone.paymentForGarbage = paymentForGarbage;
        clone.total = total;

        return clone;
    }
}

