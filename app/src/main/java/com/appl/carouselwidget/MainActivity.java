package com.appl.carouselwidget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.*;

import com.appl.library.CoverFlowCarousel;


public class MainActivity extends AppCompatActivity {

    CoverFlowCarousel carousel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        carousel = (CoverFlowCarousel)findViewById(R.id.carousel);

        WindowManager wm= getWindowManager();

        DisplayMetrics dm=new DisplayMetrics();
         wm.getDefaultDisplay().getMetrics(dm);
        int widthPixels= (int)dm.widthPixels;//(int)dm.xdpi;
        int heightPixels=dm.heightPixels;//(int)dm.ydpi;//
        int destDpi=dm.densityDpi;
        float dest=dm.density;

        int dpWidth=widthPixels/(int)dest;
        int dpHeight=heightPixels/(int)dest;

        Toast.makeText(this,"width(pix):"+dpWidth+", height(pix):"+dpHeight+", densitydpi:"+destDpi+",desity:"+dest,Toast.LENGTH_LONG).show();


        final MyAdapter adapter = new MyAdapter();
        carousel.setAdapter(adapter);
        carousel.setSelection(adapter.getCount() / 2);//设置默认选择的视图索引
        carousel.setSlowDownCoefficient(3); //数字越大,滚动速度越慢
        carousel.setSpacing(0.5f);//设置每个视图之间的间距,数值越小,被中间视图遮挡的部分越多
        carousel.setRotationThreshold(5f);//设置除中间试图外的其他视图的透视比例,数字越大,靠近中间的边越高,原理中间的边越小,数字越小,靠近中间的边越小,远离中间的边越大.
        carousel.shouldRepeat(true); //是否循环显示视图,如果否,则滑动到最左侧的视图就只能再往回滑动 //When not using repeat, I suggest replacing getCount() below = mCount along with getItem = mResourceIds[position % mResourceIds.length].


        Configuration mconfig=getResources().getConfiguration();
        int ori=mconfig.orientation;
        if(ori==mconfig.ORIENTATION_LANDSCAPE){
            //横屏
            carousel.setChildHeight((heightPixels*11/20));
            //carousel.setChildWidth(widthPixels*11/20);
        }else{
            //竖屏
            carousel.setChildHeight((heightPixels*11/20));
            carousel.setChildWidth(widthPixels*11/20);
        }



        //Pointless to add a view when we are repeating.
        Button addButton = (Button)findViewById(R.id.add_botton);
        if (!carousel.isRepeating()) {
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapter.addView();
                }
            });
        }
        else {
            addButton.setVisibility(View.GONE);
        }
    }


    private class MyAdapter extends BaseAdapter {
        private int[] mResourceIds = {R.drawable.poster1, R.drawable.poster2, R.drawable.poster3, R.drawable.poster4,
            R.drawable.poster5};

        private int mCount = mResourceIds.length * 5;

        @Override
        public int getCount() {
            return mResourceIds.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            MyFrame v;
            if (convertView == null) {
                v = new MyFrame(MainActivity.this);
            } else {
                v = (MyFrame)convertView;
            }

            v.setImageResource(mResourceIds[position % mResourceIds.length]);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //carousel.scrollToItemPosition(position);
                    Toast.makeText(MainActivity.this, "clicked position:"+position,Toast.LENGTH_SHORT).show();
                }
            });


            return v;
        }

        public void addView(){
            mCount++;
            notifyDataSetChanged();
        }
    }

    public static class MyFrame extends FrameLayout {
        private ImageView mImageView;
        private View mContentView;
        protected boolean mHasReflection = true;
        private static int REFHEIGHT = -1;
        public static Paint RefPaint = null;

        private Bitmap mReflectBitmap;
        private Canvas mReflectCanvas;

        public void setImageResource(int resId){
            mImageView.setImageResource(resId);
        }

        public MyFrame(Context context) {
            super(context);

            mImageView = new ImageView(context);
            mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
           // addView(mImageView);
            setContentView(mImageView);

           // setBackgroundColor(Color.TRANSPARENT);
            setBackgroundColor(Color.WHITE);
            setSelected(false);

//            if (REFHEIGHT == -1)
//                REFHEIGHT = 100;
//            if (RefPaint == null) {
//                RefPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//                RefPaint.setShader(new LinearGradient(0, 0, 0, REFHEIGHT, new int[] { 0x77000000, 0x66AAAAAA, 0x0500000, 0x00000000 }, new float[] { 0.0f, 0.1f, 0.9f, 1.0f }, Shader.TileMode.CLAMP));
//                RefPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
//            }
        }

        public void setContentView(View view) {
//            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//            lp.bottomMargin = REFHEIGHT;
            mContentView = view;
          //  addView(view, lp);
            addView(mImageView);
        }
        public View getContentView() {
            return mContentView;
        }

        public void setReflection(boolean ref) {
            mHasReflection = ref;
        }
        @Override
        public void setSelected(boolean selected) {
            super.setSelected(selected);

            if(selected) {
                mImageView.setAlpha(1.0f);
            } else {
                mImageView.setAlpha(0.5f);
            }
        }
        public static Bitmap convertViewToBitmap(View view)
        {
            view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
            view.buildDrawingCache();
            Bitmap bitmap = view.getDrawingCache();
            return bitmap;
        }



    }
}
