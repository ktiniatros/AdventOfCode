package nl.giorgos.adventofcode.y2020

import nl.giorgos.adventofcode.Utils
import org.junit.Test
import java.nio.file.InvalidPathException

class Day13 {
    private val fileName = "input13.txt"
    private val input = this.javaClass.getResourceAsStream(fileName) ?: throw InvalidPathException( fileName, "Invalid input")
    private val inputText = Utils.convertFileToString(input).split("\n")
    private val lines = inputText.subList(0, inputText.size - 1)

    @Test
    fun ex1() {
        val myTimestamp = lines.first().toInt()
        val result = lines[1].split(',')
            .asSequence()
            .filter { it != "x" }
            .map {
                val itAsInt = it.toInt()
                Pair(itAsInt, itAsInt - (myTimestamp % itAsInt))
            }.sortedBy { it.second }.map { it.first * it.second }.first()

        println(result)
    }

    @Test
    fun ex2() {
        val busIds = lines[1].split(',')
        val offsets = busIds.mapIndexed { index, s -> Pair(index, if (s == "x") {
            -1
        } else {
            s.toInt()
        }) }.filter { it.second != -1 }
        var condition = true

        var currentTimestamp = offsets.first().second
        while(condition) {
            currentTimestamp += offsets.first().second
            condition = offsets.map {
                (currentTimestamp + it.first) % it.second
            }.sum() != 0
        }
        println(currentTimestamp)
    }

    private fun findMinimumForTwo(currentTimestamp: Int, two: List<Pair<Int, Int>>): Int {

    }
}
