package com.lqr.opengl.demo7;

import android.opengl.GLU;

import com.lqr.opengl.common.BaseRenderer;
import com.lqr.opengl.utils.BufferUtil;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

/**
 * 创建者：CSDN_LQR
 * 描述：圆环渲染器
 */
public class RingRenderer extends BaseRenderer {


    @Override
    public void onDrawFrame(GL10 gl) {
        // 清屏、设置眼球与观察点
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        GLU.gluLookAt(gl, 0, 0, 5, 0, 0, 0, 0, 1, 0);

        // 坐标系旋转
        gl.glRotatef(mRotateX, 1, 0, 0);
        gl.glRotatef(mRotateY, 0, 1, 0);

        float Rinner = 0.2f;// 内环半径
        float Router = 0.3f;// 外环半径（厚度的一半）
        float x0, y0, z0, x1, y1, z1;
        int count0 = 60;// 整个环（大圆）的切割数（从z轴正方向往负方向看）
        int count1 = 60;// 外环管（小圆）的切割数（从x轴负方向往正方向看）

        float alpha;// 大圆 从x轴开始的旋转角
        float alphaStep = ((float) (Math.PI * 2) / count0);
        float beta;// 小圆 从z轴开始的旋转角
        float betaStep =  ((float)(Math.PI * 2) / count1);

        List<Float> coords = new ArrayList<>();

        // 获取环上每个点
        for (int i = 0; i < count0; i++) {
            alpha = i * alphaStep;
            for (int j = 0; j < count1; j++) {
                beta = j * betaStep;

                // lP = Router * Math.cos(beta) ： 得到环上点垂直到大圆半径上的点（称为点P）到小圆中心的距离
                // length = Rinner + Router + lP ：得到点P到大圆圆心的距离
                float length = (float) (Rinner + Router * (1 + Math.cos(beta)));

                // 算出第一个小圆上点的坐标
                x0 = (float) Math.cos(alpha) * length;
                y0 = (float) (Math.sin(alpha) * length);
                z0 = -(float) (Math.sin(beta) * Router);

                // 算出下一个小圆上点的坐标
                x1 = (float) (Math.cos(alpha + alphaStep) * length);
                y1 = (float) (Math.sin(alpha + alphaStep) * length);
                z1 = z0;

                coords.add(x0);
                coords.add(y0);
                coords.add(z0);
                coords.add(x1);
                coords.add(y1);
                coords.add(z1);

            }
        }

        // 使用线带绘制整个环
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, BufferUtil.list2Buffer(coords));
        gl.glDrawArrays(GL10.GL_LINE_STRIP, 0, coords.size() / 3);

    }
}
