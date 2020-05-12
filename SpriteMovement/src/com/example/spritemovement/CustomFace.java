package com.example.spritemovement;

import org.anddev.andengine.engine.handler.IUpdateHandler;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

public class CustomFace extends AnimatedSprite {

	public CustomFace(float pX, float pY, TiledTextureRegion pTiledTextureRegion,
			final Scene scene, final AnimatedSprite heli) {
		super(pX, pY, pTiledTextureRegion);
		// TODO Auto-generated constructor stub
		
		scene.registerUpdateHandler(new IUpdateHandler(){

			@Override
			public void onUpdate(float pSecondsElapsed) {
				// TODO Auto-generated method stub
				if(CustomFace.this.collidesWith(heli))
				{
					scene.detachChild(CustomFace.this);
				}
			}

			@Override
			public void reset() {
				// TODO Auto-generated method stub
				
			}});
		
		scene.attachChild(CustomFace.this);
	}

}
