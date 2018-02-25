package com.lqr.opengl.demo1;

import android.opengl.GLU;

import com.lqr.opengl.common.BaseRenderer;
import com.lqr.opengl.utils.BufferUtil;

import javax.microedition.khronos.opengles.GL10;

/**
 * 创建者：CSDN_LQR
 * 描述：使用三角形代绘制正方形
 */
public class TriangleStripRenderer extends BaseRenderer {
    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        GLU.gluLookAt(gl, 0, 0, 5, 0, 0, 0, 0, 1, 0);

        gl.glRotatef(mRotateX,1,0,0);
        gl.glRotatef(mRotateY,0,1,0);

        float[] coords = new float[]{
                -0.5f, 0.5f, 0f,
                -0.5f, -0.5f, 0f,
                0.5f, 0.5f, 0f,
                0.5f, -0.5f, 0f
        };
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, BufferUtil.arr2Buffer(coords));
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
    }
}
