package com.example.sample8

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
    fun testFloatArray(){
        val result=FloatArray(20){
            i->
            if(i%3==2) 1f else 0f
        }

        result.forEachIndexed { index, fl ->
            println("index $index fl $fl")
        }

    }
}
