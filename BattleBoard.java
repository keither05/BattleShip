///////    BattleBoard.java    ///////

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;

public class BattleBoard {
    public BattleBoard(int ax, int ay, int w, int h, Image ship, Image ac, Image cr, Image sub, Image miss) {
        x0 = ax;
        y0 = ay;
        width = (w-90)/2;
        height = width;
        tw = (width-10*lw)/10;
        th = tw;
        this.ship = ship;
        this.ac = ac;
        this.cr = cr;
        this.sub = sub;
        this.miss = miss;
    }
    public void drawBoard(Graphics g) {
        for (int i = 0; i<11; i++) {
            g.drawLine(i*(tw+lw)+20, y0+80, i*(tw+lw)+20, y0+height+80);
            g.drawLine(width+70+i*(tw+lw), y0+80, width+70+i*(tw+lw), y0+height+80);
            g.drawLine(x0+20, y0+80+i*(th+lw), x0+20+width, y0+80+i*(th+lw));
            g.drawLine(x0+width+70, y0+80+i*(th+lw), x0+2*width+70, y0+80+i*(th+lw));
        }
    }
    public void drawInstructions(Graphics g, int s, int c, boolean who, boolean end) {
        if(end)
            g.drawString(who?"Player Wins!":"Computer Wins" , width + 20, 65);
        else if (s > 15 & who == true) {
            str = "Fire Away!";
            g.drawString(str, width+70, 65);
        }
        else if (s >15 & who == false) {
            System.out.println("thisisworking");
            str = "Computer Fires!";
            g.drawString(str, 20, 65);
        }
        else {
            str = "Place Ship: ";
            if (s%8<=1) {
                str += "Aircraft Carrier ";
                if(c <1 )
                    str += "front";
                else
                    str += "back (6)";
            }
            else if (s%8<=5) {
                str += "Cruiser ";
                if(c <1 )
                    str += "front";
                else
                    str += "back (3)";
            }
            else {
                str += "Submarine ";
                if(c <1 )
                    str += "front";
                else
                    str += "back (2)";
            }
            g.drawString(str, 20, 65);
        }
    }
    public void drawShip(Graphics g, int r, int c, Applet ap) {
        g.drawImage(ship, c*(tw+lw)+21, r*(th+lw)+81, tw, th, ap);
    }
    public void drawShot(Graphics g, int r, int c, int k, Applet ap, boolean pl) {
        Image temp;
        if (k<0)
            temp=miss;
        else if (k<=12)
            temp=ac;
        else if (k<=16)
            temp=cr;
        else
            temp=sub;
        g.drawImage(temp, ((pl==true)?c*(tw+lw)+21:c*(tw+lw)+321), r*(th+lw)+81, tw, th, ap);
    }
    public void drawStats(Graphics g, int usshots, int themshots, int [] ushits, int [] themhits){
        g.drawString(""+themshots, 130, 355);
        g.drawString(""+usshots, 400, 355);
        g.drawString("  "+(12-ushits[0]-ushits[1])+"        "+((ushits[0]==0)?((ushits[1]==0)?2:1):((ushits[1]==0)?1:0)), 170, 373);
        g.drawString("  "+(12-themhits[0]-themhits[1])+"        "+((themhits[0]==0)?((themhits[1]==0)?2:1):((themhits[1]==0)?1:0)), 470, 373);
        int uscrhits = (12-ushits[2]-ushits[3]-ushits[4]-ushits[5]);
        int themcrhits = (12-themhits[2]-themhits[3]-themhits[4]-themhits[5]);
        int uscrsunk = 0;
        int themcrsunk = 0;
        for(int i = 2; i<6; i++) {
            if(ushits[i]==0)
                uscrsunk++;
            if(themhits[i]==0)
                themcrsunk++;
        }
        g.drawString("  "+uscrhits+"        "+uscrsunk, 170, 393);
        g.drawString("  "+themcrhits+"        "+themcrsunk, 470, 393);
        g.drawString("  "+(4-ushits[6]-ushits[7])+"        "+((ushits[6]==0)?((ushits[7]==0)?2:1):((ushits[7]==0)?1:0)), 170, 413);
        g.drawString("  "+(4-themhits[6]-themhits[7])+"        "+((themhits[6]==0)?((themhits[7]==0)?2:1):((themhits[7]==0)?1:0)), 470, 413);
        
    }
    public int col(int x, int pl, boolean over) {
        if (!over) {
            if (x >= 20 & x <= width+20 & pl < 16)
                return ((x-20)/25);
            else if (x >= width+70 & x<=2*width+70 & pl >=16)
                return ((x-(width+70))/25);
        }
        return -1;
    }
    public int row(int y) {  
        if (y >= 80 & y <= height+80)
            return ((y-80)/25);
        else return -1;
    }
 
    protected Image ship, miss, ac, cr, sub;        //graphical images of pieces 
    protected int x0, y0;                   //upper left corner of board
    protected int width, height;            //of board
    protected int tw, th;                   //width and height of token
    protected int lw = 1;                   // thickness of line
    protected String str;
}
