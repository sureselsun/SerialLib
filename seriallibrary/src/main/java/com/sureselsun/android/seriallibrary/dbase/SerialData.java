package com.sureselsun.android.seriallibrary.dbase;

import android_serialport_api.SerialPort;

/**
 * 创建于 2019/11/14 by Thor
 */
public class SerialData {
    //字节数据
    private byte[] data;

    /**
     * 初始化，将接收到的数据瘦身减少
     * @param data 收到到的数据
     * @param size 数据大小
     */
    public SerialData(byte[] data,int size){
        this.data  = new byte[size];
        for(int i=0;i<size;i++){
            this.data[i] = data[i];
        }
    }

    /**
     * 获取数据
     * @return 返回数据
     */
    public byte[] getDate(){
        return data;
    }
}
