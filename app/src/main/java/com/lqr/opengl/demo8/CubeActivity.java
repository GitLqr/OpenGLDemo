package com.lqr.opengl.demo8;

import com.lqr.opengl.common.BaseActivity;
import com.lqr.opengl.common.BaseRenderer;

/**
 * 创建者：CSDN_LQR
 * 描述：正方体
 */
public class CubeActivity extends BaseActivity{

    @Override
    protected BaseRenderer getRenderer() {
//        return new CubeRenderer1();
        return new CubeRenderer2();
    }
}
