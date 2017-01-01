package gameCode;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * entities are things like the ship, enemies etc.
 * They are represented by sprites in the game.
 */
public abstract class Entity {
	/**location and movement data*/
	protected double x;
	protected double y;
	protected double dx;
	protected double dy;
	
	/** The sprite that represents this entity */
	protected Sprite sprite;

	/** used to represent this object in relation from itself to others */
	private Rectangle me = new Rectangle();
	/** used to represent this object in relation from others to itself */
	private Rectangle him = new Rectangle();
	
	/** basic constructor.*/
	public Entity(String ref,int x,int y) {
		this.sprite = SpriteStorage.get().getSprite(ref);
		this.x = x;
		this.y = y;
	}
	
	/** basic movement I suppose? Pretty self explanatory? Why did I even write this comment?*/
	public void move(long delta) {
		x += (delta * dx) / 1000;
		y += (delta * dy) / 1000;
	}
	
	/** Setter method for horizontal speed */
	public void setHorizontalMovement(double dx) {
		this.dx = dx;
	}

	/** Setter method for vertical speed, SHOCKER I TELL YOU! */
	public void setVerticalMovement(double dy) {
		this.dy = dy;
	}
	
	/** Drummroll please.... A GETTER METHOD! */
	public double getHorizontalMovement() {
		return dx;
	}

	/** Not sure whether or whether not I should describe this.	 */
	public double getVerticalMovement() {
		return dy;
	}
	
	/** Draws our sprite, it uses Graphics which we've loaded so it knows what it should draw on. */
	public void draw(Graphics g) {
		sprite.draw(g,(int) x,(int) y);
	}
	
	/**Essentially we call this logic from the entity itself, and it'll have a certain behavior. */
	public void doLogic(){};
	
	/** try to guess, really hard!*/
	public int getX() {
		return (int) x;
	}

	public int getY() {
		return (int) y;
	}
	
	/** Check if this entity had a collision with another entity(I love the rectangle class) */
	public boolean collidesWith(Entity other) {
		me.setBounds((int) x,(int) y,sprite.getWidth(),sprite.getHeight());
		him.setBounds((int) other.x,(int) other.y,other.sprite.getWidth(),other.sprite.getHeight());
		return me.intersects(him);
	}
	
	/**Used to notify about the collision */
	public abstract void collidedWith(Entity other);
}
