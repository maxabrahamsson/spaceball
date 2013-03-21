package com.game.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.scenes.scene2d.ui.Align;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Scaling;
import com.game.core.GameObject;

public class Player extends GameObject {
	Game game;
	Vector2 TopTutma_RelativePos=new Vector2(120,140);
	Image playerImage;
	Body playerBody;
	public Player(Game g)
	{
		game=g;
		playerImage=g.UIManager.AddImage("karakter", Scaling.none, Align.LEFT, new Vector2(50,25));
        PolygonShape duvarBox = new PolygonShape();  
        duvarBox.setAsBox(playerImage.getPrefWidth()/2, playerImage.getPrefHeight()/2);
        
		BodyDef bd =new BodyDef();  
		bd.type=BodyType.DynamicBody;
		bd.position.set(new Vector2(playerImage.x+playerImage.getPrefWidth()/2,playerImage.y+playerImage.getPrefHeight()/2));  
		playerBody = g.world.createBody(bd); 
		
		playerBody.createFixture(duvarBox, 0.5f); 
	}
	public Vector2 getTutusPos()
	{
		Vector2 temp=new Vector2(playerImage.getImageX(),playerImage.getImageY());
		Image.toScreenCoordinates(playerImage, temp);
		float x=temp.x+TopTutma_RelativePos.x;
		float y=temp.y+TopTutma_RelativePos.y;
		return new Vector2(x,y);
	}
	@Override
	public void Update() {
		Vector2 bodyPos=playerBody.getPosition();
		playerImage.x=bodyPos.x-playerImage.getPrefWidth()/2;
		playerImage.y=bodyPos.y-playerImage.getPrefHeight()/2;
		playerImage.rotation=(float) Math.toDegrees(playerBody.getAngle());
		if(Gdx.input.isKeyPressed(Input.Keys.SPACE))
		{
			playerBody.applyLinearImpulse(new Vector2(0,10000), bodyPos);
		}
	}
}
