package com.app.ykc.zigzag_challenge

import com.app.ykc.zigzag_challenge.data.Ages
import com.app.ykc.zigzag_challenge.data.Range
import org.junit.Test
import org.junit.Assert.*

class AgesCreateTest {

    /**
     * '10대', '20대초반', '20대중반', '20대후반', '30대초반', '30대중반', '30대후반'
     * [0, 0, 1, 1, 1, 0, 0]
     * */

    @Test
    fun createTest() {

        assertEquals(Ages.create(0), Ages.Teens)
        assertEquals(Ages.create(1), Ages.Twenties(Range.create(1)))
        assertEquals(Ages.create(2), Ages.Twenties(Range.create(2)))
        assertEquals(Ages.create(3), Ages.Twenties(Range.create(3)))
        assertEquals(Ages.create(4), Ages.Thirties(Range.create(4)))
        assertEquals(Ages.create(5), Ages.Thirties(Range.create(5)))
        assertEquals(Ages.create(6), Ages.Thirties(Range.create(6)))

    }

    @Test
    fun createSetFromRawTest() {
        assertEquals(Ages.createSetFromRaw(listOf(0, 0, 1, 1, 1, 0, 0)),
            setOf(Ages.Twenties(Range.Mid), Ages.Twenties(Range.Late), Ages.Thirties(Range.Early)))
        assertEquals(Ages.createSetFromRaw(listOf(1, 0, 0, 0, 0, 0, 0)), setOf(Ages.Teens))

    }

}