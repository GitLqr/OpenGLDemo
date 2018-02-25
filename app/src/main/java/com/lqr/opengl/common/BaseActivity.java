package com.lqr.opengl.common;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

/**
 * 创建者：CSDN_LQR
 * 描述：OpenGl例子的BaseActivity
 */
public abstract class BaseActivity extends AppCompatActivity {

    private MyGLSurfaceView myGLSurfaceView;
    private BaseRenderer renderer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myGLSurfaceView = new MyGLSurfaceView(this);
        renderer = getRenderer();
        myGLSurfaceView.setRenderer(renderer);
        // myGLSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);// 持续渲染（默认渲染模式）
        if (useDirtyMode()) {
            myGLSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);// 脏渲染（也叫：命令渲染），需要与requestRender()配合使用，可提高性能。
        }
        setContentView(myGLSurfaceView);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        float step = 1f;
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_DPAD_UP:
                    renderer.mRotateX -= step;
                    break;
                case KeyEvent.KEYCODE_DPAD_DOWN:
                    renderer.mRotateX += step;
                    break;
                case KeyEvent.KEYCODE_DPAD_LEFT:
                    renderer.mRotateY -= step;
                    break;
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    renderer.mRotateY += step;
                    break;
            }
            if (useDirtyMode()) {
                myGLSurfaceView.requestRender();// 请求渲染
            }
        }
        return true;
    }

    protected abstract BaseRenderer getRenderer();

    /**
     * 是否使用脏渲染
     *
     * @return 默认是true
     */
    protected boolean useDirtyMode() {
        return true;
    }

}
