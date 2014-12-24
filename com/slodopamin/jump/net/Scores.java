
package com.slodopamin.jump.net;


public class Scores {

	// The top scores
	public static Score[] scores = new Score[10];

	public static void addScore(int n, Score score) {
		scores[n] = score;
	}

	public static Score getScore(int n) {
		return scores[n];
	}

	public static boolean noScores() {
		if (scores[0] == null)
			return true;
		return false;
	}

}
