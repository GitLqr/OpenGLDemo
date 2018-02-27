package com.lqr.opengl.demo5;

import android.opengl.GLU;

import com.lqr.opengl.common.BaseRenderer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @创建者 CSDN_LQR
 * @时间 2018/2/27
 * @描述 使用模板缓冲区（相当于使用矩阵）实现绘制不同的物体 动画效果，使用模板缓冲区需要硬件支持GPU编程才可以。
 */
public class StencilRenderer extends BaseRenderer {

    public float left, top = 1f, width = 0.3f;
    boolean xadd = false;
    boolean yadd = false;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glClearColor(0f, 0f, 0f, 1f);// 清屏色
        gl.glClearStencil(0);// 模板清除值

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnable(GL10.GL_DEPTH_TEST);// 开启深度测试
        gl.glEnable(GL10.GL_STENCIL_TEST);// 开启模板测试
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);// 设置视口
        ratio = (float) width / (float) height;
        left = -ratio;
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glFrustumf(-ratio, ratio, -1f, 1f, 3f, 7f);// 设置平截头体
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // 清除颜色、深度、模板缓冲区
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT
                | GL10.GL_DEPTH_BUFFER_BIT
                | GL10.GL_STENCIL_BUFFER_BIT
        );
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        GLU.gluLookAt(gl, 0, 0, 5, 0, 0, 0, 0, 1, 0);

        /*================== 绘制白色螺旋线 ==================*/
        gl.glPushMatrix();
        gl.glRotatef(mRotateX, 1, 0, 0);
        gl.glRotatef(mRotateY, 0, 1, 0);

//        gl.glShadeModel(GL10.GL_FLAT);// 设置单调着色

        List<Float> vertexList = new ArrayList<>();
        float x = 0, y = 0.8f, z = 0f, yStep = 0.005f, r = 0.7f;
        for (float angle = 0f; angle < (Math.PI * 2 * 3); angle += (Math.PI / 40)) {
            x = (float) (r * Math.cos(angle));
            z = -(float) (r * Math.sin(angle));
            y = y - yStep;
            vertexList.add(x);
            vertexList.add(y);
            vertexList.add(z);
        }

        ByteBuffer vbb = ByteBuffer.allocateDirect(vertexList.size() * 4);
        vbb.order(ByteOrder.nativeOrder());
        FloatBuffer fvb = vbb.asFloatBuffer();
        for (Float f : vertexList) {
            fvb.put(f);
        }
        fvb.position(0);

        // 设置模板函数：所有操作都不能通过测试，但是对模板缓冲区的值进行增加
        gl.glStencilFunc(GL10.GL_NEVER, 1, 0);
        gl.glStencilOp(GL10.GL_INCR, GL10.GL_INCR, GL10.GL_INCR);

        // 绘制白色螺旋线
        gl.glColor4f(1f, 1f, 1f, 1f);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, fvb);
        gl.glDrawArrays(GL10.GL_LINE_STRIP, 0, vertexList.size() / 3);
        gl.glPopMatrix();

        /*================== 绘制红色方块 ==================*/
        if (xadd) {
            left += 0.01f;
        } else {
            left -= 0.01f;
        }
        if (left <= (-ratio)) {
            xadd = true;
        }
        if (left >= (ratio - width)) {
            xadd = false;
        }

        if (yadd) {
            top += 0.01f;
        } else {
            top -= 0.01f;
        }
        if (top >= 1) {
            yadd = false;
        }
        if (top <= (-1 + width)) {
            yadd = true;
        }

        float[] rectVertex = {
                left, top - width, 2f,
                left, top, 2f,
                left + width, top - width, 2f,
                left + width, top, 2f
        };
        vbb = ByteBuffer.allocateDirect(3 * 4 * 4);
        vbb.order(ByteOrder.nativeOrder());
        FloatBuffer rectfb = vbb.asFloatBuffer();
        rectfb.put(rectVertex);
        rectfb.position(0);

        // 设置模板函数：
        gl.glStencilFunc(GL10.GL_EQUAL, 1, 1);
        gl.glStencilOp(GL10.GL_KEEP, GL10.GL_KEEP, GL10.GL_KEEP);

        gl.glColor4f(1f, 0f, 0f, 1f);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, rectfb);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
    }

}
