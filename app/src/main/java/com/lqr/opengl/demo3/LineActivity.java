package com.lqr.opengl.demo3;

import com.lqr.opengl.common.BaseRenderer;
import com.lqr.opengl.common.BaseActivity;

/**
 * 创建者：CSDN_LQR
 * 描述：画线
 */
public class LineActivity extends BaseActivity {
    @Override
    protected BaseRenderer getRenderer() {
//        return new LineRenderer();
        return new LineStripRender();
    }
}
