/**
 * Gameplay.java
 * Scrabble
 *
 * Created by mscndle on 2/24/14.
 */

import java.util.*;

/**
 * This class handles move validation and score handling
 * Also handles the tileBag and tileScores
 */
public class Gameplay {

    private HashMap<Character, Integer> tileBag;    //unchecked warning without types
    private Player[] players = new Player[2];
    private int turn;
    Scores scores;

    public Gameplay() {
        this.initTileBag();
    }

    /**
     * Tile bag initialization and distribution
     * //TODO: Find a way to read these values directly from the file (if time permits)
     */
    private void initTileBag() {
        this.tileBag =  new HashMap<Character, Integer>();
        if (this.tileBag.isEmpty()) {
            this.tileBag.put('A', 9);
            this.tileBag.put('B', 2);
            this.tileBag.put('C', 2);
            this.tileBag.put('D', 4);
            this.tileBag.put('E', 12);
            this.tileBag.put('F', 2);
            this.tileBag.put('G', 3);
            this.tileBag.put('H', 2);
            this.tileBag.put('I', 9);
            this.tileBag.put('J', 1);
            this.tileBag.put('K', 1);
            this.tileBag.put('L', 4);
            this.tileBag.put('M', 2);
            this.tileBag.put('N', 6);
            this.tileBag.put('O', 8);
            this.tileBag.put('P', 2);
            this.tileBag.put('Q', 1);
            this.tileBag.put('R', 6);
            this.tileBag.put('S', 4);
            this.tileBag.put('T', 6);
            this.tileBag.put('U', 4);
            this.tileBag.put('V', 2);
            this.tileBag.put('W', 2);
            this.tileBag.put('X', 1);
            this.tileBag.put('Y', 2);
            this.tileBag.put('Z', 1);
        }
    }

    /**
     * / *****  USE THE getRandomLetterFromBag(char C) instead of this    *****  /
     *
     * Used when game starts and after each move
     * @param C     Tile to be removed
     * @return      removed successfully
     */
    public boolean removeTileFromBag(char C) {
        if (this.tileBag.containsKey(C) && this.tileBag.get(C) > 0) {
            int newValue = this.tileBag.get(C) - 1;
            this.tileBag.put(C, newValue);
            return true;
        }
        return false;
    }

    /**
     * Used when players give up their turns
     * @param C     Tile added back to bag
     * @return      added successfully
     */
    public void addTileToBag(char C) {
        if (this.tileBag.containsKey(C)) {
            int newValue = this.tileBag.get(C) + 1;
            this.tileBag.put(C, newValue);
        }
    }

    public int totalNumberOfTilesInBag() {
        int total = 0;

        for (int i=65; i<=90; i++) {
            char C = (char) i;
            total += tileBag.get(C);
        }

        return total;

    }


    /**
     * Used when players give up their turns
     * @param C     Letter missing from tray
     */
    public Object getRandomLetterFromBag(char C) {
        Random random = new Random();
        Object[] letters = this.tileBag.keySet().toArray();
        Object randomLetter = letters[random.nextInt(letters.length)];

        if (this.tileBag.get(randomLetter) == 0) {
            // need to go recursive on the getRandomLetterFromBag function
        }

        else {
            int newValue = this.tileBag.get(randomLetter) -1;
            this.tileBag.put((Character) randomLetter, newValue);
        }
        return randomLetter;
    }


    public void createNewPlayers() {
        Scanner scanner = new Scanner(System.in);
        for (int i=0; i < players.length; i++) {
            System.out.println("Enter the name of player "+ (i+1) +" : ");
            players[i] = new Player(scanner.next(), i, new String[7]);

        }

        this.scores = new Scores(players[0], players[1]);
    }

    public void endGame() {
        System.out.println("Game Over!");
        System.out.println("Thanks for playing "+players[0].getName()+" and "+players[1].getName()+"!");
    }


    public int switchTurn() {
        if (turn == 1 || turn == 0) {
            turn = 2;
        }
        else if (turn == 2) {
            turn = 1;
        }
        return turn;
    }

