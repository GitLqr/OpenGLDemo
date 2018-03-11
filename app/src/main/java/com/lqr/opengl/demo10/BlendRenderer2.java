package com.lqr.opengl.demo10;

import android.opengl.GLU;

import com.lqr.opengl.common.BaseRenderer;
import com.lqr.opengl.utils.ShapeUtil;

import javax.microedition.khronos.opengles.GL10;

/**
 * 创建者：CSDN_LQR
 * 描述：使用混合模式（点平滑、线平滑）实现抗锯齿
 */
public class BlendRenderer2 extends BaseRenderer {
    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        GLU.gluLookAt(gl, 0, 0, 5, 0, 0, 0, 0, 1, 0);

        gl.glRotatef(mRotateX, 1, 0, 0);
        gl.glRotatef(mRotateY, 0, 1, 0);

        // 开启混合模式
        gl.glEnable(GL10.GL_BLEND);
        // 设置混合因子
        gl.glBlendFunc(GL10.GL_SRC_ALPHA,GL10.GL_ONE_MINUS_SRC_ALPHA);

        // 启用点平滑
        gl.glEnable(GL10.GL_POINT_SMOOTH);
        // 设置点平滑暗示（这里暗示以快为主）
        gl.glHint(GL10.GL_POINT_SMOOTH_HINT,GL10.GL_NICEST);
        // 启用线平滑
        gl.glEnable(GL10.GL_LINE_SMOOTH);
        // 设置线平滑暗示（这里暗示以质量为主）
        gl.glHint(GL10.GL_LINE_SMOOTH_HINT,GL10.GL_NICEST);

        float[] point = new float[]{
                0.5f, 0.3f, 1,
        };
        gl.glPointSize(35f);
        gl.glColor4f(0,0,1,1);
        ShapeUtil.drawPoint(gl, point);

        float[] lineStrip = new float[]{
                -0.5f, -0.2f, 1,
                -0.2f, 0.2f, 1,
                0.2f, -0.2f, 1,
                0.5f, 0.5f, 1,
        };
        gl.glLineWidth(5f);
        gl.glColor4f(0,1,0,1);
        ShapeUtil.drawLineStrip(gl, lineStrip);
    }
}
