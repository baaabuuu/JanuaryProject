package gameCode;

/** The entity that represents the players shi(t)p.*/
public class ShipEntity extends Entity {
	/** The game in which the ship exists, could be used for online code? */
	private Game game;
	
	public ShipEntity(Game game,String ref,int x,int y) {
		super(ref,x,y);	
		this.game = game;
	}
	
	public void move(long delta) {
		if ((dx < 0) && (x < 10)) {
			return;
		} else if ((dx>0) && x>750){
			return;
		}
		super.move(delta);
	}
	
	public void collidedWith(Entity other) {
		/** notify if we accidently touch an alien (then kill)*/
		if (other instanceof AlienEntity) {
			game.notifyDeath();
		}
	}
}