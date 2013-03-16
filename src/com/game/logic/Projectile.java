package com.game.logic;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Align;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Scaling;

public class Projectile {
	private Image image;
	public boolean isPhysical=false;
	public Vector2 v,a;
	public float m=10000,wA=0,wV=0;
	public Projectile(Image image,Vector2 force)
	{
		this.image=image;
		a=new Vector2(force.x/m,force.y/m);
		v=new Vector2(0,0);
	}
	public void Update()
	{
		if(isPhysical)
		{
			v.x+=a.x;
			v.y+=a.y;
			wV+=wA;
			
			image.x+=v.x;
			image.y+=v.y;
			image.rotation+=wV;
			
			a.y-=10/m;
			
			if(image.y<=50)
			{
				v.y*=-0.9;
				wA-=image.width*v.x/m;
			}
		}
	}
	
}
