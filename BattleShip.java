///////    BattleShip.java    ///////

import java.awt.event.*;
import java.net.URL;
import java.awt.*;
import java.applet.*;

// Applet creates a BattleBoard object
// and BattleGame object (game) that uses the board
// The game object manages the game 
// The board applet handles events and drawing
 

public class BattleShip extends Applet {
    Button one = new Button("new");
    Button two = new Button("quit");
    Label lab1 = new Label("Computer's shots: ");
    Label lab2 = new Label("Your shots: ");
    Label lab3 = new Label("Hits    Sunk");
    Label lab4 = new Label("Hits    Sunk");
    Label lab5 = new Label("Aircraft Carrier");
    Label lab6 = new Label("Aircraft Carrier");
    Label lab7 = new Label("Cruiser");
    Label lab8 = new Label("Cruiser");
    Label lab9 = new Label("Submarine");
    Label lab10 = new Label("Submarine");
        
    public void init() {
        setLayout(null);
        codeBase = getCodeBase();
        setBoard();
        game = new BattleGame(board);
        add(one);
        one.setBounds(20,20,40,25);
        one.addActionListener(new B1());
        add(two);
        two.setBounds(65, 20, 40,25);
        two.addActionListener(new B2());
        add(lab1);
        add(lab2);
        add(lab3);
        add(lab4);
        add(lab5);
        add(lab6);
        add(lab7);
        add(lab8);
        add(lab9);
        add(lab10);
        lab1.setBounds(20, 340, 100, 20);
        lab2.setBounds(320, 340,80,20);
        lab3.setBounds(170, 340,100,20);
        lab4.setBounds(470, 340,100,20);
        lab5.setBounds(20, 360,100,20);
        lab6.setBounds(320, 360,100,20);
        lab7.setBounds(20, 380,100,20);
        lab8.setBounds(320, 380,100,20);
        lab9.setBounds(20, 400,100,20);
        lab10.setBounds(320, 400,100,20);
        addMouseListener(new BattleClickHandler(this));
        newGame();
    }
    public void paint(Graphics g) {
        g.setColor(Color.red);                // foreground color
        game.draw(g, this, first);
    }
    protected void invalidMove() {}
    public void gameBegin() {}
    protected void setBoard() {
        board = new BattleBoard(0, 0,
                             getSize().width, getSize().height,
                             getImage(codeBase, "images/ship.gif"),    // Load
                             getImage(codeBase, "images/ac.gif"),
                             getImage(codeBase, "images/cr.gif"),
                             getImage(codeBase, "images/sub.gif"),
                             getImage(codeBase, "images/miss.gif"));   // images
    }
    public void userMove(int x, int y) {
        //if (game.ended()) {                 // setup new game
        //    newGame();
        //    return;                         // event handled
        //}
        // determine mouse position on board
        int c = board.col(x, game.placed, game.ended());
        int r = board.row(y);
        if (c == -1 | r == -1)
            invalidMove();
        else if(game.placed < 2*game.NUM_SHIPS) {
            if(game.placeShip(r, c, game.PLAYER))
                repaint();
            if(game.placed == 2*game.NUM_SHIPS & !first) {
                while(!first) {
                    if(game.computerFires()){
                        first = !first;
                        repaint();
                    }
                }
            }
        }
        else {
            if(game.fire(r, c, first)) {
                if (!game.isWin(first)) {
                    first = !first;
                    repaint();
                }
                else {
                    game.winner();
                    repaint();
                    return;
                }
            } else return;
            while(!first) {
                if(game.computerFires()) {
                    if (!game.isWin(first)) {
                        first = !first;
                        repaint();
                    }
                    else {
                        game.winner();
                        repaint();
                        return;
                    }
                }
            }
        }
    }
    protected void newGame() {
        first = (Math.random()*2 <1)?true:false;
        game.reset();
        if(game.placed < game.NUM_SHIPS) 
            game.computerPlaceShips();
        gameBegin(); 
    }
    protected URL codeBase = null;
    protected BattleBoard board;
    protected BattleGame game = null;
    protected boolean first; // user moves first
    
    class B1 implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            getAppletContext( ).showStatus("new");
            game.reset();
            repaint();
            newGame();
            
        }
    }
    class B2 implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            getAppletContext( ).showStatus("quit");
        }
    }


}


