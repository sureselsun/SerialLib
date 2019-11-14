package com.sureselsun.android.seriallibrary.manage;

/**
 * Created Administrator Time 2019/11/14 0014
 */
public interface SerialCallBack {
    /**
     * 串口写入数据回调
     * @param bytes
     */
    public void SerialWriteCallBack(byte[] bytes);
}
