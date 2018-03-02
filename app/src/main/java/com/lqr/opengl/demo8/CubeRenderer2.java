package com.lqr.opengl.demo8;

import android.opengl.GLU;

import com.lqr.opengl.common.BaseRenderer;
import com.lqr.opengl.utils.BufferUtil;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * 创建者：CSDN_LQR
 * 描述：有颜色正方体渲染器（三角形、颜色缓冲区）
 */
public class CubeRenderer2 extends BaseRenderer {

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glClearColor(0f, 0f, 0f, 1f);
        // 开启顶点缓冲区
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        // 开启颜色缓冲区
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        // 开启深度测试
        gl.glEnable(GL10.GL_DEPTH_TEST);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        GLU.gluLookAt(gl, 0, 0, 5, 0, 0, 0, 0, 1, 0);

        gl.glRotatef(mRotateX, 1, 0, 0);
        gl.glRotatef(mRotateY, 0, 1, 0);

        float r = 0.4f;// 正方体各边长的一半
        // 8个顶点坐标
        float[] coords = new float[]{
                -r, r, r,// front：left top
                -r, -r, r,// front：left bottom
                r, r, r,// front：right top
                r, -r, r,// front：right bottom

                -r, r, -r,// back：left top
                -r, -r, -r,// back：left bottom
                r, r, -r,// back：right top
                r, -r, -r,// back：right bottom
        };
        // 顶点索引
        byte[] indices = new byte[]{
                0, 1, 2, 2, 1, 3, // front
                4, 5, 6, 6, 5, 7, // back
                0, 1, 4, 4, 1, 5, // left
                2, 3, 6, 6, 3, 7, // right
                4, 0, 2, 4, 2, 6, // top
                5, 1, 3, 5, 3, 7, // bottom
        };

        // 顶点颜色
        float[] colors = new float[]{
                0, 1, 1, 1,// g + b
                0, 1, 0, 1,// g
                1, 1, 1, 1,// white
                1, 1, 0, 1,// g + r

                0, 0, 1, 1,// b
                0, 0, 0, 1,// black
                1, 0, 1, 1,// b + r
                1, 0, 0, 1,// r

        };

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, BufferUtil.arr2FloatBuffer(coords));
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, BufferUtil.arr2FloatBuffer(colors));
        gl.glDrawElements(GL10.GL_TRIANGLES, indices.length, GL10.GL_UNSIGNED_BYTE, BufferUtil.arr2Buffer(indices));
    }
}
