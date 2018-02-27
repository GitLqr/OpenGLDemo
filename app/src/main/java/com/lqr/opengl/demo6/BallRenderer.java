package com.lqr.opengl.demo6;

import android.opengl.GLU;

import com.lqr.opengl.common.BaseRenderer;
import com.lqr.opengl.utils.BufferUtil;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

/**
 * @创建者 CSDN_LQR
 * @时间 2018/2/27
 * @描述 使用三角形带（或线带）绘制球
 */
public class BallRenderer extends BaseRenderer {

    @Override
    public void onDrawFrame(GL10 gl) {
        //清屏
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        // 设置眼球与观察点
        GLU.gluLookAt(gl, 0, 0, 5, 0, 0, 0, 0, 1, 0);

        gl.glRotatef(mRotateX, 1, 0, 0);
        gl.glRotatef(mRotateY, 0, 1, 0);
        List<Float> coords = new ArrayList<>();

        // 球的半径
        float R = 0.7f;
        // 球的层数
        int layer = 20;
        float layerStep = (float) (Math.PI / layer);// 层上的点到圆心形成的线与球的中心轴之间的夹角的角度增量
        // 层的片数
        int slice = 120;// 一半切面的片数，完整切面的片数为 slice * 2
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
//            alpha0 = (float) (-Math.PI / 2 + (i * layerStep));
//            alpha1 = (float) (-Math.PI / 2 + ((i + 1) * layerStep));
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
        gl.glDrawArrays(GL10.GL_LINE_STRIP, 0, coords.size() / 3);
//        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, coords.size() / 3);
    }
}
