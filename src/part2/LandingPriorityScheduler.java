package part2;

import java.util.Queue;

public class LandingPriorityScheduler implements RunwayScheduler {
    @Override
    public Aircraft getNextAircraft(Queue<Aircraft> arrivalQueue, Queue<Aircraft> departureQueue) {
        Aircraft distressedAircraft = checkForEmergencies(arrivalQueue, departureQueue);
        if (distressedAircraft != null) {
            return distressedAircraft;
        }
        
        if (!arrivalQueue.isEmpty()) {
            return arrivalQueue.poll();
        } 
        
        if (!departureQueue.isEmpty()) {
            return departureQueue.poll();
        }
        
        return null;
    }
    

    private Aircraft checkForEmergencies(Queue<Aircraft> arrivalQueue, Queue<Aircraft> departureQueue) {
        for (Aircraft aircraft : arrivalQueue) {
            if (aircraft.isEmergency()) {
                arrivalQueue.remove(aircraft);
                return aircraft;
            }
        }
        
        for (Aircraft aircraft : departureQueue) {
            if (aircraft.isEmergency()) {
                departureQueue.remove(aircraft);
                return aircraft;
            }
        }
        
        return null;
    }
}
