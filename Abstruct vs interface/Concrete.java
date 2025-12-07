class DeliveryRobot extends RoboModel implements RoboRules {

    @Override
    public void move() {
        System.out.println("Moving with wheels...");
    }

    @Override
    public void startService() {
        System.out.println("Starting delivery service...");
    }

    @Override
    public void transferData() {
        System.out.println("Transferring delivery status...");
    }
}
