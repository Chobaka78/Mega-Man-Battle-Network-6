package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

public class Main extends ApplicationAdapter {
    SpriteBatch batch;
    Texture img;
    public static final float PPM = 0.3f;
    public static OrthographicCamera camera;
    public static int moves1;
    static boolean animation, shop = false, moveBody, House = false, Weirdplace = false, Map2 = false;
    public static final int UP = 0, Down = 1, Left = 2, Right = 3, NW = 4, SW = 5, NE = 6;
    private Player p;

    static WorldCreator worldCreator;

    public static World world;

    private TiledMap map;

    private TmxMapLoader mapLoader;

    private OrthogonalTiledMapRenderer renderer;

    Box2DDebugRenderer b2dr;



    @Override
    public void create() {
        world = new World(new Vector2(0,0),true);

        batch = new SpriteBatch();
        //img = new Texture("Assets/TitleScreen.png");
        img = new Texture("Assets/player assets/Lan's room.png");
        camera = new OrthographicCamera(720f, 480f);
        p = new Player();

        mapLoader = new TmxMapLoader();

        map = mapLoader.load("Assets/Maps/Map.tmx");

		renderer = new OrthogonalTiledMapRenderer(map,PPM);

        world.setContactListener(new WorldContactListener());

        WorldCreator.Boundaries(world,map.getLayers().get("Boundary").getObjects());

        b2dr = new Box2DDebugRenderer();

    }

    @Override
    public void render() {
        camera.zoom = 0.1f;
        world.step(1/60f,6,2);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        renderer.setView(camera);
		renderer.render();

        batch.setProjectionMatrix(camera.combined);

        b2dr.render(world,camera.combined);

        System.out.println(p.getX() + " , " + p.getY());


        batch.begin();
        update();
        batch.end();
        move();
    }

    public void update(){
        p.update(batch);
    }


    public void move() {
        p.body.setLinearVelocity(0, 0);


        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {// moves the player up
            moves1 = UP;
            System.out.println("up");
            p.getBody().applyLinearImpulse(new Vector2(0, 80), p.getBody().getWorldCenter(), true);
            animation = true;
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            moves1 = Down;
            System.out.println("down");
            p.getBody().applyLinearImpulse(new Vector2(0, -80), p.getBody().getWorldCenter(), true);
            animation = true;
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            moves1 = Left;
            System.out.println("left");
			p.getBody().applyLinearImpulse(new Vector2(-80, 0), p.getBody().getWorldCenter(), true);
            animation = true;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            moves1 = Right;
            System.out.println("right");
            p.getBody().applyLinearImpulse(new Vector2(80, 0), p.getBody().getWorldCenter(), true);
            animation = true;
        } else {
            p.getBody().applyLinearImpulse(new Vector2(p.getBody().getLinearVelocity().x * -1, p.getBody().getLinearVelocity().y * -1), p.getBody().getWorldCenter(), true);
            animation = false;
            p.frames = 0;
        }

	    p.setX(p.body.getPosition().x);
	    p.setY(p.body.getPosition().y);

	    camera.position.x = p.getX();
	    camera.position.y = p.getY();

    }



    @Override
    public void dispose () {
        batch.dispose();
        img.dispose();
    }
}
