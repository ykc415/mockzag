package com.app.ykc.zigzag_challenge

import com.app.ykc.zigzag_challenge.data.Ages
import org.junit.Assert.*
import com.app.ykc.zigzag_challenge.views.BlueChip
import org.junit.Test


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {

        val a = BlueChip(Ages.Teens, false)
        val b = BlueChip(Ages.Teens, false)

        assertEquals(a , b)


    }

}
