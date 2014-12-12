
package com.slodopamin.jump.game;

import com.slodopamin.jump.input.Input;
import com.slodopamin.jump.math.Vector2f;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;


public class Player {

	public Vector2f pos;
	public Vector2f size;

	private int start;
	private int distance;
	private int middle;

	private int groundLevel = 650;

	private final int jumpTime = 90;
	private int currentTime = 0;

	private boolean goingRight = true;

	private float baseJumpHeight = 150; // amplituda
	private float jumpHeight = baseJumpHeight; // amplituda

	public Player() {
		pos = new Vector2f(50, 790);
		size = new Vector2f(50, 50);

		start = 50;
		distance = 480 - start * 2 - (int) (size.x);
		Log.e("Hello", "hello " + distance);
		middle = 480 / 2 - (int) size.x / 2;
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
				jumpHeight += 9;
			}
		}
	}

	public void update() {
		currentTime++;
		if (currentTime >= jumpTime) {// bounce
			jumpHeight = baseJumpHeight;
			currentTime = 0;
			goingRight = !goingRight;
		}

		getInput();
		calculatePosition();
	}

	public void render(Canvas g) {

		RectF r = new RectF(pos.x, pos.y, pos.x + size.x, pos.y + size.y);
		Paint p = new Paint();
		p.setColor(0xffff0000);

		g.drawRect(r, p);
	}
}
