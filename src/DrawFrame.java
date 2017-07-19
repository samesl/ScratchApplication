import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Color;

/**
 * Provides the GUI and encapsulates the DrawPanel
 * It creates 3 buttons undo, redo and clear.
 * It creates 2 combobox colors and shapes.
 * It creates 1 checkbox filled to select whether shape is filled or not.
 * Has two private inner classes 
 * One for handling button events
 * Another for handling both combo box events and checkbox events
 */
public class DrawFrame extends JFrame
{

    private DrawPanel panel; //draw panel for the shapes
    
    private JButton undo; // button to undo last drawn shape
    private JButton redo; // button to redo an undo
    private JButton clear; // button to clear panel
    //Anjana
    private JButton save; //button to save
    private JButton load; //button to load
    
    private JComboBox colors; //combobox with color options
    
    //array of strings containing color options for JComboBox colors
    private String colorOptions[]=
    {"Black","Blue","Cyan","Dark Gray","Gray","Green","Light Gray",
        "Magenta","Orange","Pink","Red","White","Yellow"};
    
    //aray of Color objects contating Color constants
    private Color colorArray[]=
    {Color.BLACK , Color.BLUE , Color.CYAN , Color.darkGray , Color.GRAY , 
        Color.GREEN, Color.lightGray , Color.MAGENTA , Color.ORANGE , 
    Color.PINK , Color.RED , Color.WHITE , Color.YELLOW};
    
    private JComboBox shapes; //combobox with shape options
    
    //array of strings containing shape options for JComboBox shapes
    private String shapeOptions[]=
    {"Line","Rectangle","Oval","Free line", "Pentagon", "Open Polygon", "Hexagon", "Circle", "Square"};
    
    private JCheckBox filled; //checkbox to select whether shape is filled or not
        
    private JButton drawing;        // Ted++
    private JButton move;                // Ted++
    private JButton cut;                // Ted++
    private JButton paste;                // Ted++
    private JButton copy;                // Ted++

    
    private JPanel widgetJPanel; //holds the widgets: buttons, comboboxes and checkbox
    private JPanel widgetPadder; //encapsulates widgetJPanel and adds padding around the edges 
    
    /**
     * This constructor sets the name of the JFrame.
     * It also creates a DrawPanel object that extends JPanel for drawing the shapes and contains
     * Initializes widgets for buttons, comboboxes and checkbox
     * It also adds event handlers for the widgets
     */
    public DrawFrame()
    {
        super("Scratch Application - HCI "); //sets the name of DrawFrame
        Font font = new Font("Wingdings", Font.BOLD,24);
        Font font2 = new Font("Webdings", Font.BOLD,24);
        
        
        panel = new DrawPanel(); //create draw panel and pass in JLabel
        
        //create buttons
        undo = new JButton( "" );
        undo.setFont(font); undo.setForeground (Color.red);
        redo = new JButton( "" );
        redo.setFont(font); redo.setForeground (Color.green);
        clear = new JButton( "Clear" );
        drawing = new JButton( "Drawing"); // Ted++
        move = new JButton( "Move");         // Ted++
        cut = new JButton( "");         // Ted++
        cut.setFont(font);
        paste = new JButton( "");         // Ted++
        //paste = new JButton( "Paste");         // Ted++
        paste.setFont(font2);
        copy = new JButton( "" ); // Ted++
        copy.setFont(font);
        //create comboboxes
        colors = new JComboBox( colorOptions );
        shapes = new JComboBox( shapeOptions );
        save = new JButton("Save"); // Anjana
        load = new JButton("Load"); //Anjana
        
        //create checkbox
        filled = new JCheckBox( "Filled Background" );
        
        //JPanel object, widgetJPanel, with grid layout for widgets
        widgetJPanel = new JPanel();
        widgetJPanel.setLayout( new GridLayout( 14, 1) ); //sets padding between widgets in gridlayout
        
        //JPanel object, widgetPadder, with flowlayout to encapsulate and pad the widgetJPanel
        widgetPadder = new JPanel();
        widgetPadder.setLayout(new FlowLayout(FlowLayout.LEADING, 20, 5)); //sets padding around the edges
            
        // add widgets to widgetJPanel
        widgetJPanel.add( undo );
        widgetJPanel.add( redo );
        widgetJPanel.add( clear );
        widgetJPanel.add( drawing ); // Ted++
        widgetJPanel.add( move ); // Ted++
        widgetJPanel.add( cut ); // Ted++
        widgetJPanel.add( paste ); // Ted++
        widgetJPanel.add( copy ); // Ted++
        widgetJPanel.add( colors );
        widgetJPanel.add( shapes );                 
        widgetJPanel.add( filled );
        widgetJPanel.add( save ); // Anjana
        widgetJPanel.add( load ); // Anjana
        // add widgetJPanel to widgetPadder
        widgetPadder.add( widgetJPanel );
        
        //add widgetPadder and panel to JFrame
        add( widgetPadder, BorderLayout.WEST);
        add( panel, BorderLayout.CENTER);
        
        // create new ButtonHandler for button event handling
        ButtonHandler buttonHandler = new ButtonHandler();
        undo.addActionListener( buttonHandler );
        redo.addActionListener( buttonHandler );
        clear.addActionListener( buttonHandler );
        drawing.addActionListener( buttonHandler ); //Ted++			
        move.addActionListener( buttonHandler ); //Ted++			
        cut.addActionListener( buttonHandler ); //Ted++			
        paste.addActionListener( buttonHandler ); //Ted++
        copy.addActionListener( buttonHandler ); //Ted++
        save.addActionListener( buttonHandler ); //Anjana++			
        load.addActionListener( buttonHandler ); //Anjana++
        
        //create handlers for combobox and checkbox
        ItemListenerHandler handler = new ItemListenerHandler();
        colors.addItemListener( handler );
        shapes.addItemListener( handler );
        filled.addItemListener( handler );
        
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setSize( 900, 800 );
        setVisible( true );
        
    } // end DrawFrame constructor
    
