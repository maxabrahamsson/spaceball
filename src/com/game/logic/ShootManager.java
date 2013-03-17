package com.game.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Align;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.utils.Scaling;
import com.game.core.TextureManager;
import com.game.core.Tools;
import com.game.core.UIManager;

public class ShootManager implements ContactListener {
	private boolean isReady=true;
	private UIManager UIManager;
	private Image Indicator;
	private Projectile Projectile;
	Body body;
	World world;
	public ShootManager (UIManager uiManager,World world)
	{
		UIManager=uiManager;	
		this.world=world;
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
				launchProjectile(x,y);
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
		image.height = 50;
		image.width = 50;
		image.x = 100;
		image.y = 100;
		image.originX=image.width/2;
		image.originY=image.height/2;
		image.visible=true;
		UIManager.GetStage("oyun").addActor(image);
		
		BodyDef boxBodyDef = new BodyDef();
		boxBodyDef.type = BodyType.DynamicBody;
		boxBodyDef.position.x = 125;
		boxBodyDef.position.y = 125;
		body = world.createBody(boxBodyDef);

		PolygonShape ps;
		Vector2[] vv=new Vector2[30];
		for(int i=0; i<vv.length; i++)
		{
			float der=i*360/vv.length;
			float x=(float) (Math.cos(Math.toRadians(der))*image.width/2);
			float y=(float) (Math.sin(Math.toRadians(der))*image.height/2);
			vv[i]=new Vector2(x,y);
		}
		vv=Tools.Triangulate(vv);
		for(int i=0; i<vv.length; i+=3)
		{
			ps = new PolygonShape();
			Vector2[] temp=new Vector2[] {
				new Vector2(vv[i+2].x,vv[i+2].y),
				new Vector2(vv[i+1].x,vv[i+1].y),
				new Vector2(vv[i].x,vv[i].y)
			};
			ps.set(temp);
			body.createFixture(ps, 0.01f);
		}
		
		
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
		
		arrow.scaleX=(float) (Math.hypot(dx, dy)/100);
		arrow.scaleY=arrow.scaleX;		
	}
	public void setArrowState(boolean state)
	{
		UIManager.GetStage("oyun").findActor("arrow").visible=state;
	}
	public void launchProjectile(float x,float y)
	{
		setArrowState(false);
		isReady=false;
		Actor arrow=UIManager.GetStage("oyun").findActor("arrow");
		arrow.visible=false;
		
		Vector2 temp=new Vector2(x,y);
		Widget.toScreenCoordinates((Actor)UIManager.GetStage("oyun").findActor("ball"), temp);
		
		float dx=temp.x-arrow.x;
		float dy=temp.y-arrow.y;
		
		Actor ball=UIManager.GetStage("oyun").findActor("ball");
		float px=body.getPosition().x;
		float py=body.getPosition().y;
		body.applyLinearImpulse(dx*10, dy*10, px,py);
		
	}
	public void Update()
	{
		Actor ball=UIManager.GetStage("oyun").findActor("ball");
		ball.x=body.getPosition().x-ball.width/2;
		ball.y=body.getPosition().y-ball.height/2;
		ball.rotation=(float) Math.toDegrees(body.getAngle());
		 
		if(!isReady)
		{
			//Projectile.Update();
		}
	}
	Body LastHit;
	@Override
	public void beginContact(Contact contact) {
        Fixture f1=contact.getFixtureA();
        Body b1=f1.getBody();
        Fixture f2=contact.getFixtureB();
        Body b2=f2.getBody();
        if(b1==body)
        {
        	if(b2!=LastHit)
        	{
        		LastHit=b2;
        		System.out.println("ses");
        	}
        }
        if(b2==body)
        {
        	if(b1!=LastHit)
        	{
        		LastHit=b1;
        		System.out.println("ses");
        	}
        }	
	}
	@Override
	public void endContact(Contact contact) {}
	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {}
	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {}
}
