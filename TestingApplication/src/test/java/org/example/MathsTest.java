package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.spy;


class MathsTest {

    private Maths maths;

    @BeforeEach
    void setUp() {
        maths  = new Maths();
    }

    @Test
    void add() {

        int x = 5,y=7;
        int obtained = maths.add(x,y);
        int expected = 12;
        assertEquals(obtained, 12);
    }

    @Test
    void arraySum() {
        int [] arr = {1,2,3,4,5};
        int obtained = maths.arraySum(arr);
        int expected = 15;
        assertEquals(obtained, expected);
    }

    @Test
    void positiveCheck() {

        boolean expected = maths.positiveCheck(5);
        assertTrue(expected);
    }

    @Test
    void testThrowsException() {

        int x=5,y=0;
        Executable executable  = () -> maths.division(x,y);
        assertThrows(ArithmeticException.class,executable);
    }

    @Test
    void testArrayEquality() {

        int [] arr = {1,2,3,4,5};

        int [] obtained = maths.arrayReturn(arr);

        assertArrayEquals(arr,obtained);

        assertAll(
                () -> assertEquals(arr[0],obtained[0]),
                () -> assertEquals(arr[1],obtained[1])
        );
    }

//    @Disabled
    @Test
    void testNull() {
        int [] arr={};
        int [] obtained = maths.arrayReturn(arr);

        assertNull(obtained);
        assertTimeout(Duration.ofMillis(10),()->maths.arraySum(arr));
    }

    @Test
    void testAssertJ() {
        int [] arr = {1,2,3,4,5};
        int expected = 15;
        assertThat(maths.arraySum(arr)).isEqualTo(expected);
    }

}