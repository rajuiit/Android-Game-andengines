package com.iit847.sound;

import java.io.IOException;

import org.anddev.andengine.audio.music.Music;
import org.anddev.andengine.audio.music.MusicFactory;
import org.anddev.andengine.audio.sound.Sound;
import org.anddev.andengine.audio.sound.SoundFactory;
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;

public class MainActivity extends BaseGameActivity {
	
	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 720;
	
	private Camera mCamera;
	private Scene mScene;
	
	private BitmapTextureAtlas mFaceAtlas;
	private TiledTextureRegion mFaceTextureRegion; 
	private AnimatedSprite mFaceButton; 

	private Music mMusic;
	private Sound mSound;

	@Override
	public Engine onLoadEngine() {
		// TODO Auto-generated method stub

		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		EngineOptions opt = new EngineOptions(true, ScreenOrientation.PORTRAIT, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera).setNeedsMusic(true).setNeedsSound(true);
		Engine engine = new Engine(opt);
		
		return engine;
	}

	@Override
	public void onLoadResources() {
		// TODO Auto-generated method stub
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		MusicFactory.setAssetBasePath("mfx/");
		SoundFactory.setAssetBasePath("mfx/");
		
		this.mFaceAtlas = new BitmapTextureAtlas(128, 128, TextureOptions.DEFAULT);
		
		this.mFaceTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mFaceAtlas, MainActivity.this, "face_box_tiled.png", 0, 0, 2, 1);
		
		try {
			this.mMusic = MusicFactory.createMusicFromAsset(this.mEngine.getMusicManager(), this, "explosion.ogg");
			this.mSound = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, "explosion.ogg");
			
		} catch (IllegalStateException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
		this.mEngine.getTextureManager().loadTextures(this.mFaceAtlas);
	}

	@Override
	public Scene onLoadScene() {
		// TODO Auto-generated method stub
		this.mScene = new Scene();
		this.mScene.setBackground(new ColorBackground(0.09804f, 0.6274f, 0.8784f));
		this.mScene.setTouchAreaBindingEnabled(true);
		
		this.mFaceButton = new AnimatedSprite(0, 0, this.mFaceTextureRegion){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				
				if(pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN)
				{
					this.setCurrentTileIndex(1);
					
					//mMusic.play();
					mSound.play();
				}
				
				if(pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP)
				{
					
					this.setCurrentTileIndex(0);
				}
				
				return true;
			}
		};
		this.mFaceButton.setPosition((CAMERA_WIDTH - this.mFaceButton.getWidth())/2, (CAMERA_HEIGHT - this.mFaceButton.getHeight())/2);
		this.mScene.attachChild(this.mFaceButton);
		this.mScene.registerTouchArea(this.mFaceButton);
		
		
		return this.mScene;
	}

	@Override
	public void onLoadComplete() {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
