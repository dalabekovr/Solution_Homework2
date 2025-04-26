package part2;

import java.util.Queue;
import java.util.Comparator;
import java.util.stream.Stream;

public class FuelPriorityScheduler implements RunwayScheduler {
    @Override
    public Aircraft getNextAircraft(Queue<Aircraft> arrivalQueue, Queue<Aircraft> departureQueue) {
        Aircraft maydayAircraft = findEmergencyAircraft(arrivalQueue, departureQueue);
        if (maydayAircraft != null) {
            return maydayAircraft;
        }
        
        Aircraft criticalFuelAircraft = findLowestFuelAircraft(arrivalQueue, departureQueue);
        if (criticalFuelAircraft != null) {
            arrivalQueue.remove(criticalFuelAircraft);
            departureQueue.remove(criticalFuelAircraft);
            return criticalFuelAircraft;
        }
        
        return null;
    }
    
    private Aircraft findEmergencyAircraft(Queue<Aircraft> arrivalQueue, Queue<Aircraft> departureQueue) {
        Aircraft emergency = Stream.concat(arrivalQueue.stream(), departureQueue.stream())
                .filter(Aircraft::isEmergency)
                .findFirst()
                .orElse(null);
                
        if (emergency != null) {
            arrivalQueue.remove(emergency);
            departureQueue.remove(emergency);
        }
        
        return emergency;
    }
    
    private Aircraft findLowestFuelAircraft(Queue<Aircraft> arrivalQueue, Queue<Aircraft> departureQueue) {
        return Stream.concat(arrivalQueue.stream(), departureQueue.stream())
                .min(Comparator.comparingInt(Aircraft::getFuelLevel))
                .orElse(null);
    }
}
