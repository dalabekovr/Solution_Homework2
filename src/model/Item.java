package model;
import game.MUDController;
import model.Player;
import model.Room;
import model.NPC;
import model.Item;


public class Item {
    private final String name;

    public Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return name;
    }
}
