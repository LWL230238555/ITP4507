import java.util.*;

// Factory class responsible for creating Hero objects based on user input
public class HeroFactory {

    // Method to create a Hero based on user input
    public Hero createHero(Scanner sc) {
        System.out.print("Please input hero information (id, name): ");
        String[] heroInfo = sc.nextLine().split(",\\s*");
        if (heroInfo.length < 2) {
            System.out.println("Invalid input. Please provide both hero ID and name.");
            return null;
        }
        String heroId = heroInfo[0];
        String heroName = heroInfo[1];

        // Prompt for hero type
        System.out.print("Hero Type (1 = Warrior | 2 = Warlock): ");
        int type;
        try {
            type = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter 1 or 2.");
            return null;
        }

        Hero hero;
        if (type == 1) {
            hero = new Warrior(heroId, heroName);
            return hero;// Assuming Warrior is a valid subclass of Hero
        } else if (type == 2) {
            hero = new Warlock(heroId, heroName);
            return hero; // Assuming Warlock is a valid subclass of Hero
        } else {
            System.out.println("Invalid hero type.");
            return null;
        }

    }

}
