import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.HashMap;


/**
 * Game class for scrabble
 * Stores the gameBoard, dictionary
 */
public class Board {

    //TODO: change board representation to add W, w, L, l, -
    private char[][] scrabbleBoard;
    private static HashSet<String> dict;
    private static HashMap<String, String> boardScores;

    public Board() {
        this.initBoard();
        this.initBoardScores();

        try { this.initDict();  }
        catch (FileNotFoundException e) {
            System.err.println("FileNotFoundException: " + e);
        }
    }

    /**
     * Initialized the board
     */
    //TODO: change display logic. Let View class handle the chars "-, W, w, L, l"
    //TODO: the actual board only has tiles or is empty
    private void initBoard() {
        this.scrabbleBoard = new char[15][15];
        for (int i=0; i<15; i++) {
            for (int j=0; j<15; j++) {
                this.scrabbleBoard[i][j] = ' ';
            }
        }
    }

    /**
     * Initializes the dictionary by reading from a file
     * @throws FileNotFoundException
     */
    private void initDict() throws FileNotFoundException {
        Board.dict = new HashSet<String>();
        BufferedReader dictReader = new BufferedReader(new FileReader("../words.txt"));
        try {
            String line = dictReader.readLine();

            //add all words from words.txt to hashSet
            while (line != null) {
                dict.add(line.toLowerCase());
                line = dictReader.readLine();
            }
        } catch (IOException e) {
            System.err.println("IOException: " + e);
            e.printStackTrace();
        }
    }

    /**
     * Initializes the map that stores special scores for cells
     */
    private void initBoardScores() {
        Board.boardScores = new HashMap<String, String>();
        //TRIPLE WORD
        boardScores.put("00", "3W");
        boardScores.put("70", "3W");
        boardScores.put("07", "3W");
        boardScores.put("014", "3W");
        boardScores.put("140", "3W");
        boardScores.put("147", "3W");
        boardScores.put("714", "3W");
        boardScores.put("1414", "3W");


        //DOUBLE WORD
        boardScores.put("11", "2W");
        boardScores.put("22", "2W");
        boardScores.put("33", "2W");
        boardScores.put("44", "2W");
        boardScores.put("113", "2W");
        boardScores.put("212", "2W");
        boardScores.put("311", "2W");
        boardScores.put("410", "2W");
        boardScores.put("131", "2W");
        boardScores.put("122", "2W");
        boardScores.put("113", "2W");
        boardScores.put("104", "2W");
        boardScores.put("1010", "2W");
        boardScores.put("1111", "2W");
        boardScores.put("1212", "2W");
        boardScores.put("1313", "2W");

        //TRIPLE LETTER
        boardScores.put("51", "3L");
        boardScores.put("91", "3L");
        boardScores.put("15", "3L");
        boardScores.put("55", "3L");
        boardScores.put("95", "3L");
        boardScores.put("135", "3L");
        boardScores.put("19", "3L");
        boardScores.put("59", "3L");
        boardScores.put("99", "3L");
        boardScores.put("139", "3L");
        boardScores.put("513", "3L");
        boardScores.put("913", "3L");

        //DOUBLE LETTER
        boardScores.put("30", "2L");
        boardScores.put("110", "2L");
        boardScores.put("62", "2L");
        boardScores.put("82", "2L");
        boardScores.put("03", "2L");
        boardScores.put("73", "2L");
        boardScores.put("143", "2L");
        boardScores.put("26", "2L");
        boardScores.put("66", "2L");
        boardScores.put("86", "2L");
        boardScores.put("126", "2L");
        boardScores.put("37", "2L");
        boardScores.put("117", "2L");
        boardScores.put("28", "2L");
        boardScores.put("68", "2L");
        boardScores.put("88", "2L");
        boardScores.put("128", "2L");
        boardScores.put("011", "2L");
        boardScores.put("711", "2L");
        boardScores.put("1411", "2L");
        boardScores.put("612", "2L");
        boardScores.put("812", "2L");
        boardScores.put("314", "2L");
        boardScores.put("1114", "2L");
    }

    public static String getBoardScoreForTile(String ref) {
        if (Board.boardScores.containsKey(ref)) {
            return Board.boardScores.get(ref);
        } else {
            return null;
        }
    }

    /**
     * Returns the char on the board at (row, col)
     * @param row   row index
     * @param col   col index
     * @return      char at (row, col)
     */
    public char getTileOnBoard(int row, int col) {
        return this.scrabbleBoard[row][col];
    }

    /**
     * @param word  input to be validated
     * @return      boolean if word is valid
     */
    public static boolean validateWord(String word) {
        //System.out.println("Dict is validating: " + word);
        return dict.contains(word.toLowerCase());
    }

    /**
     * Returns string representation of the Board
     * @return      Board string object
     */


    /**
     * @return  The scrabble board for use by the View class
     */
    public char[][] getScrabbleBoardClone() {
        char[][] boardClone = new char[15][15];

        for (int i=0; i<15; i++) {
            for (int j=0; j<15; j++) {
                boardClone[i][j] = this.scrabbleBoard[i][j];
            }
        }

        return boardClone;
    }


    /**
     * Adds the new word on the board
     * @param move
     */
    public void placeWordOnBoard(Move move) {
        //assumption is that the word is valid
        if (move.isValid) {
            String word = move.word;
            int row = move.startRow;
            int col = move.startCol;

            for (int i=0; i<word.length(); i++) {
                if (move.direction == Move.RIGHT) {
                    //increase row val to add word to the right
                    System.out.println("adding word to right");
                    this.scrabbleBoard[row][col+i] = word.toUpperCase().charAt(i);
                } else if (move.direction == Move.DOWN) {
                    //increase col val to add word down
                    this.scrabbleBoard[row+i][col] = word.toUpperCase().charAt(i);
                }
            }
        }
    }


}
