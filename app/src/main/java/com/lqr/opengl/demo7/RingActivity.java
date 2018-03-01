package com.lqr.opengl.demo7;

import com.lqr.opengl.common.BaseActivity;
import com.lqr.opengl.common.BaseRenderer;

/**
 * 创建者：CSDN_LQR
 * 描述：圆环
 */
public class RingActivity extends BaseActivity {

    @Override
    protected BaseRenderer getRenderer() {
        return new RingRenderer();
    }
}
