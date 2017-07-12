import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.geom.GeneralPath;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * This class handles mouse events and uses them to draw shapes.
 * It contains a dynamic stack myShapes which is the shapes drawn on the panel.
 * It contains a dynamic stack clearedShape which is the shapes cleared from the panel.
 * It has many variables for the current shape [type, variable to store shape object, color, fill].
 * It contains a JLabel called statusLabel for the mouse coordinates
 * It has mutator methods for currentShapeType, currentShapeColor and currentShapeFilled.
 * It has methods for undoing, redoing and clearing shapes.
 * It has a private inner class MouseHandler which extends MouseAdapter and 
 * handles mouse and mouse motion events used for drawing the current shape.
 */

public class DrawPanel extends JPanel
{
    private LinkedList<MyShape> myShapes; //dynamic stack of shapes
    private LinkedList<MyShape> clearedShapes; //dynamic stack of cleared shapes from undo
    
    //current Shape variables
    private int currentShapeType; //0 for line, 1 for rect, 2 for oval
    private MyShape currentShapeObject; //stores the current shape object
    private Color currentShapeColor; //current shape color
    private boolean currentShapeFilled; //determine whether shape is filled or not
    
    private int currentMode; // 0 for drawing, 1 for selection	
    private int selectedIndex;			
    private int movingMode;        // 1 : activated mode			
    private int cutMode;        // 1 : activated mode			
    private int pasteMode;        // 1 : activated mode
    
    JLabel statusLabel; //status label for mouse coordinates
    
    /**
     * This constructor initializes the dynamic stack for myShapes and clearedShapes.
     * It sets the current shape variables to default values.
     * It initalizes statusLabel from JLabel passed in.
     * Sets up the panel and adds event handling for mouse events.
     * 
     */
    
    GeneralPath p = new GeneralPath();
    LinkedList<GeneralPath> old_paths = new LinkedList<GeneralPath>();
    
    int lastx, lasty;
    int selected_X1, selected_Y1, selected_X2, selected_Y2;


    public DrawPanel(JLabel statusLabel){
        
        myShapes = new LinkedList<MyShape>(); //initialize myShapes dynamic stack
        clearedShapes = new LinkedList<MyShape>(); //initialize clearedShapes dynamic stack
        
        //Initialize current Shape variables
        currentShapeType=0;
        currentShapeObject=null;
        currentShapeColor=Color.BLACK;
        currentShapeFilled=false;
        
        this.statusLabel = statusLabel; //Initialize statusLabel
        
        setLayout(new BorderLayout()); //sets layout to border layout; default is flow layout
        setBackground( Color.WHITE ); //sets background color of panel to white
        add( statusLabel, BorderLayout.SOUTH );  //adds a statuslabel to the south border
        
        // event handling for mouse and mouse motion events
        MouseHandler handler = new MouseHandler();                                    
        addMouseListener( handler );
        addMouseMotionListener( handler ); 
    }
    
    
    /**
     * Calls the draw method for the existing shapes.
     */
    @Override
    public void paintComponent( Graphics g )
    {
        super.paintComponent( g );
        

        // draw the shapes
        ArrayList<MyShape> shapeArray=myShapes.getArray();
        for ( int counter=shapeArray.size()-1; counter>=0; counter-- )
           shapeArray.get(counter).draw(g);
        
        //draws the current Shape Object if it is not null
        if (currentShapeObject!=null)
            currentShapeObject.draw(g);
    }
    
    //Mutator methods for currentShapeType, currentShapeColor and currentShapeFilled
    
    /**
     * Sets the currentShapeType to type (0 for line, 1 for rect, 2 for oval) passed in.
     */
    public void setCurrentShapeType(int type)
    {
        currentShapeType=type;
    }
    
    /**
     * Sets the currentShapeColor to the Color object passed in.
     * The Color object contains the color for the current shape.
     */
    public void setCurrentShapeColor(Color color)
    {
        currentShapeColor=color;
    }
    
    /**
     * Sets the boolean currentShapeFilled to boolean filled passed in. 
     * If filled=true, current shape is filled. 
     * If filled=false, current shape is not filled.
     */
    public void setCurrentShapeFilled(boolean filled)
    {
        currentShapeFilled=filled;
    }
    
    
    /**
     * Clear the last shape drawn and calls repaint() to redraw the panel if clearedShapes is not empty
     */
    public void clearLastShape()
    {
        if (! myShapes.isEmpty())
        {
            clearedShapes.addFront(myShapes.removeFront());
            repaint();
        }
    }
    
