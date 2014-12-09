
package com.slodopamin.jump.input;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.slodopamin.jump.MyCanvas;
import com.slodopamin.jump.math.Vector2f;

@SuppressLint("ClickableViewAccessibility")
public class Input implements OnTouchListener {

	private static float x, y;
	private static boolean isDown;

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		x = event.getX() * MyCanvas.scaleX;
		y = event.getY() * MyCanvas.scaleY;
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				isDown = true;
				break;
			case MotionEvent.ACTION_UP:
				isDown = false;
				break;
			case MotionEvent.ACTION_MOVE:
				isDown = true;
				break;
		}

		return true;
	}

	public static float getX() {
		return x;
	}

	public static float getY() {
		return y;
	}

	public static boolean isDown() {
		return isDown;
	}

	public static Vector2f getPointer() {
		return new Vector2f(x, y);
	}

}
