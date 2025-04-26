package part2;

import java.util.Queue;

public class FifoScheduler implements RunwayScheduler {
    @Override
    public Aircraft getNextAircraft(Queue<Aircraft> arrivalQueue, Queue<Aircraft> departureQueue) {
        Aircraft priorityAircraft = findEmergencyAircraft(arrivalQueue, departureQueue);
        if (priorityAircraft != null) {
            return priorityAircraft;
        }
        
        if (!arrivalQueue.isEmpty()) {
            return arrivalQueue.poll();
        } else if (!departureQueue.isEmpty()) {
            return departureQueue.poll();
        }
        
        return null;
    }
    
    private Aircraft findEmergencyAircraft(Queue<Aircraft> arrivalQueue, Queue<Aircraft> departureQueue) {
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
