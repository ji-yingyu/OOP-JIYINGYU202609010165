public class Main {
    public static void main(String[] args) {
        Appliance[] appliances = {
            new WashingMachine("LG"),
            new Refrigerator("Panasonic"),
            new AirConditioner("Daikin"),
            new Microwave("Samsung")
        };
        for (Appliance appliance : appliances) {
            appliance.displayBrand();
            appliance.turnOn();
            appliance.operate();
            appliance.turnOff();
            System.out.println();
        }
    }
}
