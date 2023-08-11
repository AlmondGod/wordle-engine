package org.cis1200.wordle;
import java.util.Scanner;
import java.util.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.*;

/**
 * CIS 120 HW09 - Wordle Demo
 * (c) University of Pennsylvania
 * Created by Bayley Tuch, Sabrina Green, and Nicolas Corona in Fall 2020.
 */

/**
 * This class is a model for Wordle.
 * 
 * This game adheres to a Model-View-Controller design framework.
 * This framework is very effective for turn-based games. We
 * STRONGLY recommend you review these lecture slides, starting at
 * slide 8, for more details on Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec36.pdf
 * 
 * This model is completely independent of the view and controller.
 * This is in keeping with the concept of modularity! We can play
 * the whole game from start to finish without ever drawing anything
 * on a screen or instantiating a Java Swing object.
 * 
 * Run this file to see the main method play a game of Wordle,
 * visualized with Strings printed to the console.
 */
public class Wordle {

    private char[][] board;
    private String[][] colorboard;
    private int numTurns = 0;
    //private boolean player1;
    private int currentcell;
    private boolean gameOver;

    private boolean lost;
    private String recentGuess;
    private String aword;

    private BufferedWriter bw;

    private BufferedReader br;

    private boolean previousGame;

    private Map<Integer, String> validwords = new TreeMap<>();

    private Map<Integer, String> wordlewords = new TreeMap<>();


    /**
     * Constructor sets up game state.
     */

