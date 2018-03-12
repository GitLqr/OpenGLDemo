package com.lqr.opengl.demo12;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.opengl.GLU;
import android.opengl.GLUtils;
import android.os.Build;

import com.lqr.opengl.R;
import com.lqr.opengl.common.BaseRenderer;
import com.lqr.opengl.utils.BufferUtil;
import com.lqr.opengl.utils.ShapeUtil;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * 创建者：CSDN_LQR
 * 描述：纹理渲染器
 */
public class TextureRenderer extends BaseRenderer {
    Context mContext;

    public TextureRenderer(Context context) {
        super();
        mContext = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        super.onSurfaceCreated(gl, config);
        // 开启纹理顶点缓冲区
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        GLU.gluLookAt(gl, 0, 0, 5, 0, 0, 0, 0, 1, 0);

        gl.glRotatef(mRotateX, 1, 0, 0);
        gl.glRotatef(mRotateY, 0, 1, 0);

        // 开启2D纹理绘制
        gl.glEnable(GL10.GL_TEXTURE_2D);
        // 生成纹理id
        int[] texIds = new int[1];
        gl.glGenTextures(1, texIds, 0);
        // 绑定纹理id
        gl.glBindTexture(GL10.GL_TEXTURE_2D, texIds[0]);
        // 加载纹理
        Bitmap bitmap = getBitmap(mContext, R.mipmap.ic_launcher_round);
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);

        // 设置纹理拉伸过滤模式
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);// 当纹理过大时，线性压缩
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);// 当纹理过小时，线性拉伸
        // 设置纹理包裹模式
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);// 当x轴方面不够填充时，重覆纹理
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);// 当y轴方面不够填充时，重覆纹理

        // 对于OpenGl而言，一个纹理的顶点为（左下角为0,0）：
        // 0,1  1,1
        // 0,0  1,0
        // 所以，当我对纹理取顶点超过1时，GL_TEXTURE_WRAP_S与GL_TEXTURE_WRAP_T就会产生作用了，会重覆纹理图片至能填充整个纹理。
        // 又因为，一些设备在实现OpenGL时，左下角不是0,0，而是在左上角，故有些设备中，纹理顶点如下：
        // 0，0  1，0
        // 0，1  1，1
        float[] texCoords = new float[]{
                0f, 2f,// 左下
                2f, 2f,// 右下
                0f, 0f,// 左上
                2f, 0f,// 右上
        };
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, BufferUtil.arr2Buffer(texCoords));
        float[] coords = new float[]{
                -1f, -1f, 0f,
                1f, -1f, 0f,
                -1f, 1f, 0f,
                1f, 1f, 0f,
        };
        ShapeUtil.drawTriangleStrip(gl, coords);
    }

    /**
     * Android 4.4与Android 5.0在获取Bitmap时方式略有不同。
     *
     * @param context
     * @param vectorDrawableId
     * @return
     */
    private static Bitmap getBitmap(Context context, int vectorDrawableId) {
        Bitmap bitmap = null;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            Drawable vectorDrawable = context.getDrawable(vectorDrawableId);
            bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                    vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            vectorDrawable.draw(canvas);
        } else {
            bitmap = BitmapFactory.decodeResource(context.getResources(), vectorDrawableId);
        }
        return bitmap;
    }
}
