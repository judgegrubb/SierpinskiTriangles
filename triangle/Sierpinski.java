package triangle;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Sierpinski extends JFrame implements ActionListener {

	public static void main(String[] args) {
		// this makes JButton all of the colors JComboBoxs and such
		// look the same across platforms and also seems to privde
		// some stability that tbh I don't really understand.
		try {
	        UIManager.setLookAndFeel(
	            UIManager.getCrossPlatformLookAndFeelClassName());
	    } 
	    catch (UnsupportedLookAndFeelException e) {
	    }
	    catch (ClassNotFoundException e) {
	    }
	    catch (InstantiationException e) {
	    }
	    catch (IllegalAccessException e) {
	    }
		
		// start the program
		Sierpinski s = new Sierpinski();
		s.setTitle("Sierpinski Triangles");
		s.setLocation(300, 100);
		s.setVisible(true);

	}
	
	// menu item File->Close that closes the entire thing
	private JMenuItem closeItem;
	// the dropdown box that holds the foreground color options
	private JComboBox foregroundBox;
	// the dropdown box that holds the background color options
	private JComboBox backgroundBox;
	// the dropdown box that holds the level options
	private JComboBox levelBox;
	// holds the panel that the controller lives in
	private SierpinskiPanel sPanel;
	// holds the background, foreground and level so all can access it.
	private Color fgColor;
	private Color bgColor;
	private int level;
	//
	private JPanel mainPanel;

	/**
	 * constructor to create a window with the level,
	 * background and foreground color gui controls and draws
	 * an initial triangle at level 1
	 */
	public Sierpinski() {
		// Exit on close
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Make a PaintPanel the content pane
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        setContentPane(mainPanel);
        
        // Add a File menu. Register this object as the
        // listener for menu selection events.
        JMenuBar menubar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        closeItem = new JMenuItem("Close");
        closeItem.addActionListener(this);
        fileMenu.add(closeItem);
        menubar.add(fileMenu);
        setJMenuBar(menubar);
        
        // Adds the color ComboBox for the user to choose
        // the background and foreground colors
        String[] colors = { "Black", "White", "Red", "Blue", "Gray", "Green", "Magenta", "Orange", "Pink", "Yellow" };
        foregroundBox = new JComboBox(colors);
        // initializes it at black
        foregroundBox.setSelectedIndex(0);
        foregroundBox.addActionListener(this);
        // initializes it at white
        colors = new String[]{"White", "Black", "Red", "Blue", "Gray", "Green", "Magenta", "Orange", "Pink", "Yellow" };
        backgroundBox = new JComboBox(colors);
        backgroundBox.setSelectedIndex(0);
        backgroundBox.addActionListener(this);
        
        // adds the level combo box for the user to choose
        // the level they want drawn
        String[] numbers = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };
        levelBox = new JComboBox(numbers);
        // initializes it at level 1
        levelBox.setSelectedIndex(1);
        levelBox.addActionListener(this);
        
        // initializes all three
        bgColor = Color.WHITE;
        fgColor = Color.BLACK;
        level = 1;
        
        // creates a controller
        sPanel = new SierpinskiPanel(this);
        
        // labels the gui controls and sets them in their place
        JLabel fgLabel = new JLabel("  Foreground Color:  ");
        JLabel bgLabel = new JLabel("  Background Color:  ");
        JLabel levelLabel = new JLabel("  Level:  ");
        JPanel comboBoxPanel = new JPanel();
        comboBoxPanel.setLayout(new GridLayout(1,6));
        comboBoxPanel.add(fgLabel);
        comboBoxPanel.add(foregroundBox);
        comboBoxPanel.add(bgLabel);
        comboBoxPanel.add(backgroundBox);
        comboBoxPanel.add(levelLabel);
        comboBoxPanel.add(levelBox);
        mainPanel.add(comboBoxPanel, "North");
        
        // Add the paint panel to the center
        mainPanel.add(sPanel, "Center");
		
        // sets the dimensions and packs it all up
		setPreferredSize(new Dimension(600, 600));
        pack();
	}
	
	/**
	 * returns the background color
	 */
	public Color getBackgroundColor() {
		return bgColor;
	}
	
	/**
	 * returns the foreground color
	 */
	public Color getForegroundColor() {
		return fgColor;
	}

	/**
	 * returns the level set by the user
	 */
	public int getLevel() {
		return level;
	}
	
	/**
     * Deals with menu actions.
     */
	@Override
    public void actionPerformed (ActionEvent e)
    {
        // Close the window
        if (e.getSource() == closeItem)
        {
            dispose();
        } else if (e.getSource() == foregroundBox || e.getSource() == backgroundBox) {
        	JComboBox<String> cb = (JComboBox) e.getSource();
        	String current = (String) cb.getSelectedItem();
        	Color temp = null;
        	
        	// tests as to which color the user chose
        	switch (current) {
        		case "White":
        			temp = Color.WHITE;
        			break;
        		case "Black":
        			temp = Color.BLACK;
        			break;
        		case "Red":
        			temp = Color.RED;
        			break;
        		case "Blue":
        			temp = Color.BLUE;
        			break;
        		case "Gray":
        			temp = Color.GRAY;
        			break;
        		case "Green":
        			temp = Color.GREEN;
        			break;
        		case "Magenta":
        			temp = Color.MAGENTA;
        			break;
        		case "Orange":
        			temp = Color.ORANGE;
        			break;
        		case "Pink":
        			temp = Color.PINK;
        			break;
        		case "Yellow":
        			temp = Color.YELLOW;
        			break;
        	}
        	
        	// sets the correct color to the new
        	// color depending on if the user
        	// was trying to change background or foreground
        	if (e.getSource() == foregroundBox) {
        		if (temp == null) {
        			fgColor = Color.BLACK;
        		} else {
        			fgColor = temp;
        			this.repaint();
        		}
        	} else {
        		if (temp == null) {
        			bgColor = Color.WHITE;
        		} else {
        			bgColor = temp;
        			this.repaint();
        		}
        	}
        } else if (e.getSource() == levelBox) {
        	// changes the level to the new one chosen by
        	// the user and redraws the triangle at that level
        	JComboBox cb = (JComboBox) e.getSource();
        	String current = (String) cb.getSelectedItem();
        	int currentInt = Integer.parseInt(current);
        	level = currentInt;
        	this.repaint();
        }
    }
	
}
