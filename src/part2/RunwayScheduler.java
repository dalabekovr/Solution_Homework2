package part2;

import java.util.Queue;

public interface RunwayScheduler {
    Aircraft getNextAircraft(Queue<Aircraft> landingQueue, Queue<Aircraft> takeoffQueue);
}
