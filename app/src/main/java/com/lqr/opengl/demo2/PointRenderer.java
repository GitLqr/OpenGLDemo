package com.lqr.opengl.demo2;

import android.opengl.GLU;

import com.lqr.opengl.common.BaseRenderer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

/**
 * 创建者：CSDN_LQR
 * 描述：点渲染器(绘制螺旋管，一次绘制多个点)
 */
public class PointRenderer extends BaseRenderer {
    @Override
    public void onDrawFrame(GL10 gl) {
        // 清除颜色缓冲区、设置眼球及观察点
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        GLU.gluLookAt(gl, 0f, 0f, 5f, 0f, 0f, 0f, 0f, 1f, 0f);

        // 坐标旋转
        gl.glRotatef(mRotateX,1,0,0);// 后面3个值可以理解为向量顶点坐标，表示原点到顶点的一个向量。所以(1,0,0)就是指向x轴正方向的向量。
        gl.glRotatef(mRotateY,0,1,0);

        // 生成螺旋管上所有的点数据
        List<Float> points = new ArrayList<>();
        float r = 0.2f;// 半径
        float x = 0f;
        float y = 0f;
        float z = 2f;
        float zStep = 4f / (float) ((Math.PI * 2 * 6) / (Math.PI / 32));// 4f表示近平面与远平面的距离；后面表示所有点的个数。
        for (float alpha = 0; alpha < Math.PI * 2 * 6; alpha = (float) (alpha + Math.PI / 32)) {// 生成6圈，角度间隔是Math.PI / 32的点
            x = (float) (r * Math.cos(alpha));
            y = (float) (r * Math.sin(alpha));
            z = z - zStep;
            points.add(x);
            points.add(y);
            points.add(z);
        }

        // 设置顶点数据、绘制点
        ByteBuffer ibb = ByteBuffer.allocateDirect(points.size() * 4);
        ibb.order(ByteOrder.nativeOrder());
        FloatBuffer fbb = ibb.asFloatBuffer();
        for (float f : points) {
            fbb.put(f);
        }
        ibb.position(0);

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, ibb);
        gl.glDrawArrays(GL10.GL_POINTS, 0, points.size() / 3);

    }
}
