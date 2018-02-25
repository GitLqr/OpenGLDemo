package com.lqr.opengl.demo3;

import android.opengl.GLU;

import com.lqr.opengl.common.BaseRenderer;
import com.lqr.opengl.utils.BufferUtil;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

/**
 * 创建者：CSDN_LQR
 * 描述：线渲染器
 */
public class LineRenderer extends BaseRenderer {

    @Override
    public void onDrawFrame(GL10 gl) {
        // 清屏
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        // 设置眼球及观察点
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        GLU.gluLookAt(gl, 0, 0, 5, 0, 0, 0, 0, 1, 0);

        // 旋转坐标系
        gl.glRotatef(mRotateX, 1, 0, 0);
        gl.glRotatef(mRotateY, 0, 1, 0);

        // 得到圆上所有的点
        List<Float> linePoints = new ArrayList<>();
        float r = 0.5f;
        float x = 0f;
        float y = 0f;
        for (float alpha = 0; alpha < Math.PI * 2 * 6; alpha = (float) (alpha + Math.PI / 32)) {
            x = (float) (r * Math.cos(alpha));
            y = (float) (r * Math.sin(alpha));

            linePoints.add(0f);
            linePoints.add(0f);

            linePoints.add(x);
            linePoints.add(y);
        }

        // 根据点画线
        gl.glVertexPointer(2, gl.GL_FLOAT, 0, BufferUtil.list2Buffer(linePoints));
        gl.glDrawArrays(GL10.GL_LINES, 0, linePoints.size() / 2);
    }
}
