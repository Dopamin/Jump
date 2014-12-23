
package com.slodopamin.jump.game;

import com.slodopamin.jump.input.Input;
import com.slodopamin.jump.math.Vector2f;
import com.slodopamin.jump.resources.Images;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;


public class Player {

    public Vector2f pos;
    public Vector2f size;

    public boolean stop = false;

    private int start;
    private int distance;
    private int middle;

    private int groundLevel = 800;

    private final int jumpTime = 90;
    private int currentTime = 0;

    private boolean goingRight = true;

    private float baseJumpHeight = 150; // amplituda
    private float jumpHeight = baseJumpHeight; // amplituda

    Bitmap img;

    public Player() {
        pos = new Vector2f(50, 800);
        size = new Vector2f(50, 50);

        start = 50;
        distance = 480 - start * 2 - (int) (size.x);
        middle = 480 / 2 - (int) size.x / 2;

        img = Bitmap.createScaledBitmap(Images.player, (int) size.x, (int) size.y, false);
    }

    private void calculatePosition() {
        float halfJump = jumpTime / 2;
        float k = (((currentTime - halfJump) / halfJump));
        float angle = k * (3.14f / 2);
        float height = (float) Math.cos(angle);

        pos.y = groundLevel - height * jumpHeight;

        float distanceFromMiddle = distance / 2 * k;
        if (!goingRight)
            distanceFromMiddle *= -1;
        pos.x = middle + distanceFromMiddle;


    }

    private void getInput() {
        if (Input.isDown()) {
            if ((goingRight && pos.x < middle) || !goingRight && pos.x > middle) {
                jumpHeight += 10;
            }
        }
    }

    float rotation = 0;

    public void update() {
        currentTime++;
        if (currentTime >= jumpTime) {// bounce
            jumpHeight = baseJumpHeight;
            currentTime = 0;
            goingRight = !goingRight;
            Game.score++;
        }

        getInput();
        if (!stop)
            calculatePosition();
        if (goingRight)
            rotation += 5;
        else
            rotation -= 5;
    }

    public void render(Canvas g) {

        RectF r = new RectF(pos.x, pos.y, pos.x + size.x, pos.y + size.y);
        Paint p = new Paint();
        g.translate(r.left + img.getWidth() / 2, r.top + img.getHeight() / 2);
        g.rotate(rotation);
        g.drawBitmap(img, -img.getWidth() / 2, -img.getHeight() / 2, p);
        g.rotate(-rotation);
        g.translate(-(r.left + img.getWidth() / 2), -(r.top + img.getHeight() / 2));

    }
}
