package com.iitgame.flappychick.scene;

import org.anddev.andengine.engine.camera.hud.HUD;
import org.anddev.andengine.engine.handler.IUpdateHandler;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.entity.scene.background.AutoParallaxBackground;
import org.anddev.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.extension.physics.box2d.PhysicsConnector;
import org.anddev.andengine.extension.physics.box2d.PhysicsFactory;
import org.anddev.andengine.extension.physics.box2d.PhysicsWorld;
import org.anddev.andengine.extension.physics.box2d.util.Vector2Pool;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.util.HorizontalAlign;

import android.graphics.Color;
import android.hardware.SensorManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.iitgame.flappychick.SceneManager.SceneType;
import com.iitgame.flappychick.entity.Pipe;
import com.iitgame.flappychick.entity.PipePool;

public class GameScene extends BaseScene implements IOnSceneTouchListener {
	 
	private AutoParallaxBackground mAutoParallaxBackground;
	private AnimatedSprite mBird;
	private Text mHudText;
	private int score;
	private int most; 
	private PhysicsWorld mPhysicsWorld;
	private Pipe mPipe;
	private PipePool mPipePool;
	 
	private boolean mGameOver;
	private float mPipeWidth;   
	
    @Override
    public void createScene() {
        mEngine.registerUpdateHandler(new FPSLogger());
         
        setOnSceneTouchListener(this);
         
        //TODO create entities
        
        final float birdX = (SCREEN_WIDTH - mResourceManager.mBirdTextureRegion.getWidth()) / 2 - 50;
        final float birdY = (SCREEN_HEIGHT - mResourceManager.mBirdTextureRegion.getHeight()) / 2 - 30;
         
        final Rectangle ground = new Rectangle(0, SCREEN_HEIGHT - mResourceManager.mParallaxLayerFront.getHeight(), SCREEN_WIDTH, mResourceManager.mParallaxLayerFront.getHeight(), mVertexBufferObjectManager);
        ground.setColor(Color.TRANSPARENT);
        final Rectangle roof = new Rectangle(0, 0, SCREEN_WIDTH, 1, mVertexBufferObjectManager);
        roof.setColor(Color.TRANSPARENT);
         
        mPipePool = new PipePool(mResourceManager.mPipeTextureRegion, mVertexBufferObjectManager, ground.getY());
        mPipePool.batchAllocatePoolItems(10);
        mPipe = mPipePool.obtainPoolItem();
     
        attachChild(ground);
        attachChild(roof);
        attachChild(mPipe);
        
        mBird = new AnimatedSprite(birdX, birdY, mResourceManager.mBirdTextureRegion, mVertexBufferObjectManager);
        mBird.setZIndex(10);
        mBird.animate(200);
         
        attachChild(mBird);
         
        //TODO create PhysicsWorld
        
        mPhysicsWorld = new PhysicsWorld(new Vector2(0, SensorManager.GRAVITY_EARTH), false);
        mPhysicsWorld.setContactListener(createContactListener());
        
        mAutoParallaxBackground = new AutoParallaxBackground(0, 0, 0, 10);
        mAutoParallaxBackground.attachParallaxEntity(new ParallaxEntity(-5.0f, new Sprite(0, SCREEN_HEIGHT - mResourceManager.mParallaxLayerBack.getHeight(), mResourceManager.mParallaxLayerBack, mVertexBufferObjectManager)));
        mAutoParallaxBackground.attachParallaxEntity(new ParallaxEntity(-10.0f, new Sprite(0, SCREEN_HEIGHT - mResourceManager.mParallaxLayerFront.getHeight(), mResourceManager.mParallaxLayerFront, mVertexBufferObjectManager)));
        setBackground(mAutoParallaxBackground);    

         
        //TODO create body and fixture
        final FixtureDef wallFixtureDef = PhysicsFactory.createFixtureDef(0, 0, 0);
        final Body groundBody = PhysicsFactory.createBoxBody(mPhysicsWorld, ground, BodyType.StaticBody, wallFixtureDef);
        groundBody.setUserData("ground");
     
        final FixtureDef birdFixtureDef = PhysicsFactory.createFixtureDef(1, 0, 0);
        final Body birdBody = PhysicsFactory.createCircleBody(mPhysicsWorld, mBird, BodyType.DynamicBody, birdFixtureDef);
        birdBody.setUserData("bird");
        mBird.setUserData(birdBody);
     
        mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(mBird, birdBody, true, false));
         
