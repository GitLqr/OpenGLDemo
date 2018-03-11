package com.lqr.opengl.demo10;

import android.opengl.GLU;

import com.lqr.opengl.common.BaseRenderer;
import com.lqr.opengl.utils.ShapeUtil;

import javax.microedition.khronos.opengles.GL10;

/**
 * 创建者：CSDN_LQR
 * 描述：混合2个矩形重叠的部分
 * <p>
 * 1、开启混合模式后，深度模式会失效。
 * 2、要先画后面的，再画前面的。
 * 3、Cf = Cs * S + Cd * D     （Cf：是最终颜色；Cs：源颜色；S：源颜色因子；Cd：目标颜色；D：目标颜色因子）。
 * 4、说明：
 * ---1）目标颜色：存储在颜色缓冲区中的颜色值。
 * ---2）源颜色：即将进入颜色缓冲区的颜色值。
 */
public class BlendRenderer1 extends BaseRenderer {

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT|GL10.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        GLU.gluLookAt(gl, 0, 0, 5, 0, 0, 0, 0, 1, 0);

        gl.glRotatef(mRotateX, 1, 0, 0);
        gl.glRotatef(mRotateY, 0, 1, 0);

        // 开启混合模式
        gl.glEnable(GL10.GL_BLEND);
        // 设置混合因子（源颜色*0.5，目标颜色*（1-0.5），这里的0.5是源颜色的透明度值）
        gl.glBlendFunc(GL10.GL_SRC_ALPHA,GL10.GL_ONE_MINUS_SRC_ALPHA);

        // 目标
        float[] back = new float[]{
                -0.2f, 0.5f, -1,
                -0.2f, -0.2f, -1,
                0.5f, 0.5f, -1,
                0.5f, -0.2f, -1,
        };
        gl.glColor4f(1,0,0,1);
        ShapeUtil.drawTriangleStrip(gl, back);

        // 源
        float[] front = new float[]{
                -0.5f, 0.2f, 1,
                -0.5f, -0.5f, 1,
                0.2f, 0.2f, 1,
                0.2f, -0.5f, 1,
        };
        gl.glColor4f(0,0,1,0.5f);
        ShapeUtil.drawTriangleStrip(gl, front);

    }
}
