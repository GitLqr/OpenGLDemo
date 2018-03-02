package com.lqr.opengl.demo8;

import android.opengl.GLU;

import com.lqr.opengl.common.BaseRenderer;
import com.lqr.opengl.utils.BufferUtil;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * 创建者：CSDN_LQR
 * 描述：正方体渲染器(线带、顶点索引、正交投影)
 * <p>
 * 将正方体中心置于坐标系中心，获取8个顶点的坐标，按照顺时针顶点索引绘制。（可以将正方体拆成平面进行分析）
 */
public class CubeRenderer1 extends BaseRenderer {

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        ratio = (float) width / (float) height;
        gl.glOrthof(-ratio, ratio, -1f, 1f, 3f, 7f);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
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
                0, 1, 5, 4,
                0, 4, 6, 2,
                6, 7, 3,
                7, 5, 1,
                0, 2, 3, 1,
        };

        // 使用线带绘制正方体
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, BufferUtil.arr2FloatBuffer(coords));
        gl.glDrawElements(GL10.GL_LINE_STRIP, indices.length, GL10.GL_UNSIGNED_BYTE, BufferUtil.arr2Buffer(indices));// 指定顶点索引需要用到glDrawElements()
    }
}