        /* The actual collision-checking. */
        registerUpdateHandler(new IUpdateHandler() {
            
            @Override
            public void reset() {}
             
            @Override
            public void onUpdate(float pSecondsElapsed) {
                 
                if (!mGameOver && mPipe.collidesWith(mBird)) {
                    mGameOver = true;
                    mResourceManager.mSound.play();
                    mBird.stopAnimation(0);
                    mPipe.die();
                    mAutoParallaxBackground.setParallaxChangePerSecond(0);
                    return;
                }
                 
                if (mPipe.getX() < -mPipeWidth) {
                    detachChild(mPipe);
                    mPipePool.recyclePoolItem(mPipe);
                    mPipePool.shufflePoolItems();
                     
                    mPipe = mPipePool.obtainPoolItem();
                    attachChild(mPipe);
                    sortChildren();
                }
                 
                if (score != mPipePool.getPipeIndex() && mBird.getX() > (mPipe.getX()+mPipeWidth)) {
                    score = mPipePool.getPipeIndex();
                    mHudText.setText(String.valueOf(score));
                }              
     
            }
        });     
         
        //TODO create CameraScene for 'get ready'
         
        //TODO create CameraScene for 'game over'
         
        //TODO create HUD for score
        most = mActivity.getMaxScore();
        
        //create HUD for score
        HUD gameHUD = new HUD();
        // CREATE SCORE TEXT
        mHudText = new Text(SCREEN_WIDTH/2, 50, mResourceManager.mFont5, "0123456789", new TextOptions(HorizontalAlign.LEFT), mVertexBufferObjectManager);
        mHudText.setText("0");
        mHudText.setX((SCREEN_WIDTH - mHudText.getWidth()) / 2);
        mHudText.setVisible(false);
        gameHUD.attachChild(mHudText);     
        mCamera.setHUD(gameHUD);        
    }
     
    @Override
    public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
        //TODO implement
        return false;
    }
 
    @Override
    public void onBackKeyPressed() {
        mSceneManager.setScene(SceneType.SCENE_MENU);
    }
 
    @Override
    public SceneType getSceneType() {
        return SceneType.SCENE_GAME;
    }
 
    @Override
    public void disposeScene() {
        //TODO
    }
    
    @Override
    public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
        if(mPhysicsWorld != null) {
            if(!mGameOver && pSceneTouchEvent.isActionDown()) {
                jumpFace(mBird);
                return true;
            }
        }
        return false;
    }
     
    private void jumpFace(final AnimatedSprite face) {
        //TODO implement
    	 face.setRotation(-15);
    	 final Body faceBody = (Body)face.getUserData();
    	 
    	 final Vector2 velocity = Vector2Pool.obtain(0, -5);
    	 faceBody.setLinearVelocity(velocity);
    	 Vector2Pool.recycle(velocity);
    }
     
    private ContactListener createContactListener() {
        //TODO implement
    	 ContactListener contactListener = new ContactListener() {
    	        @Override
    	        public void beginContact(Contact pContact) {
    	            final Fixture fixtureA = pContact.getFixtureA();
    	            final Body bodyA = fixtureA.getBody();
    	            final String userDataA = (String) bodyA.getUserData();
    	 
    	            final Fixture fixtureB = pContact.getFixtureB();
    	            final Body bodyB = fixtureB.getBody();
    	            final String userDataB = (String) bodyB.getUserData();             
    	 
    	            if (("bird".equals(userDataA) && "ground".equals(userDataB)) || ("ground".equals(userDataA) && "bird".equals(userDataB))) {
    	                mGameOver = true;
    	                mBird.stopAnimation(0);
    	                mPipe.die();
    	                mAutoParallaxBackground.setParallaxChangePerSecond(0);
    	                 
    	                if (score > most) {
    	                    most = score;
    	                    mActivity.setMaxScore(most);
    	                }
    	                 
    	                mBird.setVisible(false);
    	                mHudText.setVisible(false);                
    	                 
    	                //TODO display game over with score
    	            }
    	        }
    	 
    	        @Override
    	        public void endContact(Contact contact) {
    	        }
    	 
    	        @Override
    	        public void preSolve(Contact contact, Manifold oldManifold) {
    	        }
    	 
    	        @Override
    	        public void postSolve(Contact contact, ContactImpulse impulse) {
    	        }
    	    };
    	    return contactListener;
    }
     
}