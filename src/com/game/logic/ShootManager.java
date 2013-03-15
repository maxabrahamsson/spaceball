package com.game.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Align;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Scaling;
import com.game.core.TextureManager;
import com.game.core.UIManager;

public class ShootManager {
	private boolean isReady=true;
	private UIManager UIManager;
	private Image Indicator;
	public ShootManager(UIManager uiManager)
	{
		UIManager=uiManager;	
		Image image = new Image(UIManager.getTextureManager().getTextureByTag("ball"), Scaling.stretch, Align.CENTER,"ball")
		{
			@Override
			public void touchUp(float x,float y,int pointer)
			{
				launchProjectile();
			}
			@Override
			public void touchDragged(float x,float y,int pointer)
			{
				pointArrowTo(x,y);
			}
			@Override
			public boolean touchDown(float x,float y,int pointer)
			{
				pointArrowTo(x,y);
				setArrowState(true);
				return false;		
			}			
		};
		image.height = image.getPrefHeight();
		image.width = image.getPrefHeight();
		image.x = 0;
		image.y = 0;
		image.visible=true;
		UIManager.GetStage("oyun").addActor(image);
		
		image = new Image(UIManager.getTextureManager().getTextureByTag("arrow"), Scaling.stretch, Align.CENTER,"arrow");
		image.height = image.getPrefHeight();
		image.width = image.getPrefHeight();
		image.x = 0;
		image.y = 0;
		image.visible=false;
		UIManager.GetStage("oyun").addActor(image);
	}
	public void pointArrowTo(float x,float y)
	{
		Actor a=UIManager.GetStage("oyun").findActor("arrow");
		
	}
	public void setArrowState(boolean state)
	{
		UIManager.GetStage("oyun").findActor("arrow").visible=state;
	}
	public void launchProjectile()
	{
		setArrowState(false);
	}
}
