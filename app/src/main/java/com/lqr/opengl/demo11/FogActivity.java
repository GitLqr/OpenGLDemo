package com.lqr.opengl.demo11;

import com.lqr.opengl.common.BaseActivity;
import com.lqr.opengl.common.BaseRenderer;

/**
 * 创建者：CSDN_LQR
 * 描述：雾
 */
public class FogActivity extends BaseActivity {

    @Override
    protected BaseRenderer getRenderer() {
        return new FogRenderer();
    }
}
