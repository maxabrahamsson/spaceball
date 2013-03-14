package com.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RenderList {
	public ArrayList<Drawable> list;
	public RenderList()
	{
		list=new ArrayList<Drawable>();
	}
	public void addObject(Drawable object)
	{
		list.add(object);
	}
	public Drawable getObject(String tag)
	{
		for(int i=0; i<list.size(); i++)
		{
			if(list.get(i).tag.equals(tag))
			{
				return list.get(i);
			}
		}
		return null;
	}
	public void Draw(SpriteBatch s)
	{
		s.begin();
		for(int i=0; i<list.size(); i++)
		{
			list.get(i).Draw(s);
		}
		s.end();
	}
}
