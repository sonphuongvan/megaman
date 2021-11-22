package gameobjects;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import effect.Animation;
import effect.CacheDataLoader;
import state.GameWorldState;

public class RocketBullet extends Bullet{
	
	private Animation forwardBulletAnimation, forwardBulletAnimationDown, forwardBulletAnimationUp;
	private Animation backBulletAnimation, backBulletAnimationDown, backBulletAnimationUp;
	
	private long startTimeForChangeSpeedY;
	
	public RocketBullet(float x, float y, GameWorldState gameWorld) {
		super(x, y, 30, 30, 1.0f, 10, gameWorld);
		backBulletAnimation = CacheDataLoader.getInstance().getAnimation("rocket");
		backBulletAnimationUp = CacheDataLoader.getInstance().getAnimation("rocketUp");
		backBulletAnimationDown = CacheDataLoader.getInstance().getAnimation("rocketDown");
		
		forwardBulletAnimationUp = CacheDataLoader.getInstance().getAnimation("rocketUp");
        forwardBulletAnimationUp.flipAllImage();
        forwardBulletAnimationDown = CacheDataLoader.getInstance().getAnimation("rocketDown");
        forwardBulletAnimationDown.flipAllImage();
        forwardBulletAnimation = CacheDataLoader.getInstance().getAnimation("rocket");
        forwardBulletAnimation.flipAllImage();

	}

	@Override
	public void draw(Graphics2D g2) {
		// TODO Auto-generated method stub
		if(getSpeedX() > 0){  
            if(getSpeedY() > 0){
                forwardBulletAnimationDown.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
            }else if(getSpeedY() < 0){
                forwardBulletAnimationUp.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
            }else
                forwardBulletAnimation.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
        }else{
            if(getSpeedY() > 0){
                backBulletAnimationDown.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
            }else if(getSpeedY() < 0){
                backBulletAnimationUp.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
            }else
                backBulletAnimation.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
        }

	}
	
	private void changeSpeedY(){
        if(System.currentTimeMillis() % 3 == 0){
            setSpeedY(getSpeedX());
        }else if(System.currentTimeMillis() % 3 == 1){
            setSpeedY(-getSpeedX());
        }else {
            setSpeedY(0);
            
        }
    }

	
    @Override
    public void Update() {
            // TODO Auto-generated method stub
        super.Update();
        
        if(System.nanoTime() - startTimeForChangeSpeedY > 500*1000000){
            startTimeForChangeSpeedY = System.nanoTime();
            changeSpeedY();
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
