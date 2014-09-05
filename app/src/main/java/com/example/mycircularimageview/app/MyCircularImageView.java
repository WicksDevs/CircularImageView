package com.example.mycircularimageview.app;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by lhernandez on 31/05/14.
 */
public class MyCircularImageView extends ImageView {

    private int borderWidth;
    private int color;
    private Paint border;

    public MyCircularImageView(Context context) {
        this(context, null);
    }

    public MyCircularImageView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.circularImageViewStyle);
    }

    public MyCircularImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs,defStyle);
    }

    private void init(AttributeSet attrs,int defStyle) {
        TypedArray attributes =
                getContext().obtainStyledAttributes(
                        attrs,
                        R.styleable.MyCircularImageView,
                        defStyle,
                        0
                );
        border = new Paint();
        borderWidth =
                attributes.getDimensionPixelOffset(R.styleable.MyCircularImageView_border_width, 10);;
        border.setAntiAlias(true);
        color = attributes.getColor(R.styleable.MyCircularImageView_border_color, Color.BLACK);
        border.setColor(color);
    }



    @Override
    public void onDraw(Canvas canvas) {
        int radius = this.getWidth()/2;
        canvas.drawCircle(this.getWidth()/2,this.getWidth()/2,radius,border);
        canvas.drawBitmap(getBitmapFromDrawable(getDrawable(),radius*2),0,0,null);
    }


    public Bitmap getBitmapFromDrawable(Drawable drawable,int diameter) {
        Bitmap original = ((BitmapDrawable) drawable).getBitmap();
        float lower = Math.min(original.getWidth(), original.getHeight());
        float factor = lower / diameter;
        Bitmap newBItmap = Bitmap.createScaledBitmap(
                original,
                (int) (original.getWidth()/factor),
                (int) (original.getHeight()/factor),
                false
        );
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap output = Bitmap.createBitmap(diameter, diameter,Bitmap.Config.ARGB_8888);
        final Rect rect = new Rect(0, 0, diameter, diameter);
        Canvas canvas = new Canvas(output);
        canvas.drawCircle(diameter / 2,diameter / 2, diameter / 2 - borderWidth, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(newBItmap,rect,rect,paint);
        return output;
    }

    public int getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
