package com.sureselsun.android.seriallibrary.utils;

/**
 * CRC校验类型
 * 创建于 2019/11/13 by Thor
 */
public enum CRCType {
    /**
     * CRC16H 高位在前
     * CRC16L 低位在前
     * CRC32H 高位在前  默认
     * CRC32L 低位在前
     * XOR 智购对换机异或校验
     */
    CRC16H,CRC16L,XOR
}
