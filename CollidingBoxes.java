import acm.graphics.*;
import acm.program.*;
import acm.util.RandomGenerator;
import java.awt.*;

public class CollidingBoxes extends GraphicsProgram {
	private static final int GROUND_BOTTOM_OFFSET = 80;
	private static final int GROUND_LEFT_OFFSET = 50;

	private static final int BIG_BOX_WIDTH = 100;
	private static final int SMALL_BOX_WIDTH = 50;

	private static final double BIGM = 10000;
	private static final double SMALLM = 1;
	private static final int DELAY = 1;
	public void init() {
		setupWorld();
		createBoxes();
	}
	public void run() {
		simulation();
	}

	private void setupWorld() {
		GLine ground = new GLine(GROUND_LEFT_OFFSET, getHeight()-GROUND_BOTTOM_OFFSET, getWidth(), getHeight()-GROUND_BOTTOM_OFFSET);
		GLine wall = new GLine(GROUND_LEFT_OFFSET, getHeight()-GROUND_BOTTOM_OFFSET, GROUND_LEFT_OFFSET, 0);
		add(ground);
		add(wall);
	}

	private void createBoxes() {
		bigBox = new GRect(550, getHeight() - GROUND_BOTTOM_OFFSET - BIG_BOX_WIDTH, BIG_BOX_WIDTH, BIG_BOX_WIDTH);
		smallBox = new GRect(250, getHeight() - GROUND_BOTTOM_OFFSET - SMALL_BOX_WIDTH, SMALL_BOX_WIDTH, SMALL_BOX_WIDTH);
		bigBox.setFilled(true);
		smallBox.setFilled(true);
		add(bigBox);
		add(smallBox);
	}

	/**/
	private void simulation() {
		bigVx = -0.1;
		smallVx = 0;
		double temp;
		collisionCount = 0;
		add(statusLabel, 100, 40);
		addMassStatus();
		while(true) {
			bigBox.move(bigVx, 0);
			if(boxesCollide()) {
				temp = smallVx;
				smallVx = (2.0*BIGM*bigVx + SMALLM*smallVx - BIGM*smallVx)/(BIGM+SMALLM);
				bigVx = (2.0*SMALLM*temp + BIGM*bigVx - SMALLM*bigVx)/(BIGM+SMALLM);
				collisionCount++;
			}
			smallBox.move(smallVx,0);
			if(smallBoxCollidesWall()) {
				smallVx *= -1;
				collisionCount++;
			}
			pause(DELAY);
			addCollisionStatus();
		}
	}

	private boolean boxesCollide() {
		if(bigBox.getX() < smallBox.getX() + SMALL_BOX_WIDTH) return true;
		else return false;
	}

	private boolean smallBoxCollidesWall() {
		if(smallBox.getX() < GROUND_LEFT_OFFSET) return true;
		else return false;
	}

	private boolean bigBoxCollidesWall() {
		if(bigBox.getX() < GROUND_LEFT_OFFSET) return true;
		else return false;
	}

	private void addCollisionStatus() {
		statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
		statusLabel.setLabel("collisions: " + collisionCount);
	}

	private void addMassStatus() {
		GLabel massB = new GLabel("Big box mass: " + (int)BIGM + " kg");
		GLabel massS = new GLabel("Small box mass: " + (int)SMALLM + " kg");
		massB.setFont(new Font("SansSerif", Font.PLAIN, 20));
		massS.setFont(new Font("SansSerif", Font.PLAIN, 20));
		add(massB, 100, 60);
		add(massS, 100, 80);
	}

	/* Instance variables */
	private GRect bigBox;
	private GRect smallBox;
	private double bigVx;
	private double smallVx;
	private GLabel statusLabel = new GLabel("status");
	private int collisionCount;
}