    public void gameOn(int turn, Board gameBoard) {
        Player thePlayer = null;
        if (turn == 1) {
            thePlayer = players[0];
        } else if (turn == 2) {
            thePlayer = players[1];
        }

        //Board game = new Board();
        Scanner scanner = new Scanner(System.in);
        // Hiding the board for now, for testing. it's big and annoying! :)
        View.printBoard(gameBoard);
        refillTray(thePlayer);
        View.showTray(thePlayer);
        System.out.println(thePlayer.getName() + ", ENTER A WORD [skip: *, quit: #, " +
                "reset tiles: @, view scores: $]");
        String theWord = scanner.next();

        if (theWord.equals("*")) {
            System.out.println("You decided to skip your turn.");
            gameOn(switchTurn(), gameBoard);
        } else if (theWord.equals("@")) {
            refreshTray(thePlayer);
            gameOn(switchTurn(), gameBoard);
        } else if (theWord.equals("$")) {
            View.printPlayerInformation(players[0], players[1], scores);
            //gameOn(turn, gameBoard);
        }

        else if (theWord.equals("#")) {
            endGame();
        }
        else {
            System.out.println("Now a direction [right: 1 or down: 2]:");
            int theDirection = scanner.nextInt();
            System.out.println("Start column: ");
            int theCol = scanner.nextInt();
            System.out.println("Start row: ");
            int theRow = scanner.nextInt();
            Move theMove = new Move(theWord, theDirection, theCol, theRow);

            if (isMoveValid(thePlayer, theMove, gameBoard)) {
                gameBoard.placeWordOnBoard(theMove);
                //int score = Scores.computeMoveScore(thePlayer, theMove);
                //this.scores.updatePlayerScore(thePlayer, score);
                refreshTray(thePlayer);
            }
            gameOn(switchTurn(), gameBoard);

            /*
            if (Board.validateWord(theWord)) {
                System.out.println(theWord +" is valid!");
                // Remove letters from user's tray
                // place word on board
                // calculate score
                // add score to total score
                gameOn(switchTurn());
            }
            else {
                // if word isn't working
                System.out.println("Sorry, your word isn't valid.");
                gameOn(switchTurn());
            }
            */
        }

    }

    public void refreshTray(Player player) {
        String[] lettersTray = player.getLetters();

        for (int i=0; i<lettersTray.length; i++) {
            this.addTileToBag(lettersTray[i].charAt(0));
            player.letters[i] = null;
        }
    }


    public void refillTray(Player player) {
        String[] lettersTray = player.getLetters();
        //int lettersMissing = 0;
        for (int i=0 ; i < lettersTray.length ; i++ ) {
            if (lettersTray[i] == "" || lettersTray[i] == null) {
                lettersTray[i] = String.valueOf(this.getRandomLetterFromBag('i'));
            }
        }
        player.setLetters(lettersTray);
    }


