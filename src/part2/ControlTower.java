package part2;

import java.util.List;
import java.util.Queue;
import java.util.ArrayList;
import java.util.LinkedList;

public class ControlTower implements TowerMediator {
    private List<Aircraft> registeredFlyers;
    private Queue<Aircraft> arrivalQueue;
    private Queue<Aircraft> departureQueue;
    private boolean runwayInUse;
    private Aircraft activeAircraft;
    private RunwayScheduler prioritySystem;

    public ControlTower() {
        registeredFlyers = new ArrayList<>();
        arrivalQueue = new LinkedList<>();
        departureQueue = new LinkedList<>();
        runwayInUse = false;
        activeAircraft = null;
        prioritySystem = new FifoScheduler();
    }
    
    public void setScheduler(RunwayScheduler newScheduler) {
        this.prioritySystem = newScheduler;
        System.out.println("ATC: Implementing new " + newScheduler.getClass().getSimpleName() + " protocol");
    }

    public void registerAircraft(Aircraft aircraft) {
        registeredFlyers.add(aircraft);
        System.out.println("ATC: " + aircraft.getId() + " has been registered with air traffic control.");
    }

    @Override
    public void relayMessage(String message, Aircraft sender) {
        System.out.println(sender.getId() + " to ATC: " + message);
        
        if (message.contains("MAYDAY")) {
            initiateEmergencyProtocol(sender);
            return;
        }
        
        for (Aircraft aircraft : registeredFlyers) {
            if (aircraft != sender) {
                aircraft.receive("ATC broadcast from " + sender.getId() + ": " + message);
            }
        }
    }

    private void initiateEmergencyProtocol(Aircraft distressedAircraft) {
        System.out.println("ATC EMERGENCY ALERT: Priority protocol activated for " + distressedAircraft.getId());
        
        if (runwayInUse && activeAircraft != distressedAircraft) {
            System.out.println("ATC to " + activeAircraft.getId() + ": Immediate abort required! Emergency situation in progress.");
            activeAircraft.receive("URGENT ABORT: Vacate runway for emergency aircraft!");
            runwayInUse = false;
        }
        
        for (Aircraft aircraft : registeredFlyers) {
            if (aircraft != distressedAircraft) {
                aircraft.receive("MAINTAIN POSITION: Emergency procedures active for " + distressedAircraft.getId());
            }
        }
        
        arrivalQueue.remove(distressedAircraft);
        departureQueue.remove(distressedAircraft);
        
        System.out.println("ATC to " + distressedAircraft.getId() + ": Runway cleared for emergency approach. Proceed immediately.");
        distressedAircraft.receive("EMERGENCY CLEARANCE GRANTED: Proceed directly to runway.");
        
        runwayInUse = true;
        activeAircraft = distressedAircraft;
    }

    @Override
    public boolean requestLandingPermission(Aircraft aircraft) {
        if (aircraft.isEmergency()) {
            if (!runwayInUse || activeAircraft == aircraft) {
                authorizeRunwayUse(aircraft);
                return true;
            } else {
                initiateEmergencyProtocol(aircraft);
                return false;
            }
        }
        
        if (runwayInUse) {
            if (aircraft.getId().contains("LND")) {
                if (!arrivalQueue.contains(aircraft)) {
                    arrivalQueue.add(aircraft);
                    System.out.println("ATC to " + aircraft.getId() + ": Added to arrival sequence, position " + arrivalQueue.size());
                    aircraft.receive("STANDBY: You are number " + arrivalQueue.size() + " in arrival sequence.");
                }
            } else {
                if (!departureQueue.contains(aircraft)) {
                    departureQueue.add(aircraft);
                    System.out.println("ATC to " + aircraft.getId() + ": Added to departure sequence, position " + departureQueue.size());
                    aircraft.receive("STANDBY: You are number " + departureQueue.size() + " in departure sequence.");
                }
            }
            return false;
        } else {
            authorizeRunwayUse(aircraft);
            return true;
        }
    }

    private void authorizeRunwayUse(Aircraft aircraft) {
        runwayInUse = true;
        activeAircraft = aircraft;
        
        if (aircraft.getId().contains("LND")) {
            System.out.println("ATC to " + aircraft.getId() + ": Cleared to land.");
            aircraft.receive("LANDING CLEARANCE: Runway is available for your approach.");
        } else {
            System.out.println("ATC to " + aircraft.getId() + ": Cleared for takeoff.");
            aircraft.receive("DEPARTURE CLEARANCE: Runway is available for your takeoff.");
        }
    }

    public void runwayFreed() {
        runwayInUse = false;
        activeAircraft = null;
        System.out.println("ATC: Runway is now available.");
        
        Aircraft next = prioritySystem.getNextAircraft(arrivalQueue, departureQueue);
        
        if (next != null) {
            System.out.println("ATC: Priority system selected: " + next.getId());
            requestLandingPermission(next);
        }
    }
    
    public String getQueueStatus() {
        return "Arrival Sequence: " + arrivalQueue.size() + ", Departure Sequence: " + departureQueue.size();
    }
}