    /**
     * Redo the last shape cleared if clearedShapes is not empty
     * It calls repaint() to redraw the panel.
     */
    public void redoLastShape()
    {
        if (! clearedShapes.isEmpty())
        {
            myShapes.addFront(clearedShapes.removeFront());
            repaint();
        }
    }
    
    /**
     * Remove all shapes in current drawing. Also makes clearedShapes empty since you cannot redo after clear.
     * It called repaint() to redraw the panel.
     */
    public void clearDrawing()
    {
        myShapes.makeEmpty();
        clearedShapes.makeEmpty();
        repaint();
    }
    
    /**			
    * Switch a mode for a drawing.			
    */			
    // Ted++			
    public void drawingObject()			
    {			
    	this.currentMode = 0; // drawing mode			
    	this.movingMode = 0; // moving mode off	
    	this.cutMode = 0;			
    	this.pasteMode = 0;
    }			
     			
    // Ted++        			
    public void MoveObject()			
    {			
    	this.currentMode = 1; // select mode			
    	this.movingMode = 1; 
    	this.cutMode = 0;	
    	this.pasteMode = 0;	
    }			
    //Ted++        			
    public void CutObject()			
    {			
    	this.currentMode = 1; // select mode			
    	this.cutMode = 1;			
    	this.movingMode = 0;	
    	this.pasteMode = 0;	
    }			
    //Ted++			
    public void PasteObject()			
    {			
    	this.currentMode = 1; // select mode			
    	this.pasteMode = 1;
    	this.movingMode = 0;	
    }			
    			
    // Ted++			
    public int GetMovingMode()			
    {			
    	return this.movingMode;			
    }			
    // Ted++    			
    public int GetCutMode()			
    {			
    	return this.cutMode;			
    	}			
    // Ted++			
    public void SetCutMode(int mode)			
    {			
    	this.cutMode = mode;			
    }			
    // Ted++    			
    public int GetPasteMode()			
    {			
    	return this.pasteMode;			
    }    			
    // Ted++			
    public void SetPasteMode(int mode)			
    {			
    	this.pasteMode = mode;			
    }    			
    			
    // Ted++			
    public void SetMovingMode(int mode)			
    {			
    	this.pasteMode = mode;			
    }    			
    			
    			
    // Ted++			
    public void SetSelectedObjectIndex( int index )			
    {			
    	this.selectedIndex =  index;            			
    }			
    			
    // Ted++			
    public int GetSelectedObjectIndex()			
    {			
    	return this.selectedIndex;            			
    }			
    		
    public int GetCurrentMode()			
    {			
    	return this.currentMode;			
    }
    
    /**
     * Private inner class that implements MouseAdapter and does event handling for mouse events.
     */
    private class MouseHandler extends MouseAdapter 
    {
        /**
         * When mouse is pressed draw a shape object based on type, color and filled.
         * X1,Y1 & X2,Y2 coordinate for the drawn shape are both set to the same X & Y mouse position.
         */
    	
    	private int activatedCut;	
    	private int activateMoving;			
    	private int horizon_size, vertical_size;			
    	private int moving_distance_x=0, moving_distance_y=0;
    	
        public void mousePressed( MouseEvent event )
        {
        	int mode = GetCurrentMode();	
        		                
        	if( mode == 0 ) // drawing mode	
        	{
        	
	            switch (currentShapeType) //0 for line, 1 for rect, 2 for oval
	            {
	                case 0:
	                    currentShapeObject= new MyLine( event.getX(), event.getY(), 
	                                                   event.getX(), event.getY(), currentShapeColor);
	                    break;
	                case 1:
	                    currentShapeObject= new MyRectangle( event.getX(), event.getY(), 
	                                                        event.getX(), event.getY(), currentShapeColor, currentShapeFilled);
	                    break;
	                case 2:
	                    currentShapeObject= new MyOval( event.getX(), event.getY(), 
	                                                   event.getX(), event.getY(), currentShapeColor, currentShapeFilled);
	                    break;          	
	                case 3:
	                	currentShapeObject= new MyScribble( event.getX(), event.getY(), 
	                            event.getX(), event.getY(), currentShapeColor);
	                	// set the first coordinate for free line
	                	lastx = event.getX();
	                	lasty = event.getY();
	                	break;
	                case 4:
	                    currentShapeObject= new MyPentagon( event.getX(), event.getY(), 
	                    		event.getX(), event.getY(), currentShapeColor, currentShapeFilled);
	                    break;
	                case 5:
	                    currentShapeObject= new MyOpenPolygon( event.getX(), event.getY(), 
	                                                   event.getX(), event.getY(), currentShapeColor, currentShapeFilled);
	                    break;
	                case 6:
	                    currentShapeObject= new MyHexagon( event.getX(), event.getY(), 
	                    		event.getX(), event.getY(), currentShapeColor, currentShapeFilled);
	                    break;
	                case 7:
	                    currentShapeObject= new MyCircle( event.getX(), event.getY(), 
	                                                   event.getX(), event.getY(), currentShapeColor, currentShapeFilled);
	                    break;  
	                case 8:
	                    currentShapeObject= new MySquare( event.getX(), event.getY(), 
	                                                        event.getX(), event.getY(), currentShapeColor, currentShapeFilled);
	                    break;
	            }// end switch case
        	}else if( mode == 1 ) // select mode
        	{
            	lastx = event.getX(); // a variable used for just free-handed drawing
            	lasty = event.getY(); // a variable used for just free-handed drawing
        		selected_X1 = event.getX();
        		selected_Y1 = event.getY();
        		
        		if( GetMovingMode() ==1 )
        		{
        			ArrayList<MyShape> shapeArray=myShapes.getArray();
        			
        			for ( int counter=shapeArray.size()-1; counter>=0; counter-- )
        			{
        				if(  event.getX() > shapeArray.get(counter).getX1()  &&
        					 event.getX() < shapeArray.get(counter).getX2() &&
        					 event.getY() > shapeArray.get(counter).getY1() &&
        					 event.getY() < shapeArray.get(counter).getY2())
        				{
        					SetSelectedObjectIndex(counter);
        					this.activateMoving =1;
        					break;
        				}
        			}
        		}
        		
        	}
          
        } // end method mousePressed
        
