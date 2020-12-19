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

    fun isReady(s: String) = s == "a" || s == "b"

    fun completeRules(map: Map<Int, List<List<String>>>): Map<Int, List<List<String>>> {
        val finalMap = mutableMapOf<Int, MutableList<List<String>>>()

        var isReady = true
        map.forEach {
            val key = it.key
            println("")
            println("")
            println(key)
            finalMap[it.key] = mutableListOf()
            val listOfSubRules = it.value
            listOfSubRules.forEachIndexed { subRuleIndex, subRule: List<String> ->
                val newListOfSubRules = mutableListOf<List<String>>()
                subRule.forEachIndexed { index, subRuleMember: String ->
                    if (!isReady(subRuleMember)) {
                        isReady = false
                        val replacedSubRules = map[subRuleMember.toInt()] ?: throw Exception("WTF, couldn't find $subRuleMember in map")
                        replacedSubRules.forEach {
                            val newOnes = subRule.toMutableList().replaceWith(index, it)
                            newListOfSubRules.add(newOnes)
                        }
                    }
                }
                newListOfSubRules.forEach {
                    finalMap[key]?.add(it)
                }
            }
        }

        finalMap.forEach(::println)

        return if (isReady) {
            finalMap
        } else {
            completeRules(finalMap)
        }
    }

    @Test
    fun ex1() {
        val rulesMap = mutableMapOf<Int, List<List<String>>>()
        lines.forEachIndexed { _, it ->
            val isRule = it.indexOf(':') > -1
            if (isRule) {
                val rule = parseRule(it)
                val ruleContent = rule.second.split(" ")
                val pipeIndex = ruleContent.indexOf("|")
                rulesMap[rule.first] = if (pipeIndex > -1) {
                    listOf(ruleContent.subList(0, pipeIndex), ruleContent.subList(pipeIndex + 1, ruleContent.size))
                } else {
                    listOf(ruleContent)
                }
            }
        }
        rulesMap.forEach {
            println(it)
            println("")
        }
        val completedRules = completeRules(rulesMap)
//        val firstRule = completedRules[0] ?: throw Exception("Impossible")

        completedRules.forEach(::println)
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
