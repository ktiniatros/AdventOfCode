package nl.giorgos.adventofcode.y2020

import nl.giorgos.adventofcode.Utils
import org.junit.Test
import java.nio.file.InvalidPathException

class Day9 {
    private val fileName = "input9.txt"
    private val input = this.javaClass.getResourceAsStream(fileName) ?: throw InvalidPathException( fileName, "Invalid input")
    private val inputText = Utils.convertFileToString(input).split("\n")
    private val numbers = inputText.subList(0, inputText.size - 1)
    val integers = numbers.map {
        it.toBigInteger()
    }

    @Test
    fun ex1() {
        val preambleSize = 25
        var minIndex = 0
        var maxIndex = 0 + preambleSize
        while (maxIndex < integers.size) {
            val preamble = integers.subList(minIndex, maxIndex).sorted()
            val numberToCheck = integers[maxIndex]
            val diff = preamble.map {
                numberToCheck - it
            }
            if (preamble.intersect(diff).size == 0) {
                println("$numberToCheck is invalid")
            }
            minIndex += 1
            maxIndex += 1
        }
    }

    @Test
    fun ex2() {
        val invalid = integers[507]
        integers.forEachIndexed { index, bigInteger ->
            var sumSofFar = bigInteger
            for (i in index + 1 until integers.size) {
                sumSofFar += integers[i]
                if (sumSofFar == invalid) {
                    val list = integers.subList(index, i).sorted()
                    println("Answer is: ${list.first() + list.last()}")
                    return@ex2
                }
            }
        }
    }
}
