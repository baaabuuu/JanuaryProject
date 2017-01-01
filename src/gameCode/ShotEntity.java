package gameCode;

/**
 * pew!
 */
public class ShotEntity extends Entity {
	private double moveSpeed = -300;
	/**Again, potential online code*/
	private Game game;
	/** Used to detect if it has collided and it is "used".*/
	private boolean used = false;
	
	/** Alien entity describes this I suppose*/
	public ShotEntity(Game game,String sprite,int x,int y) {
		super(sprite,x,y);
		
		this.game = game;
		
		dy = moveSpeed;
	}

	/**More movement code. */
	public void move(long delta) {
		super.move(delta);
		if (y < -100) { /** if our shot gets offscren, it's removed */
			game.removeEntity(this);
		}
	}
	
	/**
	 * Tells us this has collided with another entity (actually used here)*/
	public void collidedWith(Entity other)
	{
		/**Checking for a dumb bug with double kills*/
		if (used)
		{
			return;
		}

		if (other instanceof AlienEntity)
		{
			/**remove this and the hit target*/
			game.removeEntity(this);
			game.removeEntity(other);
			/** notify the game what happened*/
			game.notifyAlienKilled();
			used = true;
		}
	}
}
