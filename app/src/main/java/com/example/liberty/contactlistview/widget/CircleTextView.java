package com.example.liberty.contactlistview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.example.liberty.contactlistview.R;

/**
 * Created by LinJinFeng on 2016/9/29.
 */

public class CircleTextView extends View {

    private Paint circlePaint,circleTextPaint;
    private float textHeight;
    private String text;
    private float radius;

    public CircleTextView(Context context) {
        this(context,null);
    }

    public CircleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView(){
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
        Paint.FontMetrics metrics=circleTextPaint.getFontMetrics();
        textHeight=metrics.bottom-metrics.top;
        radius=TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,100,getResources().getDisplayMetrics())/2;
    }

    public void setText(String text) {
        this.text = text;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension((int)radius*2,(int) radius*2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (text!=null){
            canvas.drawCircle(radius,radius,radius,circlePaint);
            canvas.drawText(text,radius,radius+textHeight/4,circleTextPaint);
        }
    }
}
