public class Main {
    public static void main(String[] args) {

        DeliveryRobot d = new DeliveryRobot();

        d.charge();          // from abstract class
        d.move();            // overridden
        d.startService();    // from interface
        d.transferData();    // from interface
    }
}
