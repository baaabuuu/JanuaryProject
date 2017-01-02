package gameCode;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Works as the game, does some funky stuff?
 */
public class Game extends Canvas {
	/** The strategy that allows us to use accelerate graphics methods */
	private BufferStrategy strategy;
	/** True if the game is currently "running", i.e. the game loop is looping */
	private boolean gameRunning = true;
	/** The list of all the entities that exist in our game */
	private ArrayList entities = new ArrayList();
	/** The list of entities that need to be removed from the game this loop */
	private ArrayList removeList = new ArrayList();
	/** The entity representing the player */
	private Entity ship;
	/** The speed at which the player's ship should move (pixels/sec) */
	private double moveSpeed = 300;
	/** The time at which last fired a shot */
	private long lastFire = 0;
	/** The interval between our players shot (ms) */
	private long firingInterval = 500;
	/** The number of aliens left on the screen */
	private int alienCount;
	
	/** The message to display which waiting for a key press */
	private String message = "";
	/** True if we're holding up game play until a key has been pressed */
	private boolean waitingForKeyPress = true;
	/** True if the left cursor key is currently pressed */
	private boolean leftPressed = false;
	/** True if the right cursor key is currently pressed */
	private boolean rightPressed = false;
	/** True if we are firing */
	private boolean firePressed = false;
	/** True if game logic needs to be applied this loop, normally as a result of a game event */
	private boolean logicRequiredThisLoop = false;
	
	public Game(){
		/**frames are windows*/
		JFrame container = new JFrame("Space Invaders 101");
		JPanel panel = (JPanel) container.getContentPane();
		panel.setPreferredSize(new Dimension(800,600));
		panel.setLayout(null);
		setBounds(0,0,800,600);
		panel.add(this);
		
		/** since this runs on accelerated graphics, we will not tell it to default paint it*/
		setIgnoreRepaint(true);
		container.pack();
		container.setResizable(false);
		container.setVisible(true);
		container.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		/**a listener for our keyboard input :D*/
		addKeyListener(new KeyInputHandler());
		requestFocus();
		/**Buffer strategy*/
		createBufferStrategy(2);
		strategy = getBufferStrategy();
		initEntities();
	}
	
	/** Starts a new game, removes entities and blanks the keyboard */
	private void startGame() {
		entities.clear();
		initEntities();
		leftPressed = false;
		rightPressed = false;
		firePressed = false;
	}
	
	/** setups entitites*/
	private void initEntities() {
		ship = new ShipEntity(this,"sprites/spaceship.png",370,550);
		entities.add(ship);
		alienCount = 0;
		for (int row=0;row<5;row++) {
			for (int x=0;x<12;x++) {
				Entity alien = new AlienEntity(this,"sprites/alien.png",100+(x*50),(50)+row*30);
				entities.add(alien);
				alienCount++;
			}
		}
	}
	
	/** used to notify that gameLogic should update! */
	public void updateLogic() {
		logicRequiredThisLoop = true;
	}
	
	/**Name should explain it. */
	public void removeEntity(Entity entity) {
		removeList.add(entity);
	}
	
	/** RIP */
	public void notifyDeath() {
		message = "Oh no! They got you, try again?";
		waitingForKeyPress = true;
	}
	
	/** Player Victory msg */
	public void notifyWin() {
		message = "Well done! You Win!";
		waitingForKeyPress = true;
	}
	
	/** pew, killed alien */
	public void notifyAlienKilled() {
		alienCount--;
		if (alienCount == 0) {
			notifyWin();
		}
		for (int i=0;i<entities.size();i++) {
			Entity entity = (Entity) entities.get(i);
			if (entity instanceof AlienEntity) {
				entity.setHorizontalMovement(entity.getHorizontalMovement() * 1.02);
			}
		}
	}
	
