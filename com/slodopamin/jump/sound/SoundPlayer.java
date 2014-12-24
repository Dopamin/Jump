package com.slodopamin.jump.sound;


import android.content.Context;
import android.media.MediaPlayer;

import com.slodopamin.jump.R;

/**
 * Created by Jure on 24.12.2014.
 */
public class SoundPlayer {
    Context context;
    MediaPlayer jump;

    public void init(Context context) {
        this.context = context;
        jump = MediaPlayer.create(context, R.drawable.jumpsound);
    }

    public void playJumpSound() {
        jump.seekTo(0);
        jump.start();
    }
}
