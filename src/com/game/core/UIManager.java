package com.game.core;

import java.util.Hashtable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Scaling;

public class UIManager {
	private Hashtable<String, Stage> Stages=new Hashtable<String, Stage>();
	private String CurrentStage=null;
	private TextureAtlas textures;
	public UIManager()
	{
		textures = new TextureAtlas(Gdx.files.internal("data/imagedata.txt"));
	}
	public void AddStage(String name,Stage stage)
	{
		Stages.put(name,stage);
		if(CurrentStage==null)
		{
			SetStage(name);
		}
	}
	public void AddStage(String name)
	{
		Stages.put(name, new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false));
		if(CurrentStage==null)
		{
			SetStage(name);
		}
	}
	public void SetStage(String name)
	{
		CurrentStage=name;
		Gdx.input.setInputProcessor(Stages.get(CurrentStage));
	}
	public Stage GetStage(String name)
	{
		if(CurrentStage!=null)
		{
			return Stages.get(CurrentStage);
		}	
		return null;
	}
	public void Draw()
	{
		if(CurrentStage!=null)
		{
			Stages.get(CurrentStage).draw();
		}
	}
	public Image AddImage(String name,Scaling sc,int align,Vector2 pos)
	{
		Image image = new Image(textures.findRegion(name), sc, align,name);
		image.height = image.getPrefHeight();
		image.width = image.getPrefHeight();
		image.x = pos.x;
		image.y = pos.y;
		GetStage("oyun").addActor(image);		
		return image;
	}
	public Image AddImage(String name,Scaling sc,int align,Vector2 pos,Vector2 size)
	{
		Image image = new Image(textures.findRegion(name), sc, align,name);
		image.height = size.y;
		image.width = size.x;
		image.x = pos.x;
		image.y = pos.y;
		GetStage("oyun").addActor(image);	
		return image;
	}
	public AtlasRegion getTexture(String name)
	{
		return textures.findRegion(name);
	}
}