    /**
     * CAN YOU PLAY THAT MOVE ?
     * @param move  Move object played
     * @return      boolean
     */
    public boolean isMoveValid(Player player, Move move, Board board) {
        String word = move.word.toUpperCase();
        int row = move.startRow;
        int col = move.startCol;
        int dir = move.direction;
        String[] tileCopy = player.getLetters();
        Arrays.sort(tileCopy);
        boolean tilesPresent = false;

        System.out.println("Move details:");
        System.out.println("word:  " + word);
        System.out.println("row:  " + row);
        System.out.println("col:  " + col);
        System.out.println("dir:  " + dir);
        System.out.println("tileCopy:  " + Arrays.toString(tileCopy));


        System.out.println("[cool move validations begin]");
        //System.out.println(player.getName());
        //System.out.println("what are the player's letter? "+player.getLetters()

        // DOES THE BOARD OVERFLOW
        if ((dir == Move.RIGHT && (col + word.length() > 14)) ||
                (dir == Move.DOWN && (row + word.length() > 14))) {
            System.out.println("isMoveValid err: : Board overflow. Invalid move!");
            return false;
        }
        System.out.println("Board overflow validation.............................done");

        // IS THE WORD VALID USING A DICT
        if (!Board.validateWord(word)) {
            System.out.println("isMoveValid err: This word doesn't exist. Can't fool us!");
            return false;
        }
        System.out.println("Dictionary validation.................................done");

        // INTENDED WORD SHOULD BE THE LARGEST CONTIGUOUS STRING IN THAT DIRECTION
        if (dir == Move.RIGHT) {
            if (board.getTileOnBoard(row, col+word.length()+1) != ' ' ||
                    board.getTileOnBoard(row, col-1) != ' ') {
                System.out.println("isMoveValid err: Incomplete input word (find better message)");
                return false;
            }
        } else if (dir == Move.DOWN) {
            if (board.getTileOnBoard(row+word.length()+1, col) != ' ' ||
                    board.getTileOnBoard(row-1, col) != ' ') {
                System.out.println("Incomplete input word");
                return false;
            }
        }
        System.out.println("Contiguous string validation..........................done");

        // DOES THE FIRST MOVE CROSS THE BOARD CENTER
//        if (Move.totalNumberOfMoves == 0 && (row > 7 && col > 7)) {
//            System.out.println("isMoveValid err: First move should touch the board center!");
//            return false;
//        }

        if (Move.totalNumberOfMoves == 0) {
            if ((dir == Move.RIGHT && (col + word.length() < 7)) ||
                    (dir == Move.DOWN && (row + word.length() < 7)) ||
                    (row > 7 && col > 7)) {
                System.out.println("isMoveValid err: First move should touch the board center!");
                return false;
            }
        }


        // DOES THE SECOND (or greater) MOVE TOUCH ONE OF THE EXISTING TILES
        if (Move.totalNumberOfMoves >= 1) {
            for (int i=0; i<word.length(); i++) {
                if (dir == Move.RIGHT) {
                    if (board.getTileOnBoard(row, col+i) != ' ')    {   tilesPresent = true;    }
                } else if (dir == Move.DOWN) {
                    if (board.getTileOnBoard(row+i, col) != ' ')    {   tilesPresent = true;    }
                }
            }
            if (!tilesPresent)  {
                System.out.println("isMoveValid err: New word has to touch an existing word-HAHA");
                return false;
            }
        }

        System.out.println("Board placement validation............................done");


        //  LETS ASSUME THE USER WILL SELECT THE TILES CORRECTLY


//        // CAN THAT WORD BE CONSTRUCTED USING EXISTING PIECES AND PLAYER TILES
//        for (int i=0; i<word.length(); i++) {
//            if (dir == Move.RIGHT) {
//                if (board.getTileOnBoard(row, col+i) == word.charAt(i)) {
//                    //don't do anything, this is expected (unless it happens for all letters)
//                } else if (board.getTileOnBoard(row, col+i) == ' ') {
//                    //empty cell - user should have it
//                    //System.out.println("HELLO--"+Arrays.binarySearch(tileCopy,
//                    //        String.valueOf(word.charAt(i))));
//                    int pos = Arrays.binarySearch(tileCopy, String.valueOf(word.charAt(i)));
//                    if (pos >= 0) {
//                        tileCopy[pos] = null;
//                    } else {
//                        System.out.println("isMoveValid err: You are missing the letter '"+ word.charAt(i)+"'.");
//                        return false;
//                    }
//                } else {
//                    //neither empty cell nor expected char -- stepping over someone else's space
//                    System.out.println("isMoveValid err: What is this character?? We're unable to insert '" + word.charAt(i)+"'.");
//                    return false;
//                }
//            } else if (dir == Move.DOWN) {
//                if (board.getTileOnBoard(row+i, col) == word.charAt(i)) {
//                    //expected for at most all-1 cases
//                } else if (board.getTileOnBoard(row+i, col) == ' ') {
//                    //empty cell - find tile with player
//                    int pos = Arrays.binarySearch(tileCopy, String.valueOf(word.charAt(i)));
//                    if (pos >= 0) {
//                        tileCopy[pos] = null;
//                    } else {
//                        System.out.println("isMoveValid err: You are missing the letter '" + word.charAt(i)+"'.");
//                        return false;
//                    }
//                } else {
//                    //neither empty cell nor expected char -- stepping over someone else's space
//                    System.out.println("isMoveValid err: What is this character?? We're unable to insert '" + word.charAt(i)+"'.");
//                    return false;
//                }
//            }
//        }
//        //no letter was used from the tray
//        if (tileCopy.length == player.getLetters().length) {
//            System.out.println("isMoveValid err: The word already exists. Try again!");
//            return false;
//        }

        // ARE SECONDARY WORDS VALID IF THEY EXIST
        ArrayList<String> secList = this.getSecondaryWords(move, board);
        if (!this.validateSecondaryWords(secList)) {
            System.out.println("isMoveValid err: The other words you tried to create are invalid. Sorry!");
            return false;
        }

        // IF YOU HAVE REACHED HERE, LIFE IS GOOD
        // 1.   SET THE MOVE TO VALID
        // 2.   SECONDARY WORDS CREATED SHOULD BE PASSED ONTO THE MOVE OBJECT
        move.isValid = true;
        Move.registerCorrectMove(move);
        move.secondaryWords = secList;
        System.out.println("[cool move validations complete]");
        return true;
    }

