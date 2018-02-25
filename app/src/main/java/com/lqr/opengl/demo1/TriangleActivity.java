package com.lqr.opengl.demo1;

import com.lqr.opengl.common.BaseRenderer;
import com.lqr.opengl.common.BaseActivity;

/**
 * 创建者：CSDN_LQR
 * 描述：使用OpenGl绘制三角形
 */
public class TriangleActivity extends BaseActivity {

    @Override
    protected BaseRenderer getRenderer() {
//        return new TriangleRenderer();
        return new TriangleStripRenderer();
    }
}
