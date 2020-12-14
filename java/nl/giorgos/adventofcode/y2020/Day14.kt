package nl.giorgos.adventofcode.y2020

import nl.giorgos.adventofcode.Utils
import org.junit.Test
import java.math.BigInteger
import java.nio.file.InvalidPathException

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

        val result = memory.map {
            BigInteger(it, 2).toLong()
        }.sum()

        //too high: 9993763675476
        //correct: 9615006043476
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
                result[index] = Character.getNumericValue(c)
            }
        }
        return result
    }

    private fun parseAllMaskLine(line: String): String {
        val parts = line.split("= ")
        return parts.last()
    }

    @Test
    fun ex2() {
        var mask = parseAllMaskLine(lines.first())

        val memory = mutableMapOf<Long, String>()

        for (i in 1 until lines.size) {
            val line = lines[i]

            if (isMaskLine(line)) {
                mask = parseAllMaskLine(line)
            }

            if (isMemoryLine(line)) {
                val it = parseMemoryLine(line)
                val binary = Integer.toBinaryString(it.first)

                val valueBeforeMask= Array(mask.length) {
                    '0'
                }.toMutableList()

                var valueBeforeMaskIndex = valueBeforeMask.size - 1
                binary.reversed().forEach {
                    valueBeforeMask[valueBeforeMaskIndex] = it
                    valueBeforeMaskIndex -= 1
                }

                mask.forEachIndexed { t, c ->
                    when (c) {
                        '1', 'X' -> valueBeforeMask[t] = c
                        else -> Unit
                    }
                }
                val maskedMemoryAddress = valueBeforeMask.toCharArray().concatToString()
                val decodedMemories = findAllFluctuatedStrings(maskedMemoryAddress)
                decodedMemories.forEach { decodedMemoryBinaryString ->
                    val decimalMemoryIndex = BigInteger(decodedMemoryBinaryString, 2).toLong()
                    memory[decimalMemoryIndex] = Integer.toBinaryString(it.second)
                }
            }
        }

        val result = memory.map {
            BigInteger(it.value, 2).toLong()
        }.sum()

        println("Result: $result")
    }


    private fun findAllFluctuatedStrings(it: String): List<String> {
        val a = mutableListOf<Int>()
        it.toCharArray().foldIndexed(a) { i, newA, c ->
            if (c == 'X') {
                newA.add(i)
            }
            newA
        }

        if (a.isEmpty()) return listOf(it)

        val combis = mutableListOf<String>()
        val itList = mutableListOf(it.toMutableList())
        a.forEach {
            combis.clear()
            itList.forEach { it2 ->
                it2[it] = '0'
                combis.add(it2.joinToString(""))
                it2[it] = '1'
                combis.add(it2.joinToString(""))
            }
            itList.clear()
            combis.forEach { tempCombi ->
                itList.add(tempCombi.toMutableList())
            }
        }

        return combis
    }
}
