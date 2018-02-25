package com.lqr.opengl.demo2;

import com.lqr.opengl.common.BaseRenderer;
import com.lqr.opengl.common.BaseActivity;

/**
 * 创建者：CSDN_LQR
 * 描述：用点画出螺旋管
 */
public class PointActivity extends BaseActivity {

    @Override
    protected BaseRenderer getRenderer() {
//        return new PointRenderer();
        return new PointSizeRenderer();
    }
}
