package com.lqr.opengl.demo9;

import android.opengl.GLU;
import android.opengl.GLUtils;

import com.lqr.opengl.common.BaseRenderer;
import com.lqr.opengl.utils.BufferUtil;
import com.lqr.opengl.utils.ShapeUtil;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * 创建者：CSDN_LQR
 * 描述：光照渲染器
 */
public class LightRenderer extends BaseRenderer {

    // 光照开关
    public boolean light_enable;
    // 全局环境光
    public float global_ambient_r;
    public float global_ambient_g;
    public float global_ambient_b;
    // 材料属性（物体对环境光与散射光的反射率）
    public float material_ambient_diffuse_r;
    public float material_ambient_diffuse_g;
    public float material_ambient_diffuse_b;
    public float material_specular_r;
    public float material_specular_g;
    public float material_specular_b;
    public float material_specular_shininess;
    // 颜色追踪开关（开启后将不需要手动来设置材料属性了，在有多个物体的情况下，效率较高）
    public boolean color_material_enable;
    public float color_r = 1f;
    public float color_g = 1f;
    public float color_b = 1f;
    // 光源0
    public boolean light0_enable;
    public float light0_ambient_r;
    public float light0_ambient_g;
    public float light0_ambient_b;
    public float light0_diffuse_r;
    public float light0_diffuse_g;
    public float light0_diffuse_b;
    public float light0_specular_r;
    public float light0_specular_g;
    public float light0_specular_b;
    public float light0_posi_x;
    public float light0_posi_y;
    public float light0_posi_z;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // 清屏色
        gl.glClearColor(0f, 0f, 0f, 1f);
        // 开启顶点缓冲区
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        // 开启深度测试
        gl.glEnable(GL10.GL_DEPTH_TEST);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // 设置视口大小
        gl.glViewport(0, 0, width, height);
        // 设置平截头体
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        ratio = (float) width / (float) height;
        gl.glFrustumf(-ratio, ratio, -1f, 1f, 3f, 7f);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // 清除颜色缓冲区、深度缓冲区
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        // 设置眼球与观察点
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        GLU.gluLookAt(gl, 0, 0, 5, 0, 0, 0, 0, 1, 0);

        // 坐标系旋转
        gl.glRotatef(mRotateX, 1, 0, 0);
        gl.glRotatef(mRotateY, 0, 1, 0);


        // 使用单调着色模式
//        gl.glShadeModel(GL10.GL_FLAT);

        // 开启光照效果
        if (light_enable) {
            gl.glEnable(GL10.GL_LIGHTING);
        } else {
            gl.glDisable(GL10.GL_LIGHTING);
        }

        // 设置全局环境光（默认值是0.2）
        float[] global_ambient = new float[]{global_ambient_r, global_ambient_g, global_ambient_b, 1f};// 环境光颜色值（rgba）
        gl.glLightModelfv(GL10.GL_LIGHT_MODEL_AMBIENT, BufferUtil.arr2FloatBuffer(global_ambient));

        // 设置物体材料属性——散射光、环境光的反射率（大多数情况下这2个值是一样的）
        float[] material_ambient_diffuse = new float[]{
                material_ambient_diffuse_r,
                material_ambient_diffuse_g,
                material_ambient_diffuse_b,
                1f
        };
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT_AND_DIFFUSE, BufferUtil.arr2FloatBuffer(material_ambient_diffuse));
        // 设置物体材料属性——镜面光反射率
        float[] material_specular = new float[]{
                material_specular_r,material_specular_g,material_specular_b,1f,
        };
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK,GL10.GL_SPECULAR,BufferUtil.arr2FloatBuffer(material_specular));
        // 设置镜面反射指数（范围0-128，值越高,高光点越小且越亮(聚焦越好) ）
        gl.glMaterialx(GL10.GL_FRONT_AND_BACK,GL10.GL_SHININESS, (int) material_specular_shininess);

        // 开启颜色追踪
        if (color_material_enable) {
            gl.glEnable(GL10.GL_COLOR_MATERIAL);
        } else {
            gl.glDisable(GL10.GL_COLOR_MATERIAL);
        }
        // 设置物体绘制颜色
        gl.glColor4f(color_r, color_g, color_b, 1);


        // 开启光源0
        if (light0_enable) {
            gl.glEnable(GL10.GL_LIGHT0);
        } else {
            gl.glDisable(GL10.GL_LIGHT0);
        }
        // 设置光源0环境光
        float[] light0_embient = new float[]{
                light0_ambient_r, light0_ambient_g, light0_ambient_b, 1f
        };
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, BufferUtil.arr2FloatBuffer(light0_embient));
        // 设置光源0散射光
        float[] light0_diffuse = new float[]{
                light0_diffuse_r, light0_diffuse_g, light0_diffuse_b, 1f
        };
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, BufferUtil.arr2FloatBuffer(light0_diffuse));
        // 设置光源0镜面光
        float[] light0_specular = new float[]{
                light0_specular_r, light0_specular_g, light0_specular_b,1f
        };
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, BufferUtil.arr2FloatBuffer(light0_specular));
        // 设置光源0位置
        float[] light0_posi = new float[]{
                light0_posi_x, light0_posi_y, light0_posi_z, 1f// 1表示光源由x,y,z决定；0表示光源在无限远处，得到的是平行光。
        };
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, BufferUtil.arr2FloatBuffer(light0_posi));
        // 设置光锥发射角度（聚光灯）
        gl.glLightf(GL10.GL_LIGHT0,GL10.GL_SPOT_CUTOFF,30);

        // 绘制一个球
//        ShapeUtil.drawSphere(gl, 0.7f, 20, 120);
        ShapeUtil.drawSphere(gl, 0.7f, 500, 500);
    }
}
