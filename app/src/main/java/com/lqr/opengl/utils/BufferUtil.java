package com.lqr.opengl.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.List;

/**
 * 创建者：CSDN_LQR
 * 描述：字节缓冲工具类
 */
public class BufferUtil {

    /**
     * 将float数组转成字节缓冲
     *
     * @param arr
     * @return
     */
    public static ByteBuffer arr2Buffer(float[] arr) {
        ByteBuffer ibb = ByteBuffer.allocateDirect(arr.length * 4);
        ibb.order(ByteOrder.nativeOrder());
        FloatBuffer fbb = ibb.asFloatBuffer();
        fbb.put(arr);
        ibb.position(0);
        return ibb;
    }

    /**
     * 将List转成字节缓冲
     *
     * @param list
     * @return
     */
    public static ByteBuffer list2Buffer(List<Float> list) {
        ByteBuffer ibb = ByteBuffer.allocateDirect(list.size() * 4);
        ibb.order(ByteOrder.nativeOrder());
        FloatBuffer fbb = ibb.asFloatBuffer();
        for (int i = 0; i < list.size(); i++) {
            fbb.put(list.get(i));
        }
        ibb.position(0);
        return ibb;
    }

    /**
     * 将float数组转成Float缓冲
     *
     * @param arr
     * @return
     */
    public static FloatBuffer arr2FloatBuffer(float[] arr) {
        ByteBuffer ibb = ByteBuffer.allocateDirect(arr.length * 4);
        ibb.order(ByteOrder.nativeOrder());
        FloatBuffer fbb = ibb.asFloatBuffer();
        fbb.put(arr);
        fbb.position(0);
        return fbb;
    }

    /**
     * 将byte数组转成字节缓冲
     *
     * @param arr
     * @return
     */
    public static ByteBuffer arr2Buffer(byte[] arr) {
        ByteBuffer ibb = ByteBuffer.allocateDirect(arr.length);
        ibb.order(ByteOrder.nativeOrder());
        ibb.put(arr);
        ibb.position(0);
        return ibb;
    }
}
