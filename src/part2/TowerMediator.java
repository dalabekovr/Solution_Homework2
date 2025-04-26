package part2;

public interface TowerMediator {
    void relayMessage(String message, Aircraft sender);
    boolean requestLandingPermission(Aircraft aircraft);
}