        /**
         * When mouse is released set currentShapeObject's x2 & y2 to mouse pos.
         * Then addFront currentShapeObject onto the myShapes dynamic Stack 
         * and set currentShapeObject to null [clearing current shape object since it has been drawn].
         * Lastly, it clears all shape objects in clearedShapes [because you cannot redo after a new drawing]
         * and calls repaint() to redraw panel.
         */
        public void mouseReleased( MouseEvent event )
        {
        	int mode = GetCurrentMode();
        	
        	if( mode == 0) // drawing mode	
        	{
	           	//Ted++
	        	if( currentShapeType == 3)	// free line mode
	        		return;
	        	
	            //sets currentShapeObject x2 & Y2
	            currentShapeObject.setX2(event.getX());
	            currentShapeObject.setY2(event.getY());
	            
	            myShapes.addFront(currentShapeObject); //addFront currentShapeObject onto myShapes
	            
	            currentShapeObject=null; //sets currentShapeObject to null
	            clearedShapes.makeEmpty(); //clears clearedShapes
	            repaint();
        	}else if( mode == 1 ) // select mode
        	{
        		ArrayList<MyShape> shapeArray=myShapes.getArray();
        		int counter=0;
        		
        		selected_X2 = event.getX();
        		selected_Y2 = event.getX();
        		//System.out.println("X1:"+selected_X1 + ",Y1:"+selected_Y1+ "X2:"+selected_Y2+ ",Y2:"+selected_Y2);
        		
        		if( GetCutMode() == 1 ) // Cut mode    // Ted++
        		{
	                for ( counter=shapeArray.size()-1; counter>=0; counter-- )
	                {
	                	if( selected_X1 < shapeArray.get(counter).getX1() &&
	                			selected_Y1 < shapeArray.get(counter).getY1() &&
	                			selected_X2 > shapeArray.get(counter).getX2() &&
	                			selected_Y2 > shapeArray.get(counter).getY2())
	                	{
	                		//System.out.println("X1:"+shapeArray.get(counter).getX1() + ",Y1:"+shapeArray.get(counter).getY1()+ "X2:"+shapeArray.get(counter).getX2()+ ",Y2:"+shapeArray.get(counter).getY2());
	                		//System.out.println("index:"+counter);
	                		
	                		// get the index of the selected object
	                		SetSelectedObjectIndex(counter);
	                		
	                		// get the dimension of the selected object
	                		this.horizon_size = shapeArray.get(counter).getX2() - shapeArray.get(counter).getX1();
	                    	this.vertical_size = shapeArray.get(counter).getY2() - shapeArray.get(counter).getY1();
	                		
	                		// initialize the value of a coordinate as '0'
	                		shapeArray.get(counter).setX1(0);
	                    	shapeArray.get(counter).setY1(0);
	                    	shapeArray.get(counter).setX2(0);
	                    	shapeArray.get(counter).setY2(0);
	                    	
	                    	
	                    	// set activatedCut as '1' because to imply that currently cutting mode is working.
	                    	this.activatedCut=1;
	                    	SetCutMode(0); // disable the cut mode
	                    	repaint();
	                    	
	                		break;
	                	}
	                }
        		}
 
        		// Paste Mode
                if( this.activatedCut==1 && GetPasteMode() == 1 ) // Ted++
                {
                	shapeArray.get( GetSelectedObjectIndex() ).setX1(selected_X1);
                	shapeArray.get( GetSelectedObjectIndex() ).setY1(selected_Y1);
                	shapeArray.get( GetSelectedObjectIndex() ).setX2(selected_X1+this.horizon_size);
                	shapeArray.get( GetSelectedObjectIndex() ).setY2(selected_Y1+this.vertical_size);
                	
    	            this.activatedCut=0;
    	            SetPasteMode(0); // disable the paste mode
                	repaint();
                }   
                
                if( GetMovingMode() == 1 )
                {
                	this.moving_distance_x=0;
                	this.moving_distance_y=0;
                	SetMovingMode(0);
                }
        	} 
	           
            
        } // end method mouseReleased
        
