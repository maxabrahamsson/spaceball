package com.game.logic;


import com.badlogic.gdx.Gdx;
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
	public float m=50,wA=0,wV=0;
	public Projectile(Image image,Vector2 force)
	{
		this.image=image;
		System.out.println("fx : "+force.x+", fy:"+force.y);
		a=new Vector2(force.x/m,force.y/m);
		v=new Vector2(0,0);
	}
	public void Update()
	{
		if(isPhysical)
		{
			wA=Math.min(5, wA);
			v.x+=a.x;
			v.y+=a.y;
			a.y=(float) Math.max(a.y-0.1,-1);
			wV=wA/image.width;
			
			image.x+=v.x;
			image.y+=v.y;
			image.rotation+=wV;
			
			if(image.y <= 25)
			{
				image.y=25;
				v.y*=-0.8;
				wA=image.width*v.y/m;
				ApplyFriction();
			}
			if(image.y >= Gdx.graphics.getHeight()-image.width)
			{
				image.y=Gdx.graphics.getHeight()-image.width;
				v.y*=-0.8;
				wA=image.width*v.y/m;
				ApplyFriction();
			}
			if(image.x >= Gdx.graphics.getWidth()-image.width)
			{
				image.x=Gdx.graphics.getWidth()-image.width;
				v.x*=-0.8;
				wV=0;
				wA=image.width*v.x/m;
				ApplyFriction();
			}
			if(image.x <= 0)
			{
				image.x=0;
				v.x*=-0.8;
				wV=0;
				wA=image.width*v.x/m;
				ApplyFriction();
			}
		}
	}
	public void ApplyFriction()
	{
		a.x=0;
		a.y=0;		
	}
	
}
