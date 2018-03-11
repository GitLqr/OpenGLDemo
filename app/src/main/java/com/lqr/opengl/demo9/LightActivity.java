package com.lqr.opengl.demo9;

import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;

import com.lqr.opengl.R;
import com.lqr.opengl.common.BaseActivity;
import com.lqr.opengl.common.BaseRenderer;

import java.lang.reflect.Field;

/**
 * 创建者：CSDN_LQR
 * 描述：光照Demo
 */
public class LightActivity extends BaseActivity {

    Dialog dialog;
    Resources resources;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildDialog();
    }

    @Override
    protected boolean useDirtyMode() {
        return false;// 使用持续渲染模式
    }

    @Override
    protected BaseRenderer getRenderer() {
        return new LightRenderer();
    }

    protected void buildDialog() {
        resources = this.getResources();
        dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.dialog_light_settting);
        addDialogViewEventListener();
    }

    protected void addDialogViewEventListener() {
        CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int id = buttonView.getId();
                String name = resources.getResourceName(id);// cb_light$enable
                name = name.substring(name.indexOf("_") + 1); // light$enable
                Log.i("listener", "checkbox name = " + name);
                try {
                    Class rendererClass = renderer.getClass();
                    Field field = rendererClass.getField(name);
                    field.setAccessible(true);
                    field.set(renderer, isChecked);
                } catch (Exception e) {
                    Log.i("listener", e.toString());
                }
            }
        };
        SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float scale = (float) progress / (float) seekBar.getMax();
                int id = seekBar.getId();
                String name = resources.getResourceName(id);
                name = name.substring(name.indexOf("_") + 1);
                Log.i("listener", "seekBar name = " + name);
                try {
                    Class rendererClass = renderer.getClass();
                    Field field = rendererClass.getField(name);
                    field.setAccessible(true);
                    if (name.contains("posi")) {
                        scale = 20 * scale - 10;// 让位置值保持在[-10,10]
                    }else if(name.contains("shininess")){
                        scale = scale * 128;// 让镜面光反射指数在[0-128]
                    }
                    field.set(renderer, scale);
                } catch (Exception e) {
                    Log.e("listener", e.toString());
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };
        try {
            Class idClass = R.id.class;
            Field[] fields = idClass.getFields();
            for (Field field : fields) {
                field.setAccessible(true);
                int id = (Integer) field.get(null);
                View view = dialog.findViewById(id);
                if (view != null) {
                    if (view instanceof CheckBox) {
                        ((CheckBox) view).setOnCheckedChangeListener(onCheckedChangeListener);
                    } else if (view instanceof SeekBar) {
                        ((SeekBar) view).setOnSeekBarChangeListener(onSeekBarChangeListener);
                    }
                }
            }
        } catch (Exception e) {
            Log.e("listener", e.toString());
        }
    }

    public void showDialog() {
        dialog.show();
    }

    public void hideDialog() {
        dialog.hide();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("设置");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        showDialog();
        return super.onOptionsItemSelected(item);
    }
}