    /**
     * private inner class for button event handling
     */
    private class ButtonHandler implements ActionListener
    {
        // handles button events
        public void actionPerformed( ActionEvent event )
        {
            if (event.getActionCommand().equals("")){
                panel.clearLastShape();
            }
            else if (event.getActionCommand().equals("")){
                panel.redoLastShape();
            }
            else if (event.getActionCommand().equals("Clear")){
                panel.clearDrawing();
            }
            else if(event.getActionCommand().equals("Drawing")){
            	panel.drawingObject();
            }
            else if(event.getActionCommand().equals("Move")){
            	panel.MoveObject();
            }
            else if(event.getActionCommand().equals("")){
            	panel.CutObject();
            }
            else if(event.getActionCommand().equals("")){
            	panel.PasteObject();
            }
            else if(event.getActionCommand().equals("")){
            	panel.CopyObject();
            }
            //Anjana
            else if(event.getActionCommand().equals("Save")){
            	panel.saveFrame(panel.getWidth(),panel.getHeight());
            }
            else if(event.getActionCommand().equals("Load")){
            	panel.loadFrame();
            }
            	       
            	                        
             
        } // end method actionPerformed
    } // end private inner class ButtonHandler
    
    /**
     * private inner class for checkbox and combobox event handling
     */
    private class ItemListenerHandler implements ItemListener
    {
        public void itemStateChanged( ItemEvent event )
        {
            // process filled checkbox events
            if ( event.getSource() == filled )
            {
                boolean checkFill=filled.isSelected() ? true : false; //
                panel.setCurrentShapeFilled(checkFill);
            }
            
            // determine whether combo box selected
            if ( event.getStateChange() == ItemEvent.SELECTED )
            {
                //if event source is combo box colors pass in colorArray at index selected.
                if ( event.getSource() == colors)
                {
                    panel.setCurrentShapeColor
                        (colorArray[colors.getSelectedIndex()]);
                }
                
                //else if event source is combo box shapes pass in index selected
                else if ( event.getSource() == shapes)
                {
                    panel.setCurrentShapeType(shapes.getSelectedIndex());
                }
            }
            
        } // end method itemStateChanged
    }
    
    
} // end class DrawFrame