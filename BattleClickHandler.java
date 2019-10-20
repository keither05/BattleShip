///////    BattleClickHandler.java    ///////

import java.awt.event.*;

public class BattleClickHandler extends MouseAdapter
{   
    BattleClickHandler(BattleShip ap)  {  app = ap;  }

    public void mouseClicked(MouseEvent evt) {
        app.userMove(evt.getX(), evt.getY());
    }

    private BattleShip app = null;
}
