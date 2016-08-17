package com.fxly.reboot.view;

/**
 * Created by Lambert Liu on 2016-08-16.
 */
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class MGView extends View {
    private Context mContext = getContext();
    private int height = 0;
    private int width = 0;
    private int x , y = 0;
    private int round = 0;
    private int ovalRoind = 0;
    private int mProgress  = 0;
    private int mTotalProgress = 100;
    private Paint solidRound = null;
    private Paint mRingPaint = null;
    private Paint percentPaint = null;
    private int line = 0;
    RectF oval  = null;
    private int m = -90;
    public MGView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public MGView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public MGView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub
        this.mContext = context;
        init();
    }

    public void init(){
        oval = new RectF();
        solidRound = new Paint();
        solidRound.setColor(0xffffffff);
        solidRound.setStyle(Style.FILL);
        solidRound.setDither(true);
        solidRound.setAntiAlias(true);

        mRingPaint = new Paint();
        mRingPaint.setAntiAlias(true);
        mRingPaint.setColor(0xffffffff);
        mRingPaint.setStyle(Paint.Style.STROKE);
        mRingPaint.setStrokeWidth(6.5f);


        percentPaint = new Paint();
        percentPaint.setAntiAlias(true);
        percentPaint.setColor(0xffffffff);
        percentPaint.setStrokeWidth(4.0f);

        new Thread(new ProgressRunable()).start();


    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        // TODO Auto-generated method stub
        super.onLayout(changed, left, top, right, bottom);
        height = getHeight();
        width = getWidth();
        x = width/2;
        y = height /2;
        line = 40;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        canvas.drawCircle(x, y, round, solidRound);
        oval.set((x - ovalRoind), (y - ovalRoind), ovalRoind * 2 + (x - ovalRoind), ovalRoind * 2 + (y - ovalRoind));
        canvas.drawArc(oval, m, ((float)mProgress / mTotalProgress) * 360, false, mRingPaint); //
        float degrees = (float) (360.0/12);
        canvas.translate(0,x);
        canvas.rotate(-70, y, 0);
        for(int i = 0;i<12;i++){
            canvas.drawLine(0, 0, line, 0, percentPaint);   //
            canvas.rotate(degrees, x, 0);
        }

    }


    public void setProgress(int i ){
        this.mProgress = i;
        postInvalidate();
    }



    class ProgressRunable implements Runnable {

        @Override
        public void run() {
            while (true) {

                round =0;
                line = 0;
                ovalRoind = 0;
                mProgress = 0;
                for (int i = -100; i < 90; i++) {
                    try {
                        if(i>=0){
                            round = x/3;
                            ovalRoind = round +18;
                            if(0<=(int) (40-i*0.5)){
                                line = (int) (40-i*0.5);
                            }
                            setProgress(i);
                        }else{
                            if(round<(x/3)){
                                if((x/3)>((i/3) + (x/3))){
                                    round =(i/(300/x)) + (x/3);
                                }
                                postInvalidate();
                            }else{
                                continue;
                            }


                        }
                        Thread.sleep(10);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                m =m+30;
                postInvalidate();
            }
        }

    }
}

