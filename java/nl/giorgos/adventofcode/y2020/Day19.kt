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

    private fun parseRule(line: String): Pair<Int, String> {
        val parts = line.split(": ")
        val index = parts[0].toInt()
        val rule = parts[1].replace("\"", "")
        return Pair(index, rule)
    }

    fun String.findAllIndexesOf(char: Char): List<Int> = this.mapIndexed { index, c ->
        Pair(index, c)
    }.filter { it.second == char }.map { it.first }

    fun isReady(s: String) = s.all { it == 'a' || it == 'b' }

    val cachedMap = mutableMapOf<Int, List<String>>()
    private fun completeMapRules(map: Map<Int, List<String>>): Map<Int, List<String>> {
        val finalMap = mutableMapOf<Int, MutableList<String>>()
        var isReady = true

        map.forEach { (ruleNumber, rules) ->
            val tempRules = mutableListOf<String>()
            rules.forEachIndexed { index, rule ->
                if (!isReady(rule)) {
                    isReady = false
                    rule.forEachIndexed { ruleIndex, c ->
                        if (c.isDigit()) {
                            val replacedRules = map[Character.getNumericValue(c)] ?: throw Exception("coundlnt' find $c")
                            replacedRules.forEach { replacedRule ->
                                val ruleAsList = rule.toMutableList()
                                ruleAsList.replaceWith(ruleIndex, replacedRule.toList())
                                val next = ruleAsList.joinToString("")
                                if (!tempRules.contains(next)) {
                                    tempRules.add(next)
                                }
                           }
                        }
                    }
                } else {
                    tempRules.add(rule)
                }
            }
            finalMap[ruleNumber] = tempRules
        }

//        re += 1
//        println("")
//        println("")
//        println("")
//        println(re)
//
//        finalMap.forEach(::println)
//        if (re > 1) throw Exception("")

        return if(isReady) {
            finalMap
        } else {
            completeMapRules(finalMap)
        }
    }

    var re = 0

    @Test
    fun ex1() {
        val rulesMap = mutableMapOf<Int, List<String>>()

        var finalRules = mapOf<Int, List<String>>()

        var sawBlank = false
        var matches = 0
        lines.forEachIndexed { _, it ->
            val isRule = it.indexOf(':') > -1
            if (isRule) {
                val rule = parseRule(it)
                val ruleContent = rule.second.replace(" ", "")
                val pipeIndex = ruleContent.indexOf("|")
                rulesMap[rule.first] = if (pipeIndex > -1) {
                    ruleContent.split("|")
                } else {
                    listOf(ruleContent)
                }
            }

            if (sawBlank) {
//                println(finalRules[0])
                if (finalRules[0]?.contains(it) == true) {
                    matches += 1
                }
            }

            if (it.isEmpty()) {
                println("blank")
                sawBlank = true
                finalRules = completeMapRules(rulesMap)
            }
        }


        println(matches)
    }

    fun <T> MutableList<T>.replaceWith(at: Int, newMembers: List<T>): MutableList<T> {
        removeAt(at)
        newMembers.forEachIndexed { index, t ->
            add(at + index, t)
        }
        return this
    }

    @Test
    fun ex2() {
        val a = arrayOf('a', '1', 'e').toMutableList()
        println(a.replaceWith(0, listOf('2', '3')))
    }
}
