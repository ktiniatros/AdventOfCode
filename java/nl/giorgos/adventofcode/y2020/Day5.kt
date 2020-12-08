package nl.giorgos.adventofcode.y2020

import nl.giorgos.adventofcode.Utils
import org.junit.Test
import java.nio.file.InvalidPathException

class Day5 {
    private val input = this.javaClass.getResourceAsStream("input5.txt") ?: throw InvalidPathException( "input5", "Invalid input")
    private val lines = Utils.convertFileToStringArray(input)

    @Test
    fun ex1() {

        parseSeat(lines.last())
        val max = lines.map(::parseSeat).maxOrNull()
        println("The max possible seat is: $max")
    }

    private fun parseSeat(input: String): Int {
        var front = 0
        var back = 127
        var left = 0
        var right = 7
        input.forEach {
            when(it) {
                'F' -> {
                    back -= (back - front + 1) / 2
                }
                'B' -> {
                    front += (back - front + 1) / 2
                }
                'L' -> {
                    right -= (right - left + 1) / 2
                }
                'R' -> {
                    left += (right - left + 1) / 2
                }
                else -> println("WTF: $it")
            }
        }

        return 8 * front + left
    }

    @Test
    fun ex2() {
        val seats = lines.map(::parseSeat).sorted()
        for (i in 1 until seats.size) {
            if (seats[i] - seats[i-1] > 1) {
                return println("Your seat is ${seats[i-1] + 1}")
            }
        }
    }
}
