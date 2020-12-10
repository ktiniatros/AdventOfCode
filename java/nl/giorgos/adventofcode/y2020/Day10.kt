package nl.giorgos.adventofcode.y2020

import nl.giorgos.adventofcode.Utils
import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigInteger
import java.nio.file.InvalidPathException

class Day10 {
    private val fileName = "input10.txt"
    private val input = this.javaClass.getResourceAsStream(fileName) ?: throw InvalidPathException( fileName, "Invalid input")
    private val inputText = Utils.convertFileToString(input).split("\n")
    private val numbers = inputText.subList(0, inputText.size - 1).map { it.toInt() }.sorted()

    @Test
    fun ex1() {
        var jolt1Diff = 1
        var jolt3Diff = 1

        for (i in numbers.indices) {
            numbers.getOrNull(i + 1)?.let {
                val diff = it - numbers[i]
                when (diff) {
                    1 -> jolt1Diff += 1
                    3 -> jolt3Diff += 1
                    else -> println("Other diff: $diff")
                }
            }
        }

        println("Result is: ${jolt1Diff * jolt3Diff}")
    }

    private fun debug(index: Int, list: List<Int>, label: String) {
        if (index < 8) {
            println("$label: $list")
        }
    }

    @Test
    fun ex2() {
        val numbers = numbers + (numbers.last() + 3)
        var result = listOf(Pair(0, 1.toBigInteger()))
        var tempResult = result.toList()

        for (current in numbers) {
            tempResult = result.filter {
                val diff = current - it.first
                diff <= 3
            }

            val tempCount = tempResult.map { it.second }.fold(initial = BigInteger.ZERO) { sumSoFar, bigInteger ->
                sumSoFar + bigInteger
            }
            result = tempResult + listOf(Pair(current, tempCount))
        }

        println("Result is: ${tempResult.first().second}")
    }
}
