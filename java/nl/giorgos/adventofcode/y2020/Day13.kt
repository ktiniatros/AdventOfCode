package nl.giorgos.adventofcode.y2020

import nl.giorgos.adventofcode.Utils
import org.junit.Test
import java.math.BigInteger
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
        val before = System.currentTimeMillis()
        val busIds = lines[1].split(',')
        val invalid = (-1).toBigInteger()
        val offsets = busIds.mapIndexed { index, s -> Pair(index.toBigInteger(), if (s == "x") {
            invalid
        } else {
            s.toBigInteger()
        }) }.filter { it.second != invalid }.sortedByDescending { it.second }
        println(offsets)


        var condition = false

        var factor = 1.toBigInteger()
        val base = offsets.first().second - offsets.first().first
        var currentTimestamp = base * factor + ((factor - 1.toBigInteger()) * offsets.first().first)
        while(!condition) {
            currentTimestamp = base * factor + ((factor - 1.toBigInteger()) * offsets.first().first)
            condition = offsets.map {
                (currentTimestamp + it.first) % it.second
            }.none { it != 0.toBigInteger() }
            factor += 1.toBigInteger()
        }

        println("Took; ${System.currentTimeMillis() - before}")
        println(currentTimestamp)

    }
}
