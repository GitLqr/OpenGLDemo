package com.lqr.opengl.demo12;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.lqr.opengl.R;
import com.lqr.opengl.common.BaseActivity;
import com.lqr.opengl.common.BaseRenderer;

/**
 * 创建者：CSDN_LQR
 * 描述：纹理
 */
public class TextureActivity extends BaseActivity {
    @Override
    protected BaseRenderer getRenderer() {
        return new TextureRenderer(this);
    }
}
