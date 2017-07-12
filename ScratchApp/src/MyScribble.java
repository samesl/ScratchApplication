import java.awt.Color;
import java.awt.Graphics;

public class MyScribble  extends MyShape{

	
	 /**
     * No parameter constructor which calls the no parameter constructor in MyShape
     */
    public MyScribble()
    {
        super();
    }
	
    /** 
     * Overloaded constructor that takes coordinates and color. It passes them to the constructor in MyShape
     */
    public MyScribble( int x1, int y1, int x2, int y2, Color color )
    {
        super(x1, y1, x2, y2, color);
    } 
    
    
    
	
	@Override
    public void draw( Graphics g )
    {
		//System.out.println("Call drawing");
		//System.out.println(getX1() +","+getY1()+","+getX2()+","+getY2());
		
		g.setColor( getColor() ); //sets the color
        g.drawLine( getX1(), getY1(), getX2(), getY2() ); //draws the line

        
        
    } 

}
