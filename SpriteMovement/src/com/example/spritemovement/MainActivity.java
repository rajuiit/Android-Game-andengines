package com.example.spritemovement;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.handler.IUpdateHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;

public class MainActivity extends BaseGameActivity {

	private static final int CAMERA_WIDTH = 800;
	private static final int CAMERA_HEIGHT = 1280;
	private Camera mCamera;
	private Scene mScene;

	private BitmapTextureAtlas mCopterTextureAtlas;
	private TiledTextureRegion mCopterTextureRegion;
	private AnimatedSprite mCopter;

	private TiledTextureRegion mFaceTextureRegion;
	private AnimatedSprite mFace;

	@Override
	public Engine onLoadEngine() {
		// TODO Auto-generated method stub
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new Engine(new EngineOptions(true, ScreenOrientation.PORTRAIT,
				new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT),
				this.mCamera));
	}

	@Override
	public void onLoadResources() {
		// TODO Auto-generated method stub
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		this.mCopterTextureAtlas = new BitmapTextureAtlas(512, 512,
				TextureOptions.BILINEAR);
		this.mCopterTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.mCopterTextureAtlas, this,
						"helicopter_tiled.png", 0, 0, 2, 2);

		this.mFaceTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.mCopterTextureAtlas, this,
						"face_circle_tiled.png", 200, 0, 2, 1);

		this.mEngine.getTextureManager().loadTextures(this.mCopterTextureAtlas);
	}

	@Override
	public Scene onLoadScene() {
		// TODO Auto-generated method stub
		this.mScene = new Scene();

		this.mCopter = new AnimatedSprite(0, 100, mCopterTextureRegion);
		this.mCopter.animate(new long[] { 100, 100, 100 }, 0, 2, true);
		this.mCopter.setPosition(0, (CAMERA_HEIGHT - mCopter.getHeight()) / 2);
		this.mScene.attachChild(this.mCopter);
		this.mCopter.setScale(2);
		this.mCopter.setFlippedHorizontal(true);

		/*
		 * this.mFace = new AnimatedSprite(0, 0, mFaceTextureRegion);
		 * this.mFace.setPosition((CAMERA_WIDTH - mFace.getWidth())/2,
		 * (CAMERA_HEIGHT- mFace.getHeight())/2);
		 * this.mScene.attachChild(this.mFace); this.mFace.setScale(2);
		 */

		CustomFace cf = new CustomFace(0, 0, mFaceTextureRegion, mScene,
				mCopter);
		cf.setPosition((CAMERA_WIDTH - cf.getWidth()) / 2,
				(CAMERA_HEIGHT - cf.getHeight()) / 2);

		this.mScene.registerUpdateHandler(new IUpdateHandler() {

			float posY = mCopter.getY();
			float posX = mCopter.getX();

			@Override
			public void onUpdate(float pSecondsElapsed) {
				// TODO Auto-generated method stub
				posX = posX + 100 * pSecondsElapsed;
				mCopter.setPosition(posX, posY);
			}

			@Override
			public void reset() {
				// TODO Auto-generated method stub

			}
		});

		return this.mScene;
	}

	@Override
	public void onLoadComplete() {
		// TODO Auto-generated method stub

	}

}