	/**
	 * Attempt to fire a shot from the player. Its called "try"
	 * since we must first check that the player can fire at this 
	 * point, i.e. has he/she waited long enough between shots
	 */
	public void tryToFire() {
		if (System.currentTimeMillis() - lastFire < firingInterval) {
			return;
		}
		lastFire = System.currentTimeMillis();
		ShotEntity shot = new ShotEntity(this,"sprites/laser.png",ship.getX()+10,ship.getY()-30);
		entities.add(shot);
	}
	
	public void gameLoop() {
		long lastLoopTime = System.currentTimeMillis();
		while (gameRunning) {
			/** used to identify how long since last looptime, used to identify how far they should move*/
			long delta = System.currentTimeMillis() - lastLoopTime;
			lastLoopTime = System.currentTimeMillis();
			
			/** blank out graphics*/
			Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
			g.setColor(Color.black);
			g.fillRect(0,0,800,600);
			if (!waitingForKeyPress) {
				for (int i=0;i<entities.size();i++) {
					Entity entity = (Entity) entities.get(i);
					entity.move(delta);
				}
			}			for (int i=0;i<entities.size();i++) {
				Entity entity = (Entity) entities.get(i);
				entity.draw(g);
			}
			/** it's not the most efficent way, it just compares positions between them and then compares them*/
			for (int p=0;p<entities.size();p++) {
				for (int s=p+1;s<entities.size();s++) {
					Entity me = (Entity) entities.get(p);
					Entity him = (Entity) entities.get(s);
					
					if (me.collidesWith(him)) {
						me.collidedWith(him);
						him.collidedWith(me);
					}
				}
			}
			/**clears "ded" objects*/
			entities.removeAll(removeList);
			removeList.clear();
			if (logicRequiredThisLoop) {
				for (int i=0;i<entities.size();i++) {
					Entity entity = (Entity) entities.get(i);
					entity.doLogic();
				}
				logicRequiredThisLoop = false;
			}
			/** press any key graphics */
			if (waitingForKeyPress) {
				g.setColor(Color.white);
				g.drawString(message,(800-g.getFontMetrics().stringWidth(message))/2,250);
				g.drawString("Press any key",(800-g.getFontMetrics().stringWidth("Press any key"))/2,300);
			}
			g.dispose();
			strategy.show();
			
			/**ship movement*/
			ship.setHorizontalMovement(0);
			if ((leftPressed) && (!rightPressed)) {
				ship.setHorizontalMovement(-moveSpeed);
			} else if ((rightPressed) && (!leftPressed)) {
				ship.setHorizontalMovement(moveSpeed);
			}
			if (firePressed) {
				tryToFire();
			}
			/** works as a timer, should perhaps look into a timer class/object as this is not prescise*/
			try { Thread.sleep(10); } catch (Exception e) {}
		}
	}
	
	/** a private class to that extends our keyAdapter and is used for neat stuff! We are keeping it inside this scope. */
	private class KeyInputHandler extends KeyAdapter {
		/** The number of key presses we've had while waiting for an "any key" press */
		private int pressCount = 1;
		public void keyPressed(KeyEvent e) {
			if (waitingForKeyPress) {
				return;
			}
			
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				leftPressed = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				rightPressed = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				firePressed = true;
			}
		} 
		
		/** checks if a key has been released */
		public void keyReleased(KeyEvent e) {
			if (waitingForKeyPress) {
				return;
			}
			
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				leftPressed = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				rightPressed = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				firePressed = false;
			}
		}

		/** Checks if we've pressed a key! */
		public void keyTyped(KeyEvent e) {
			/** any key pressed?*/
			if (waitingForKeyPress) {
				if (pressCount == 1) {

					waitingForKeyPress = false;
					startGame();
					pressCount = 0;
				} else {
					pressCount++;
				}
			}
			/** escape button closes the game.. why is it handled here again? Cause it's the keypressed and not released! */

			if (e.getKeyChar() == 27) {
				System.exit(0);
			}
		}
	}
	
	/** essentially creates a new game and causes it to loop. */
	public static void main(String argv[]) {
		Game g =new Game();
		g.gameLoop();
	}
}
