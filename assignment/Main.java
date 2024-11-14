import java.util.Scanner;
import java.util.Vector;
import java.util.Stack;

public class Main {
    public static Scanner scanner = new Scanner(System.in);
    public static Vector<Player> playerList = new Vector<>();
    public static Stack<Command> commandStack = new Stack<>();
    public static Stack<Command> redoStack = new Stack<>();
    public static Player currentPlayer = null;

    public static void main(String[] args) {
        System.out.println("Fantastic World (FW)");
        String menuText = "c = create player, g = set current player, a = add hero,\n" +
                          "m = call hero skill, d = delete hero, s = show player,\n" +
                          "p = display all players, t = change player’s name,\n" +
                          "u = undo, r = redo, l = list undo/redo, x = exit system";



        while (true) {
            displayCurrentPlayer();
            System.out.println(menuText);
            System.out.print("Please enter command [ c | g | a | m | d | s | p | t | u | r | l | x ]: ");
            String option = scanner.nextLine().toLowerCase().trim();

            Command command = createCommand(option);
            if (command != null) {
                command.execute();
            } else if (option.equals("x")) {
                System.out.println("\nExiting System...");
                break;
            } else {
                System.out.println("Invalid command. Please try again.");
            }
        }
    }

    private static void displayCurrentPlayer() {
        if (currentPlayer != null) {
            System.out.println("The current player is " + currentPlayer.getPlayerID() + " " + currentPlayer.getPlayerName() + ".");
        }
    }

    public static Command createCommand(String option) {
        switch (option) {
            case "c":
                return new CreatePlayerCommand(scanner, playerList);
            case "g":
                return new SetCurrentPlayerCommand(scanner, playerList);
            case "a":
                return new AddHeroCommand(scanner, currentPlayer);
            case "m":
                return new CallHeroSkillCommand(currentPlayer);
            case "d":
                return new DeleteHeroCommand(scanner, currentPlayer);
            case "s":
                return new ShowPlayerCommand(currentPlayer);
            case "p":
                return new DisplayAllPlayersCommand(playerList);
            case "t":
                return new ChangePlayerNameCommand(scanner, currentPlayer);
            case "u":
                return new UndoCommand(commandStack, redoStack);
            case "r":
                return new RedoCommand(commandStack, redoStack);
            case "l":
                return new UndoRedoListCommand(commandStack, redoStack);
            default:
                return null;
        }
    }
}