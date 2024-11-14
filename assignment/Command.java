import java.util.Scanner;
import java.util.Stack;
import java.util.Vector;

interface Command {
    void execute();

    void undo();

    void redo();
}

class CreatePlayerCommand implements Command {
    private Scanner scanner;
    private Vector<Player> playerList;
    private PlayerFactory playerFactory = new PlayerFactory();
    private Player newPlayer;
    private String playerName;

    public CreatePlayerCommand(Scanner scanner, Vector<Player> playerList) {
        this.scanner = scanner;
        this.playerList = playerList;
    }

    @Override
    public void execute() {
        System.out.print("Enter Player ID: ");
        String playerId = scanner.nextLine();
        System.out.print("Enter Player Name: ");
        playerName = scanner.nextLine();
        newPlayer = playerFactory.createPlayer(playerId, playerName);
        playerList.add(newPlayer);
        System.out.println("Player " + playerName + " is created.");
        // Set the new player as the current player
        System.out.println("Current player is changed to " + playerId + "."); // Updated message
        Main.commandStack.push(this);
    }

    @Override
    public void undo() {
        playerList.remove(newPlayer);
    }

    @Override
    public void redo() {
        playerList.add(newPlayer);
    }

    public String toString() {
        return "Create player, " + newPlayer.getPlayerID() + ", " + playerName;
    }
}

class SetCurrentPlayerCommand implements Command {
    private Scanner scanner;
    private Vector<Player> playerList;

    public SetCurrentPlayerCommand(Scanner scanner, Vector<Player> playerList) {
        this.scanner = scanner;
        this.playerList = playerList;
    }

    @Override
    public void execute() {
        System.out.print("Enter Player ID to set as current: ");
        String playerId = scanner.nextLine();

        for (Player player : playerList) {
            if (player.getPlayerID().equals(playerId)) {
                Main.currentPlayer = player;
                System.out.println("Current player set to " + player.getPlayerName());
                return;
            }
        }
        System.out.println("Player not found.");
    }

    @Override
    public void undo() {
        // Undo logic here
    }

    @Override
    public void redo() {
        // Redo logic here
    }
}

class AddHeroCommand implements Command {
    private Scanner scanner;
    private Player currentPlayer;
    private HeroFactory heroFactory = new HeroFactory();

    public AddHeroCommand(Scanner scanner, Player currentPlayer) {
        this.scanner = scanner;
        this.currentPlayer = currentPlayer;
    }

    @Override
    public void execute() {
        if (currentPlayer == null) {
            System.out.println("No current player set.");
            return;
        }

        // Display current player
        System.out.println(
                "The current player is " + currentPlayer.getPlayerID() + " " + currentPlayer.getPlayerName() + ".");

        // Prompt for hero information
        currentPlayer.addHero(heroFactory.createHero(scanner));
        System.out.println("Hero is added.");
    }

    @Override
    public void undo() {
        // Undo logic here
    }

    @Override
    public void redo() {
        // Redo logic here
    }
}

class CallHeroSkillCommand implements Command {
    private MementoCallSkill undo;
    private MementoCallSkill redo;
    private Player currentPlayer;
    private Hero hero;

    public CallHeroSkillCommand(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    @Override
    public void execute() {
        if (currentPlayer == null) {
            System.out.println("No current player set.");
            return;
        }
        if (currentPlayer.getHeroes().isEmpty()) {
            System.out.println("No heroes available for this player.");
            return;
        }
        System.out.print("Enter Hero ID to call skill: ");
        Scanner scanner = new Scanner(System.in);
        String heroId = scanner.nextLine();
        for (Hero hero : currentPlayer.getHeroes()) {
            if (hero.getHeroID().equals(heroId)) {
                this.hero = hero;
                if (hero instanceof Warrior)
                    undo = new MementoWarrior((Warrior) hero);
                if (hero instanceof Warlock)
                    redo = new MementoWarlock((Warlock) hero);
                hero.callSkill();
                return;
            }
        }
        System.out.println("Hero not found.");
    }

    @Override
    public void undo() {
        redo = new MementoCallSkill(hero);
        undo.restore();
    }

    @Override
    public void redo() {
        undo = new MementoCallSkill(hero);
        redo.restore(); // Re-apply the change
    }
}

class DeleteHeroCommand implements Command {
    private Scanner scanner;
    private Player currentPlayer;
    private Hero removedHero;

    public DeleteHeroCommand(Scanner scanner, Player currentPlayer) {
        this.scanner = scanner;
        this.currentPlayer = currentPlayer;
    }

    @Override
    public void execute() {
        if (currentPlayer == null) {
            System.out.println("No current player set.");
            return;
        }
        System.out.print("Enter Hero ID to delete: ");
        String heroId = scanner.nextLine();
        for (Hero hero : currentPlayer.getHeroes()) {
            if (hero.getHeroID().equals(heroId)) {
                removedHero = hero; // Save the hero for undo
                currentPlayer.getHeroes().remove(hero);
                System.out.println("Hero " + hero.getHeroName() + " deleted.");
                return;
            }
        }
        System.out.println("Hero not found.");
    }

