package com.game.logic;


import java.awt.Dimension;
import java.awt.Toolkit;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Align;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Scaling;
import com.game.core.TextureManager;
import com.game.core.UIManager;


public class Game implements ApplicationListener {
	TextureManager TextureManager;
	UIManager UIManager;
	OrthographicCamera cam;
	Rectangle glViewport;
	
	ShootManager ShootManager;
	@Override
	public void create() {	
		
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		glViewport = new Rectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());	
		System.out.print(Gdx.graphics.getWidth());
		TextureManager=new TextureManager();
		UIManager=new UIManager(TextureManager);
		TextureManager.AddRegion("ball", 0, 0, 100, 100);
		TextureManager.AddRegion("floor", 0, 100, 256, 50);
		TextureManager.AddRegion("basket", 100, 0, 112, 75);
		TextureManager.AddRegion("arrow", 0, 150, 100, 36);
		TextureManager.AddRegion("debugPointer", 0, 186, 29, 29);
		UIManager.AddStage("oyun",new Stage(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),false));
		
		
		Image image = new Image(TextureManager.getTextureByTag("floor"), Scaling.fillX, Align.TOP);
		image.height = image.getPrefHeight();
		image.width = 500;
		image.x = 0;
		image.y = 0;
		UIManager.GetStage("oyun").addActor(image);
		
		image = new Image(TextureManager.getTextureByTag("basket"), Scaling.none, Align.TOP);
		image.height = image.getPrefHeight();
		image.width = image.getPrefHeight();
		image.x = 400;
		image.y = 100;
		UIManager.GetStage("oyun").addActor(image);

		
		ShootManager=new ShootManager(UIManager);
	}
	@Override
	public void render() {			
		if(Gdx.graphics.getDeltaTime()>1/30)
		{   
			GL10 gl = Gdx.graphics.getGL10();
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			gl.glViewport((int) glViewport.x, (int) glViewport.y, (int) glViewport.width, (int) glViewport.height);
			cam.update();
			cam.apply(gl);
			UIManager.Draw();
			ShootManager.Update();
		}
		Gdx.graphics.getGL10().glFlush();	
	}
	@Override public void resize(int arg0, int arg1) {	}
	@Override public void resume() {}
	@Override public void dispose() {}
	@Override public void pause() {}
}
