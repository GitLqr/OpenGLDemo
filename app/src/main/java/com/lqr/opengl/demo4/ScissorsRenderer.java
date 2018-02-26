package com.lqr.opengl.demo4;

import android.opengl.GLU;

import com.lqr.opengl.common.BaseRenderer;
import com.lqr.opengl.utils.BufferUtil;

import javax.microedition.khronos.opengles.GL10;

/**
 * 创建者：CSDN_LQR
 * 描述：剪裁渲染器（使用剪裁，在z值相同的情况下，绘制多个矩形重叠。）
 * <p>
 * 剪裁：控制绘制区域。
 * -------1）开启：gl.glEnable(GL10.GL_SCISSOR_TEST);
 * -------2）剪裁：gl.glScissor(x,y,width,height);
 */
public class ScissorsRenderer extends BaseRenderer {

    int width, height;

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        super.onSurfaceChanged(gl, width, height);
        this.width = width;
        this.height = height;
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        // 开启剪裁
        gl.glEnable(GL10.GL_SCISSOR_TEST);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        GLU.gluLookAt(gl, 0, 0, 5, 0, 0, 0, 0, 1, 0);

        float z = 2f;
        float[] coords = new float[]{
                -ratio, 1f, z,
                -ratio, -1f, z,
                ratio, 1f, z,
                ratio, -1f, z
        };
        float[][] colors = new float[][]{
                new float[]{1f, 0f, 0f, 1f},
                new float[]{0f, 1f, 0f, 1f},
                new float[]{0f, 0f, 1f, 1f},
                new float[]{1f, 1f, 0f, 1f},
                new float[]{0f, 1f, 1f, 1f},
                new float[]{1f, 0f, 1f, 1f},
        };

        int step = 30;
        for (int i = 0; i < colors.length; i++) {
            // 剪裁
            gl.glScissor(i * step, i * step, width - (i * step * 2), height - (i * step * 2));

            gl.glColor4f(colors[i][0], colors[i][1], colors[i][2], colors[i][3]);
            gl.glVertexPointer(3, GL10.GL_FLOAT, 0, BufferUtil.arr2Buffer(coords));
            gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
        }
    }
}
