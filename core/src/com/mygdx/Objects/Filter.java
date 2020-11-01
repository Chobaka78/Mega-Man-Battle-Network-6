package com.mygdx.Objects;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.game.Main;
import com.badlogic.gdx.math.Rectangle;

public class Filter {
    public Body body;
    Rectangle rect;
    private float x, y, width, height, angle;
    private int layer;


    public Filter(Rectangle rect, float x, float y, float width, float height, float angle, int layer){
        this.rect = rect;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.angle = angle;
        this.layer = layer;
        CreateBox2d();
    }

    private void CreateBox2d(){ // create the body
        BodyDef bdef = new BodyDef();
        FixtureDef def = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.angle = -angle* MathUtils.degreesToRadians;
        bdef.position.set(rect.getX() * Main.PPM + rect.getWidth() / 2 * Main.PPM, rect.getY() * Main.PPM + rect.getHeight() / 2 * Main.PPM);
        body = Main.world.createBody(bdef);
        shape.setAsBox(rect.getWidth() / 2 * Main.PPM, rect.getHeight() / 2 * Main.PPM);
        def.shape = shape;
        def.filter.categoryBits = Main.Filter;
        body.createFixture(def);
        body.getFixtureList().get(0).setUserData(this);
    }
    public int getLayer(){
        return layer;
    }
}
