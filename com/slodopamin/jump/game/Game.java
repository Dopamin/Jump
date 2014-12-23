
package com.slodopamin.jump.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import com.slodopamin.jump.MyCanvas;
import com.slodopamin.jump.resources.Images;


public class Game {

    Player player;
    Ramp ramp;

    Bitmap background;

    public static int score = 0;

    public Game() {
        player = new Player();
        ramp = new Ramp();

        background = Bitmap.createScaledBitmap(Images.background, 480, 900, false);

    }

    public void reset() {
        score = 0;
        player = new Player();
        ramp = new Ramp();
    }

    float rotation;

    public void update() {
        ramp.update();
        player.update();
        if (checkCollision())
            reset();
        rotation += 5;
    }

    Paint p = new Paint();

    public void render(Canvas g) {
        // draw background
        g.drawBitmap(background, 0, 0, p);
        // render player and ramp
        ramp.render(g);
        player.render(g);
        // render text on screen and center
        p.setColor(Color.BLACK);
        p.setTextSize(100);
        p.setTextAlign(Paint.Align.CENTER);
        g.drawText("" + score, 480 / 4, 100, p);
        g.drawText("" + score, 480 * 3 / 4, 100, p);

    }

    private boolean checkCollision() {
        RectF r1 = new RectF(player.pos.x, player.pos.y, player.pos.x + player.size.x, player.pos.y + player.size.y);
        RectF r2 = new RectF(ramp.pos1.x, ramp.pos1.y, ramp.pos1.x + ramp.size1.x, ramp.pos1.y + ramp.size1.y);
        RectF r3 = new RectF(ramp.pos2.x, ramp.pos2.y, ramp.pos2.x + ramp.size2.x, ramp.pos2.y + ramp.size2.y);
        if (r1.intersect(r2) || r1.intersect(r3))
            return true;
        return false;
    }
}
