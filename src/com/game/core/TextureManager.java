package com.game.core;

import java.util.ArrayList;
import java.util.Hashtable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureManager {
	private Hashtable<String,TextureRegion> regions=new Hashtable<String, TextureRegion>();
	private Texture texture;
	public TextureManager()
	{
		texture=new Texture(Gdx.files.internal("data/images.png"));
	}
	public void AddRegion(String tag,int x,int y,int width,int height)
	{
		TextureRegion tr=new TextureRegion(texture,x,y,width,height);
		regions.put(tag, tr);
	}
	public TextureRegion getTextureByTag(String tag)
	{
		return regions.get(tag);
	}
}
