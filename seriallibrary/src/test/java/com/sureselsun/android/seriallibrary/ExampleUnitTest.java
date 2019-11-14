package com.sureselsun.android.seriallibrary;

import org.junit.Test;

import static org.junit.Assert.*;

import com.sureselsun.android.seriallibrary.dbase.Dbytes;
import com.sureselsun.android.seriallibrary.utils.byteExtend;
import com.sureselsun.android.seriallibrary.utils.CRCType;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testCrc() {
        String b = "0A10000000010200AA";

        System.out.println("551F");
        Dbytes db = new Dbytes(b);
        System.out.println( db.addWithBytes(db.getDataWithCRC(CRCType.CRC16L)).getDataToString());
    }
}