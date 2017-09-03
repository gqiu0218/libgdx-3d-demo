package com.mygdx.game.android;

import android.util.Log;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class MyGdxGame extends ApplicationAdapter {
    private PerspectiveCamera cam;
    private Array<ModelInstance> instances = new Array<>();
    private ModelBatch modelBatch;
    private Environment environment;
    private AssetManager assets;
    private boolean loading;
    private int mLastY;

    @Override
    public void create() {
        modelBatch = new ModelBatch();
        cam = new PerspectiveCamera(90, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        assets = new AssetManager();
        assets.load("tab.g3db", Model.class);
        loading = true;

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        cam.position.set(0,0,0);
        cam.translate(3,0,8);
        cam.lookAt(0, 0, 0);
        cam.near = 1f;
        cam.far = 300f;
        cam.update();
    }

    private void doneLoading() {
        ModelInstance  model = new ModelInstance(assets.get("tab.g3db", Model.class));
        model.transform.setToRotation(Vector3.Y, 180).trn(0, 0, 6f);
        mLastY = 180;
        model.transform.setToTranslation(0,0,0);
        instances.add(model);
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


    public void rotateToLeft(){
        mLastY += 2;
        if(mLastY>359){
            mLastY = 0;
        }
        Log.e("gqiu","Y轴="+mLastY);
        instances.get(0).transform.setToRotation(Vector3.Y, mLastY).trn(0, 0, 0f);
    }

    public void rotateToRight(){
        mLastY -= 2;
        if(mLastY<0){
            mLastY = 359;
        }

        Log.e("gqiu","Y轴="+mLastY);
        instances.get(0).transform.setToRotation(Vector3.Y, mLastY).trn(0, 0, 0f);
    }
}
