import acm.graphics.*;
import acm.program.*;
import acm.util.RandomGenerator;
import java.awt.*;

public class BuffonsNeedle extends GraphicsProgram {
	private static final int ONE_CM = 50;
	private static final int NEEDLE_LENGTH = ONE_CM/2;
	private static final int LINE_OFFSET = ONE_CM;
	private static final int NEEDLE_COUNT = 50000;
	public void run() {
		addHorizontalLines();
		int intersectCount = 0;
		int thrown = 0;
		add(statusLabel, 20, 20);
		pause(1000);
		for(int i = 0; i < NEEDLE_COUNT; i++) {
			GLine needle = throwNeedle();
			if(intersects(needle)) {
				needle.setColor(Color.RED);
				intersectCount++;
			}
			add(needle);
			thrown++;
			displayInfo(thrown,intersectCount);
			//pause(1);
		}
	}

	/**
	* Throws length 1 needle on canvas 
	*/
	private GLine throwNeedle() {
		double x0 = rgen.nextDouble(0,getWidth());
		double y0 = rgen.nextDouble(ONE_CM,getHeight()-ONE_CM);
		//add(new GOval(x0,y0,3,3));
		double degrees = rgen.nextDouble(0,360);
		
		double x1 = x0 + NEEDLE_LENGTH*Math.cos(Math.toRadians(degrees));
		double y1 = y0 + NEEDLE_LENGTH*Math.sin(Math.toRadians(degrees));
		GLine needle = new GLine(x0,y0,x1,y1);
		return needle;
	}

	/**
	* Draw horizontal lines
	*/
	private void addHorizontalLines() {
		for(int h = LINE_OFFSET; h < getHeight(); h += LINE_OFFSET) {
			GLine line = new GLine(0, h, getWidth(), h);
			line.setColor(Color.BLUE);
			add(line);
		}
	}
	/**
	* Check for interseciton 
	*/
	private boolean intersects(GLine needle) {
		double y0 = needle.getStartPoint().getY();
		double y1 = needle.getEndPoint().getY();
		int nth = (int) (y0/ONE_CM) + 1;
		/*if((y0 > nth*ONE_CM && y1 < nth*ONE_CM) || (y0 < nth*ONE_CM && y1 > nth*ONE_CM)) {
			return true;
		}*/
		if(y1 > nth*ONE_CM || y1 < (nth-1)*ONE_CM ) {
			return true;
		}
		else return false;
	}

	private void displayInfo(int total, int intersectCount) {
		double proportions = (double) intersectCount/total;
		double pi = (double) (2*NEEDLE_LENGTH)/(LINE_OFFSET*proportions);
		statusLabel.setLabel("total: " + total + " intersected: " + intersectCount + " PI: " + pi);
	}

	private void testNeedle() {
		int x0 = 90;
		int y0 = 2*ONE_CM;
		for (int i = 0; i < 360; i += 20 ) {
			double x1 = x0 + ONE_CM*Math.cos(Math.toRadians(i));
			double y1 = y0 + ONE_CM*Math.sin(Math.toRadians(i));
			GLine needle = new GLine(x0,y0,x1,y1);
			add(needle);
		}
	}

	GLabel statusLabel = new GLabel("status");
	private RandomGenerator rgen = RandomGenerator.getInstance();
}
