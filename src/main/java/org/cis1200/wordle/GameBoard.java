package org.cis1200.wordle;

/*
 * CIS 120 HW09 - Wordle Demo
 * (c) University of Pennsylvania
 * Created by Bayley Tuch, Sabrina Green, and Nicolas Corona in Fall 2020.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * This class instantiates a Wordle object, which is the model for the game.
 * As the user clicks the game board, the model is updated. Whenever the model
 * is updated, the game board repaints itself and updates its status JLabel to
 * reflect the current state of the model.
 * 
 * This game adheres to a Model-View-Controller design framework. This
 * framework is very effective for turn-based games. We STRONGLY
 * recommend you review these lecture slides, starting at slide 8,
 * for more details on Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec37.pdf
 * 
 * In a Model-View-Controller framework, GameBoard stores the model as a field
 * and acts as both the controller (with a MouseListener) and the view (with
 * its paintComponent method and the status JLabel).
 */
@SuppressWarnings("serial")
public class GameBoard extends JPanel {
    private Wordle ttt; // model for the game
    private JLabel status; // current status text

    // Game constants
    public static final int BOARD_WIDTH = 300;
    public static final int BOARD_HEIGHT = 300;

    /**
     * Initializes the game board.
     */
    public GameBoard(JLabel statusInit) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        ttt = new Wordle(); // initializes model for the game
        status = statusInit; // initializes the status JLabel

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (ttt.getRecentGuess().length() == 5
                            && ttt.checkValid(ttt.getRecentGuess())) {
                        //System.out.println(ttt.getRecentGuess());
                        ttt.playTurn(ttt.getRecentGuess());
                        ttt.checkwin(ttt.getRecentGuess());
                        ttt.setRecentGuess("");
                        ttt.setCurrentcell(0);
                        //System.out.print(ttt.getCurrentcell());
                        repaint();
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    if (!(ttt.getCurrentcell() <= 0)) {
                        ttt.setBoard(ttt.getNumTurns(), ttt.getCurrentcell() - 1, ' ');
                        ttt.deleteCharRecentGuess(ttt.getCurrentcell());
                        ttt.setCurrentcell(ttt.getCurrentcell() - 1);
                        ttt.deOneRecentGuess();
                        repaint();
                    }
                } else if (!(ttt.getCurrentcell() > 4)) {
                    if (!(ttt.getGameOver())) {
                        if ((e.getKeyChar() > 96 && e.getKeyChar() < 123)) {
                            //System.out.println(ttt.getCurrentcell());
                            //System.out.println(e.getKeyChar());
                            ttt.setBoard(ttt.getNumTurns(), ttt.getCurrentcell(), e.getKeyChar());
                            ttt.setRecentGuess(ttt.getRecentGuess() + e.getKeyChar());
                            ttt.setCurrentcell(ttt.getCurrentcell() + 1);
                            repaint();
                        }
                    }
                }
            }
            public void keyReleased(KeyEvent e) {
            }
        }
        );
    }

    /**
     * (Re-)sets the game to its initial state.
     */
    public void reset() {
        ttt.reset();
        status.setText("Wordle");
        repaint();
        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }

    public void load() {
        ttt.load();
        status.setText("Wordle");
        repaint();
        requestFocusInWindow();
    }

    public String instructions() {
        return ttt.instructions();
    }

    /**
     * Draws the game board.
     * 
     * There are many ways to draw a game board. This approach
     * will not be sufficient for most games, because it is not
     * modular. All of the logic for drawing the game board is
     * in this method, and it does not take advantage of helper
     * methods. Consider breaking up your paintComponent logic
     * into multiple methods or classes, like Mushroom of Doom.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draws board grid
       /* g.drawLine(60, 0, 60, 300);
        g.drawLine(120, 0, 120, 300);
        g.drawLine(180, 0, 180, 300);
        g.drawLine(240, 0, 240, 300);

        g.drawLine(0, 50, 300, 50);
        g.drawLine(0, 100, 300, 100);
        g.drawLine(0, 150, 300, 150);
        g.drawLine(0, 200, 300, 200);
        g.drawLine(0, 250, 300, 250);*/

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                g.setColor(Color.gray);
                g.drawRect(8 + (j * 60), 6 + (i * 50), 45,40);
            }
        }

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                //draw color squares
                if (ttt.getColorBoardIndex(i,j).equals("green")) {
                    g.setColor(Color.green);
                }
                if (ttt.getColorBoardIndex(i,j).equals("yellow")) {
                    g.setColor(Color.yellow);
                }
                if (ttt.getColorBoardIndex(i,j).equals("gray")) {
                    g.setColor(Color.gray);
                }
                if (ttt.getColorBoardIndex(i,j).equals("white")) {
                    g.setColor(Color.white);
                }
                g.fillRect(8 + (j * 60), 6 + (i * 50), 45,40);
                //draw letters
                g.setColor(Color.black);
                char[] state = {ttt.getCell(j, i)};
                g.setFont(new Font("TimesNewRoman", Font.PLAIN, 25));
                g.drawChars(state, 0, 1, 22 + j * 60, 32 + i * 50);
            }
        }
        if (ttt.getLost()) {
            g.setColor(Color.black);
            g.setFont(new Font("TimesNewRoman", Font.PLAIN, 25));
            g.drawString("YOU LOST!", 78, 155);
            g.setFont(new Font("TimesNewRoman", Font.PLAIN, 10));
            g.drawString("CLICK RESET TO PLAY AGAIN", 68, 165);
        } else if (ttt.getGameOver()) {
            g.setColor(Color.black);
            g.setFont(new Font("TimesNewRoman", Font.PLAIN, 25));
            g.drawString("YOU WON!", 78, 155);
            g.setFont(new Font("TimesNewRoman", Font.PLAIN, 10));
            g.drawString("CLICK RESET TO PLAY AGAIN", 68, 165);
        }

    }

    /**
     * Returns the size of the game board.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }
}