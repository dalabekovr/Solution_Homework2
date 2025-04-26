package part2;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ScheduledExecutorService;

public class AirportSimulation {
    private static final Random randomGenerator = new Random();
    private static final String[] OPERATIONS = {"medical", "rescue", "surveillance", "vip", "training"};
    
    public static void main(String[] args) {
        ControlTower airTrafficControl = new ControlTower();
        List<Aircraft> airportFleet = generateAircraftFleet(airTrafficControl, 10);
        
        ScheduledExecutorService simulationExecutor = Executors.newScheduledThreadPool(1);
        
        System.out.println("=== AIRPORT CONTROL SIMULATION INITIATED ===");
        System.out.println("10 aircraft registered with air traffic control");
        System.out.println("Simulation duration: 30 seconds with randomized events");
        System.out.println("Runway allocation strategy will rotate every 10 seconds");
        System.out.println("==================================================");
        
        simulationExecutor.scheduleAtFixedRate(() -> {
            int fleetIndex = randomGenerator.nextInt(airportFleet.size());
            Aircraft currentAircraft = airportFleet.get(fleetIndex);
            
            currentAircraft.decreaseFuel();
            
            if (randomGenerator.nextInt(10) < 3) {
                requestAircraftMovement(currentAircraft);
            }
            
            if (randomGenerator.nextInt(20) == 0) {
                airTrafficControl.runwayFreed();
            }
            
            if (randomGenerator.nextInt(50) == 0 && !currentAircraft.isEmergency()) {
                currentAircraft.declareEmergency("Technical malfunction");
            }
            
            System.out.println("--- " + airTrafficControl.getQueueStatus() + " ---");
        }, 0, 1, TimeUnit.SECONDS);
        
        AtomicInteger schedulerRotation = new AtomicInteger(0);
        simulationExecutor.scheduleAtFixedRate(() -> {
            int strategyIndex = schedulerRotation.incrementAndGet() % 3;
            switch (strategyIndex) {
                case 0:
                    airTrafficControl.setScheduler(new FifoScheduler());
                    break;
                case 1:
                    airTrafficControl.setScheduler(new FuelPriorityScheduler());
                    break;
                case 2:
                    airTrafficControl.setScheduler(new LandingPriorityScheduler());
                    break;
            }
        }, 10, 10, TimeUnit.SECONDS);
        
        simulationExecutor.schedule(() -> {
            simulationExecutor.shutdown();
            System.out.println("=== SIMULATION COMPLETED ===");
        }, 30, TimeUnit.SECONDS);
    }

    private static void requestAircraftMovement(Aircraft aircraft) {
        if (aircraft.getId().contains("LND")) {
            if (aircraft instanceof PassengerPlane) {
                ((PassengerPlane) aircraft).requestLanding();
            } else if (aircraft instanceof CargoPlane) {
                ((CargoPlane) aircraft).requestLanding();
            } else if (aircraft instanceof Helicopter) {
                ((Helicopter) aircraft).requestLanding();
            }
        } else {
            if (aircraft instanceof PassengerPlane) {
                ((PassengerPlane) aircraft).requestTakeoff();
            } else if (aircraft instanceof CargoPlane) {
                ((CargoPlane) aircraft).requestTakeoff();
            } else if (aircraft instanceof Helicopter) {
                ((Helicopter) aircraft).requestTakeoff();
            }
        }
    }

    private static List<Aircraft> generateAircraftFleet(ControlTower controlTower, int fleetSize) {
        List<Aircraft> aircraftFleet = new ArrayList<>();
        
        for (int i = 0; i < fleetSize; i++) {
            String identifier;
            Aircraft newAircraft;
            
            boolean isArriving = randomGenerator.nextBoolean();
            String flightPhase = isArriving ? "LND" : "DEP";
            
            int fuelLevel = randomGenerator.nextInt(20) + 5;
            
            int aircraftType = randomGenerator.nextInt(3);
            switch (aircraftType) {
                case 0:
                    identifier = "PP" + flightPhase + (i + 100);
                    int passengerCount = randomGenerator.nextInt(200) + 50;
                    newAircraft = new PassengerPlane(identifier, fuelLevel, controlTower, passengerCount);
                    break;
                case 1:
                    identifier = "CP" + flightPhase + (i + 100);
                    int freightWeight = randomGenerator.nextInt(50) + 10;
                    newAircraft = new CargoPlane(identifier, fuelLevel, controlTower, freightWeight);
                    break;
                default:
                    identifier = "HC" + flightPhase + (i + 100);
                    String operationType = OPERATIONS[randomGenerator.nextInt(OPERATIONS.length)];
                    newAircraft = new Helicopter(identifier, fuelLevel, controlTower, operationType);
                    break;
            }
            
            controlTower.registerAircraft(newAircraft);
            aircraftFleet.add(newAircraft);
        }
        
        return aircraftFleet;
    }
}