        /**
         * This method gets the mouse pos when it is moving and sets it to statusLabel.
         */
        public void mouseMoved( MouseEvent event )
        {
            statusLabel.setText(String.format("Mouse Coordinates X: %d Y: %d",event.getX(),event.getY()));
        } // end method mouseMoved
        
        /**
         * This method gets the mouse position when it is dragging and sets x2 & y2 of current shape to the mouse pos
         * It also gets the mouse position when it is dragging and sets it to statusLabel
         * Then it calls repaint() to redraw the panel
         */
        public void mouseDragged( MouseEvent event )
        {
        	Graphics g= getGraphics();
        	int mode = GetCurrentMode();
        	

        	if( mode == 0 ){
		        currentShapeObject.setX2(event.getX());
		        currentShapeObject.setY2(event.getY());
		        
		        // Free-handed drawing mode
		        if(currentShapeType == 3 )
		        {
		        	// start drawing from the latest point
			        currentShapeObject.setX1( currentShapeObject.getX2() );
			        currentShapeObject.setY1( currentShapeObject.getY2() );
			        
			        g.setColor( currentShapeObject.getColor() ); //sets the color
			        g.drawLine(lastx,lasty,currentShapeObject.getX2(),currentShapeObject.getY2());
			        myShapes.addFront(currentShapeObject); //addFront currentShapeObject onto myShapes
			        
			        lastx = currentShapeObject.getX2(); // update while free-handled drawing
			        lasty = currentShapeObject.getY2(); // update while free-handled drawing
			        	        
		        }		        
		        //System.out.println(currentShapeObject.getX2() +","+currentShapeObject.getY2()+","+currentShapeObject.getX2()+","+currentShapeObject.getY2());
	            
	            //repaint();
        	}else if( mode == 1 ) // select mode
        	{	
        		if( GetMovingMode() == 1 )
        		{
        			ArrayList<MyShape> shapeArray=myShapes.getArray();

        			
        			if( this.activateMoving == 1 )
        			{
        				System.out.println("moving");
        				moving_distance_x = lastx-event.getX();
        				moving_distance_y = lasty-event.getY();
        				//System.out.println("x:"+event.getX()+",y:"+event.getY());
        				//System.out.println("x:"+moving_distance_x+",y:"+moving_distance_y);
        				int new_x1 = shapeArray.get( GetSelectedObjectIndex() ).getX1()-moving_distance_x;
        				int new_y1 = shapeArray.get( GetSelectedObjectIndex() ).getY1()-moving_distance_y;
        				int new_x2 = shapeArray.get( GetSelectedObjectIndex() ).getX2()-moving_distance_x;
        				int new_y2 = shapeArray.get( GetSelectedObjectIndex() ).getY2()-moving_distance_y;
        			
	        			shapeArray.get( GetSelectedObjectIndex() ).setX1(new_x1);
	                	shapeArray.get( GetSelectedObjectIndex() ).setY1(new_y1);
	                	shapeArray.get( GetSelectedObjectIndex() ).setX2(new_x2);
	                	shapeArray.get( GetSelectedObjectIndex() ).setY2(new_y2);
	                	System.out.println("X1:"+new_x1 + ",Y1:"+new_y1 + ",X2:"+new_x2 + ",Y2:"+new_y2);
	                	repaint();
	                	
	                	lastx = event.getX()-moving_distance_x; // update while moving
				        lasty = event.getY()-moving_distance_y; // update while moving
        			}

        		}
        		
        	}
        	p.moveTo(event.getX(), event.getY());
          //sets statusLabel to current mouse position
          statusLabel.setText(String.format("Dragging Mouse Coordinates X: %d Y: %d [%d, %d]",event.getX(),event.getY(), lastx, lasty));
            
        } // end method mouseDragged
        
    }// end MouseHandler
    
} // end class DrawPanel