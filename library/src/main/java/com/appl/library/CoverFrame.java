package com.appl.library;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by rock_ on 2017/3/3.
 */

public class  CoverFrame extends FrameLayout {

    private float saturation;

    private boolean isReflectionEnabled = false;

    private float imageReflectionRatio;

    private int reflectionGap;

    private float originalScaledownFactor;

    /**
     * This is a matrix to apply color filters (like saturation) to the wrapped view.
     */
    private ColorMatrix colorMatrix;

    /**
     * This paint is used to draw the wrapped view including any filters.
     */
    private Paint paint;

    /**
     * This is a cache holding the wrapped view's visual representation.
     */
    private Bitmap wrappedViewBitmap;

    /**
     * This canvas is used to let the wrapped view draw it's content.
     */
    private Canvas wrappedViewDrawingCanvas;

    public CoverFrame(Context context, View cover) {
        super(context);
        setCover(cover);

    }

    public void setCover(View cover){
        removeAllViews();
        this.paint = new Paint();
        this.colorMatrix = new ColorMatrix();


        //mReflectionCacheInvalid = true; //todo uncomment after adding support for reflection
        if(cover.getLayoutParams() != null) setLayoutParams(cover.getLayoutParams());



        final FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        lp.leftMargin = 1;
        lp.topMargin = 1;
        lp.rightMargin = 1;
        lp.bottomMargin = 1;

        if (cover.getParent()!=null && cover.getParent() instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup) cover.getParent();
            parent.removeView(cover);
        }

        addView(cover, lp);
    }





//    @Override
//    public Bitmap getDrawingCache(boolean autoScale) {
//        final Bitmap b = super.getDrawingCache(autoScale);
//
//        if(mReflectionCacheInvalid){
//            if (/*(mTouchState != TOUCH_STATE_FLING && mTouchState != TOUCH_STATE_ALIGN) ||*/ mReflectionCache == null){
//                try{
//                    mReflectionCache = createReflectionBitmap(b);
//                    mReflectionCacheInvalid = false;
//                }
//                catch (NullPointerException e){
//                    Log.e(VIEW_LOG_TAG, "Null pointer in createReflectionBitmap. Bitmap b=" + b, e);
//                }
//            }
//        }
//        return b;
//    }

//    public void recycle(){ //todo add puttocache method and call recycle
//        if(mReflectionCache != null){
//            mReflectionCache.recycle();
//            mReflectionCache = null;
//        }
//        mReflectionCacheInvalid = true;
//
//        //removeAllViewsInLayout();
//    }

}