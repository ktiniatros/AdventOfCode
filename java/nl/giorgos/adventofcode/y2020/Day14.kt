package nl.giorgos.adventofcode.y2020

import nl.giorgos.adventofcode.Utils
import org.junit.Test
import java.math.BigInteger
import java.nio.file.InvalidPathException
import kotlin.math.max

class Day14 {
    private val fileName = "input14.txt"
    private val input = this.javaClass.getResourceAsStream(fileName) ?: throw InvalidPathException(
        fileName,
        "Invalid input"
    )
    private val inputText = Utils.convertFileToString(input).split("\n")
    private val lines = inputText.subList(0, inputText.size - 1)

    private fun isMaskLine(line: String) = line.indexOf("mask") == 0
    private fun isMemoryLine(line: String) = line.indexOf("mem") == 0
    private val initialMemoryValue = "000000000000000000000000000000000000"
    @Test
    fun ex1() {
        var mask = parseMaskLine(lines.first())

        val maxMemory = lines.filter { isMemoryLine(it) }.map(::parseMemoryLine).map { it.first }.maxOrNull()!!
        println("maxM: $maxMemory")

        val memory = Array(maxMemory + 1) { initialMemoryValue }.toMutableList()

        for (i in 1 until lines.size) {
            val line = lines[i]

            if (isMaskLine(line)) {
                mask = parseMaskLine(line)
            }

            if (isMemoryLine(line)) {
                val it = parseMemoryLine(line)
                val valueBeforeMask = if (it.second != 0) {
                    val binary = Integer.toBinaryString(it.second)
                    println("${it.first} ${it.second} $binary")
                    val mem = memory[it.first].toCharArray().toMutableList()
                    var memIndex = mem.size - 1
                    binary.reversed().forEachIndexed { _, c ->
                        mem[memIndex] = c
                        memIndex -= 1
                    }

                    for (mi in memIndex downTo 0) {
                        mem[mi] = '0'
                    }

                    mem
                } else {
                    initialMemoryValue.toMutableList()
                }

                mask.forEach { (t, u) ->
                    valueBeforeMask[t] = u.toString().first()
                }
                memory[it.first] = valueBeforeMask.toCharArray().concatToString()
            }
        }


//        memory.map {
//            Integer.parseInt(it, 2)
//        }.forEach(::println)


        val result = memory.map {
            BigInteger(it, 2).toLong()
        }.sum()


        //too high: 9993763675476
        //too high: 9615006043476
        println("Result: $result")
    }

    private fun parseMemoryLine(line: String): Pair<Int, Int> {
        val parts1 = line.split("] = ")
        val parts2 = parts1.first().split("mem[")
        return Pair(parts2.last().toInt(), parts1.last().toInt())
    }

    private fun parseMaskLine(line: String): Map<Int, Int> {
        val parts = line.split("= ")
        val result = mutableMapOf<Int, Int>()
        parts.last().forEachIndexed { index, c ->
            if (c == '1' || c == '0') {
                result.put(index, Character.getNumericValue(c))
            }
        }
        return result
    }

    @Test
    fun ex2() {
        for (i in 3 downTo 0) {
            println(i)
        }
    }
}
