package com.iitgame.flappychick.scene;

import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.util.HorizontalAlign;

import com.iitgame.flappychick.SceneManager.SceneType;

public class SplashScene extends BaseScene {
    
    @Override
    public void createScene() {
 
        Sprite splash = new Sprite(0, 0, mResourceManager.mSplashTextureRegion, mVertexBufferObjectManager) {
            @Override
            protected void preDraw(GLState pGLState, Camera pCamera)
            {
               super.preDraw(pGLState, pCamera);
               pGLState.enableDither();
            }
        };
        attachChild(splash);
         
        Text copyrightText = new Text(0, 0, mResourceManager.mFont1, "(c) 2011-2014", new TextOptions(HorizontalAlign.LEFT), mVertexBufferObjectManager);
        copyrightText.setPosition(SCREEN_WIDTH - copyrightText.getWidth()-5, SCREEN_HEIGHT - copyrightText.getHeight()-5);
        attachChild(copyrightText);
    }
     
    @Override
    public void onBackKeyPressed() {
        mActivity.finish();
    }
 
    @Override
    public SceneType getSceneType() {
        return SceneType.SCENE_SPLASH;
    }
 
    @Override
    public void disposeScene() {
    }
 
}