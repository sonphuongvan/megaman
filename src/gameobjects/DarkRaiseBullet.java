package gameobjects;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import effect.Animation;
import effect.CacheDataLoader;
import state.GameWorldState;

public class DarkRaiseBullet extends Bullet{
	
	private Animation forwardBulletAnimation, backBulletAnimation;
	public DarkRaiseBullet(float x, float y, GameWorldState gameWorld) {
		super(x, y, 30, 30, 1.0f, 10, gameWorld);
		forwardBulletAnimation = CacheDataLoader.getInstance().getAnimation("darkraisebullet");
		backBulletAnimation = CacheDataLoader.getInstance().getAnimation("darkraisebullet");
		backBulletAnimation.flipAllImage();
	}
	@Override
	public void draw(Graphics2D g2) {
		if(getSpeedX() > 0) {
			forwardBulletAnimation.Update(System.nanoTime());
            forwardBulletAnimation.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
		}else {
			backBulletAnimation.Update(System.nanoTime());
			backBulletAnimation.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
		}
		
	}
	@Override
	public void attack() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Rectangle getBoundForCollisionWithEnemy() {
		return getBoundForCollisionWithMap();
	}
	
	
	
}
