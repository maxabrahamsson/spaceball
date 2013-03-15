package com.game.core;

import java.util.Hashtable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class UIManager {
	private Hashtable<String, Stage> Stages=new Hashtable<String, Stage>();
	private String CurrentStage=null;
	public UIManager()
	{

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
}
