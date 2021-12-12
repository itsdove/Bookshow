package com.example.bookshow.game;

import static java.lang.Math.random;

import android.content.ContentProvider;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.bookshow.R;

public class CirleSpriter {
    float x,y,radius;
    double direction;
    float maxWidth,maxHeight;

    public CirleSpriter(float x,float y, float radius,float maxWidth,float maxHeight)
    {
        this.x=x;
        this.y=y;
        this.radius=radius;
        this.direction=Math.random();
        this.maxHeight=maxHeight;
        this.maxWidth=maxWidth;
    }
    public void draw(Canvas canvas, Context c)
    {

        Bitmap bitmap = BitmapFactory.decodeResource(c.getResources(), R.drawable.game2);
        Rect rect = new Rect((int) x, (int)y,(int)x+150,(int)y+150);   //w和h分别是屏幕的宽和高，也就是你想让图片显示的宽和高
        canvas.drawBitmap(bitmap,null, rect, null);

    }
    public void move()
    {

            x+=150;
            if(x>maxWidth-100)
            {x=100;
                this.y+=300;}
            if(y>1000)
                y=400;
    }

    public boolean isShot(float touchedX, float touchedY) {
        double distance=(touchedX-this.x)*(touchedX-this.x)+(touchedY-this.y)*(touchedY-this.y);
        return distance<radius*radius;
    }
}