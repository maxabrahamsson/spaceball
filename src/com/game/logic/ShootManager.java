package com.game.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Align;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
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
		
		
		Image image = new Image(UIManager.getTextureManager().getTextureByTag("arrow"), Scaling.none, Align.LEFT,"arrow");
		image.height = image.getPrefHeight();
		image.width = image.getPrefHeight();
		image.x = 0;
		image.y = 0;
		image.originX=0;
		image.originY=image.height/2;
		image.visible=false;
		UIManager.GetStage("oyun").addActor(image);
		
		image = new Image(UIManager.getTextureManager().getTextureByTag("ball"), Scaling.stretch, Align.CENTER,"ball")
		{
			@Override
			public void touchUp(float x,float y,int pointer)
			{
				pointArrowTo(x,y);
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
				return true;		
			}			
		};
		image.height = image.getPrefHeight();
		image.width = image.getPrefHeight();
		image.x = 100;
		image.y = 100;
		image.visible=true;
		UIManager.GetStage("oyun").addActor(image);

		
		image = new Image(UIManager.getTextureManager().getTextureByTag("debugPointer"), Scaling.none, Align.LEFT,"point");
		image.height = image.getPrefHeight();
		image.width = image.getPrefHeight();
		image.x = 0;
		image.y = 0;
		image.visible=false;
		UIManager.GetStage("oyun").addActor(image);
		
	}

	public void pointArrowTo(float x,float y)
	{		
		Image ball=(Image) UIManager.GetStage("oyun").findActor("ball");
		Image arrow=(Image) UIManager.GetStage("oyun").findActor("arrow");
		
		Vector2 temp=new Vector2(x,y);
		Widget.toScreenCoordinates((Actor)UIManager.GetStage("oyun").findActor("ball"), temp);
		
		float dx=temp.x-arrow.x;
		float dy=temp.y-arrow.y;
		arrow.rotation=(float)(Math.toDegrees(Math.atan(dy/dx)));
		if(dx<0)
		{
			arrow.rotation+=180;
		}

		arrow.x=ball.x+ball.width/2;
		arrow.y=ball.y+ball.height/2;
		
		System.out.println(arrow.width);
		arrow.scaleX=(float) (Math.hypot(dx, dy)/100);
		arrow.scaleY=arrow.scaleX;
		
		
	}
	public void setArrowState(boolean state)
	{
		UIManager.GetStage("oyun").findActor("arrow").visible=state;
	}
	public void launchProjectile()
	{
		setArrowState(false);
		isReady=false;
		UIManager.GetStage("oyun").findActor("arrow").visible=false;
		
	}
}
