package com.lqr.opengl.demo1;

import android.opengl.GLU;

import com.lqr.opengl.common.BaseRenderer;
import com.lqr.opengl.utils.BufferUtil;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @创建者 CSDN_LQR
 * @时间 2018/2/26
 * @描述 使用三角形扇绘制棱锥(加上 底面 + 深度测试)
 */
public class TriangleFanRenderer1 extends BaseRenderer {

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // 清屏
        gl.glClearColor(0, 0, 0, 1f);
        // 开启顶点缓冲区
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        // 开启颜色缓冲区
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // 清除颜色缓冲区、深度缓冲区
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        // 设置颜色着色模式，默认是平滑着色模式：GL10.GL_SMOOTH
        gl.glShadeModel(GL10.GL_FLAT);// 修改为单调着色模式
        // 开启深度测试（不开启的话，在应该看不到锥面的时候，锥面还是会被绘制出来）
        gl.glEnable(GL10.GL_DEPTH_TEST);

        // 设置眼球位置与观察点
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        GLU.gluLookAt(gl, 0, 0, 5, 0, 0, 0, 0, 1, 0);

        // 旋转坐标系
        gl.glRotatef(mRotateX, 1, 0, 0);
        gl.glRotatef(mRotateY, 0, 1, 0);

        List<Float> topCoords = new ArrayList<>();
        List<Float> bottomCoords = new ArrayList<>();
        List<Float> colors = new ArrayList<>();
        // 设置锥面顶点坐标
        topCoords.add(0f);
        topCoords.add(0f);
        topCoords.add(0.2f);
        // 设置锥顶底点坐标
        bottomCoords.add(0f);
        bottomCoords.add(0f);
        bottomCoords.add(-0.2f);
        // 设置锥面顶点颜色（红色）
        colors.add(1f);// r
        colors.add(0f);// g
        colors.add(0f);// b
        colors.add(1f);// a
        // 计算出圆上的点
        float r = 0.2f;
        float x = 0f;
        float y = 0f;
        float z = -0.2f;
        boolean isRed = false;
        for (float alpha = 0; alpha < Math.PI * 2 * 3; alpha = (float) (alpha + Math.PI / 8)) {
            x = (float) (r * Math.cos(alpha));
            y = (float) (r * Math.sin(alpha));
            // 锥面
            topCoords.add(x);
            topCoords.add(y);
            topCoords.add(z);
            // 锥底
            bottomCoords.add(x);
            bottomCoords.add(y);
            bottomCoords.add(z);

            if (isRed = !isRed) { // 红色
                colors.add(1f);
                colors.add(0f);
                colors.add(0f);
                colors.add(1f);
            } else { // 黄色
                colors.add(1f);
                colors.add(1f);
                colors.add(0f);
                colors.add(1f);
            }
        }

        // 多添加一个颜色，方便绘制锥底时与锥面的颜色错开
        if (!isRed) { // 红色
            colors.add(1f);
            colors.add(0f);
            colors.add(0f);
            colors.add(1f);
        } else { // 黄色
            colors.add(1f);
            colors.add(1f);
            colors.add(0f);
            colors.add(1f);
        }

        // 绘制锥面
        ByteBuffer colorBuffer = BufferUtil.list2Buffer(colors);
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, BufferUtil.list2Buffer(topCoords));
        gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, topCoords.size() / 3);

        // 绘制锥底
        colorBuffer = BufferUtil.list2Buffer(colors.subList(4, colors.size()));
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, BufferUtil.list2Buffer(bottomCoords));
        gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, bottomCoords.size() / 3);
    }
}
