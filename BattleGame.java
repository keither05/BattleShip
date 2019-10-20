///////    BattleGame.java    ///////

import java.applet.Applet;
import java.awt.Graphics;

public class BattleGame {
    public BattleGame(BattleBoard b) {  
        reset();  
        board = b;  
    }
    public void reset() {
        moveCount = 0;
        us = new int[10][10];
        them = new int[10][10];
        end = false;
        placed = 0;
        click = 0;
        ushits = new int[NUM_SHIPS];
        themhits = new int[NUM_SHIPS];
        for(int i=0; i<NUM_SHIPS; i++) {
            if(i<=1)
                ushits[i]=themhits[i]=6;
            else if(i<=5)
                ushits[i]=themhits[i]=3;
            else
                ushits[i]=themhits[i]=2;
        }
        usshots = themshots = 0;
    }
    public boolean ended()  {  return end;  }
    public void draw(Graphics g, Applet ap, boolean who) {
        board.drawBoard(g);
        int mv = moveCount;
        board.drawInstructions(g, placed, click, who, end);
        if(placed>=2*NUM_SHIPS)
            board.drawStats(g, usshots, themshots, ushits, themhits);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j <10; j++) {
                int k = us[i][j];
                if(k >= 1 & k <=10)
                    board.drawShip(g, i, j, ap);
                else if (k<0 | k>10)
                    board.drawShot(g,i,j,k,ap,PLAYER);
                int l = them[i][j];
                if (l < 0 | l>10)
                    board.drawShot(g,i,j,l,ap,COMPUTER);
            }
        }
       
    }
    public int getSize(int p) {
        if (p%8 <= 1)
            return 6;
        else if (p%8 <= 5)
            return 3;
        else if (p%8<=7)
            return 2;
        else
            return -1;
    }
    
    public boolean moveValidHorM(int r, int c, boolean pl) {
        int [][] temp;
        int [] hits;
        if(pl==true) {
            temp = them;
            hits = themhits;
            }
        else {
            temp = us;
            hits = ushits;
        }
        int k = temp[r][c];
        if(k>0&k<10) {
            if(pl)
                usshots++;
            else
                themshots++;
            hits[k-1]--;
            temp[r][c] += 10;
            return true;
        }
        else if (k==0) {
             if(pl)
                usshots++;
            else
                themshots++;
            temp[r][c] = -1;
            return true;
        }
        else
            return false;
    }
    public void winner() {
        end = true;
    }
    public boolean isWin(boolean pl) {
        int [] hits;
        int shots;
        if(pl==true) {
            hits = themhits;
            shots = usshots;
        }
        else {
            hits = ushits;
            shots = themshots;
        }
        if (shots >= 28) {
            int i = 0;
            while(i<NUM_SHIPS) {
                if(hits[i]==0)
                    i++;
                else
                    return false;
            }
            return true;
        }
        else 
            return false;
    }
    public boolean fire(int r, int c, boolean pl) {
        if(moveValidHorM(r,c, pl)) 
            return true;
        else
            return false;
    }
    public boolean computerFires() {
        int i = (int) (Math.random()*10);
        int j = (int) (Math.random()*10);
        if(fire(i,j, COMPUTER))
            return true;
        else
            return false;
    }
    
    public boolean isValid(int r, int c, int size, boolean pl) {
        int w = 0, x = 0, y = 0, z = 0, count=size;
        int [][] temp;
        if(pl==PLAYER)
            temp = us;
        else
            temp = them;
        if(0<=r&r<=9&0<=c&c<=9) {
            while(count>0) {
                if(r+w<=9 && temp[r+w][c]==0) 
                    w++;
                if(r-x>=0 && temp[r-x][c]==0)
                    x++;
                if(c+y<=9 && temp[r][c+y]==0)
                    y++;
                if(c-z>=0 && temp[r][c-z]==0)
                    z++;
                count--;
            }
            if(w>0&x>0&y>0&z>0) {
                if (click==0){
                    if (!(w==size | x==size | y==size | z==size))
                        return false;
                    else
                        return true;
                }
                else {
                    if (c==lastc) {
                        if (r==(lastr+size-1)&x==size-1)
                            return true;
                        else if (r==(lastr-(size-1))&w==size-1)
                            return true;
                        else
                            return false;
                    }
                    else if (r==lastr) {
                        if (c==(lastc+size-1)&z==size-1)
                            return true;
                        else if (c==(lastc-(size-1))&y==size-1)
                            return true;
                        else
                            return false;
                  }
                }
                return false;
            }
            else
                return false;
        }
        else
            return false;
    }
    public void computerPlaceShips() {
        while(placed < NUM_SHIPS) {
            int i = (int) (Math.random()*10);
            int j = (int) (Math.random()*10);
            int k = getSize(placed)-1;
            if(placeShip(i, j, COMPUTER)) { 
                while(click!=0) {
                    int l = (int) (Math.random()*4);
                    i = (l<2)?((l<1)?i+k:i-k):i;
                    j = (l<2)?j:((l<3)?j+k:j-k);
                    if(!placeShip( i, j, COMPUTER)) {
                        i = (l<2)?((l<1)?i-k:i+k):i;
                        j = (l<2)?j:(l<3)?j-k:j+k;
                    }
                }
            }
        }
    }
    public boolean placeShip(int r, int c, boolean pl) {
       int size = getSize(placed);
        int i, j;
        if (isValid(r,c,size,pl)) {
            if (click == 0) {
                if(pl==PLAYER)
                    us[r][c] = placed%8+1;
                else
                    them[r][c] = placed+1;
                lastr = r;
                lastc = c;
                click++;
            }
            else if (r == lastr) {
                i = ((c<lastc)?c:lastc);
                j = i + size;
                while (i < j) { 
                    if(pl==PLAYER)
                        us[r][i] = placed%8+1;
                    else
                        them[r][i] = placed+1;
                    i++;
                }
                click = 0;
                placed++;
            }
            else if (c == lastc) {
                i = ((r<lastr)?r:lastr);
                j = i + size;
                while (i<j) {
                    if(pl==PLAYER)
                        us[i][c] = placed%8+1;
                    else
                        them[i][c] = placed+1;
                    i++;
                }
                click = 0;
                placed++;
            }
            return true;
        }
        else 
            return false;
    }
    //public int move(int m, boolean player) {
    //}
    //public int genMove(boolean player) {
    //}
    protected BattleBoard board;
    protected int [][] us;          // ships are stored in array representing board:
    protected int [][] them;        // elements are 0 if empty, -1 if shot = miss
                                    // +10 if shot = hit, # of ship placed
                                    // 1,2 = AC 3-6 = Cruiser 7,8 = Sub
    public int [] ushits;        // total hits left on each ship
    public int [] themhits;
    public int usshots, themshots;
    protected int placed;         // # OF SHIPS THAT HAVE BEEN PLACED
    protected int moveCount = 0;    // total number of shots made
    private int lastr, lastc;
    private boolean end;            // end of game flag
    protected int NUM_SHIPS = 8;
    public static final boolean PLAYER = true;
    public static final boolean COMPUTER = false;
    protected int click;
}
