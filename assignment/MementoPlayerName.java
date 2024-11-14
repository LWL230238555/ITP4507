class MementoPlayerName {
    private Player player;
    private String playerName;

    public MementoPlayerName(Player player) {
        this.player = player;
        playerName = player.getPlayerName();
    }

    public Player getPlayer() {
        return player;
    }

    public void restore() {
        player.setPlayerName(playerName);
    }
}
