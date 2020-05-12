package com.iitgame.flappychick.entity;

import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.entity.shape.IShape;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.sprite.TiledSprite;
import org.anddev.andengine.entity.sprite.batch.DynamicSpriteBatch;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.iitgame.flappychick.GameActivity;

public class Pipe extends DynamicSpriteBatch {
	 
    private static final float DEMO_VELOCITY = 150.0f;
    private static final float DEMO_GAP = 75.0f;
    private static final float DEMO_HEIGHT = 150.0f;
    private static final float DEMO_POSITION = 1.1f*GameActivity.CAMERA_WIDTH;
     
    private TiledSprite mPipe1a;
    private TiledSprite mPipe1b;
     
    private float mGroundY;
    private float mPipeWidth;
    private float mPipeHeight;
     
    private final PhysicsHandler mPhysicsHandler;
     
    public Pipe(TiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, float pGroundY, float pDeltaHeight) {
        super(pTiledTextureRegion.getTexture(), 2, pVertexBufferObjectManager);
         
        mGroundY = pGroundY;
        mPipeWidth = pTiledTextureRegion.getWidth();
        mPipeHeight = DEMO_HEIGHT + pDeltaHeight;
         
        mPhysicsHandler = new PhysicsHandler(this);
        registerUpdateHandler(mPhysicsHandler);
        mPhysicsHandler.setVelocity(-DEMO_VELOCITY, 0);
         
        //top
        final float pipe1aX = 0;
        final float pipe1aY = 0;
         
        mPipe1a = new TiledSprite(pipe1aX, pipe1aY, pTiledTextureRegion, pVertexBufferObjectManager);
        mPipe1a.setCurrentTileIndex(0);
        mPipe1a.setHeight(mPipeHeight);
         
        //bottom
        final float pipe1bX = 0;
        final float pipe1bY = mPipeHeight + Pipe.DEMO_GAP;
         
        mPipe1b = new TiledSprite(pipe1bX, pipe1bY, pTiledTextureRegion, pVertexBufferObjectManager);
        mPipe1b.setCurrentTileIndex(1);
        mPipe1b.setHeight(mGroundY - pipe1bY);
         
        setPosition(DEMO_POSITION, 0);
    }
     
    @Override
    protected boolean onUpdateSpriteBatch() {
        this.draw(mPipe1a);
        this.draw(mPipe1b);
 
        return true;
    }
 
    @Override
    public boolean collidesWith(IShape pOtherShape) {
        Sprite sprite = (Sprite) pOtherShape;
        if (sprite.getX()+sprite.getWidth() > this.mX && (sprite.getY() < mPipe1a.getHeight() || sprite.getY()+sprite.getHeight() > mPipe1b.getY()) && sprite.getX() < this.mX+mPipeWidth) {
            return true;
        }
        return super.collidesWith(pOtherShape);
    }
     
    public void die() {
        unregisterUpdateHandler(mPhysicsHandler);
    }
     
    public void alive() {
        registerUpdateHandler(mPhysicsHandler);
    }  
 
    @Override
    public void reset() {
        super.reset();
        setX(DEMO_POSITION);
    }
 
}
                