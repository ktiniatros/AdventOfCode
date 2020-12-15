package nl.giorgos.adventofcode.y2020

import org.junit.Assert.assertEquals
import org.junit.Test

class Day15 {
    private fun find(input: Array<Int>, maxIndex: Int = 2020): Int {
        val before = System.currentTimeMillis()
        val numbersSpoken = mutableListOf<Int>()

        val alreadySeenNumbers = mutableMapOf<Int, Pair<Int, Int>>()
        var alreadyLastSpoken = -1
        for (i in 1..maxIndex) {
            if (i > input.size) {
                if (i == input.size + 1) {
                    val spoken = 0
                    val p = alreadySeenNumbers[spoken]!!
                    alreadySeenNumbers[spoken] = Pair(p.second, i)
                    alreadyLastSpoken = spoken
                    numbersSpoken.add(0)
                } else {
                    val p = alreadySeenNumbers[alreadyLastSpoken] ?: Pair(0, 0)
                    val spoken = if (p.first == 0) {
                        0
                    } else {
                        p.second - p.first
                    }
                    alreadySeenNumbers[spoken] = Pair(alreadySeenNumbers[spoken]?.second ?: 0, i)
                    alreadyLastSpoken = spoken
                }
            } else {
                alreadyLastSpoken = input[i - 1]
                alreadySeenNumbers[input[i - 1]] = Pair(0, i)
                numbersSpoken.add(input[i - 1])
            }
        }
        println("It took: ${System.currentTimeMillis() - before}")
        return alreadyLastSpoken
    }
    @Test
    fun ex1() {
        assertEquals(436, find(arrayOf(0, 3, 6)))
        assertEquals(1, find(arrayOf(1,3,2)))
//        assertEquals(10, find(arrayOf(2,1,3)))
//        assertEquals(27, find(arrayOf(1,2,3)))
//        assertEquals(78, find(arrayOf(2,3,1)))
//        assertEquals(438, find(arrayOf(3,2,1)))
//        assertEquals(1836, find(arrayOf(3,1,2)))
//        assertEquals(1280, find(arrayOf(2,15,0,9,1,20)))
    }

    @Test
    fun ex2() {
        assertEquals(175594, find(arrayOf(0, 3, 6), 30000000))
        assertEquals(2578, find(arrayOf(1,3,2), 30000000))
        assertEquals(3544142, find(arrayOf(2,1,3), 30000000))
        assertEquals(261214, find(arrayOf(1,2,3), 30000000))
        assertEquals(6895259, find(arrayOf(2,3,1), 30000000))
        assertEquals(18, find(arrayOf(3,2,1), 30000000))
        assertEquals(362, find(arrayOf(3,1,2), 30000000))
        assertEquals(651639, find(arrayOf(2,15,0,9,1,20), 30000000))
    }
}
