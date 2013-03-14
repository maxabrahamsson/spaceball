package com.game;


import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Game implements ApplicationListener,InputProcessor {
	RenderList rs=new RenderList();
	SpriteBatch sb;
	@Override
	public void create() {	   
		sb=new SpriteBatch();
	}
	@Override
	public void render() {			
		if(Gdx.graphics.getDeltaTime()>1/30)
		{   
			
		}
		Gdx.graphics.getGL10().glFlush();	
	}
	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {		
		return false;
	}
	@Override
	public boolean touchDragged(int x, int y, int arg2) {
		return false;
	}

	@Override
	public boolean touchMoved(int x, int y) {
		return false;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		return false;
	}
	@Override public void resize(int arg0, int arg1) {	}
	@Override public void resume() {}
	@Override public boolean keyDown(int keycode) {return false;}
	@Override public boolean keyTyped(char arg0) {return false;}
	@Override public boolean keyUp(int arg0) {return false;}
	@Override public boolean scrolled(int arg0) {return false;}
	@Override public void dispose() {}
	@Override public void pause() {}
}
