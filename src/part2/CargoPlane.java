package part2;

public class CargoPlane extends Aircraft {
    private int payloadWeight;

    public CargoPlane(String callSign, int fuelRemaining, TowerMediator controlTower, int payloadWeight) {
        super(callSign, fuelRemaining, controlTower);
        this.payloadWeight = payloadWeight;
    }

    @Override
    public void receive(String message) {
        System.out.println(callSign + " (Freight Aircraft carrying " + payloadWeight + " tons) received: " + message);
    }
    
    public void requestLanding() {
        System.out.println(callSign + " requesting clearance to land with " + payloadWeight + " tons of freight.");
        controlTower.requestLandingPermission(this);
    }
    
    public void requestTakeoff() {
        System.out.println(callSign + " requesting clearance for departure with " + payloadWeight + " tons of freight.");
        controlTower.requestLandingPermission(this);
    }
}
