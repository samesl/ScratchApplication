import java.awt.Color;
import java.awt.Graphics;
//
///**
// * This class inherits from MyBoundedShape and is responsible for drawing a rectangle
// */
//public class MyPentagon extends MyBoundedShape
//{ 
//    /**
//     * No parameter constructor which calls the no parameter constructor in MyBoundedShape
//     */
//    public MyPentagon()
//    {
//        super();
//    }
//    
//    /** 
//     * Overloaded constructor that takes coordinates, color and fill. 
//     * It passes them into MyBoundedShape's constructor
//     */
//    public MyPentagon( int x1, int y1, int x2, int y2, Color color, boolean fill )
//    {
//        super(x1, y1, x2, y2, color,fill);
//    } 
//    
//    /**
//     * Overrides the draw method in MyBoundedShape. It sets the gets the color from MyBoundedShape
//     * to set the color and the values it needs to draw from MyBoundedShape as well.
//     */
//    @Override
//    public void draw( Graphics g )
//    {
//        g.setColor( getColor() ); //sets the color
//        if (getFill()) //determines whether fill is true or false
//            g.fillRect( getUpperLeftX(), getUpperLeftY(), getWidth(), getHeight() ); //draws a filled rectangle
//        else
//            g.drawRect( getUpperLeftX(), getUpperLeftY(), getWidth(), getHeight() ); //draws a regular rectangle
//        
//    } 
//    
//}

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MyPentagon extends MyBoundedShape
{ 
  /**
   * No parameter constructor which calls the no parameter constructor in MyBoundedShape
   */
  public MyPentagon()
  {
      super();
  }
  
  /** 
   * Overloaded constructor that takes coordinates, color and fill. 
   * It passes them into MyBoundedShape's constructor
   */
  public MyPentagon( int x1, int y1, int x2, int y2, Color color, boolean fill )
  {
      super(x1, y1, x2, y2, color,fill);
  } 
  
  @Override
public void draw( Graphics g )
{
	  
	  int cx = getUpperLeftX(); int cy = getUpperLeftX(); int r = getHeight(); int i;
    g.setColor( getColor() ); //sets the color
    if (getFill()) //determines whether fill is true or false
    {
    	Polygon filledPenta = new Polygon();
        for (i = 0; i<5; i++)
        	filledPenta.addPoint(
        	  (int)(cx + r * Math.cos(i * 2 * Math.PI / 5)),
        	  (int)(cy + r * Math.sin(i * 2 * Math.PI / 5)));
            g.setColor(getColor());
            g.fillPolygon(filledPenta);
    }
    else {
    	Polygon pentagon = new Polygon();
    for (i = 0; i<5; i++)
	pentagon.addPoint(
	  (int)(cx + r * Math.cos(i * 2 * Math.PI / 5)),
	  (int)(cy + r * Math.sin(i * 2 * Math.PI / 5)));
    g.setColor( getColor());
    g.drawPolygon(pentagon);
    }
    
} 
  

  }