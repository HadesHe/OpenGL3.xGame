package com.example.sample7

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testMinus(){
        val left=180
        val right=320
        println(left/right.toFloat())
        println(left/right)
        println(left.toFloat()/right)
    }
}
