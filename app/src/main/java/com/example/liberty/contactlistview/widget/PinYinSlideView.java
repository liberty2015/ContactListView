package com.example.liberty.contactlistview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.example.liberty.contactlistview.R;

/**
 * Created by Liberty on 2016/9/28.
 */

public class PinYinSlideView extends View {

    private Paint textPaint;
    private Paint backgroundPaint;
    private Paint circlePaint,circleTextPaint;
    private Context mContext;
    private int height;
    private float textHeight;
    private float paddingHeight;
    private float radius;
    private float backgroundSize;
    private boolean hasTouch;
    private float lastY;
    private float screenX,screenY;

    public PinYinSlideView(Context context) {
        this(context,null);
    }

    public PinYinSlideView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        initView();
    }

    private void initView(){
        textPaint=new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(getResources().getColor(R.color.txtGray));
        float textSize= TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,10,getResources().getDisplayMetrics());
        textPaint.setTextSize(textSize);
        textPaint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics metrics=textPaint.getFontMetrics();
        textHeight=metrics.bottom-metrics.top;
        backgroundPaint=new Paint();
        backgroundPaint.setAntiAlias(true);
        backgroundPaint.setStyle(Paint.Style.FILL);
        backgroundPaint.setColor(getResources().getColor(R.color.backgroundGray));
        backgroundSize=TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,30,getResources().getDisplayMetrics());
        radius=TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,3,getResources().getDisplayMetrics());

        circlePaint=new Paint();
        circleTextPaint=new Paint();
        circlePaint.setAntiAlias(true);
        circleTextPaint.setAntiAlias(true);
        circlePaint.setColor(getResources().getColor(R.color.backgroundGray));
        circleTextPaint.setColor(getResources().getColor(R.color.txtGray));
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,100,getResources().getDisplayMetrics()));
        circleTextPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,30,getResources().getDisplayMetrics()));
        circleTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        height=MeasureSpec.getSize(heightMeasureSpec);
        paddingHeight=(height-26*textHeight)/27;
        screenX=getResources().getDisplayMetrics().widthPixels/2;
        screenY=height/2;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                float x=event.getX();
                lastY=event.getY();
                if (x>=0&&x<=backgroundSize){
                    hasTouch=true;
                    invalidate();
                }

                break;
            case MotionEvent.ACTION_UP:
                if (hasTouch){
                    hasTouch=false;
                    invalidate();
                }
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        char c[]={'A'};
        float baseY=textHeight;
        float baseX=(0+backgroundSize)/2;
        float radius1=TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,100,getResources().getDisplayMetrics())/2;
        if (hasTouch){
            char c1[]={'A'};
            canvas.drawRoundRect(0,0,backgroundSize,height,radius,radius,backgroundPaint);
            float offsetY=textHeight+paddingHeight;
            for (int i=0;i<26;i++){
                if (lastY>=(i*offsetY)&&lastY<=((i+1)*offsetY)){
                    canvas.drawCircle(screenX,screenY,radius1,circlePaint);
                    canvas.drawText(c1,0,1,screenX,screenY,circleTextPaint);
                }
                c1[0]++;
            }
        }
        for (int i=0;i<26;i++){
            canvas.drawText(c,0,1,baseX,baseY,textPaint);
            c[0]++;
            baseY+=(paddingHeight+textHeight);
//            canvas.drawText(c,0,0,textPaint);
        }


    }
}
