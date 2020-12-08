package nl.giorgos.adventofcode.y2020

import nl.giorgos.adventofcode.Utils
import org.junit.Test
import java.nio.file.InvalidPathException

class Day3 {
    private val input = this.javaClass.getResourceAsStream("input3.txt") ?: throw InvalidPathException( "input4", "Invalid input")
    private val lines = Utils.convertFileToStringArray(input)

    @Test
    fun ex1() {
        println("Found: ${findSlope(lines, 3, 1)} trees")
    }

    @Test
    fun ex2() {
//        Right 1, down 1.
//        Right 3, down 1. (This is the slope you already checked.)
//        Right 5, down 1.
//        Right 7, down 1.
//        Right 1, down 2.

        val routes = listOf(Pair(1,1), Pair(3,1), Pair(5,1), Pair(7,1), Pair(1,2))
        var multipliedTrees = 1
        routes.forEach {
            val tc = findSlope(lines, it.first, it.second)
            multipliedTrees *= tc
        }
        println("Result: ${multipliedTrees}")
    }

    private fun findSlope(lines: List<String>, right: Int, bottom: Int): Int {
        var verticalIndex = bottom
        var horizontalIndex = right
        var treesCount = 0
        while (verticalIndex < lines.size) {
            val horizontalOffset = horizontalIndex - lines[verticalIndex].length
            if (horizontalOffset > -1) {
                horizontalIndex = horizontalOffset
            }
            if (lines[verticalIndex][horizontalIndex] == '#') {
                treesCount += 1
            }
            verticalIndex += bottom
            horizontalIndex += right
        }

        return treesCount
    }
}
