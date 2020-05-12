package com.iitgame.flappychick;

import java.io.IOException;

import org.anddev.andengine.audio.music.Music;
import org.anddev.andengine.audio.music.MusicFactory;
import org.anddev.andengine.audio.sound.Sound;
import org.anddev.andengine.audio.sound.SoundFactory;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.font.FontFactory;
import org.anddev.andengine.opengl.texture.ITexture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.util.Debug;

import android.graphics.Color;


public class ResourceManager {
	
	private static final ResourceManager INSTANCE = new ResourceManager();
    
    public GameActivity mActivity;
    private BitmapTextureAtlas mSplashTextureAtlas;
    public TextureRegion mSplashTextureRegion;
    
    private BitmapTextureAtlas mAutoParallaxBackgroundTexture;
    public TextureRegion mParallaxLayerBack;
    public TextureRegion mParallaxLayerFront;
     
    private BitmapTextureAtlas mBitmapTextureAtlas;
    public TiledTextureRegion mBirdTextureRegion;
    public TiledTextureRegion mPipeTextureRegion;
     
    private BitmapTextureAtlas mSubBitmapTextureAtlas;
    public TiledTextureRegion mStateTextureRegion;
    public TextureRegion mPausedTextureRegion;
    public TextureRegion mResumedTextureRegion;
    public TiledTextureRegion mButtonTextureRegion;
    public TiledTextureRegion mMedalTextureRegion;
    
    private BitmapTextureAtlas mFontAtlas;
	private TextureRegion mFontTextureRegion;
	private Font mFont;

	public Sound mSound;
	public Music mMusic; 
	
	public Font mFont1;
	public Font mFont2;
	public Font mFont3;
	public Font mFont4;
	public Font mFont5;
     
    private ResourceManager() {}
     
    public static ResourceManager getInstance() {
        return INSTANCE;
    }
     
    public void prepare(GameActivity activity) {
        INSTANCE.mActivity = activity;
    }
     
    public void loadSplashResources() {
        //TODO implement
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/splash/");
        mSplashTextureAtlas = new BitmapTextureAtlas(512, 512, TextureOptions.BILINEAR);
        mSplashTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mSplashTextureAtlas, mActivity, "logo.png", 0, 0);
        //mSplashTextureAtlas.load(); 
        
        FontFactory.setAssetBasePath("font/");
		
     //   mFont = FontFactory.create(mActivity.getFontManager(), mActivity.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.NORMAL), 10, Color.GRAY);
        //mFont.load(); 
        
      
        
    }
     
    public void unloadSplashResources() {
        //TODO implement
    	 //mSplashTextureAtlas.unload();
    	 mSplashTextureRegion = null;
    }
     
    public void loadGameResources() {
        //TODO implement
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/game/");
        
        mAutoParallaxBackgroundTexture = new BitmapTextureAtlas(512, 1024);
        mParallaxLayerFront = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mAutoParallaxBackgroundTexture, mActivity, "Flappy_Ground.png", 0, 0);
        mParallaxLayerBack = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mAutoParallaxBackgroundTexture, mActivity, "Flappy_Background.png", 0, 150);
       // mAutoParallaxBackgroundTexture.load();
         
        mBitmapTextureAtlas = new BitmapTextureAtlas(128, 512, TextureOptions.BILINEAR);
        mBirdTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlas, mActivity, "Flappy_Birdies.png", 0, 0, 1, 3);
        mPipeTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlas, mActivity, "Flappy_Pipe.png", 0, 125, 2, 1);
     //   mBitmapTextureAtlas.load();
         
        mSubBitmapTextureAtlas = new BitmapTextureAtlas(512, 512, TextureOptions.BILINEAR);
        mStateTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mSubBitmapTextureAtlas, mActivity, "ready_over.png", 0, 0, 2, 1);
        mPausedTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mSubBitmapTextureAtlas, mActivity, "board.png", 0, 60);
        mResumedTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mSubBitmapTextureAtlas, mActivity, "help.png", 0, 200);
        mButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mSubBitmapTextureAtlas, mActivity, "play_pos.png", 0, 350, 2, 1);
        mMedalTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mSubBitmapTextureAtlas, mActivity, "medal.png", 0, 450, 4, 1);
      //  mSubBitmapTextureAtlas.load();
        
        /*
        mFont4 = FontFactory.create(mActivity.getFontManager(), mActivity.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.NORMAL), 16, Color.BLACK);
        mFont4.load();     
         
        FontFactory.setAssetBasePath("font/");
        ITexture fontTexture2 = new BitmapTextureAtlas(mActivity.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
        mFont2 = FontFactory.createStrokeFromAsset(mActivity.getFontManager(), fontTexture2, mActivity.getAssets(), "Font1.ttf", 40, true, Color.YELLOW, 2, Color.DKGRAY);
        mFont2.load();
         
        ITexture fontTexture3 = new BitmapTextureAtlas(mActivity.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
        mFont3 = FontFactory.createFromAsset(mActivity.getFontManager(), fontTexture3, mActivity.getAssets(), "Font2.ttf", 24, true, Color.WHITE);
        mFont3.load();
         
        ITexture fontTexture5 = new BitmapTextureAtlas(mActivity.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
        mFont5 = FontFactory.createStrokeFromAsset(mActivity.getFontManager(), fontTexture5, mActivity.getAssets(), "Font1.ttf", 36, true, Color.WHITE, 2, Color.DKGRAY);
        mFont5.load(); 
        
        SoundFactory.setAssetBasePath("mfx/");
        try {
            mSound = SoundFactory.createSoundFromAsset(mActivity.getEngine().getSoundManager(), mActivity, "metal_hit.ogg");
        } catch (final IOException e) {
            Debug.e(e);
        }*/
         
        MusicFactory.setAssetBasePath("mfx/");
        try {
            mMusic = MusicFactory.createMusicFromAsset(mActivity.getEngine().getMusicManager(), mActivity, "bird_sound.ogg");
            mMusic.setLooping(true);
        } catch (final IOException e) {
            Debug.e(e);
        }
    }
     
    public void unloadGameResources() {
        //TODO implement
    	//mAutoParallaxBackgroundTexture.unload();
      //  mBitmapTextureAtlas.unload();
       // mSubBitmapTextureAtlas.unload();
         
        mAutoParallaxBackgroundTexture = null;
        mParallaxLayerFront = null;
        mParallaxLayerBack = null;
         
        mBitmapTextureAtlas = null;
        mBirdTextureRegion = null;
        mPipeTextureRegion = null;
         
        mSubBitmapTextureAtlas = null;
        mStateTextureRegion = null;
        mPausedTextureRegion = null;
        mResumedTextureRegion = null;
        mButtonTextureRegion = null;
        mMedalTextureRegion = null;
        
        
       // mFont4.unload();
        mFont4 = null;     
         
       // mFont2.unload();
        mFont2 = null;
         
       // mFont3.unload();
        mFont3 = null;
         
      //  mFont5.unload();
        mFont5 = null; 
        
        
        mSound.release();
        mSound = null;
         
        mMusic.stop();
        mMusic.release();
        mMusic = null;
    }    

}
