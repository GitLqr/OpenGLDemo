package com.lqr.opengl.utils;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

/**
 * 创建者：CSDN_LQR
 * 描述：形状绘制工具
 */
public class ShapeUtil {

    /**
     * 绘制一个球
     *
     * @param gl
     * @param R     球的半径
     * @param layer 球的层数(越大越好)
     * @param slice 层的片数(越大越好)
     */
    public static void drawSphere(GL10 gl, float R, int layer, int slice) {
        List<Float> coords = new ArrayList<>();

        float layerStep = (float) (Math.PI / layer);// 层上的点到圆心形成的线与球的中心轴之间的夹角的角度增量
        float sliceStep = (float) (2 * Math.PI / slice * 2);// bate角增量

        // 一个层面分上下层：
        // r0:下层切面圆的半径；r1:上层切面圆的半径。
        // x0:下层切面圆周上点的x值；x1:上层切面圆周上点的x值。
        // y0:下层切面圆周上点的y值；y1:上层切面圆周上点的y值。
        // z0:下层切面圆周上点的z值；z1:上层切面圆周上点的z值。
        float r0, r1, x0, x1, y0, y1, z0, z1;
        // alpha：层上的点到圆心形成的线与层切面中心之间的夹角。
        float alpha0, alpha1;
        float beta = 0;
        for (int i = 0; i < layer; i++) {
            alpha0 = (float) (Math.PI / 2 - (i * layerStep));
            alpha1 = (float) (Math.PI / 2 - ((i + 1) * layerStep));
            y0 = (float) (R * Math.sin(alpha0));
            r0 = (float) (R * Math.cos(alpha0));
            y1 = (float) (R * Math.sin(alpha1));
            r1 = (float) (R * Math.cos(alpha1));

            for (int j = 0; j <= slice * 2; j++) {
                beta = j * sliceStep;
                x0 = (float) (r0 * Math.cos(beta));
                z0 = -(float) (r0 * Math.sin(beta));
                x1 = (float) (r1 * Math.cos(beta));
                z1 = -(float) (r1 * Math.sin(beta));
                coords.add(x0);
                coords.add(y0);
                coords.add(z0);
                coords.add(x1);
                coords.add(y1);
                coords.add(z1);
            }
        }

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, BufferUtil.list2Buffer(coords));
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, coords.size() / 3);
    }

    /**
     * 绘制正方体
     *
     * @param gl
     * @param r
     */
    public static void drawCube(GL10 gl, float r) {
        // 8个顶点坐标
        float[] coords = new float[]{
                -r, r, r,// front：left top
                -r, -r, r,// front：left bottom
                r, r, r,// front：right top
                r, -r, r,// front：right bottom

                -r, r, -r,// back：left top
                -r, -r, -r,// back：left bottom
                r, r, -r,// back：right top
                r, -r, -r,// back：right bottom
        };
        // 顶点索引
        byte[] indices = new byte[]{
                0, 1, 5, 4,
                0, 4, 6, 2,
                6, 7, 3,
                7, 5, 1,
                0, 2, 3, 1,
        };

        // 使用线带绘制正方体
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, BufferUtil.arr2FloatBuffer(coords));
        gl.glDrawElements(GL10.GL_LINE_STRIP, indices.length, GL10.GL_UNSIGNED_BYTE, BufferUtil.arr2Buffer(indices));// 指定顶点索引需要用到glDrawElements()
    }

    /**
     * 绘制点
     *
     * @param gl
     * @param coords
     */
    public static void drawPoint(GL10 gl, float[] coords) {
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, BufferUtil.arr2Buffer(coords));
        gl.glDrawArrays(GL10.GL_POINTS, 0, coords.length / 3);
    }

    /**
     * 绘制线带
     *
     * @param gl
     * @param coords
     */
    public static void drawLineStrip(GL10 gl, float[] coords) {
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, BufferUtil.arr2Buffer(coords));
        gl.glDrawArrays(GL10.GL_LINE_STRIP, 0, coords.length / 3);
    }

    /**
     * 绘制三角形带
     *
     * @param gl
     * @param coords
     */
    public static void drawTriangleStrip(GL10 gl, float[] coords) {
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, BufferUtil.arr2Buffer(coords));
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, coords.length / 3);
    }

    /**
     * 画圆
     *
     * @param gl
     * @param r
     */
    public static void drawCircle(GL10 gl, float r) {
        List<Float> coords = new ArrayList<>();
        for (float angle = 0; angle < Math.PI * 2; angle = (float) (angle + Math.PI / 32)) {
            float x = (float) (r * Math.cos(angle));
            float y = (float) (r * Math.sin(angle));
            float z = 0f;
            coords.add(x);
            coords.add(y);
            coords.add(z);
        }
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, BufferUtil.list2Buffer(coords));
        gl.glDrawArrays(GL10.GL_LINE_STRIP, 0, coords.size() / 3);
    }
}
