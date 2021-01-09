package com.github.javahello.erm.generator.core.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringHelperTest {


    @Test
    public void testShowLen() {
        int len = StringHelper.showLen("a张a三a");
        assertEquals(6, len);

        len = StringHelper.showLen("a张a三四a");
        assertEquals(8, len);

        len = StringHelper.showLen("a张a三四五a");
        assertEquals(9, len);
    }

}