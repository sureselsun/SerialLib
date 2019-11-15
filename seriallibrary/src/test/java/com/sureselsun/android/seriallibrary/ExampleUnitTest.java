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
        String b = "3BB3000014A810010001010000000000000000000000000000";

        System.out.println("25");
        Dbytes db = new Dbytes(b);
        System.out.println(db.getCRCByString(CRCType.XOR));
        db.addCRC(CRCType.XOR);

        System.out.println(db.getDataToString());
    }
}