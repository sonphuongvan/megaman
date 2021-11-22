package gameobjects;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import effect.Animation;
import effect.CacheDataLoader;
import state.GameWorldState;

public class BlueFire extends Bullet {
	
	private Animation forwardBulletAnimation, backBulletAnimation;
	
	public BlueFire(float x, float y, GameWorldState gameWorld) {
		super(x, y, 60, 30, 1.0f, 10, gameWorld);
		forwardBulletAnimation = CacheDataLoader.getInstance().getAnimation("bluefire");
		backBulletAnimation = CacheDataLoader.getInstance().getAnimation("bluefire");
		backBulletAnimation.flipAllImage();
	}

	@Override
	public void draw(Graphics2D g2d) {
		if(getSpeedX() > 0) {
			if(!forwardBulletAnimation.isIgnoreFrame(0) && forwardBulletAnimation.getCurrentFrame() == 3) {
				forwardBulletAnimation.setIgnoreFrame(0);
				forwardBulletAnimation.setIgnoreFrame(1);
				forwardBulletAnimation.setIgnoreFrame(2);
			}
			forwardBulletAnimation.Update(System.nanoTime());
			forwardBulletAnimation.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) (getPosY() - getGameWorld().camera.getPosY()), g2d);
		}else {
			if(!backBulletAnimation.isIgnoreFrame(0) && backBulletAnimation.getCurrentFrame() == 3) {
				backBulletAnimation.setIgnoreFrame(0);
				backBulletAnimation.setIgnoreFrame(1);
				backBulletAnimation.setIgnoreFrame(2);
			}
			backBulletAnimation.Update(System.nanoTime());
			backBulletAnimation.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) (getPosY() - getGameWorld().camera.getPosY()), g2d);
		}
		
	}
	
	@Override
	public void Update() {
		if(forwardBulletAnimation.isIgnoreFrame(0) || backBulletAnimation.isIgnoreFrame(0))
            setPosX(getPosX() + getSpeedX());
        ParticularObject object = getGameWorld().particularObjectManager.getCollisionWithEnemyObject(this);
        if(object!=null && object.getState() == ALIVE){
            setBlood(0);
            object.setBlood(object.getBlood() - getDamage());
            object.setState(BEHURT);
            System.out.println("Bullet set behurt for enemy");
        }

	}
	
	@Override
	public void attack() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Rectangle getBoundForCollisionWithEnemy() {
		// TODO Auto-generated method stub
		return getBoundForCollisionWithMap();
	}
	
	
}
