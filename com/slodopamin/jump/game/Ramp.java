
package com.slodopamin.jump.game;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import com.slodopamin.jump.math.Vector2f;


public class Ramp {

	private Vector2f pos1;
	private Vector2f size1;
	private Vector2f pos2;
	private Vector2f size2;

	private final int holeSize = 150;
	private final int rampWidth = 30;
	private final float maxHoleHeightDelta = 300;

	private float holeHeight = 400;
	private final float middle = holeHeight;

	private int rampRotationTime = 400;
	private int time = 0;

	public Ramp() {
		calculatePositions();
	}

	private void calculatePositions() {
		float xP = 480 / 2 - (int) rampWidth / 2;

		pos1 = new Vector2f(xP, 0);
		size1 = new Vector2f(rampWidth, holeHeight - holeSize / 2);

		pos2 = new Vector2f(xP, holeHeight + holeSize / 2);
		size2 = new Vector2f(rampWidth, 700);
	}

	public void update() {
		time++;
		if (time >= rampRotationTime)
			time = 0;

		float angle = ((time - rampRotationTime / 2.0f) / (rampRotationTime / 2.0f)) * 3.14f;
		holeHeight = middle + ((float) Math.sin(angle)) * (maxHoleHeightDelta / 2);


		calculatePositions();
	}

	public void render(Canvas g) {

		RectF r1 = new RectF(pos1.x, pos1.y, pos1.x + size1.x, pos1.y + size1.y);
		RectF r2 = new RectF(pos2.x, pos2.y, pos2.x + size2.x, pos2.y + size2.y);
		Paint p = new Paint();
		p.setColor(0xff000000);

		g.drawRect(r1, p);
		g.drawRect(r2, p);
	}

}
