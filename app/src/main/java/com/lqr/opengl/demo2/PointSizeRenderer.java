package com.lqr.opengl.demo2;

import android.opengl.GLU;

import com.lqr.opengl.common.BaseRenderer;
import com.lqr.opengl.utils.BufferUtil;

import javax.microedition.khronos.opengles.GL10;

/**
 * 创建者：CSDN_LQR
 * 描述：点渲染器（设置点大小，一次绘制一个点）
 */
public class PointSizeRenderer extends BaseRenderer {

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        GLU.gluLookAt(gl, 0, 0, 5, 0, 0, 0, 0, 1, 0);

        // 坐标旋转
        gl.glRotatef(mRotateX, 1, 0, 0);
        gl.glRotatef(mRotateY, 0, 1, 0);

        float r = 0.2f;
        float x;
        float y;
        float z = 2f;
        float zStep = 4f / (float) ((Math.PI * 2 * 6) / (Math.PI / 32));
        float size = 10f;
        float sizeStep = 0.02f;
        for (float alpha = 0; alpha < Math.PI * 2 * 6; alpha = (float) (alpha + Math.PI / 32)) {
            x = (float) (r * Math.cos(alpha));
            y = (float) (r * Math.sin(alpha));
            z = z - zStep;
            gl.glPointSize(size -= sizeStep);
            gl.glVertexPointer(3, GL10.GL_FLOAT, 0, BufferUtil.arr2Buffer(new float[]{x, y, z}));
            gl.glDrawArrays(GL10.GL_POINTS, 0, 1);
        }

    }
}