    @Override
    public void undo() {
        if (removedHero != null) {
            currentPlayer.addHero(removedHero);
            System.out.println("Restored hero " + removedHero.getHeroName());
        }
    }

    @Override
    public void redo() {
        currentPlayer.removeHero(removedHero);
    }

    public String toString() {
        return "Delete hero, " + removedHero.getHeroID();
    }
}

class ShowPlayerCommand implements Command {
    private Player currentPlayer;

    public ShowPlayerCommand(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    @Override
    public void execute() {
        if (currentPlayer == null) {
            System.out.println("No current player set.");
            return;
        }
        currentPlayer.showPlayerDetails();
    }

    @Override
    public void undo() {
        // No undo needed for showing player
    }

    @Override
    public void redo() {
        // No redo needed for showing player
    }
}

class DisplayAllPlayersCommand implements Command {
    private Vector<Player> playerList;

    public DisplayAllPlayersCommand(Vector<Player> playerList) {
        this.playerList = playerList;
    }

    @Override
    public void execute() {
        if (playerList.isEmpty()) {
            System.out.println("No players available.");
            return;
        }
        System.out.println("Players:");
        for (Player player : playerList) {
            System.out.println("- " + player.getPlayerName() + " (ID: " + player.getPlayerID() + ")");
        }
    }

    @Override
    public void undo() {
        // No undo needed for displaying players
    }

    @Override
    public void redo() {
        // No redo needed for displaying players
    }
}

class ChangePlayerNameCommand implements Command {
    private Scanner scanner;
    private Player currentPlayer;
    private String oldName;
    private String newName;
    private MementoPlayerName undo;
    private MementoPlayerName redo;

    public ChangePlayerNameCommand(Scanner scanner, Player currentPlayer) {
        this.scanner = scanner;
        this.currentPlayer = currentPlayer;
    }

    @Override
    public void execute() {
        if (currentPlayer == null) {
            System.out.println("No current player set.");
            return;
        }
        oldName = currentPlayer.getPlayerName();
        System.out.print("Enter new name for player " + oldName + ": ");
        newName = scanner.nextLine();
        undo = new MementoPlayerName(currentPlayer);
        currentPlayer.setPlayerName(newName);
        System.out.println("Player name changed to " + newName);
    }

    @Override
    public void undo() {
        redo = new MementoPlayerName(currentPlayer);
        undo.restore(); // Restore previous name
    }

    @Override
    public void redo() {
        undo = new MementoPlayerName(currentPlayer);
        redo.restore(); // Restore previous name
    }

    public String toString() {
        return "Change player's name, " + currentPlayer.getPlayerID() + ", " + newName;
    }
}

class UndoCommand implements Command {
    private Stack<Command> commandStack;
    private Stack<Command> redoStack;

    public UndoCommand(Stack<Command> commandStack, Stack<Command> redoStack) {
        this.commandStack = commandStack;
        this.redoStack = redoStack;
    }

    @Override
    public void execute() {
        if (!commandStack.isEmpty()) {
            Command command = commandStack.pop();
            command.undo();
            redoStack.push(command);
            System.out.println("Command (" + command + ") is undone.");
        } else {
            System.out.println("Nothing to undo!");
        }
    }

    @Override
    public void undo() {
        // No operation needed for UndoCommand
    }

    @Override
    public void redo() {
        // No operation needed for UndoCommand
    }
}

class RedoCommand implements Command {
    private Stack<Command> commandStack;
    private Stack<Command> redoStack;

    public RedoCommand(Stack<Command> commandStack, Stack<Command> redoStack) {
        this.commandStack = commandStack;
        this.redoStack = redoStack;
    }

    @Override
    public void execute() {
        if (!redoStack.isEmpty()) {
            Command command = redoStack.pop();
            command.execute();
            commandStack.push(command);
            System.out.println("Command (" + command + ") is redone.");
        } else {
            System.out.println("Nothing to redo!");
        }
    }

    @Override
    public void undo() {
        // No operation needed for RedoCommand
    }

    @Override
    public void redo() {
        // No operation needed for RedoCommand
    }
}

class UndoRedoListCommand implements Command {
    private Stack<Command> commandStack;
    private Stack<Command> redoStack;

    public UndoRedoListCommand(Stack<Command> commandStack, Stack<Command> redoStack) {
        this.commandStack = commandStack;
        this.redoStack = redoStack;
    }

    @Override
    public void execute() {
        System.out.println("Undo List:");
        if (commandStack.isEmpty()) {
            System.out.println("Nothing to Undo!");
        } else {
            for (int i = commandStack.size() - 1; i >= 0; i--) {
                System.out.println(commandStack.get(i));
            }
        }

        System.out.println("Redo List:");
        if (redoStack.isEmpty()) {
            System.out.println("Nothing to Redo!");
        } else {
            for (int i = redoStack.size() - 1; i >= 0; i--) {
                System.out.println(redoStack.get(i));
            }
        }
    }

    @Override
    public void undo() {
        // No operation needed for UndoRedoListCommand
    }

    @Override
    public void redo() {
        // No operation needed for UndoRedoListCommand
    }
}