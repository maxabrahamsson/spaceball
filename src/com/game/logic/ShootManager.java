package com.game.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
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
import com.game.core.Tools;
import com.game.core.UIManager;

public class ShootManager implements ContactListener {
	private boolean isReady=true;
	private UIManager UIManager;
	private Image Indicator;
	Body body;
	World world;
	public ShootManager (UIManager uiManager,World world)
	{
		UIManager=uiManager;	
		this.world=world;
		Image ok=UIManager.AddImage("ok", Scaling.stretch, Align.LEFT, new Vector2(0,0));
		ok.originX=0;
		ok.originY=ok.height/2;
		ok.visible=false;
		
		Image image = new Image(UIManager.getTexture("top"), Scaling.stretch, Align.CENTER,"top")
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
		CircleShape cs=new CircleShape();
		cs.setRadius(image.width/2);
		body.createFixture(cs, 0.01f);	
	}
	Body basketBirinciLevelbody2, basketBirinciLevelbody;
	public void pointArrowTo(float x,float y)
	{		
		Image ball=(Image) UIManager.GetStage("oyun").findActor("top");
		Image arrow=(Image) UIManager.GetStage("oyun").findActor("ok");
		
		Vector2 temp=new Vector2(x,y);
		Widget.toScreenCoordinates((Actor)UIManager.GetStage("oyun").findActor("top"), temp);
		
		float dx=temp.x-arrow.x;
		float dy=temp.y-arrow.y;
		arrow.rotation=(float)(Math.toDegrees(Math.atan(dy/dx)));
		if(dx<0)
		{
			arrow.rotation+=180;
		}

		arrow.x=body.getPosition().x+ball.width/2;
		arrow.y=body.getPosition().y+ball.height/2;
		
		arrow.scaleX=(float) (Math.hypot(dx, dy)/100);
		arrow.scaleY=arrow.scaleX;		
	}
	public void setArrowState(boolean state)
	{
		UIManager.GetStage("oyun").findActor("ok").visible=state;
	}
	public void launchProjectile(float x,float y)
	{
		setArrowState(false);
		isReady=false;
		Actor arrow=UIManager.GetStage("oyun").findActor("ok");
		arrow.visible=false;
		
		Vector2 temp=new Vector2(x,y);
		Widget.toScreenCoordinates((Actor)UIManager.GetStage("oyun").findActor("top"), temp);
		
		float dx=temp.x-arrow.x;
		float dy=temp.y-arrow.y;
		
		Actor ball=UIManager.GetStage("oyun").findActor("top");
		float px=body.getPosition().x;
		float py=body.getPosition().y;
		body.applyLinearImpulse(dx*100, dy*100, px,py);
		
	}
	public void Update()
	{
		Actor ball=UIManager.GetStage("oyun").findActor("top");
		ball.x=body.getPosition().x-ball.width/2;
		ball.y=body.getPosition().y-ball.height/2;
		ball.rotation=(float) Math.toDegrees(body.getAngle());
		if(
				body.getPosition().x>Gdx.graphics.getWidth()-350-20 &&
				body.getPosition().x<Gdx.graphics.getWidth()-350+20 &&
				body.getPosition().y>340-10 &&
				body.getPosition().y<340+10
		)
		{
			
		}
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
        if(b1==basketBirinciLevelbody || b2==basketBirinciLevelbody)
        {
        	
        }
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
