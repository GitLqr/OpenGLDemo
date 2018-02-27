package com.lqr.opengl.demo5;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.lqr.opengl.common.MyGLSurfaceView;

/**
 * @创建者 CSDN_LQR
 * @时间 2018/2/27
 * @描述 模板
 */
public class StencilActivity extends AppCompatActivity {

    private StencilRenderer mRenderer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyGLSurfaceView view = new MyGLSurfaceView(this);
        view.setEGLConfigChooser(5, 6, 5, 0, 16, 8);// 必须在setRenderer()之前设置
        mRenderer = new StencilRenderer();
        view.setRenderer(mRenderer);
        view.setRenderMode(MyGLSurfaceView.RENDERMODE_CONTINUOUSLY);
        setContentView(view);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        float step = 5f;
        //up
        if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
            mRenderer.mRotateX = mRenderer.mRotateX - step;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
            mRenderer.mRotateX = mRenderer.mRotateX + step;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
            mRenderer.mRotateY = mRenderer.mRotateY - step;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
            mRenderer.mRotateY = mRenderer.mRotateY + step;
        }
        return super.onKeyDown(keyCode, event);
    }
}
