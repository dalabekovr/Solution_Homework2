package part2;

public abstract class Aircraft {
    protected String callSign;
    protected int fuelRemaining;
    protected boolean emergencyStatus;
    protected TowerMediator controlTower;

    public Aircraft(String callSign, int fuelRemaining, TowerMediator controlTower) {
        this.callSign = callSign;
        this.fuelRemaining = fuelRemaining;
        this.controlTower = controlTower;
        this.emergencyStatus = false;
    }

    public abstract void receive(String message);

    public void transmit(String message, TowerMediator tower) {
        tower.relayMessage(message, this);
    }

    public String getId() {
        return callSign;
    }

    public int getFuelLevel() {
        return fuelRemaining;
    }

    public void decreaseFuel() {
        fuelRemaining--;
        if (fuelRemaining <= 5 && !emergencyStatus) {
            emergencyStatus = true;
            System.out.println(callSign + " critically low on fuel! Emergency declared.");
            transmit("MAYDAY! Critical fuel shortage!", controlTower);
        }
    }

    public boolean isEmergency() {
        return emergencyStatus;
    }

    public void declareEmergency(String reason) {
        emergencyStatus = true;
        transmit("MAYDAY! " + reason, controlTower);
    }

    @Override
    public String toString() {
        return callSign;
    }
}
