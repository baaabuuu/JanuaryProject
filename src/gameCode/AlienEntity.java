package gameCode;

/**
 * spooky scary aliens
 */
public class AlienEntity extends Entity {
	private double moveSpeed = 75;
	/** The game in which the entity exists, 
	 * can be used for online code?
	 */
	private Game game;
	/**
	 * game, sprite x and y
	 */
	public AlienEntity(Game game,String ref,int x,int y) {
		super(ref,x,y);
		this.game = game;
		dx = -moveSpeed;
	}

	/** Movement left and right, we use delta as a timer */
	public void move(long delta) {
		/** checks if we are on the left*/
		if ((dx < 0) && (x < 10))
		{
			game.updateLogic();
		}
		else if ((dx > 0) && (x > 750)) /**Checks if we are on the right*/
		{
			game.updateLogic();
		}
		super.move(delta);
	}
	
	/** essentially moving down logic AND death! (me too thanks) */
	public void doLogic() {
		dx = -dx;
		y += 10;
		if (y > 570) {
			game.notifyDeath();
		}
	}
	
	/** notifies we've collided YAY, but we handle this else where sooo.. (filthy abstracts, should maybe have used something else?*/
	public void collidedWith(Entity other) {}
}
