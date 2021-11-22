package gameobjects;

import java.applet.AudioClip;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import effect.Animation;
import effect.CacheDataLoader;
import state.GameWorldState;

public class RedEyeDevil extends ParticularObject{
	
	private Animation forwardAnimation, backAnimation;
	private long startTimeToShoot;
	private AudioClip shooting;
	
	public RedEyeDevil(float x, float y, GameWorldState gameWorld) {
		super(x, y, 127, 89, 0, 100, gameWorld);
		backAnimation = CacheDataLoader.getInstance().getAnimation("redeye");
		forwardAnimation = CacheDataLoader.getInstance().getAnimation("redeye");
		forwardAnimation.flipAllImage();
		startTimeToShoot = 0;
		setDamage(10);
		setTimeForNotBeHurt(3000*1000000);
		shooting = CacheDataLoader.getInstance().getSound("redeyeshooting");
	}
	
	@Override
	public void attack() {
		shooting.play();
		Bullet bullet = new RedEyeBullet(getPosX(), getPosY(), getGameWorld());
		if(getDirection() == LEFT_DIR) bullet.setSpeedX(-8);
		else bullet.setSpeedX(8);
		bullet.setTeamType(getTeamType());
		getGameWorld().bulletManager.addObject(bullet);
		//shooting
	}
	
	public void Update() {
		super.Update();
		if(System.nanoTime() - startTimeToShoot > 1000*1000000) {
			attack();
			System.out.println("Red Eye attack");
			startTimeToShoot = System.nanoTime();
		}
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
		if(!isObjectOutOfCameraView()){
            if(getState() == NOTBEHURT && (System.nanoTime()/10000000)%2!=1){
                // plash...
            }else{
                if(getDirection() == LEFT_DIR){
                    backAnimation.Update(System.nanoTime());
                    backAnimation.draw((int) (getPosX() - getGameWorld().camera.getPosX()), 
                            (int)(getPosY() - getGameWorld().camera.getPosY()), g2);
                }else{
                    forwardAnimation.Update(System.nanoTime());
                    forwardAnimation.draw((int) (getPosX() - getGameWorld().camera.getPosX()), 
                            (int)(getPosY() - getGameWorld().camera.getPosY()), g2);
                }
            }
        }
        //drawBoundForCollisionWithEnemy(g2);
		
	}
	
	
	
}
