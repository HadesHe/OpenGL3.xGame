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

        var b=B(1,2)
    }

    abstract class A(a:Int){

        init {
            println("inint A")
            a()
        }
        abstract fun a()
    }

    class B(a: Int,var b:Int):A(a){
        override fun a() {
            println("b $b")
        }

    }
}
