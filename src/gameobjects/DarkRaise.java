package gameobjects;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import effect.Animation;
import effect.CacheDataLoader;
import state.GameWorldState;

public class DarkRaise extends ParticularObject{
	
	private Animation forwardAnimation, backAnimation;
	private long startTimeToShoot;
	private float x1, x2;
	public DarkRaise(float x, float y, GameWorldState gameWorld) {
		super(x, y, 127, 89, 0, 100, gameWorld);
		backAnimation = CacheDataLoader.getInstance().getAnimation("darkraise");
		forwardAnimation = CacheDataLoader.getInstance().getAnimation("darkraise");
		forwardAnimation.flipAllImage();
		startTimeToShoot = 0;
		setTimeForNotBeHurt(300*1000000);
		
		x1 = x - 100;
		x2 = x + 100;
		setSpeedX(1);
		setDamage(10);
	}
	
	@Override
	public void attack() {
		float megamanX = getGameWorld().megaMan.getPosX();
		float megamanY = getGameWorld().megaMan.getPosY();
		
		float deltaX = megamanX - getPosX();
		float deltaY = megamanY - getPosY();
		float speed = 3;
		
		float a = Math.abs(deltaX/deltaY);
		float speedX = (float) Math.sqrt(speed*speed*a*a/(a*a+1));
		float speedY = (float) Math.sqrt(speed*speed/(a*a+1));
		
		Bullet bullet = new DarkRaiseBullet(getPosX(), getPosY(), getGameWorld());
		if(deltaX < 0) {
			bullet.setSpeedX(-speedX);
		}else {
			bullet.setSpeedX(speedX);
		}
		bullet.setSpeedY(speedY);
		bullet.setTeamType(getTeamType());
		getGameWorld().bulletManager.addObject(bullet);
		
	}
	
	@Override
	public Rectangle getBoundForCollisionWithEnemy() {
		Rectangle rect = getBoundForCollisionWithMap();
		rect.x += 20;
		rect.width -= 40;
		return rect;
	}
	@Override
	public void draw(Graphics2D g2) {
		if(!isObjectOutOfCameraView()) {
			if(getState() == NOTBEHURT && ((System.nanoTime()/10000000)%2 != 1)){
				//splash...
			}else {
				if(getDirection() == LEFT_DIR) {
					backAnimation.Update(System.nanoTime());
                    backAnimation.draw((int) (getPosX() - getGameWorld().camera.getPosX()), 
                            (int)(getPosY() - getGameWorld().camera.getPosY()), g2);

				}else {
					forwardAnimation.Update(System.nanoTime());
					forwardAnimation.draw((int) (getPosX() - getGameWorld().camera.getPosX()), 
                            (int)(getPosY() - getGameWorld().camera.getPosY()), g2);

				}
			}
		}
		
	}
	
	public void Update(){
        super.Update();
        if(getPosX() < x1)
            setSpeedX(1);
        else if(getPosX() > x2)
            setSpeedX(-1);
        setPosX(getPosX() + getSpeedX());
        
        if(System.nanoTime() - startTimeToShoot > 1000*10000000*1.5){
            attack();
            startTimeToShoot = System.nanoTime();
        }
    }

	
}
