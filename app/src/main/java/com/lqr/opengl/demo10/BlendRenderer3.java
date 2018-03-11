package com.lqr.opengl.demo10;

import android.opengl.GLU;

import com.lqr.opengl.common.BaseRenderer;
import com.lqr.opengl.utils.ShapeUtil;

import javax.microedition.khronos.opengles.GL10;

/**
 * 创建者：CSDN_LQR
 * 描述：三个不同轴的圆，圆上各有一个小球在运动
 * <p>
 * 说明：
 * 1、gl.glPushMatrix()与gl.glPopMatrix()类似画布剪切前后的保存与复原。
 * 2、因为gl.glRotatef()会影响整个三维坐标系，为了之后的绘制不被前面的旋转所影响，需要对原来的矩阵进行保存和恢复。
 */
public class BlendRenderer3 extends BaseRenderer {

    float r = 0.4f;
    float[] circle_center = new float[]{0, 0, 0};
    float redAngle = 0;
    float blueAngle = 45;
    float yellowAngle = 90;

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        GLU.gluLookAt(gl, 0, 0, 5, 0, 0, 0, 0, 1, 0);

        gl.glRotatef(mRotateX, 1, 0, 0);
        gl.glRotatef(mRotateY, 0, 1, 0);

        // 开启混合模式并设置混合因子
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        // 开启点平滑模式（让点搞锯齿）并设置暗示质量为主
        gl.glEnable(GL10.GL_POINT_SMOOTH);// 模拟器上可能渲染不出来，可以在真机上尝试
        gl.glHint(GL10.GL_POINT_SMOOTH_HINT, GL10.GL_NICEST);

        // 设置点的大小，方便观看效果。
        gl.glPointSize(20f);

        gl.glPushMatrix();
        // 红色圆圈（x,y面）
        gl.glColor4f(1, 0, 0, 1);
        ShapeUtil.drawCircle(gl, r);
        // 红色点
        gl.glTranslatef((float) (r * Math.cos(redAngle)), (float) (r * Math.sin(redAngle)), 0);
        ShapeUtil.drawPoint(gl, circle_center);
        gl.glPopMatrix();

        gl.glPushMatrix();
        // 蓝色圆圈（z,x面）
        gl.glColor4f(0, 0, 1, 1);
        gl.glRotatef(90, 1, 0, 0);
        gl.glRotatef(45, 0, 1, 0);
        ShapeUtil.drawCircle(gl, r);
        // 蓝色点
        gl.glTranslatef((float) (r * Math.cos(blueAngle)), (float) (r * Math.sin(blueAngle)), 0);
        ShapeUtil.drawPoint(gl, circle_center);
        gl.glPopMatrix();

        gl.glPushMatrix();
        // 黄色圆圈（y,z面）
        gl.glColor4f(1, 1, 0, 1);
        gl.glRotatef(-90, 0, 1, 0);
        gl.glRotatef(45, 1, 0, 0);
        ShapeUtil.drawCircle(gl, r);
        // 黄色点
        gl.glTranslatef((float) (r * Math.cos(yellowAngle)), (float) (r * Math.sin(yellowAngle)), 0);
        ShapeUtil.drawPoint(gl, circle_center);
        gl.glPopMatrix();

        redAngle += 0.1f;
        blueAngle += 0.1f;
        yellowAngle += 0.1f;
    }
}
