package com.mygdx.game.android;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.SpotLight;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class MyGdxGame extends ApplicationAdapter {
    private PerspectiveCamera cam;
    private Array<ModelInstance> instances = new Array<>();
    private ModelBatch modelBatch;
    private Environment environment;
    private AssetManager assets;
    private boolean loading;

    @Override
    public void create() {
        modelBatch = new ModelBatch();
        cam = new PerspectiveCamera(90, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        assets = new AssetManager();
        assets.load("person.g3dj", Model.class);
//        assets.load("constructor_done.tif", Texture.class);
//        assets.load("constructor_normals.tif", Texture.class);
        assets.update();

        loading = true;

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new SpotLight().set(Color.RED,0,0,40,0,0,0,0.5f,0.5f));

        cam.position.set(0, 0, 40);
        cam.translate(3, 0, 8);
        cam.lookAt(0, 0, 0);
        cam.near = 1f;
        cam.far = 300f;
        cam.update();
    }

    private void doneLoading() {
        Model model = assets.get("person.g3dj", Model.class);
        ModelInstance modelInstance = new ModelInstance(model);
        modelInstance.transform.setToRotation(Vector3.Y, 0);
        modelInstance.transform.setToTranslation(0, 0, 0);
        modelInstance.transform.scale(0.2f, 0.2f, 0.2f);
        instances.add(modelInstance);
        loading = false;
    }


    @Override
    public void render() {
        if (loading && assets.update()) {
            doneLoading();
        }

        cam.update();

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        modelBatch.begin(cam);
        modelBatch.render(instances, environment);
        modelBatch.end();
    }

    @Override
    public void dispose() {
        modelBatch.dispose();
        instances.clear();
        assets.dispose();
    }

    @Override
    public void resume() {
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
    }


    public void rotateToLeft() {
        instances.get(0).transform.rotateRad(Vector3.Y, 0.1f);
    }

    public void rotateToRight() {
        instances.get(0).transform.rotateRad(Vector3.Y, -0.1f);
    }
}
