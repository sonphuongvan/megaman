package gameobjects;

import java.awt.Rectangle;

import state.GameWorldState;

public abstract class Human extends ParticularObject{
	
	private boolean isJumping;
	private boolean isSitting;
	private boolean isLanding;
	
	public Human(float x, float y, float width, float height, float mass, int blood, GameWorldState gameWorld) {
		super(x, y, width, height, mass, blood, gameWorld);
		setState(ALIVE);
	}
	
	public abstract void run();	
	public abstract void jump();
	public abstract void sit();
	public abstract void standUp();
	public abstract void stopRun();

	public boolean isJumping() {
		return isJumping;
	}

	public void setJumping(boolean isJumping) {
		this.isJumping = isJumping;
	}

	public boolean isSitting() {
		return isSitting;
	}

	public void isSitting(boolean isSitting) {
		this.isSitting = isSitting;
	}

	public boolean isLanding() {
		return isLanding;
	}

	public void setLanding(boolean isLanding) {
		this.isLanding = isLanding;
	}
	
	
	
	public void setSitting(boolean isSitting) {
		this.isSitting = isSitting;
	}

    @Override
    public void Update(){
        
        super.Update();
        
        if(getState() == ALIVE || getState() == NOTBEHURT){
        
            if(!isLanding){

                setPosX(getPosX() + getSpeedX());


                if(getDirection() == LEFT_DIR && 
                        getGameWorld().physicalMap.haveCollisionWithLeftWall(getBoundForCollisionWithMap())!=null){

                    Rectangle rectLeftWall = getGameWorld().physicalMap.haveCollisionWithLeftWall(getBoundForCollisionWithMap());
                    setPosX(rectLeftWall.x + rectLeftWall.width + getWidth()/2);

                }
                if(getDirection() == RIGHT_DIR && 
                        getGameWorld().physicalMap.haveCollisionWithRightWall(getBoundForCollisionWithMap())!=null){

                    Rectangle rectRightWall = getGameWorld().physicalMap.haveCollisionWithRightWall(getBoundForCollisionWithMap());
                    setPosX(rectRightWall.x - getWidth()/2);

                }



                /**
                 * Codes below check the posY of megaMan
                 */
                // plus (+2) because we must check below the character when he's speedY = 0

                Rectangle boundForCollisionWithMapFuture = getBoundForCollisionWithMap();
                boundForCollisionWithMapFuture.y += (getSpeedY()!=0?getSpeedY(): 2);
                Rectangle rectLand = getGameWorld().physicalMap.haveCollisionWithLand(boundForCollisionWithMapFuture);
                
                Rectangle rectTop = getGameWorld().physicalMap.haveCollisionWithTop(boundForCollisionWithMapFuture);
                
                if(rectTop !=null){
                    
                    setSpeedY(0);
                    setPosY(rectTop.y + getGameWorld().physicalMap.getTileSize() + getHeight()/2);
                    
                }else if(rectLand != null){
                    setJumping(false);
                    if(getSpeedY() > 0) setLanding(true);
                    setSpeedY(0);
                    setPosY(rectLand.y - getHeight()/2 - 1);
                }else{
                    setJumping(true);
                    setSpeedY(getSpeedY() + getMass());
                    setPosY(getPosY() + getSpeedY());
                }
            }
        }
    }
    
}


