package com.lqr.opengl.common;

import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * 创建者：CSDN_LQR
 * 描述：抽象渲染器
 */
public abstract class BaseRenderer implements GLSurfaceView.Renderer{
    public float mRotateX = 0;
    public float mRotateY = 0;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // 清屏并开启顶点缓冲区
        gl.glClearColor(0, 0, 0, 1f);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // 设置视口与平截头体
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        float ratio = (float) width / (float) height;
        gl.glFrustumf(-ratio, ratio, -1f, 1f, 3f, 7f);
    }
}
