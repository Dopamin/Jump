
package com.slodopamin.jump;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.slodopamin.jump.game.Game;
import com.slodopamin.jump.input.Input;
import com.slodopamin.jump.resources.Images;
import com.slodopamin.jump.sound.SoundPlayer;

/**
 * @author Ziga Vene, zvene28@gmail.com
 */
public class MyCanvas extends View implements Runnable {


    private Thread thread;
    public boolean running = false;
    public Canvas canvas = null;
    public MainActivity context;

    public static int screenWidth, screentHeight;
    public static float scaleX;
    public static float scaleY;

    private Game game;
    public static SoundPlayer soundplayer;
    private Input input;


    public MyCanvas(Context context) {
        super(context);
        this.context = (MainActivity) context;
        init();
        start();
    }

    public MyCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = (MainActivity) context;
        init();
        start();
    }

    @SuppressLint("ClickableViewAccessibility")
    @SuppressWarnings("deprecation")
    private void init() {
        Images.init(context);
        soundplayer = new SoundPlayer();
        soundplayer.init(context);
        input = new Input();
        setOnTouchListener(input);

        // scaling
        Display display = context.getWindowManager().getDefaultDisplay();
        screenWidth = display.getWidth();
        screentHeight = display.getHeight();
        scaleX = (float) 480 / (float) screenWidth;
        scaleY = (float) 800 / (float) screentHeight;

        game = new Game();
    }

    public void start() {
        if (running)
            return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    int frames = 0;

    @SuppressWarnings("static-access")
    @Override
    public void run() {

        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / 60.0;
        double delta = 0;

        int updates = 0;
        while (running) {
            while (true) {
                long now = System.nanoTime();
                delta += (now - lastTime) / ns;
                lastTime = now;
                while (delta >= 1) {
                    updates++;
                    delta--;
                    update();
                }
                if (System.currentTimeMillis() - timer > 1000) {
                    Log.e("A:", "" + frames + " --- " + updates);
                    timer += 1000;
                    updates = 0;
                    frames = 0;
                }
            }
        }
    }

    private void update() {
        game.update();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.scale(1 / scaleX, 1 / scaleY);
        game.render(canvas);
        invalidate();
    }
}