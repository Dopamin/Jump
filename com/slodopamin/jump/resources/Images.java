
package com.slodopamin.jump.resources;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.slodopamin.jump.R;

public class Images {

	public static Bitmap logo;

	public static Context context;

	public static void init(Context x) {
		logo = BitmapFactory.decodeResource(x.getResources(), R.drawable.ic_launcher);

	}

}
