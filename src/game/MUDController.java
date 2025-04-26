package game;

import java.util.List;
import java.util.Scanner;
import model.Player;
import model.Room;
import model.NPC;
import model.Item;


public class MUDController {
    private final Player player;
    private boolean running;
    private final Scanner scanner;

    public MUDController(Player player) {
        this.player = player;
        this.running = true;
        this.scanner = new Scanner(System.in);
    }

    public void runGameLoop() {
        System.out.println("Добро пожаловать! Введите 'help' для отображения списка команд.");

        while (running) {
            System.out.print(">> ");
            String input = scanner.nextLine().trim();
            handleInput(input);
        }
        System.out.println("Игра завершена.");
    }

    private void handleInput(String input) {
        switch (input.toLowerCase()) {
            case "look":
                lookAround();
                break;
            case "move":
                handleMove();
                break;
            case "pick":
                handlePickUp();
                break;
            case "inventory":
                checkInventory();
                break;
            case "talk":
                handleTalk();
                break;
            case "help":
                showHelp();
                break;
            case "quit":
            case "exit":
                running = false;
                break;
            default:
                System.out.println("Неизвестная команда. Введите 'help' для списка доступных команд.");
        }
    }

    private void handleTalk() {
        List<NPC> npcs = player.getCurrentRoom().getNPCs();
        if (npcs.isEmpty()) {
            System.out.println("Здесь не с кем разговаривать.");
            return;
        }
        System.out.println("Поговорить с:");
        for (int i = 0; i < npcs.size(); i++) {
            System.out.println((i + 1) + ". " + npcs.get(i).getName());
        }
        System.out.print(">> ");
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            if (choice >= 1 && choice <= npcs.size()) {
                NPC npc = npcs.get(choice - 1);
                System.out.println(npc.getName() + " говорит: \"" + npc.getDialogue() + "\"");
            } else {
                System.out.println("Некорректный выбор.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Введите число.");
        }
    }

    private void lookAround() {
        System.out.println(player.getCurrentRoom().describe());
    }

    private void handleMove() {
        System.out.println("Введите направление (forward, back, left, right): ");
        System.out.print(">> ");
        String direction = scanner.nextLine().trim();
        move(direction);
    }

    private void move(String direction) {
        Room nextRoom = player.getCurrentRoom().getConnectedRoom(direction);
        if (nextRoom != null) {
            player.setCurrentRoom(nextRoom);
            System.out.println("Вы переместились в: " + nextRoom.describe());
        } else {
            System.out.println("Вы не можете идти в этом направлении!");
        }
    }

    private void handlePickUp() {
        System.out.println("Введите название предмета: ");
        System.out.print(">> ");
        String itemName = scanner.nextLine().trim();
        pickUp(itemName);
    }

    private void pickUp(String itemName) {
        Room room = player.getCurrentRoom();
        Item item = room.getItem(itemName);
        if (item != null) {
            player.addItem(item);
            room.removeItem(item);
            System.out.println("Вы подобрали " + item.getName());
        } else {
            System.out.println("Ошибка! Такого предмета нет.");
        }
    }

    private void checkInventory() {
        if (player.getInventory().isEmpty()) {
            System.out.println("Инвентарь пуст.");
        } else {
            System.out.println("В инвентаре: " + player.getInventory());
        }
    }

    private void showHelp() {
        System.out.println("Команды:");
        System.out.println("  look - осмотреть комнату");
        System.out.println("  help - показать список команд");
        System.out.println("  inventory - проверить инвентарь");
        System.out.println("  talk - поговорить с персонажем");
        System.out.println("  move - переместиться в другое место");
        System.out.println("  pick - подобрать предмет");
        System.out.println("  quit - выйти из игры");
    }
}
