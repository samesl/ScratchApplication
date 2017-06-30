import java.awt.Color;
import java.awt.Graphics;


/**
 * This class inherits from MyShape and is responsible for drawing a line.
 */
public class MyOpenPolygon extends MyBoundedShape
{  
    /**
     * No parameter constructor which calls the no parameter constructor in MyShape
     */
    public MyOpenPolygon()
    {
        super();
    }
    
    /** 
     * Overloaded constructor that takes coordinates and color. It passes them to the constructor in MyShape
     */
    public MyOpenPolygon( int x1, int y1, int x2, int y2, Color color, Boolean fill )
    {
        super(x1, y1, x2, y2, color, fill);
    } 
     
    /**
     * Overrides the draw method in Myshape. It sets the gets the color from Myshape
     * and the coordinates it needs to draw from MyShape as well.
     */
    @Override
    public void draw( Graphics g )
    {
        g.setColor( getColor() ); //sets the color
        int[] xs = {getX1(), getX2()-25, getX1()+35, getX2()+70, getX1()+60, getX1()-50};
        int[] ys = {getY2(), getY1()-35, getY2()+35, getY1()+60, getY2()+90, getY1()-10};
        g.drawPolyline(xs, ys, 6);
    } 
} // end class open polygon
