package com.lqr.opengl.demo1;

import android.opengl.GLU;

import com.lqr.opengl.common.BaseRenderer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * 创建者：CSDN_LQR
 * 描述：三角形渲染器
 */
public class TriangleRenderer extends BaseRenderer {

    // GLSurfaceView创建时
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // 1、设置清屏的颜色和启用顶点缓冲区
        // 设置清屏色
        gl.glClearColor(0, 0, 0, 1);
        // 开启顶点缓冲
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
    }

    // GLSurfaceView大小变化时
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // 1、设置viewport（视口）
        gl.glViewport(0, 0, width, height);
        // 2、操纵投影矩阵，设置平截头体（比例通常和视口比例相同，否则输出画面会走样）
        float ratio = (float) width / (float) height;
        // 设置模式，投影矩阵，openGL基于状态机
        gl.glMatrixMode(GL10.GL_PROJECTION);
        // 加载单位矩阵
        gl.glLoadIdentity();
        // 设置平截头体
        gl.glFrustumf(-ratio, ratio, -1f, 1f, 3, 7);
    }

    // 绘制
    @Override
    public void onDrawFrame(GL10 gl) {
        // 1、清除颜色缓冲区
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        // 2、操纵模型视图矩阵，设置眼球的参数
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        // 设置 眼球位置、观察点、相机方向向量 坐标
        GLU.gluLookAt(gl, 0f, 0f, 5f, 0f, 0f, 0f, 0f, 1f, 0f);

        // 3、定义图形顶点坐标值数组
        float[] coords = new float[]{
                0f, 0.5f, 0f,
                -0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0f
        };

        // 4、将顶点坐标转换成缓冲区数据
        // 分配字节缓存区空间，存放顶点坐标数据
        ByteBuffer ibb = ByteBuffer.allocateDirect(coords.length * 4);
        // 设置缓存区数据顺序（计算机内存数据存储有大小端问题，这里使用本地顺序，即根据当前计算机内存数据顺序决定）
        ibb.order(ByteOrder.nativeOrder());
        // 转成Float缓存后，放置顶点坐标数组
        FloatBuffer fbb = ibb.asFloatBuffer();
        fbb.put(coords);
        // 定位指针的位置，从该位置开始读取顶点数据
        ibb.position(0);

        // 5、设置绘制颜色
        gl.glColor4f(1f, 0, 0, 1f);
        // 6、设置顶点缓冲区指针
        // size:维度（如三维：需要3个坐标来表示一个点）
        // type:每个点的数据类型
        // stride:跨度
        // pointer:指定顶点缓冲区
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, ibb);
        // 绘制三角形
        gl.glDrawArrays(gl.GL_TRIANGLES, 0, 3);
    }
}
