package com.game.logic;


import java.awt.Dimension;
import java.awt.Toolkit;

import aurelienribon.bodyeditor.BodyEditorLoader;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Align;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Scaling;
import com.game.core.Tools;
import com.game.core.UIManager;


public class Game implements ApplicationListener {
	UIManager UIManager;
	OrthographicCamera cam;
	Rectangle glViewport;
	
	ShootManager ShootManager;
	
	World world;
	Box2DDebugRenderer debugRenderer;
    static final float BOX_STEP=1/45f;  
    static final int BOX_VELOCITY_ITERATIONS=6;  
    static final int BOX_POSITION_ITERATIONS=2; 
    
    
	@Override
	public void create() {	
		world = new World(new Vector2(0, -10), true);    	
		debugRenderer = new Box2DDebugRenderer(); 
		
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		glViewport = new Rectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());	

		UIManager=new UIManager();	
		ShootManager=new ShootManager(UIManager,world);
		world.setContactListener(ShootManager);      
		BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("data/box2d.json"));
		
		UIManager.AddStage("oyun",new Stage(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),false));

        PolygonShape groundBox = new PolygonShape();  
        groundBox.setAsBox((cam.viewportWidth), 5); 
        
		BodyDef groundBodyDef =new BodyDef();  
        groundBodyDef.position.set(new Vector2(0, 25));  
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


        UIManager.AddImage("taban",Scaling.fillX,Align.TOP,new Vector2(0,0),new Vector2(Gdx.graphics.getWidth(),50));
		Image pota=UIManager.AddImage("pota",Scaling.none,Align.LEFT,new Vector2(Gdx.graphics.getWidth()-250,25));
		BodyDef bd = new BodyDef();
		bd.type = BodyType.StaticBody;
		FixtureDef fd = new FixtureDef();
		Body bottleModel = world.createBody(bd);
		bottleModel.setTransform(pota.x, pota.y, 0);
		loader.attachFixture(bottleModel, "pota", fd,UIManager.getTexture("pota").getRegionWidth());		
		Vector2 bottleModelOrigin = loader.getOrigin("pota",  pota.width/2).cpy();
	}

	@Override
	public void render() {	
		world.step(BOX_STEP, BOX_VELOCITY_ITERATIONS, BOX_POSITION_ITERATIONS);
		if(Gdx.graphics.getDeltaTime()>1/30)
		{   
			GL10 gl = Gdx.graphics.getGL10();
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			gl.glClearColor(0,0, 1, 1);
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
