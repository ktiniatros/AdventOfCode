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
    private var lines = inputText.subList(0, inputText.size - 1)

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

    var re = 0

    class Rule(val input: List<String>) {

        fun String.isNumber() = this.all { it.isDigit() }

        fun isReady() = input.none { it.any { it.isDigit() } }

        private fun toStringForComparison() = input.joinToString("|")
        override fun toString() = input.joinToString("")

        fun containedIn(list: List<Rule>) = list.map { it.toStringForComparison() }.contains(this.toStringForComparison())

        fun debug() = println(toStringForComparison())

        fun <T> MutableList<T>.replaceWith(at: Int, newMembers: List<T>): MutableList<T> {
            removeAt(at)
            newMembers.forEachIndexed { index, t ->
                add(at + index, t)
            }
            return this
        }

        private fun combinationsList(currentIndex: Int, list: MutableList<List<String>>, result: MutableList<MutableList<String>>): List<List<String>> {
            return if (currentIndex == list.size) {
                result
            } else {
                val subList = list[currentIndex]
                val temp = result.toMutableList()
                temp.forEachIndexed { index, it ->
                    temp[index] = it.toMutableList()
                }
                result.clear()
                subList.forEach { subString ->
                    if (temp.isEmpty()) {
                        result.add(mutableListOf(subString))
                    }
                    temp.forEach {
                        val itt = it.toMutableList()
                        itt.add(subString)
                        result.add(itt)
                    }
                }
                combinationsList(currentIndex + 1, list, result)
            }
        }

        fun replaceFrom(map: Map<Int, List<Rule>>): List<Rule> {
            val outputs = mutableListOf<List<String>>()
            input.forEachIndexed { _, it ->
                if (it.isNumber()) {
                    val replacements = map[it.toInt()] ?: throw Exception("coundlnt' find $it in map")
                    val tempOutputs = mutableListOf<List<String>>()
                    outputs.forEach {
                        tempOutputs.add(it)
                    }
                    outputs.clear()

                    if (tempOutputs.isEmpty()) {
                        replacements.forEach { _ -> tempOutputs.add(emptyList()) }
                    }

                    replacements.forEachIndexed { _, replacement ->
                        tempOutputs.forEach { tempOutput ->
                            outputs.add(tempOutput + replacement.input)
                        }
                    }
                } else {
                    if (outputs.isEmpty()) {
                        outputs.add(listOf(it))
                    } else {
                        val letterRule = it
                        val tempOutputs = outputs.map { it.toMutableList() + letterRule}
                        outputs.clear()
                        tempOutputs.forEach {
                            outputs.add(it)
                        }
                    }

                }
            }

            return outputs.toSet().map { Rule(it) }
        }
    }

    fun completeRule(map: Map<Int, List<Rule>>, rules: List<Rule>): List<Rule> {
//        println(rules)
        var isReady = true
        val tempRules = mutableListOf<Rule>()
//        println(rules)
        rules.forEachIndexed { index, rule ->
            if (!rule.isReady()) {
                isReady = false
                val result = rule.replaceFrom(map)
//                println(result)
                tempRules.addAll(result)
            } else {
                tempRules.add(rule)
            }
        }
//        println("")
//        println("")
//        println("")

        re += 1
        println("Completed: $re lists:${tempRules.size}, isReady:$isReady")

        return if (isReady) {
            tempRules
        } else {
            completeRule(map, tempRules)
        }
    }

    private fun findMatchesFromRules(): Int {
        val rulesMap = mutableMapOf<Int, List<Rule>>()

        val finalRules = mutableMapOf<Int, List<Rule>>()

        val acceptableMessages = mutableListOf<String>()

        var sawBlank = false
        var matches = 0
        lines.forEachIndexed { _, it ->
            val isRule = it.indexOf(':') > -1
            if (isRule) {
                val rule = parseRule(it)
                val ruleContent = parseRule(it).second
                rulesMap[rule.first] = ruleContent.split(" | ").map { Rule(it.split(" ")) }
            }

            if (sawBlank) {
//                println(finalRules[0])
//                if (finalRules[0]?.map { it.toString() }?.contains(it) == true) {
                if (acceptableMessages.contains(it)) {
                    matches += 1
                }
            }

            if (it.isEmpty()) {
                println("blank")
                sawBlank = true
//                rulesMap.forEach(::println)
//                finalRules = completeMapRules(rulesMap).toMutableMap()
                val before1 = System.currentTimeMillis()
                finalRules[0] = completeRule(rulesMap, rulesMap[0] ?: throw Exception("No."))
                println("Getting the ryles took: ${System.currentTimeMillis() - before1}")
                val before2 = System.currentTimeMillis()
                finalRules[0]?.map { acceptableMessages.add(it.toString()) }
                println("g0t the rules: ${System.currentTimeMillis() - before2}")
//                acceptableMessages.forEach(::println)
            }
        }
        return matches
    }

    @Test
    fun ex1() {
        val matches = findMatchesFromRules()

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
        lines = lines.map {
            when {
                it == "8: 42" -> "8: 42 8"
                it == "11: 42 31" -> "11: 42 31"
                else -> it
            }
        }

        val matches = findMatchesFromRules()

        println(matches)
    }
}
