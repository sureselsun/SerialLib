package com.sureselsun.android.seriallibrary.manage;

import android.util.Log;

import com.sureselsun.android.seriallibrary.dbase.Dbytes;
import com.sureselsun.android.seriallibrary.dbase.SerialData;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android_serialport_api.SerialPort;

/**
 * 串口管理类
 * 创建于2019-11-14 by Thor
 */
public class SerialManage {

    private final String TAG = "SerialManage";
    //串口设备地址
    private String serialPath;
    //串口波特率
    private int baudrate;
    //串口flags，默认0
    private int flags;
    //读取回复信息超时时间
    private int timeOut = 400;
    //串口实例唯一标识
    private String key;
    //串口是否打开成功
    private boolean isOpen = false;
    //串口是否在工作中
    private boolean isWork = false;
    //默认存放读取数据的缓冲区大小
    private int bufferSize = 1024;
    //串口打开成功返回的实例
    private SerialPort SP;
    //串口读取数据流
    private InputStream inputStream = null;
    //串口发出数据流
    private OutputStream outputStream = null;

    /**
     * 构造函数
     * @param serialPath 串口设备地址
     * @param baudrate 串口波特率
     * @param flags 串口flags
     * @param key 串口实例唯一标识
     */
    public SerialManage(String serialPath,int baudrate,int flags,String key){
        this.serialPath = serialPath;
        this.baudrate = baudrate;
        this.flags = flags;
        this.key = key;

        this.init();
    }

    /**
     * 打开串口，初始化串口，成功isOpen==true  else false
     */
    private void init(){
        try {
            this.SP = new SerialPort(new File(this.serialPath),this.baudrate,this.flags);
            this.isOpen = true;

            this.inputStream = this.SP.getInputStream();
            this.outputStream = this.SP.getOutputStream();
        }catch (IOException e){
            Log.e(TAG,"init 打开串口失败："+e.toString());
            this.isOpen = false;
        }
    }

    /**
     * 获取串口标识
     * @return 标识
     */
    public String getKey(){
        return this.key;
    }

    /**
     * 关闭串口
     */
    public void close(){
        try {
            inputStream.close();
            outputStream.close();

            this.isOpen = false;
            this.SP.close();
        } catch (IOException e) {
            Log.e(TAG, "close: 关闭串口异常："+e.toString());
            return;
        }
        Log.d(TAG, "close: 关闭串口成功");
    }

    /**
     * 读取串口数据
     * @return SerialData 如果读取失败或读不到数据，返回空
     */
    public SerialData serialRead(){
        while (this.isWork);
        this.isWork= true;
        byte[] buffer = new byte[this.bufferSize];
        int size; //读到数据的大小
        try {
            size = inputStream.read(buffer);
            this.isWork = false;
            if (size > 0){
                SerialData sd = new SerialData(buffer,size);
                Log.d(TAG, "serialRead: 成功接收数据: "+ Dbytes.bytesToHexStr(sd.getDate()));
                Log.d(TAG, "serialRead: 接收到数据的大小" + String.valueOf(size));
                return sd;
            }
            Log.d(TAG, "serialRead: 读到的数据为空" + String.valueOf(size));
            return null;
        } catch (IOException e) {
            this.isWork = false;
            Log.e(TAG, "serialRead: 数据读取异常：" +e.toString());
            return null;
        }
    }

    /**
     * 向串口发送数据
     * @param data 要发送的数据
     */
    public void serialWrite(byte[] data){
        while (this.isWork);
        this.isWork = true;
        try{
            if(data.length>0){
                outputStream.write(data);
                outputStream.flush();
                this.isWork = false;
                Log.d(TAG, "serialWrite: 串口数据发送成功");
            }else {
                Log.e(TAG,"serialWrite: 要发送的数据为空");
            }
        }catch (IOException e){
            this.isWork = false;
            Log.e(TAG, "serialWrite: 串口数据发送失败："+e.toString());
        }
    }

    /**
     * 向串口发送数据，带回调
     * @param data 要发送的数据
     * @param timeOut 回调等待时间  传0为默认
     * @param callBack 回调操作
     */
    public void serialWriteWithCallBack(byte[] data,int timeOut,SerialCallBack callBack){
        while (this.isWork);
        this.isWork=true;
        try{
            if(data.length>0){
                long ot = (long)timeOut==0?this.timeOut:timeOut;
                outputStream.write(data);
                outputStream.flush();
                this.isWork = false;
                Log.d(TAG, "serialWrite: 串口数据发送成功");
                Thread.sleep(ot);
                callBack.SerialWriteCallBack(this.serialRead().getDate());
            }else {
                Log.e(TAG,"serialWrite: 要发送的数据为空");
            }
        } catch (IOException e) {
            this.isWork = false;
            Log.e(TAG, "serialWrite: 串口数据发送失败："+e.toString());
        }catch (InterruptedException e){
            this.isWork = false;
            Log.e(TAG, "serialWrite: sleep error: "+e.toString());
        }
    }

    /**
     * 清除串口缓冲区内的数据
     */
    public void serialClear(){
        while (this.isWork);
        this.isWork = true;
        byte[] buffer = new byte[this.bufferSize];
        try {
            inputStream.read(buffer);
            this.isWork = false;
            Log.d(TAG, "serialClear: 清除成功");
        } catch (IOException e) {
            this.isWork = false;
            Log.e(TAG, "serialClear: 清除失败，读取数据异常：" +e.toString());
        }
    }
}
