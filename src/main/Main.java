package main;

import game.MUDController;
import game.MUDController;
import model.Player;
import model.Room;
import model.NPC;
import model.Item;


public class Main {
    public static void main(String[] args) {
        Room tavern = new Room("Таверна", "Шумная таверна с посетителями");
        Room street = new Room("Улица", "Приятная улица");
        Room shop = new Room("Магазин", "Магазин с товарами");

        NPC bartender = new NPC("Бармен", "Привет!");
        NPC merchant = new NPC("Торговец", "Покупай у меня!");

        tavern.connectRoom("forward", street);
        street.connectRoom("back", tavern);
        street.connectRoom("right", shop);
        shop.connectRoom("left", street);
        tavern.addNPC(bartender);
        shop.addNPC(merchant);
        Player player = new Player("Главный герой", tavern);
        MUDController controller = new MUDController(player);
        controller.runGameLoop();
    }
}
