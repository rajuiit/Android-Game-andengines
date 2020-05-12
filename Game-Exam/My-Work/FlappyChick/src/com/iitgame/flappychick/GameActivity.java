package com.iitgame.flappychick;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.content.Context;


public class GameActivity extends BaseGameActivity {

	public static final int CAMERA_WIDTH = 320;
	public static final int CAMERA_HEIGHT = 480;
	private Camera mCamera;
    private ResourceManager mResourceManager;
    private SceneManager mSceneManager;
	
	@Override
	public Engine onLoadEngine() {
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		EngineOptions opt = new EngineOptions(true, ScreenOrientation.PORTRAIT, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera).setNeedsMusic(true).setNeedsSound(true);
		Engine engine = new Engine(opt);
		
		return engine;
	}

	@Override
	public void onLoadResources() {
		// TODO Auto-generated method stub
		mResourceManager = ResourceManager.getInstance();
        mResourceManager.prepare(this);
        mResourceManager.loadSplashResources();
         
       // mSceneManager = SceneManager.getInstance();
		
	}

	@Override
	public Scene onLoadScene() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onLoadComplete() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onBackPressed() {
	    if (mSceneManager.getCurrentScene() != null) {
	        mSceneManager.getCurrentScene().onBackKeyPressed();
	        return;
	    }
	    super.onBackPressed();
	}
	
	public int getMaxScore() {
	    return getPreferences(Context.MODE_PRIVATE).getInt("maxScore", 0);
	}
	 
	public void setMaxScore(int maxScore) {
	    getPreferences(Context.MODE_PRIVATE).edit().putInt("maxScore", maxScore).commit();
	}
	  

}
