package com.lqr.opengl.demo6;

import com.lqr.opengl.common.BaseActivity;
import com.lqr.opengl.common.BaseRenderer;

/**
 * @创建者 CSDN_LQR
 * @时间 2018/2/27
 * @描述 绘制球
 */
public class BallActivity extends BaseActivity {

    @Override
    protected BaseRenderer getRenderer() {
        return new BallRenderer();
    }
}
