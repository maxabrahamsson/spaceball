package com.game.logic;


import java.awt.Dimension;
import java.awt.Toolkit;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Align;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Scaling;
import com.game.core.TextureManager;
import com.game.core.Tools;
import com.game.core.UIManager;


public class Game implements ApplicationListener {
	TextureManager TextureManager;
	UIManager UIManager;
	OrthographicCamera cam;
	Rectangle glViewport;
	
	ShootManager ShootManager;
	
	World world;
	Box2DDebugRenderer debugRenderer;
    static final float BOX_STEP=1/60f;  
    static final int BOX_VELOCITY_ITERATIONS=60;  
    static final int BOX_POSITION_ITERATIONS=20; 
	@Override
	public void create() {	
		world = new World(new Vector2(0, -10), true);    	
		debugRenderer = new Box2DDebugRenderer(); 	
		
		
		
		
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		glViewport = new Rectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());	
		TextureManager=new TextureManager();
		UIManager=new UIManager(TextureManager);
		TextureManager.AddRegion("ball", 0, 0, 100, 100);
		TextureManager.AddRegion("floor", 0, 100, 256, 50);
		TextureManager.AddRegion("basket", 256, 0, 256, 355);
		TextureManager.AddRegion("arrow", 0, 150, 100, 36);
		TextureManager.AddRegion("debugPointer", 0, 186, 29, 29);
		UIManager.AddStage("oyun",new Stage(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),false));

        PolygonShape groundBox = new PolygonShape();  
        groundBox.setAsBox((cam.viewportWidth), 5); 
        
		BodyDef groundBodyDef =new BodyDef();  
        groundBodyDef.position.set(new Vector2(0, 0));  
        Body groundBody = world.createBody(groundBodyDef);  
        groundBody.createFixture(groundBox, 1.0f);  
 
		BodyDef tavan =new BodyDef();  
		tavan.position.set(new Vector2(0, Gdx.graphics.getHeight()));  
        Body tavanBody = world.createBody(tavan);     
        tavanBody.createFixture(groundBox, 1.0f);  
        
        PolygonShape duvarBox = new PolygonShape();  
        duvarBox.setAsBox(0, Gdx.graphics.getHeight());
        
		BodyDef sagKolon =new BodyDef();  
		sagKolon.position.set(new Vector2(Gdx.graphics.getWidth(),0));  
        Body sagKolonBody = world.createBody(sagKolon);  
        sagKolonBody.createFixture(duvarBox, 1.0f); 


		
		Image image = new Image(TextureManager.getTextureByTag("floor"), Scaling.fillX, Align.TOP);
		image.height = image.getPrefHeight();
		image.width = 800;
		image.x = 0;
		image.y = 0;
		UIManager.GetStage("oyun").addActor(image);
		
		image = new Image(TextureManager.getTextureByTag("basket"), Scaling.none, Align.TOP);
		image.height = image.getPrefHeight();
		image.width = image.getPrefHeight();
		image.x = Gdx.graphics.getWidth()-image.width;
		image.y = 25;
		UIManager.GetStage("oyun").addActor(image);
		BodyDef solKolon =new BodyDef();  
		solKolon.position.set(new Vector2(0,0));  
        Body solKolonBody = world.createBody(solKolon);   
        solKolonBody.createFixture(duvarBox, 1.0f); 
       
        Body body;
		BodyDef boxBodyDef = new BodyDef();
		boxBodyDef.position.x = Gdx.graphics.getWidth()-image.width/2-5;
		boxBodyDef.position.y = 25;
		body = world.createBody(boxBodyDef);

		Vector2[] vv=new Vector2[]{
			new Vector2(0,0),
			new Vector2(116,0),
			new Vector2(61,236),
			new Vector2(-8,324),
			new Vector2(-13,355),
			new Vector2(-13,196)
			
		};
		vv=Tools.Triangulate(vv);
		for(int i=0; i<vv.length; i+=3)
		{
			PolygonShape ps = new PolygonShape();
			Vector2[] temp=new Vector2[] {
				new Vector2(vv[i+2].x,vv[i+2].y),
				new Vector2(vv[i+1].x,vv[i+1].y),
				new Vector2(vv[i].x,vv[i].y)
			};
			ps.set(temp);
			body.createFixture(ps, 1);
		}
		
		ShootManager=new ShootManager(UIManager,world);
		world.setContactListener(ShootManager);
	}
	@Override
	public void render() {	
		world.step(BOX_STEP, BOX_VELOCITY_ITERATIONS, BOX_POSITION_ITERATIONS);
		if(Gdx.graphics.getDeltaTime()>1/30)
		{   
			GL10 gl = Gdx.graphics.getGL10();
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			debugRenderer.render(world, cam.combined); 
			
			gl.glViewport((int) glViewport.x, (int) glViewport.y, (int) glViewport.width, (int) glViewport.height);
			cam.update();
			cam.apply(gl);
			UIManager.Draw();
			ShootManager.Update();
		}
		Gdx.graphics.getGL10().glFlush();	
	}
	@Override public void resize(int arg0, int arg1) {	}
	@Override public void resume() {}
	@Override public void dispose() {}
	@Override public void pause() {}
}
