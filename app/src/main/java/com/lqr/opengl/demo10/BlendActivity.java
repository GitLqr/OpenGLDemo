package com.lqr.opengl.demo10;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lqr.opengl.common.BaseActivity;
import com.lqr.opengl.common.BaseRenderer;

/**
 * 创建者：CSDN_LQR
 * 描述：混合
 */
public class BlendActivity extends BaseActivity {

    @Override
    protected boolean useDirtyMode() {
        return false;
    }

    @Override
    protected BaseRenderer getRenderer() {
//        return new BlendRenderer1();
//        return new BlendRenderer2();
        return new BlendRenderer3();
    }
}
