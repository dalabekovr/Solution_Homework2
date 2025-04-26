package model;
import game.MUDController;
import model.Player;
import model.Room;
import model.NPC;
import model.Item;


import java.util.ArrayList;
import java.util.List;

public class Player {
    private final String name;
    private Room currentRoom;
    private final List<Item> inventory = new ArrayList<>();

    public Player(String name, Room startingRoom) {
        this.name = name;
        this.currentRoom = startingRoom;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room newRoom) {
        this.currentRoom = newRoom;
    }

    public void addItem(Item item) {
        inventory.add(item);
    }

    public List<Item> getInventory() {
        return inventory;
    }
}
