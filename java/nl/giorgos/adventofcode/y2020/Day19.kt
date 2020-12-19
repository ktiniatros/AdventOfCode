package nl.giorgos.adventofcode.y2020

import nl.giorgos.adventofcode.Utils
import org.junit.Test
import java.nio.file.InvalidPathException

class Day19 {
    private val fileName = "input19.txt"
    private val input = this.javaClass.getResourceAsStream(fileName) ?: throw InvalidPathException(
            fileName,
            "Invalid input"
    )
    private val inputText = Utils.convertFileToString(input).split("\n")
    private val lines = inputText.subList(0, inputText.size - 1)

    private fun isRule(line: String) = line.getOrNull(1) == ':'

    private fun parseRule(line: String): String {
        val chars = line.split(" ").filter { !it.contains(":")}.map { it.replace("\"", "").first() }
        return chars.joinToString("")
    }

    private fun completeRules(map: Map<Int, String>): Map<Int, String> {
        val letterKeys = map.filter { ruleLine ->
            ruleLine.value.all {
                it == 'a' || it == 'b' || it == '|'
            }
        }.keys
//        println("Valid entry: ")
//        letterKeys.forEach {
//            print("${map[it]}, ")
//        }
//        println("")
        val result = mutableMapOf<Int, String>()
        var isReady = true
        map.forEach { (t, rule) ->
            val r = rule.toCharArray().toMutableList()
            val rString = r.map { it.toString() }.toMutableList()
            r.forEachIndexed { index, it ->
                if (it.isDigit()) {
                    val number = Character.getNumericValue(it)
                    if (letterKeys.contains(number)) {
                        rString[index] = map[number] ?: throw Exception("$number does not exist in $map")
                    }
                }
            }
            val resultLine = rString.joinToString("")
            if (resultLine.any { it.isDigit() }) {
                isReady = false
            }
            result[t] = resultLine
        }
        println("isReady: $isReady $map")
        return if (isReady) {
            result
        } else {
            completeRules(result)
        }
    }

    fun String.findAllIndexesOf(char: Char): List<Int> = this.mapIndexed { index, c ->
        Pair(index, c)
    }.filter { it.second == char }.map { it.first }

    @Test
    fun ex1() {
        val rulesMap = mutableMapOf<Int, String>()
        lines.forEachIndexed { index, it ->
            if (isRule(it)) {
                val rule = parseRule(it)
                rulesMap[index] = rule
            }
        }
        val completedRules = completeRules(rulesMap)
        val firstRule = completedRules[0] ?: throw Exception("Impossible")

        completedRules.forEach(::println)
    }

    @Test
    fun ex2() {
        val a = arrayOf('a', '1', 'e')
        val a2 = a.map { it.toString() }
        println(a2)
    }
}
