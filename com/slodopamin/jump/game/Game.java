
package com.slodopamin.jump.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import com.slodopamin.jump.MyCanvas;
import com.slodopamin.jump.input.Input;
import com.slodopamin.jump.resources.Images;


public class Game {

    Player player;
    Ramp ramp;

    Bitmap background;

    public static int score = 0;
    /*
     Game state variables:
     0 -> in menu
     1 -> playing game
     2 -> game over
    */
    int gamestate = 0;
    int gamedelay = 180;

    public Game() {
        player = new Player();
        ramp = new Ramp();

        background = Bitmap.createScaledBitmap(Images.background, 480, 900, false);

    }

    public void reset() {
        score = 0;
        gamedelay = 180;
        player = new Player();
        ramp = new Ramp();
    }

    float rotation;

    public void update() {
        if (gamestate == 0)
            if (Input.isDown())
                gamestate = 1;

        if (gamestate == 1) {
            gamedelay--;
            if (gamedelay <= 0) {
                ramp.update();
                player.update();
                if (checkCollision())
                    gamestate = 2;
                rotation += 5;
            }
        }
        if (gamestate == 2)
            if (Input.isDown()) {
                gamestate = 1;
                reset();
            }
    }

    Paint p = new Paint();

    public void render(Canvas g) {
        p.setColor(Color.BLACK);
        g.drawRect(0, 0, 480, 900, p);
        if (gamestate == 0)
            renderMenu(g);
        else if (gamestate == 1)
            renderGame(g);
        else if (gamestate == 2)
            renderGameOver(g);


    }

    private void renderMenu(Canvas g) {
        // draw background
        g.drawBitmap(background, 0, 0, p);
        g.drawBitmap(Images.playbutton, 240 - Images.playbutton.getWidth() / 2, 400 - Images.playbutton.getHeight() / 2, p);
    }

    private void renderGame(Canvas g) {
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
        //render timer
        if (gamedelay > 0)
            g.drawText("" + (gamedelay / 60 + 1), 240, 400, p);
    }

    private void renderGameOver(Canvas g) {
        p.setAlpha(90);
        g.drawBitmap(background, 0, 0, p);
        ramp.render(g);
        player.render(g);
        p.setAlpha(255);
        g.drawBitmap(Images.gameover, 240 - Images.gameover.getWidth() / 2, 200 - Images.gameover.getHeight() / 2, p);
        g.drawBitmap(Images.playagain, 240 - Images.playagain.getWidth() / 2, 400 - Images.playagain.getHeight() / 2, p);
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
