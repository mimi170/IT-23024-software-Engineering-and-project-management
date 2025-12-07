abstract class RoboModel {

    public void charge() {
        System.out.println("Charging with standard 220V power!");
    }

    public abstract void move();  // must be implemented
}
