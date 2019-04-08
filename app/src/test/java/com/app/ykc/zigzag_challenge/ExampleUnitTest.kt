package com.app.ykc.zigzag_challenge

import com.app.ykc.zigzag_challenge.data.Ages
import com.app.ykc.zigzag_challenge.data.Range
import org.junit.Test


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {

        val ages = listOf(Ages.Teens, Ages.Twenties(Range.Early), Ages.Twenties(Range.Mid), Ages.Thirties(Range.Mid))


        ages.map { age ->
            when (age) {
                is Ages.Teens -> "10대"
                is Ages.Twenties -> "20대"
                is Ages.Thirties -> "30대"
            }
        }.distinct().let {
            if (it.size == 3) {
                listOf("모두")
            } else {
                it
            }
        }.forEach { println(it) }


    }

}
