import java.util.Scanner;

public class Player  {

    private String name;
    private int id;
    String[] letters;
    int currentScore;
    Player[] players;

    public Player (String name, int id, String[] letters) {
        this.name = name;
        this.id = id;
        this.letters = letters;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    // TODO: score should be stored in gameplay.
    public int getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    public String[] getLetters() {
        return letters;
    }

    public void setLetters(String[] letters) {
        this.letters = letters;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    /**
     * Used to edit the player's tray after each move
     * @param C
     */
    //TODO: do we need this ?
    public void removeTileFromTray(char C) {


    }
}
