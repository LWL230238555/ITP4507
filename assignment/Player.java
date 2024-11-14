
import java.util.Vector;

public class Player {
    private String playerID;
    private String playerName;
    private Vector<Hero> heroes;

    public Player(String playerID, String playerName) {
        this.playerID = playerID;
        this.playerName = playerName;
        this.heroes = new Vector<>();
    }

    public String getPlayerID() {
        return playerID;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Vector<Hero> getHeroes() {
        return heroes;
    }

    public void addHero(Hero hero) {
        heroes.add(hero);
    }
    
    public void removeHero(Hero hero) {
        heroes.remove(hero);
    }
    
    public void showPlayerDetails(){
        //add your own codes
        System.out.println("Player " +playerName+" ("+playerID+")");
        for (Hero hero : heroes) {
            hero.showHeroStatus();
    }
}
}