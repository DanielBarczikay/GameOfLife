package sejtautomata;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.*;


public class drawBoard extends JPanel {
	ArrayList<Cell> newList = new ArrayList<>();

   @Override
   protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      // draw the rectangle here
      for (int i = 0; i < newList.size(); i++) {
          Point tPoint = newList.get(i).getPoint();
          g.setColor(Color.BLUE);
          if (newList.get(i).isAlive()==true) g.setColor(Color.RED);
          g.drawRect(10+tPoint.x*13, 10+tPoint.y*13, 10, 10);
          g.fillRect(10+tPoint.x*13, 10+tPoint.y*13, 10, 10);
      }
      
   }

   public drawBoard(ArrayList lista) {
		  newList = lista;

   }
}