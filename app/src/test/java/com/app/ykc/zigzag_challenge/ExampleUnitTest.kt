package com.app.ykc.zigzag_challenge

import org.junit.Test

import org.junit.Assert.*
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {

        j(0)

    }

    tailrec fun j(i: Int) : Int{
        println(i)
        if(i == 10) return 10
        else return j(i+1)
    }

}
