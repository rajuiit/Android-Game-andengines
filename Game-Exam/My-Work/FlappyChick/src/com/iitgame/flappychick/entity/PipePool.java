package com.iitgame.flappychick.entity;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.util.MathUtils;
import org.anddev.andengine.util.pool.GenericPool;

public class PipePool extends GenericPool<Pipe> {
    
    private TiledTextureRegion mPipeTextureRegion;
    private VertexBufferObjectManager mVertexBufferObjectManager;
    private float mGroundY;
    private int mPipeIndex;
     
    public PipePool(TiledTextureRegion pPipeTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, float pGroundY) {
        super();
        this.mPipeTextureRegion = pPipeTextureRegion;
        this.mVertexBufferObjectManager = pVertexBufferObjectManager;
        this.mGroundY = pGroundY;
    }
 
    @Override
    protected Pipe onAllocatePoolItem() {
        return new Pipe(mPipeTextureRegion, mVertexBufferObjectManager, mGroundY, 50*MathUtils.random(-2, +2));
    }
 
    @Override
    protected void onHandleRecycleItem(Pipe pItem) {
//        pItem.setIgnoreUpdate(true);
//        pItem.setVisible(false);
    }
 
    @Override
    protected void onHandleObtainItem(Pipe pItem) {
        pItem.reset();
    }
 
    @Override
    public synchronized Pipe obtainPoolItem() {
        mPipeIndex++;
        return super.obtainPoolItem();
    }
 
    public int getPipeIndex() {
        return mPipeIndex;
    }
 
}
  
