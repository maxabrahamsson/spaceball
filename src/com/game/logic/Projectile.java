package com.game.logic;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Align;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Scaling;

public class Projectile {
	private Image image;
	private boolean isPhysical=false;
	public Projectile(TextureRegion texture,int xPos,int yPos)
	{
		image = new Image(texture, Scaling.none, Align.CENTER);
		image.height = image.getPrefHeight();
		image.width = image.getPrefWidth();
		image.x = xPos;
		image.y = yPos;
	}
	
}
