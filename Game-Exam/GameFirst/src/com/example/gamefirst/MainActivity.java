package com.example.gamefirst;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;

public class MainActivity extends BaseGameActivity {

	
	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 720;
	
	private Camera mCamera;
	private Scene mScene;
	
	
	private BitmapTextureAtlas mBananaAtlas;
	//private TextureRegion mBananaTextureRegion; //for sprite or picture
	private TiledTextureRegion mBananaTextureRegion; //for sprite or picture
	//private Sprite mBanana; // for sprite
	private AnimatedSprite mBanana;
	
	@Override
	public Engine onLoadEngine() {
		// TODO Auto-generated method stub
		
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		EngineOptions opt = new EngineOptions(true, ScreenOrientation.PORTRAIT, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera);
		//EngineOptions opt = new EngineOptions(true, ScreenOrientation.PORTRAIT, new FillResolutionPolicy(), this.mCamera);
		//fillresolutionpolicy aase;
		
		Engine engine  = new Engine(opt);
		return engine;
		
	}

	@Override
	public void onLoadResources() {
		// TODO Auto-generated method stub
		
		
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		this.mBananaAtlas = new BitmapTextureAtlas(512, 256,TextureOptions.DEFAULT);
		//this.mBananaTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBananaAtlas, MainActivity.this, "face_box.png",0,0); //for sprite
		this.mBananaTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBananaAtlas, MainActivity.this, "snapdragon_tiled.png",0,0, 4, 3); // for animation
		this.mEngine.getTextureManager().loadTextures(this.mBananaAtlas);
		
	
	}

	@Override
	public Scene onLoadScene() {
		
		// TODO Auto-generated method stub
		
		
		this.mScene= new Scene();
		this.mScene.setBackground(new ColorBackground(0.09804f, 0.6274f, 0.8784f));
		
		//mBanana = new Sprite((CAMERA_WIDTH - mBananaTextureRegion.getWidth())/2, (CAMERA_HEIGHT - mBananaTextureRegion.getHeight())/2, mBananaTextureRegion);
		mBanana = new AnimatedSprite(0,0, mBananaTextureRegion);
		mBanana.animate(100);
		mBanana.setPosition((CAMERA_WIDTH - mBanana.getWidth())/2, (CAMERA_HEIGHT - mBanana.getHeight())/2);
		//mBanana.setRotation(90);
		mBanana.setScale(3);
		mScene.attachChild(mBanana);
		return this.mScene;
	}

	@Override
	public void onLoadComplete() {
		// TODO Auto-generated method stub
		
	}

	

}
