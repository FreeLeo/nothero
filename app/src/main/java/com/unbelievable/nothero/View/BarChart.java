package com.unbelievable.nothero.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.View;

import com.unbelievable.nothero.R;

import java.util.Calendar;
import java.util.List;

/**
 * Created by lizhen on 2016/12/5.
 */

public class BarChart extends View {
    private Context context;
    private int widthSize;
    private int heightSize;
    private List<History> list;
    public BarChart(Context context) {
        super(context);
        this.context = context;
    }
    public BarChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }
    public BarChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        widthSize = MeasureSpec.getSize(widthMeasureSpec);
        heightSize = MeasureSpec.getSize(heightMeasureSpec);
    }

    public BarChart(Context context ,List<History> list){
        super(context);
        this.list = list;
    }

    public void setData(List<History> list){
        this.list = list;
    }

    @SuppressLint("DrawAllocation") @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        int target1 = 0;
        int target2 = 1000;
        for (History history : list) {
            target2 = history.step > target2 ? history.step : target2;
        }
        target2 = (int) Math.ceil(target2 / 1000f) * 1000;
        target1 = target2 / 2;
        float l = widthSize / 26f;
        float r = widthSize - l;
        //compute max height
        float maxBarHeight = target2;

//		//draw text
//		int textHeight = heightSize - 30;
//		Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//		textPaint.setTextSize(AppContext.getContext().getResources().getDimension(R.dimen.text_size_barchart));
//		textPaint.setColor(Color.rgb(0x99, 0x99, 0x99));
//		canvas.drawText("一", (float)widthSize/24f*1.5f, textHeight, textPaint);
//		canvas.drawText("二", (float)widthSize/24f*4.5f, textHeight, textPaint);
//		canvas.drawText("三", (float)widthSize/24f*7.5f, textHeight, textPaint);
//		canvas.drawText("四", (float)widthSize/24f*10.5f, textHeight, textPaint);
//		canvas.drawText("五", (float)widthSize/24f*13.5f, textHeight, textPaint);
//		canvas.drawText("六", (float)widthSize/24f*16.5f, textHeight, textPaint);
//		canvas.drawText("七", (float)widthSize/24f*19.5f, textHeight, textPaint);
//
//		//draw bottom line
//		Paint bottomLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//		bottomLinePaint.setColor(Color.rgb(0x99, 0x99, 0x99));
//		bottomLinePaint.setStrokeWidth(10);
//		 Path path = new Path();
//	     path.addCircle(5, 0, 3, Direction.CCW);
//	     path.moveTo(0, heightSize - rect_top);
//	     path.lineTo(widthSize, heightSize - rect_top);
//	     PathEffect pathEffect = new PathDashPathEffect(path, 20, 0, PathDashPathEffect.Style.ROTATE);
//	     bottomLinePaint.setPathEffect(pathEffect);
//	     canvas.drawPath(path, bottomLinePaint);
        int rect_top = (int) (heightSize * 0.2);

        Paint bottomRectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bottomRectPaint.setStyle(Paint.Style.FILL);
        bottomRectPaint.setColor(Color.rgb(0xf0, 0xf0, 0xf0));
        canvas.drawRect(0, heightSize - rect_top, widthSize, heightSize, bottomRectPaint);

        //target line
        Paint targetLineP = new Paint(Paint.ANTI_ALIAS_FLAG);
        targetLineP.setColor(Color.rgb(0xdf, 0xdf, 0xdf));
        targetLineP.setStrokeWidth(1);
        Paint targetLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        targetLinePaint.setColor(Color.rgb(0x99, 0x99, 0x99));
        targetLinePaint.setStrokeWidth(1);
        targetLinePaint.setTextSize(context.getResources().getDimension(R.dimen.text_size_barchat_bottom));
        targetLinePaint.setTextAlign(Paint.Align.RIGHT);
        float startX = l;
        float startY = heightSize - (target1 / (float) maxBarHeight) * (heightSize - rect_top) - rect_top;
        float stopX = r;
        float stopY = startY;
        Path path = new Path();
        path.addCircle(1, 0, 2, Path.Direction.CCW);
        PathEffect pathEffect = new PathDashPathEffect(path, 10, 0, PathDashPathEffect.Style.ROTATE);
        targetLineP.setPathEffect(pathEffect);
        path.moveTo(startX, startY);
        path.lineTo(stopX, stopY);
        canvas.drawPath(path, targetLineP);
//			canvas.drawLine(startX, startY, stopX, stopY, targetLineP);
        canvas.drawText(target1 + "", stopX, stopY + 35, targetLinePaint);
        startY = heightSize - (target2 / (float) maxBarHeight) * (heightSize - rect_top) - rect_top + 10;
        stopY = startY;
        path.moveTo(startX, startY);
        path.lineTo(stopX, stopY);
        canvas.drawPath(path, targetLineP);
//			canvas.drawLine(startX, startY, stopX, stopY, targetLineP);
        canvas.drawText(target2 + "", stopX, stopY + 35, targetLinePaint);

        targetLinePaint.setColor(Color.rgb(0x99, 0x99, 0x99));
        targetLinePaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("00:00", l * 0.8f, heightSize * 0.95f, targetLinePaint);
        canvas.drawText("06:00", (float) widthSize / 26f * (6 + 0.1f), heightSize * 0.95f, targetLinePaint);
        canvas.drawText("12:00", (float) widthSize / 26f * (12 + 0.1f), heightSize * 0.95f, targetLinePaint);
        canvas.drawText("18:00", (float) widthSize / 26f * (18 + 0.1f), heightSize * 0.95f, targetLinePaint);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sport_dayarrow);
        canvas.drawBitmap(bitmap, l + widthSize / 26f * 0.1f, heightSize * 0.83f, null);
        canvas.drawBitmap(bitmap, (float) widthSize / 26f * (7 + 0.1f), heightSize * 0.83f, null);
        canvas.drawBitmap(bitmap, (float) widthSize / 26f * (13 + 0.1f), heightSize * 0.83f, null);
        canvas.drawBitmap(bitmap, (float) widthSize / 26f * (19 + 0.1f), heightSize * 0.83f, null);

        //bar
        Paint rectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        rectPaint.setStyle(Paint.Style.FILL);
        int i = 0;
        while (i < 24) {
            float step = list.get(23 - i).step > maxBarHeight ? maxBarHeight : list.get(23 - i).step;
            float rectHeight = (step / (float) maxBarHeight) * (heightSize - rect_top);
            if (rectHeight < 0.03f * heightSize) {
                if (rectHeight > 0) {
                    rectHeight = 0.04f * heightSize;
                    rectPaint.setColor(Color.rgb(0x8f, 0xc7, 0x17));
                } else {
                    rectHeight = 0.03f * heightSize;
                    rectPaint.setColor(Color.rgb(0xdf, 0xdf, 0xdf));
                }
            } else {
//	    		 if(step >= 1000){
                rectPaint.setColor(Color.rgb(0x8f, 0xc7, 0x17));
//	    		 }else{
//	    			 rectPaint.setColor(Color.rgb(0xe6, 0x4a, 0x67));
//	    		 }
            }
            canvas.drawRect((float) widthSize / 26f * (i + 1 + 0.1f), heightSize - rectHeight - rect_top, (float) widthSize / 26f * (i + 2 - 0.1f), heightSize - rect_top, rectPaint);
            i++;
        }
    }

    public static class History{
        public String stamp;
        public int step;

        public History(String stamp,int step){
            this.stamp = stamp;
            this.step = step;
        }

    }
}

