package model;
import game.MUDController;
import model.Player;
import model.Room;
import model.NPC;
import model.Item;


public class NPC {
    private final String name, dialogue;

    public NPC(String name, String dialogue) {
        this.name = name;
        this.dialogue = dialogue;
    }

    public String getName() {
        return name;
    }

    public String getDialogue() {
        return dialogue;
    }

    public String toString() {
        return name;
    }
}
