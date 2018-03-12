package com.lqr.opengl.demo11;

import android.opengl.GLU;

import com.lqr.opengl.common.BaseRenderer;
import com.lqr.opengl.utils.BufferUtil;
import com.lqr.opengl.utils.ShapeUtil;

import javax.microedition.khronos.opengles.GL10;

/**
 * 创建者：CSDN_LQR
 * 描述：雾渲染器
 */
public class FogRenderer extends BaseRenderer {

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        GLU.gluLookAt(gl, 0, 0, 5, 0, 0, 0, 0, 1, 0);

        gl.glRotatef(mRotateX, 1, 0, 0);
        gl.glRotatef(mRotateY, 0, 1, 0);

        // 开启雾
        gl.glEnable(GL10.GL_FOG);
        // 设置雾的颜色
        float[] flog_color = new float[]{0,0,0,1};
        gl.glFogfv(GL10.GL_FOG_COLOR, BufferUtil.arr2FloatBuffer(flog_color));

        // 设置雾模式（线性雾，需要设置开始与结束）
        gl.glFogx(GL10.GL_FOG_MODE,GL10.GL_LINEAR);
        // 设置雾的开始与结束距离（只对Linear有效）
        gl.glFogf(GL10.GL_FOG_START,3f);
        gl.glFogf(GL10.GL_FOG_END,7f);

//        // 设置雾模式（Exp与Exp2，需要雾密度）
//        gl.glFogx(GL10.GL_FOG_MODE,GL10.GL_EXP);
//        // 设置雾的密度（对Linear无效）
//        gl.glFogf(GL10.GL_FOG_DENSITY,0.3f);

        gl.glColor4f(0, 0, 1, 1f);
        float R = 0.2f;
        gl.glTranslatef(-ratio + R, 0, 0);
        for (int i = 0; i < 3; i++) {
            ShapeUtil.drawSphere(gl, R, 12, 12);
            gl.glTranslatef(2 * R, 0, 0);
            gl.glTranslatef(0, 0, -0.5f);
        }
    }
}
