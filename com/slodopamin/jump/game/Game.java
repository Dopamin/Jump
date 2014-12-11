
package com.slodopamin.jump.game;

import android.graphics.Canvas;



public class Game {

	Player player;
	Ramp ramp;

	public Game() {
		reset();
	}

	public void reset() {
		player = new Player();
		ramp = new Ramp();
	}

	public void update() {
		ramp.update();
		player.update();
	}

	public void render(Canvas g) {
		g.drawARGB(0xff, 0xec, 0xf0, 0xf1);

		ramp.render(g);
		player.render(g);
	}
}
