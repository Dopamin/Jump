
package com.slodopamin.jump.resources;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.slodopamin.jump.R;

public class Images {

    public static Bitmap logo;
    public static Bitmap ramp;
    public static Bitmap background;
    public static Bitmap player;
    public static Bitmap playbutton;
    public static Bitmap playagain;
    public static Bitmap gameover;

    public static void init(Context x) {
        logo = BitmapFactory.decodeResource(x.getResources(), R.drawable.ic_launcher);
        ramp = BitmapFactory.decodeResource(x.getResources(), R.drawable.ramp);
        background = BitmapFactory.decodeResource(x.getResources(), R.drawable.background);
        player = BitmapFactory.decodeResource(x.getResources(), R.drawable.player);
        playbutton = BitmapFactory.decodeResource(x.getResources(), R.drawable.playbutton);
        //scale the button
        playbutton = Bitmap.createScaledBitmap(playbutton, 150, 45, false);
        playagain = BitmapFactory.decodeResource(x.getResources(), R.drawable.playagain);
        //scale the play again button
        playagain = Bitmap.createScaledBitmap(playagain, 350, 45, false);
        gameover = BitmapFactory.decodeResource(x.getResources(), R.drawable.gameover);
        //scale the game over
        gameover = Bitmap.createScaledBitmap(gameover, 360, 47, false);
    }

}
