
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
	public static Context context;

	public static void init(Context x) {
		logo = BitmapFactory.decodeResource(x.getResources(), R.drawable.ic_launcher);
        ramp = BitmapFactory.decodeResource(x.getResources(), R.drawable.ramp);
        background = BitmapFactory.decodeResource(x.getResources(), R.drawable.background);
        player = BitmapFactory.decodeResource(x.getResources(), R.drawable.player);
	}

}
