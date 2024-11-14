public class PlayerFactory {

    public Player createPlayer(String id, String name) {
        return new Player(id, name);
    }
}
