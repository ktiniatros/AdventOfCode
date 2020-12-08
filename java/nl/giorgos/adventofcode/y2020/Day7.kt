package nl.giorgos.adventofcode.y2020

import nl.giorgos.adventofcode.Utils
import org.junit.Test
import java.nio.file.InvalidPathException

class Day7 {
    private val fileName = "input7.txt"
    private val input = this.javaClass.getResourceAsStream(fileName) ?: throw InvalidPathException( fileName, "Invalid input")
    private val inputText = Utils.convertFileToString(input).split("\n")

    class ParsedLine(text: String) {
        val listOfBagColors = mutableListOf<String>()
        val map = mutableMapOf<String, Int>()

        init {
            if (!text.contains("no other bags")) {
                val textWithoutBags = text.replace(" bags.", "").replace(" bag.", "").replace(" bags", "").replace(" bag", "")
                val bagsAndNumbers = textWithoutBags.split(", ")
                bagsAndNumbers.forEach {
                    val number = it.first() - '0'
                    val bagColor = it.substring(2 until it.length)
                    listOfBagColors.add(bagColor)
                    map[bagColor] = number
                }
            }

        }
    }

    private val shinyGold = "shiny gold"

    private val rules = inputText.subList(0, inputText.size  - 1)

    private val mapOfRules = mutableMapOf<String, ParsedLine>().apply {
        rules.forEach { line ->
            val parentChild = line.split(" bags contain ")
            this[parentChild.first()] = ParsedLine(parentChild[1])
        }
    }


    @Test
    fun ex1() {
        findRules1(emptySet(), setOf(shinyGold), mapOfRules)
    }

    private fun findRules1(res: Set<String>, rule: Set<String>, mapOfRules: Map<String, ParsedLine>) {
        val result = res.toMutableSet()
        val contain = mapOfRules.filter {
            rule.intersect(it.value.listOfBagColors).isNotEmpty()

        }
        val keys = contain.keys
        result.addAll(keys)

        if (keys.isNotEmpty()) findRules1(result, keys, mapOfRules) else {
            println(result.size)
        }
    }

    @Test
    fun ex2() {
        val nextMap = mapOfRules[shinyGold]!!.map
        findRules2(nextMap.values.sum(), nextMap, mapOfRules)
    }

    private fun findRules2(number: Int, bags: Map<String, Int>, mapOfRules: Map<String, ParsedLine>) {
        var result = number
        val nextMap = mutableMapOf<String, Int>()
        bags.forEach { (t, u) ->
            mapOfRules[t]?.map?.forEach { tt, uu ->
                val theseBagsCount = u * uu
                result += theseBagsCount
                nextMap[tt] = theseBagsCount
            }

        }

        if (nextMap.isEmpty()) {
            println(result)
        } else {
            findRules2(result, nextMap, mapOfRules)
        }
    }
}
