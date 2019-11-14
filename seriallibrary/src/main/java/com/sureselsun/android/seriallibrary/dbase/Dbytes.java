package com.sureselsun.android.seriallibrary.dbase;

import com.sureselsun.android.seriallibrary.utils.CRCType;
import com.sureselsun.android.seriallibrary.utils.byteExtend;

/**
 * 字节处里基类
 * 创建 2019-11-13 by Thor
 */
public class Dbytes extends byteExtend {

    //原始字节值
    private byte[] sbytes;

    /**
     * 初始化实例
     * @param bytes 传入字节数组
     */
    public Dbytes(byte[] bytes){
        this.sbytes = bytes;
    }

    /**
     * 初始化实例
     * @param bytes 传入String
     */
    public Dbytes(String bytes){
        this.sbytes = hexStrToBytes(bytes);
    }

    /**
     * 获取当前byte数据
     * @return 返回当前的byte数据
     */
    public byte[] getData(){
        return this.sbytes;
    }

    /**
     * 以十六进制String格式获取当前byte数据
     * @return 返回当前数据的十六进制String
     */
    public String getDataToString(){
        return bytesToHexStr(getData());
    }

    /**
     * 获取当前数据带CRC验证
     * @param type CRC类型
     * @return 返回当前带CRC校验的数据
     */
    public byte[] getDataWithCRC(CRCType type){
        return getCRC(getData(),type);
    }

    /**
     * 获取肖前数据带CRC验证,十六进制String
     * @param type CRC类型
     * @return 返回当前带CRC校验的十六进制String
     */
    public String getStrDataWithCRC(CRCType type){
        return bytesToHexStr(getDataWithCRC(type));
    }

    /**
     *在当前数据后添加数据
     * @param data 要添加的数据
     * @return 返回当前实例
     */
    public Dbytes addWithBytes(byte[] data){
        if(data.length<1)
            return this;
        byte[] b = new byte[this.sbytes.length+data.length];
        int i = 0;
        for(byte b1:this.sbytes){
            b[i] = b1;
            i++;
        }
        for(byte b2:data) {
            b[i] = b2;
            i++;
        }
        this.sbytes = b.clone();
        return this;
    }

    /**
     * 在当前数据后添加数据,如data的个数为单数删除未位，如data的格式不正确，不作添加处理
     * @param data 要添加数据的String十六进制型式
     */
    public void addWithString(String data){
        if(!checkHexStr(data))
            return;
        byte[] tmpBytes = hexStrToBytes(data);

        byte[] b = new byte[this.sbytes.length+tmpBytes.length];
        int i = 0;
        for(byte b1:this.sbytes){
            b[i] = b1;
            i++;
        }
        for(byte b2:tmpBytes) {
            b[i] = b2;
            i++;
        }

        this.sbytes = b.clone();
    }
}
