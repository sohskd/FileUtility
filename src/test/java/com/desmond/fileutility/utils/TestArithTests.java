package com.desmond.fileutility.utils;

import com.desmond.fileutility.utils.impl.TestArith;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TestArithTests {

    private TestArith testArith;

    @Autowired
    public TestArithTests(TestArith testArith) {
        this.testArith = testArith;
    }

    @DisplayName("Test Arith add")
    @Test
    public void testArithAdd() {
        assertEquals(10, this.testArith.add(5,5));
    }
}
