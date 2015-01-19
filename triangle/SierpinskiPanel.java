package triangle;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class SierpinskiPanel extends JPanel {

	// used to store the initial triangles x coordinates
	private int[] xPoints;
	// used to store the initial triangles y coordinates
	private int[] yPoints;
	// stores the graphics object passed into paintComponent
	// so we can draw inside of other methods
	private Graphics g;
	// stores the gui object passed into the constructor
	private Sierpinski s;
	
	/**
	 * Builds the controller based on the Sierpinski gui
	 * @param sierpinski
	 */
	public SierpinskiPanel(Sierpinski sierpinski) {
		s = sierpinski;
		setBackground(Color.WHITE);
		repaint();
	}
	
	/**
	 * recursive method that draws the triangle and if the level is > 0
	 * calls itself again on the 3 smaller triangles
	 * @param x
	 * @param y
	 * @param l
	 */
	private void drawTriangle(int[] x, int[] y, int l) {
		if (l == 0) {
			paintTriangle(x,y, 0);
		} else {
			paintTriangle(x,y, 0);
			int[] midX = findMidpoints(x);
			int[] midY = findMidpoints(y);
			paintTriangle(midX, midY, 1);
			drawTriangle(new int[]{x[0],midX[0],midX[2]}, new int[]{y[0],midY[0],midY[2]}, l - 1);
			drawTriangle(new int[]{midX[0], x[1], midX[1]}, new int[]{midY[0], y[1], midY[1]}, l - 1);
			drawTriangle(new int[]{midX[2], midX[1], x[2]}, new int[]{midY[2], midY[1], y[2]}, l - 1);
		}
	}
	
	/**
     * When Swing determines that a component needs to be painted, it calls this
     * method. The version that comes with JPanel fills in the background color.
     * This overrides that version by also painting the shapes.
     */
    @Override
    public void paintComponent (Graphics graphics) {
    	
    	// We invoke the original, overridden version to paint the background.
        g = graphics;
    	super.paintComponent(g);

        // Turn on anti-aliasing
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        
        // Refreshes the original points to make sure that the triangle
        // is as big as possible inside of the window
        xPoints = new int[]{0, this.getWidth()/2, this.getWidth()};
		yPoints = new int[]{this.getHeight(), 0, this.getHeight()};
		// call my recursive method to begin drawing it!
		drawTriangle(xPoints, yPoints, s.getLevel());
    }
    
    /**
     * This actually uses the graphics object to draw the triangle
     * Uses the 3rd int parameter to decide if it's drawing with the
     * foreground or background color
     * @param x
     * @param y
     * @param i
     */
	private void paintTriangle(int[] x, int[] y, int i) {
		if (i == 1 && s.getBackgroundColor() != null) {
			g.setColor(s.getBackgroundColor());
		} else if (i == 0 && s.getForegroundColor() != null){
			g.setColor(s.getForegroundColor());
		}
		g.fillPolygon(x, y, 3);
	}
	
	/**
	 * send in an int array of 3 points and get returned the three midpoints
	 * of that triangle in a new int array
	 * @param points
	 * @return
	 */
	public int[] findMidpoints(int[] points) {
		if (points.length != 3) {
			throw new IllegalArgumentException();
		}
		return new int[]{(points[0] + points[1]) / 2, (points[1] + points[2]) / 2, (points[0] + points[2]) / 2};
	}
}
