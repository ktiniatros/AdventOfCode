package nl.giorgos.adventofcode.y2020

import nl.giorgos.adventofcode.Utils
import org.junit.Assert.assertEquals
import org.junit.Test
import java.nio.file.InvalidPathException

class Day15 {
//    private val fileName = "input15.txt"
//    private val input = this.javaClass.getResourceAsStream(fileName) ?: throw InvalidPathException(
//            fileName,
//            "Invalid input"
//    )
//    private val inputText = Utils.convertFileToString(input).split("\n")
//    private val lines = inputText.subList(0, inputText.size - 1)

    private fun find(input: Array<Int>, maxIndex: Int = 2020): Int {
        val before = System.currentTimeMillis()
        val numbersSpoken = mutableListOf<Int>()

        val alreadySeenNumbers = mutableMapOf<Int, Int>()
        for (i in 1..maxIndex) {
            if (i > input.size) {
                if (i == input.size + 1) {
//                    println("Spoken 1s 0")
                    numbersSpoken.add(0)
                } else {
                    val lastSpoken = numbersSpoken.last()
                    val lastIndex = numbersSpoken.size - 1
                    var currentIndex = numbersSpoken.size - 2
                    while (currentIndex >= 0) {
                        if (numbersSpoken[currentIndex] == lastSpoken) {
                            val spoken = lastIndex - currentIndex
//                            println("Spoken is: $spoken")
                            alreadySeenNumbers.add(spoken)
                            numbersSpoken.add(spoken)
                            break;
                        }
                        currentIndex -= 1
                    }
                    if (currentIndex == -1) {
//                        println("Spok3n is 0")
                        numbersSpoken.add(0)
                    }
                }
            } else {
                numbersSpoken.add(input[i - 1])
            }
        }
        println("It took: ${System.currentTimeMillis() - before}")
        return numbersSpoken.last()
    }
    @Test
    fun ex1() {
        assertEquals(436, find(arrayOf(0, 3, 6)))
//        assertEquals(1, find(arrayOf(1,3,2)))
//        assertEquals(10, find(arrayOf(2,1,3)))
//        assertEquals(27, find(arrayOf(1,2,3)))
//        assertEquals(78, find(arrayOf(2,3,1)))
//        assertEquals(438, find(arrayOf(3,2,1)))
//        assertEquals(1836, find(arrayOf(3,1,2)))
//
//        println(find(arrayOf(2,15,0,9,1,20)))
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

        println(find(arrayOf(2,15,0,9,1,20)))
    }
}
