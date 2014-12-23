
package com.slodopamin.jump.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import com.slodopamin.jump.math.Vector2f;
import com.slodopamin.jump.resources.Images;


public class Ramp {

    public Vector2f pos1;
    public Vector2f size1;
    public Vector2f pos2;
    public Vector2f size2;

    private int holeSize = 400;
    private final int minimHoleSize = 150;
    private final int rampWidth = 30;
    private final float maxHoleHeightDelta = 300;

    private float holeHeight = 400;
    private final float middle = holeHeight;

    private int rampRotationTime = 400;
    private int time = 0;

    private int holeReduceTime = 0;
    Bitmap img;

    public Ramp() {
        calculatePositions();
        img = Bitmap.createScaledBitmap(Images.ramp, (int) size1.x, 800, false);
    }

    private void calculatePositions() {
        float xP = 480 / 2 - (int) rampWidth / 2;

        pos1 = new Vector2f(xP, 0);
        size1 = new Vector2f(rampWidth, holeHeight - holeSize/2);

        pos2 = new Vector2f(xP, holeHeight + holeSize/2);
        size2 = new Vector2f(rampWidth, 700);

    }

    public void update() {
        if (holeSize > minimHoleSize)
            holeReduceTime++;

        if (holeReduceTime == 60) {
            holeSize -= 5;
            holeReduceTime = 0;
        }


        time++;
        if (time >= rampRotationTime)
            time = 0;

        float angle = ((time - rampRotationTime / 2.0f) / (rampRotationTime / 2.0f)) * 3.14f;
        holeHeight = middle + ((float) Math.sin(angle)) * (maxHoleHeightDelta / 2);

        calculatePositions();
    }

    public void render(Canvas g) {

        RectF r1 = new RectF(pos1.x, pos1.y, pos1.x + size1.x, pos1.y + size1.y);
        RectF r2 = new RectF(pos2.x, pos2.y, pos2.x + size2.x, pos2.y + size2.y);

        Paint p = new Paint();

        g.drawBitmap(img, r1.left, r1.bottom - img.getHeight(), p);
        g.drawBitmap(img, r2.left, r2.top, p);

    }

}
