package model;
import game.MUDController;
import model.Player;
import model.Room;
import model.NPC;
import model.Item;


import java.util.*;


public class Room {
    private final String name, description;
    private final List<Item> items = new ArrayList<>();
    private final List<NPC> npcs = new ArrayList<>();
    private final Map<String, Room> connections = new HashMap<>();

    public Room(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void connectRoom(String direction, Room room) { connections.put(direction, room); }
    public Room getConnectedRoom(String direction) { return connections.get(direction); }
    public void addItem(Item item) { items.add(item); }
    public void removeItem(Item item) { items.remove(item); }
    public Item getItem(String name) { return items.stream().filter(i -> i.getName().equalsIgnoreCase(name)).findFirst().orElse(null); }
    public void addNPC(NPC npc) { npcs.add(npc); }
    public NPC getNPC(String name) { return npcs.stream().filter(n -> n.getName().equalsIgnoreCase(name)).findFirst().orElse(null); }
    public List<NPC> getNPCs() {
        return npcs;
    }
    public String describe() { return name + ": " + description + "\nВы видите: " + (npcs.isEmpty() ? "никого" : npcs); }
}


