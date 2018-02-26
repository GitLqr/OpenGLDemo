package com.lqr.opengl.demo4;

import com.lqr.opengl.common.BaseActivity;
import com.lqr.opengl.common.BaseRenderer;

/**
 * 创建者：CSDN_LQR
 * 描述：剪裁
 */
public class ScissorsActivity extends BaseActivity {

    @Override
    protected BaseRenderer getRenderer() {
        return new ScissorsRenderer();
    }
}
