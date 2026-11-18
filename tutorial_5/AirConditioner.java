public class AirConditioner extends Appliance {
    public AirConditioner(String brand) { super(brand); }

    @Override
    public void operate() { System.out.println("Cooling the room..."); }
}