    public Wordle() {
        previousGame = false;
        reset();
        gameOver = false;
        lost = false;
        aword = "train";
        int x;
        char s;
        colorboard = new String[6][5];
        for (int i = 0; i < 5; i++) {
            s = aword.charAt(i);
        }
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                colorboard[i][j] = "white";
            }
        }
        recentGuess = "";
    }

    public void setValidWords() {
        String filePath = "files/validwords.txt";
        try {
            BufferedReader b = new BufferedReader(new FileReader(filePath));
            if (filePath == null) {
                throw new IllegalArgumentException();
            }
            String word = b.readLine();
            System.out.println(word);
            int i = 0;
            while (word != null) {
                validwords.put(i, word);
                i++;
                word = b.readLine();
            }
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public void setWordleWords() {
        String filePath2 = "files/wordlewords.txt";
        try {
            if (filePath2 == null) {
                throw new IllegalArgumentException();
            }
            BufferedReader b2 = new BufferedReader(new FileReader(filePath2));
            String wor = b2.readLine();
            int i = 0;
            while (wor != null) {
                wordlewords.put(i, wor);
                i++;
                wor = b2.readLine();
            }
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    /*public void recordCurrents (ArrayList<String> l) {
        String wor = "files/savedstate";
        try{
            w = new BufferedWriter(new FileWriter(wor));
            for (int i = 0; i < l.size(); i++) {
                w.write(l.get(i));
                w.newLine();
            }
            w.flush();
            w.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }*/

    public void writeword(String word) {
        //String wor = "files/save.txt";
        try {
            //bw = new BufferedWriter(new FileWriter(wor));
            //System.out.println("writing this to file "+ word);
            bw.write(word);
            //System.out.println("writer reader: " + readsavedstate());
            bw.newLine();
            //bw.flush();
            //bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String> readsavedstate() {
        String wor = "files/save.txt";
        //String wor = "files/s.txt";
        ArrayList<String> returnlist = new ArrayList<>();
        String s;
        try {
            System.out.println("hi");
            br = new BufferedReader(new FileReader(wor));
            s = br.readLine();
            //System.out.println(s);
            //System.out.println("reader: " + s);
            while (s != null) {
                returnlist.add(s);
                //System.out.println(s);
                s = br.readLine();
            }
            System.out.println(returnlist);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
        return returnlist;
    }

    /*public void readsavedkeys() {
        String wor = "files/savedkeys";
        String s;
        try {
            BufferedReader b = new BufferedReader(new FileReader(wor));
            while((s = b.readLine()) != null) {
                aword = s;
            }
        }
        catch(FileNotFoundException e) {
            throw new IllegalArgumentException();
        }
        catch(IOException e) {
            throw new RuntimeException();
        }
    }*/

    /////////////////////////////////////////////////////////////////////
    //------------------------WORDLE FUNCTIONS-------------------------//
    /////////////////////////////////////////////////////////////////////
    public int getNumTurns() {
        return this.numTurns;
    }

    public char[][] getBoard() {
        return this.board;
    }

    public boolean getGameOver() {
        return this.gameOver;
    }

    public void setGameOver(boolean b) {
        this.gameOver = b;
    }

    public void setBoard(int i, int j, char setto) {
        this.board[i][j] = setto;
    }

    public String getRecentGuess() {
        return this.recentGuess;
    }

    public void setRecentGuess(String s) {
        this.recentGuess = s;
    }

    public String getaword() {
        return this.aword;
    }

    public void setaword(String s) {
        this.aword = s;
    }

    public boolean getLost() {
        return this.lost;
    }

    public void setLost(boolean b) {
        this.lost = b;
    }

    public void deOneRecentGuess() {
        String z = "";
        for (int i = 0; i < recentGuess.length() - 1; i++) {
            z += recentGuess.charAt(i);
        }
        recentGuess = z;
    }

    public int getCurrentcell() {
        return currentcell;
    }

    public void setCurrentcell(int i) {
        this.currentcell = i;
    }

    public String getColorBoardIndex(int i, int j) {
        return colorboard[i][j];
    }

    public void deleteCharRecentGuess(int x) {
        String y = "";
        for (int i = 0; i < recentGuess.length(); i++) {
            if (!(i == x)) {
                y += recentGuess.charAt(i);
            }
        }
        recentGuess = y;
    }

    public boolean checkValid(String guess) {
        if (validwords.containsValue(guess)) {
            return true;
        }
        return false;
        //return false;
    }

    public String[] checkTilesCorrect(String guess) {
        String[] r = new String[5];
        //make sure Character can equal char
        Map<Character, Integer> charsguessed = new TreeMap<>();
        //Set<Character> charlistguessed = new TreeSet<>();
        char s;

        for (int i = 0; i < 5; i++) {
            //correct letter correct spot
            if (aword.charAt(i) == guess.charAt(i)) {
                r[i]  = "green";
            } else if (aword.indexOf(guess.charAt(i)) != -1) {
                r[i] = "yellow";
            } else {
                r[i] = "gray";
            }
        }
        return r;
    }


    /**
     * playTurn allows players to play a turn. Returns true if the move is
     * successful and false if a player tries to play in a location that is
     * taken or after the game has ended. If the turn is successful and the game
     * has not ended, the player is changed. If the turn is unsuccessful or the
     * game has ended, the player is not changed.
     *
     * @param guess string guessed
     * @return whether the turn was successful
     */
    public boolean playTurn(String guess) {
        if (!checkValid(guess) || gameOver) {
            return false;
        }
        for (int i = 0; i < 5; i++) {
            board[numTurns][i] = guess.charAt(i);
            colorboard[numTurns][i] = checkTilesCorrect(guess)[i];
        }
        checkwin(guess);
        numTurns++;
        return true;
    }

    /**
     * checkWinner checks whether the game has reached a win condition.
     * checkWinner only looks for horizontal wins.
     *
     * @param guess
     */
    public void checkwin(String guess) {
        gameOver = false;
        lost = false;
        if (guess.equals(aword)) {
            gameOver = true;
        }
        if (numTurns >= 6 && !(guess.equals(aword))) {
            lost = true;
        }

    }

    /**
     * printGameState prints the current game state
     * for debugging.
     */
    public void printGameState() {
        System.out.println("\n\nTurn " + numTurns + ":\n");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j]);
                System.out.print(colorboard[i][j]);
                if (j < 5) {
                    System.out.print(" | ");
                }
            }
            if (i < 5) {
                System.out.println("\n---------");
            }
        }
    }

    /**
     * reset (re-)sets the game state to start a new game.
     */
    public void reset() {
        //save
        if (previousGame) {
            try {
                String filePath = "files/save.txt";
                String s = "";
                bw = new BufferedWriter(new FileWriter(filePath));
                br = new BufferedReader(new FileReader(filePath));
                writeword(aword);
                //System.out.println("reset1" + readsavedstate());
                for (int i = 0; i < 6; i++) {
                    for (int j = 0; j < 5; j++) {
                        s += board[i][j];
                    }
                    if (s.length() == 5) {
                        writeword(s);
                    }
                    s = "";
                }
                //System.out.println("please work: " + readsavedstate());
                bw.flush();
                bw.close();
            } catch (IOException e) {
                throw new IllegalArgumentException(e);
            }
        }
        board = new char[6][5];
        colorboard = new String[6][5];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                colorboard[i][j] = "white";
            }
        }
        numTurns = 0;
        gameOver = false;
        lost = false;
        setWordleWords();
        setValidWords();
        int x = (int) (Math.random() * wordlewords.size());
        //System.out.println(wordlewords.size());
        //System.out.println(x);
        aword = wordlewords.get(x);
        System.out.println(aword);
        previousGame = true;
        currentcell = 0;
    }

    public void load() {
        try {
            //System.out.println("load 1" + readsavedstate());
            String filePath = "files/save.txt";
            br = new BufferedReader(new FileReader(filePath));
            ArrayList<String> savedstate = readsavedstate();
            aword = savedstate.get(0);
            //System.out.println("load 2" + readsavedstate());
            board = new char[6][5];
            colorboard = new String[6][5];
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 5; j++) {
                    colorboard[i][j] = "white";
                }
            }
            numTurns = 0;
            gameOver = false;
            lost = false;
            setWordleWords();
            setValidWords();

            int x = 0;
            String word;
            while (x < 6) {
                word = savedstate.get(x + 1);
                this.playTurn(word);
                x++;
            }
            currentcell = 0;

            System.out.println(aword);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public String instructions() {
        String wor = "files/instructions.txt";
        String s = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(wor));
            String l;
            while ((l = br.readLine()) != null) {
                s += l + "\n";
            }
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
        return s;
    }

    /**
     * getCurrentPlayer is a getter for the player
     * whose turn it is in the game.
     * 
     * @return true if it's Player 1's turn,
     *         false if it's Player 2's turn.
     */
    /*public boolean getCurrentPlayer() {
        return player1;
    }*/

    /**
     * getCell is a getter for the contents of the cell specified by the method
     * arguments.
     *
     * @param c column to retrieve
     * @param r row to retrieve
     * @return an integer denoting the contents of the corresponding cell on the
     *         game board. 0 = empty, 1 = Player 1, 2 = Player 2
     */
    public char getCell(int c, int r) {
        return board[r][c];
    }

    /**
     * This main method illustrates how the model is completely independent of
     * the view and controller. We can play the game from start to finish
     * without ever creating a Java Swing object.
     *
     * This is modularity in action, and modularity is the bedrock of the
     * Model-View-Controller design framework.
     *
     * Run this file to see the output of this method in your console.
     */
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Wordle t = new Wordle();
        String s = scan.nextLine();

        t.playTurn(s);
        t.printGameState();

        while (!t.gameOver) {
            s = scan.nextLine();
            t.playTurn(s);
            t.printGameState();
        }

        System.out.println();
        System.out.println();
        System.out.println("You won!");
    }
}
