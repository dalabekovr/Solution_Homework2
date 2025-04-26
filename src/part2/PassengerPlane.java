package part2;

public class PassengerPlane extends Aircraft {
    private int passengerCount;

    public PassengerPlane(String callSign, int fuelRemaining, TowerMediator controlTower, int passengerCount) {
        super(callSign, fuelRemaining, controlTower);
        this.passengerCount = passengerCount;
    }

    @Override
    public void receive(String message) {
        System.out.println(callSign + " (Commercial Airliner with " + passengerCount + " travelers) received: " + message);
    }
    
    public void requestLanding() {
        System.out.println(callSign + " requesting clearance to land with " + passengerCount + " travelers aboard.");
        controlTower.requestLandingPermission(this);
    }
    
    public void requestTakeoff() {
        System.out.println(callSign + " requesting clearance for departure with " + passengerCount + " travelers aboard.");
        controlTower.requestLandingPermission(this);
    }
}
