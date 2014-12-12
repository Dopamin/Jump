
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

import com.slodopamin.jump.game.Game;
import com.slodopamin.jump.input.Input;
import com.slodopamin.jump.resources.Images;

/**
 * @author Ziga Vene, zvene28@gmail.com
 */
public class MyCanvas extends SurfaceView implements Runnable {

    private SurfaceHolder ourHolder;
    private Thread thread;
    public boolean running = false;

    public MainActivity context;
    public static MyCanvas canvas;

    public static int screenWidth, screentHeight;
    public static float scaleX;
    public static float scaleY;

    private Game game;

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

        game = new Game();

        canvas = this;
        ourHolder = getHolder();
        Images.init(context);

        input = new Input();
        setOnTouchListener(input);

        // scaling
        Display display = context.getWindowManager().getDefaultDisplay();
        screenWidth = display.getWidth();
        screentHeight = display.getHeight();
        scaleX = (float) 480 / (float) screenWidth;
        scaleY = (float) 800 / (float) screentHeight;

    }

    public void start() {
        if (running)
            return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public void stop() {
        running = false;

        while (true) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            break;
        }
    }

    @SuppressWarnings("static-access")
    @Override
    public void run() {

        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / 60.0;
        double delta = 0;
        int frames = 0;
        int updates = 0;
        while (running) {
            long now = System.nanoTime();

            delta += (now - lastTime) / ns;

            try {
                thread.sleep(2);
                thread.yield();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (!ourHolder.getSurface().isValid())
                continue;

            Canvas canvas = ourHolder.lockCanvas();

            if (canvas != null) {
                canvas.drawRGB(0, 0, 0);

                lastTime = now;
                while (delta >= 1) {
                    updates++;
                    delta--;
                    update();
                }
                render(canvas);
                frames++;
                if (System.currentTimeMillis() - timer > 1000) {
                    //Log.e("Running ", "fps: " + frames + "| ups:" + updates);
                    timer += 1000;
                    frames = updates;
                    updates = frames;
                    updates = 0;
                    frames = 0;
                }

                ourHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    private void update() { game.update(); }

    private void render(Canvas canvas) {
        canvas.scale(1 / scaleX, 1 / scaleY);
        game.render(canvas);
    }

}
