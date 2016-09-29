package com.example.liberty.contactlistview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.example.liberty.contactlistview.R;

/**
 * Created by Liberty on 2016/9/28.
 */

public class PinYinSlideView extends View implements View.OnTouchListener{

    private Paint textPaint;
    private Paint backgroundPaint;
    private Paint circlePaint,circleTextPaint;
    private int height;
    private float textHeight;
    private float paddingHeight;
    private float radius;
    private float backgroundSize;
    private boolean hasTouch;
    private float lastY,lastX;
    private float screenX,screenY;
    private OnShowTextListener onShowTextListener;

    public PinYinSlideView(Context context) {
        this(context,null);
    }

    public PinYinSlideView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public void setOnShowTextListener(OnShowTextListener onShowTextListener) {
        this.onShowTextListener = onShowTextListener;
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
        this.setOnTouchListener(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        height=MeasureSpec.getSize(heightMeasureSpec);
        paddingHeight=(height-28*textHeight)/29;
        screenX=getResources().getDisplayMetrics().widthPixels/2;
        screenY=height/2;
        int mode=MeasureSpec.getMode(widthMeasureSpec);
        float size = 0;
        switch (mode){
            case MeasureSpec.EXACTLY:
                size=MeasureSpec.getSize(widthMeasureSpec);
                break;
            case MeasureSpec.AT_MOST:
                size=backgroundSize;
                break;
        }
        setMeasuredDimension((int) size,height);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastX=event.getX();
                lastY=event.getY();
                if (lastX>0&&lastX<=backgroundSize){
                    hasTouch=true;
                    invalidate();
                    requestLayout();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                lastX=event.getX();
                lastY=event.getY();
                if (lastX>0&&lastX<=backgroundSize){
                    hasTouch=true;
                    invalidate();
                    requestLayout();
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

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        float x=0;
//        switch (event.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                lastX=event.getX();
//                lastY=event.getY();
//                if (lastX>=0&&lastX<=backgroundSize){
//                    hasTouch=true;
//                    invalidate();
//                    requestLayout();
//                }
//                break;
//            case MotionEvent.ACTION_MOVE:
//                lastX=event.getX();
//                lastY=event.getY();
//                if (lastX>=0&&lastX<=backgroundSize){
//                    hasTouch=true;
//                    invalidate();
//                    requestLayout();
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//                if (hasTouch){
//                    hasTouch=false;
//                    invalidate();
//                }
//                break;
//        }
//        return true;
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        char c[]={'↑','A','#'};
        float baseY=textHeight;
        float baseX=(0+backgroundSize)/2;
        float radius1=TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,100,getResources().getDisplayMetrics())/2;
        if (hasTouch){
            char c1[]={'↑','A','#'};
                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
                    canvas.drawRoundRect(0,0,backgroundSize,height,radius,radius,backgroundPaint);
                }else {
                    canvas.drawRect(0,0,backgroundSize,height,backgroundPaint);
                }
            float offsetY=textHeight+paddingHeight;
            for (int i=0;i<28;i++){
                if (lastY>=(i*offsetY)&&lastY<=((i+1)*offsetY)){
                    canvas.drawCircle(screenX,screenY,radius1,circlePaint);
                    if (i==0){
                        if (onShowTextListener!=null){
                            onShowTextListener.showText(String.valueOf(c1[0]));
                        }
                        //canvas.drawText(c1,0,1,screenX,screenY+textHeight,circleTextPaint);
                    }else if (i>0&&i<27){
                        if (onShowTextListener!=null){
                            onShowTextListener.showText(String.valueOf(c1[1]));
                        }
                        //canvas.drawText(c1,1,1,screenX,screenY+textHeight,circleTextPaint);
                    }else if (i==27){
                        if (onShowTextListener!=null){
                            onShowTextListener.showText(String.valueOf(c1[2]));
                        }
                        //canvas.drawText(c1,2,1,screenX,screenY+textHeight,circleTextPaint);
                    }
                    break;
                    //canvas.drawText(c1,0,1,screenX,screenY+textHeight,circleTextPaint);
                }
                if (i>0&&i<27){
                    c1[1]++;
                }
            }
        }
        else {
            if (onShowTextListener!=null){
                onShowTextListener.showText(null);
            }
        }

        for (int i=0;i<28;i++){
            if (i==0){
                canvas.drawText(c,0,1,baseX,baseY,textPaint);
            }else if (i>0&&i<27){
                canvas.drawText(c,1,1,baseX,baseY,textPaint);
                c[1]++;
            }else if (i==27){
                canvas.drawText(c,2,1,baseX,baseY,textPaint);
            }
            baseY+=(paddingHeight+textHeight);
        }
    }

    public interface OnShowTextListener{
        void showText(String text);
    }

}
