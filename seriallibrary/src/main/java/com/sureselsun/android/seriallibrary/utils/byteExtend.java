package com.sureselsun.android.seriallibrary.utils;

/**
 * 字节处理，转换工能类
 * 创建于 2019/11/13 by Thor
 */
public class byteExtend {

    /**
     * 将十六进制String转为字节数组
     *
     * @param data 要转换的String值，String必须为双数，范围0-F十六进制格式，两个位数转为一个字节;如出现String 为单个数，删除未位
     * @return 转换成功, 返回对应的字节数组，失败返回null
     */
    public static byte[] hexStrToBytes(String data) {
        if (data.length() < 1) {
            return null;
        } else if (data.length() % 2 != 0) {
            data = data.substring(0, data.length() - 1);
        }

        byte[] result = new byte[data.length() / 2];
        int j = 0;
        for (int i = 0; i < data.length(); i += 2) {
            result[j++] = (byte) Integer.parseInt(data.substring(i, i + 2), 16);
        }
        return result;

    }

    /**
     * 将byte数组转换为十六进制String
     *
     * @param data 要转换的byte数组
     * @return 转换成功, 返回对应的十六进制String，失败返回null
     */
    public static String bytesToHexStr(byte[] data) {
        if (data.length < 1)
            return null;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            String hex = Integer.toHexString(data[i] & 0xFF);
            if (hex.length() < 2) {
                hex = "0" + hex;
            }
            sb.append(hex.toUpperCase());
        }

        return sb.toString();
    }

    /**
     * 计算byte数组的CRC16值
     *
     * @param data 要计算的bytes
     * @param type CRC类型
     * @return 成功返回CRC16的结查，失败返回null
     */
    public static byte[] getCRC(byte[] data, CRCType type) {
        switch (type) {
            case CRC16H:
                return CRC16_MODBUS_H(data);
            case CRC16L:
                return CRC16_MODBUS_L(data);
            case XOR:
                return XOR_CHECK(data);
            default:
                return null;
        }
    }

    /**
     * 检查传入的String是否符合十六进制string规格
     *
     * @param data 要检查的Str
     * @return 返回ture符合 false不符合
     */
    public static boolean checkHexStr(String data) {
        if (data.length() < 1)
            return false;
        if (data.length() % 2 != 0)
            return false;
        data = data.toUpperCase();
        String exp = "0123456789ABCDEF";
        for (int i = 0; i < data.length(); i++) {
            if (!exp.contains(data.substring(i, i + 1)))
                return false;
        }
        return true;
    }

    /**
     * CRC-16 (Modbus)
     * CRC16_MODBUS：多项式x16+x15+x2+1（0x8005），初始值0xFFFF，低位在前，高位在后，结果与0x0000异或
     * 0xA001是0x8005按位颠倒后的结果
     *
     * @param buffer
     * @return
     */
    private static byte[] CRC16_MODBUS_L(byte[] buffer) {
        int wCRCin = 0xffff;
        int POLYNOMIAL = 0xa001;
        for (byte b : buffer) {
            wCRCin ^= ((int) b & 0x00ff);
            for (int j = 0; j < 8; j++) {
                if ((wCRCin & 0x0001) != 0) {
                    wCRCin >>= 1;
                    wCRCin ^= POLYNOMIAL;
                } else {
                    wCRCin >>= 1;
                }
            }
        }
        wCRCin ^= 0x0000;
        byte[] b = new byte[2];
        b[0] = (byte) (wCRCin >> 8);
        b[1] = (byte) (wCRCin & 0x00ff);
        return b;
    }

    /**
     * CRC-16 (Modbus)
     * CRC16_MODBUS：多项式x16+x15+x2+1（0x8005），初始值0xFFFF，低位在前，高位在后，结果与0x0000异或
     * 0xA001是0x8005按位颠倒后的结果
     *
     * @param buffer
     * @return
     */
    private static byte[] CRC16_MODBUS_H(byte[] buffer) {
        int wCRCin = 0xffff;
        int POLYNOMIAL = 0xa001;
        for (byte b : buffer) {
            wCRCin ^= ((int) b & 0x00ff);
            for (int j = 0; j < 8; j++) {
                if ((wCRCin & 0x0001) != 0) {
                    wCRCin >>= 1;
                    wCRCin ^= POLYNOMIAL;
                } else {
                    wCRCin >>= 1;
                }
            }
        }
        wCRCin ^= 0x0000;
        byte[] b = new byte[2];
        b[1] = (byte) (wCRCin >> 8);
        b[0] = (byte) (wCRCin & 0x00ff);
        return b;
    }

    /**
     * 异或校验，智购兑换机用
     * @param data 要样校验的数据
     * @return 返回校验码数组
     */
    private static byte[] XOR_CHECK(byte[] data){
        byte[] tmp_b = new byte[1];
        byte by = 0x0;
        for(int i =0;i<data.length;i++){
            by ^= data[i];
        }
        tmp_b[0] = by;
        return tmp_b;
    }

}
