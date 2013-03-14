package com.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class ImageElement extends Drawable {
	private Vector3 color;
	private boolean fill;
	private Mesh mesh=null;
	
	private TextureRegion texture;
	public ImageElement(Vector2 position,Vector2 dim,Vector3 color,boolean fill)
	{
		super(position, dim, 0, false);
		this.color=color;
		this.fill=fill;
		this.angle=0;
        if (mesh == null) {
            mesh = new Mesh(false, 4, 4,
                    new VertexAttribute(Usage.Position, 3, "a_position"),
                    new VertexAttribute(Usage.ColorPacked, 4, "a_color"),
                    new VertexAttribute(Usage.TextureCoordinates,2, "a_texCoords"));

            mesh.setVertices(new float[] { 
            		position.x, position.y, 0, Color.toFloatBits(color.x, color.y, color.z, 255), 1, 1,
            		position.x+dim.x, position.y, 0, Color.toFloatBits(color.x, color.y, color.z, 255), 1, 1,
            		position.x+dim.x, position.y-dim.y, 0, Color.toFloatBits(color.x, color.y, color.z, 255), 1, 1,
            		position.x, position.y-dim.y, 0, Color.toFloatBits(color.x, color.y, color.z, 255), 1, 1
            });
                                          
            mesh.setIndices(new short[] { 0, 1,2,3 });
        }
        visible=true;
	}
	public ImageElement(Vector2 position,Vector2 dim,TextureRegion texture)
	{
		super(position, dim, 0, true);
		this.texture=texture;
		visible=true;
	}
	@Override
	public void Draw(SpriteBatch s) {	
		if(!visible) {return; }
		s.draw(texture,position.x,position.y,dim.x/2,dim.y/2,  dim.x, dim.y, 1, 1, angle); 
	}
	public Vector2 getCenterPos()
	{
		return new Vector2(position.x+dim.x/2,position.y+dim.y/2);
	}
}
