
package com.slodopamin.jump.game;

import android.graphics.Canvas;



public class Game {

	Player player;

	public Game() {
		reset();
	}

	public void reset() {
		player = new Player();
	}

	public void update() {
		player.update();
	}

	public void render(Canvas g) {
		g.drawARGB(0xff, 0xec, 0xf0, 0xf1);

		player.render(g);
	}
}