    /**
     * Private helper to see if all secondary words are valid
     * @param list  input list of secondary words
     * @return      boolean
     */
    private boolean validateSecondaryWords(ArrayList<String> list) {
        for (String str: list) {
            if (!Board.validateWord(str)) {
                System.out.println(str + " not valid secondary word.");
                return false;
            }
        }

        return true;
    }

    /**
     * Private helper method that returns the other words formed due to a move
     * @param move  current move
     * @param board board object
     * @return      List containing secondary words
     */
    //TODO: change back to private once done testing
    public ArrayList<String> getSecondaryWords(Move move, Board board) {
        ArrayList<String> secWords = new ArrayList<String>();
        String word = move.word.toUpperCase();
        int row = move.startRow;
        int col = move.startCol;
        int dir = move.direction;

        for (int i=0; i<word.length(); i++) {
            if (dir == Move.RIGHT) {
                if (board.getTileOnBoard(row, col+i) == ' ') {
                    //here the user is inserting a tile
                    if (board.getTileOnBoard(row-1, col+i) != ' ' ||
                            board.getTileOnBoard(row+1, col+i) != ' ') {
                        System.out.println("calling construct word for: " + word.charAt(i));
                        secWords.add(this.constructWord(row, col+i ,word.charAt(i), move, board));
                    }
                }
            } else if (dir == Move.DOWN) {
                if (board.getTileOnBoard(row+i, col) == ' ') {
                    if (board.getTileOnBoard(row+i, col-1) != ' ' ||
                            board.getTileOnBoard(row+i, col+1) != ' ') {
                        System.out.println("calling construct word for: " + word.charAt(i));
                        secWords.add(this.constructWord(row+i, col, word.charAt(i), move, board));
                    }
                }
            }
        }

        return secWords;
    }


    /**
     * Private helper that constructs word out of the longest contiguous string
     * @param row   row index
     * @param col   col index
     * @param C
     * @return      constructed word / null
     */
    private String constructWord(int row, int col, char C, Move move, Board board) {
        StringBuilder newWord = new StringBuilder();
        int start;
        int end;
        int i = 1;
        int j = 1;
        int dir = move.direction;
        //String word = move.word;
        C = Character.toUpperCase(C);

        // sorry
        // very ugly logic
        // but it works
        if (dir == Move.DOWN) {
            while (col-j >= 0 && board.getTileOnBoard(row, col-j) != ' ') {
                j++;
            }
            start = col-j+1;    //determine start index of word to be formed
            j = 1;              //reset j

            while (col+j <= 14 && board.getTileOnBoard(row, col+j) != ' ') {
                j++;
            }
            end = col+j-1;      //determine end index of word

            //construct new word
            for (int newCol=start; newCol<=end;  newCol++) {
                if (newCol == col) {    newWord.append(C);                  }
                else {  newWord.append(board.getTileOnBoard(row, newCol));  }
            }

            return newWord.toString().trim();

        } else if (dir == Move.RIGHT) {
            //same logic as above in a different direction
            while (row-i >= 0 && board.getTileOnBoard(row-i, col) != ' ') {
                i++;
            }
            start = row-i+1;
            i = 1;

            while (row+i <= 14 && board.getTileOnBoard(row+i, col) != ' ') {
                i++;
            }
            end = row+i-1;

            for (int newRow = start; newRow <= end; newRow++) {
                if (newRow == row) {    newWord.append(C);                  }
                else {  newWord.append(board.getTileOnBoard(newRow, col));  }
            }

            return newWord.toString().trim();
        }

        //Ideally it should never this side of the function
        System.out.println("constructing null");
        return null;
    }




}