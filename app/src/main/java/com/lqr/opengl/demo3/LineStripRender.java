package com.lqr.opengl.demo3;

import android.opengl.GLU;

import com.lqr.opengl.common.BaseRenderer;
import com.lqr.opengl.utils.BufferUtil;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

/**
 * 创建者：CSDN_LQR
 * 描述：线代渲染器（画螺旋管）
 */
public class LineStripRender extends BaseRenderer {
    @Override
    public void onDrawFrame(GL10 gl) {
        // 清屏
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        GLU.gluLookAt(gl, 0, 0, 5, 0, 0, 0, 0, 1, 0);

        // 旋转坐标系
        gl.glRotatef(mRotateX, 1, 0, 0);
        gl.glRotatef(mRotateY, 0, 1, 0);

        // 得到圆上所有的点
        List<Float> linePoints = new ArrayList<>();
        float r = 0.3f;
        float x = 0;
        float y = 0;
        float z = 0;
        float zStep = 1f / (float) ((Math.PI * 2 * 3) / (Math.PI / 32));
        for (float alpha = 0; alpha < Math.PI * 2 * 3; alpha = (float) (alpha + Math.PI / 32)) {
            x = (float) (r * Math.cos(alpha));
            y = (float) (r * Math.sin(alpha));
            z = z - zStep;

            linePoints.add(x);
            linePoints.add(y);
            linePoints.add(z);
        }

        // 根据点画线
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, BufferUtil.list2Buffer(linePoints));
        gl.glDrawArrays(GL10.GL_LINE_STRIP, 0, linePoints.size() / 3);

    }
}
