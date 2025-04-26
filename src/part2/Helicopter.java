package part2;

public class Helicopter extends Aircraft {
    private String operationType;

    public Helicopter(String callSign, int fuelRemaining, TowerMediator controlTower, String operationType) {
        super(callSign, fuelRemaining, controlTower);
        this.operationType = operationType;
    }

    @Override
    public void receive(String message) {
        System.out.println(callSign + " (Rotorcraft on " + operationType + " operation) received: " + message);
    }
    
    public void requestLanding() {
        System.out.println(callSign + " requesting helipad clearance after " + operationType + " operation.");
        controlTower.requestLandingPermission(this);
    }
    
    public void requestTakeoff() {
        System.out.println(callSign + " requesting clearance for departure on " + operationType + " operation.");
        controlTower.requestLandingPermission(this);
    }
}
