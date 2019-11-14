package com.sureselsun.android.seriallibrary.manage;

import com.sureselsun.android.seriallibrary.dbase.Dbytes;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.ServiceConfigurationError;

/**
 * 串口生成工厂
 * 创建于 2019/11/14 by Thor
 */
public class SerialFactory {

    private final String TAG = "SerialFactory";

    private static SerialFactory instance;
    private HashMap<String,SerialManage> SerialList = null;
    /**
     * 初始化
     */
    private SerialFactory(){
        SerialList = new HashMap<String, SerialManage>();
    }

    /**
     * 获取当前类，单类实例
     * @return 当前类
     */
    public static SerialFactory getInstance() {
        if (instance == null) {
            instance = new SerialFactory();
        }
        return instance;
    }

    /**
     * 跟据key获取对应的实例
     * @param key 索引
     * @return SerialManage实例，如果传入未知key返回null
     */
    public SerialManage getSerialPort(String key){
        return SerialList.get(key);
    }

    /**
     * 新建一个串口并返回
     * @param key 串口唯一标识
     * @param serialPath 串口设备地址
     * @param baudrate 串口波特率
     * @return
     */
    public SerialManage newSerialPort(String key,String serialPath,int baudrate){
        SerialManage sm = new SerialManage(serialPath,baudrate,0,key);
        SerialList.put(key,sm);
        return SerialList.get(key);
    }

    /**
     * 删除key对应的SerialManage实例
     * @param key 串口标识
     */
    public void removeSerialPort(String key){
        if(SerialList.containsKey(key)){
            SerialManage sm = SerialList.get(key);
            sm.close();
            SerialList.remove(key);
        }
    }
}
