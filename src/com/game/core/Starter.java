package com.game.core;

import com.badlogic.gdx.backends.jogl.JoglApplication;
import com.game.logic.Game;

public class Starter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		new JoglApplication(new Game(),"pencere",500,500,false);
		
	}

}
