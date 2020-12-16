package nl.giorgos.adventofcode.y2020

import nl.giorgos.adventofcode.Utils
import org.junit.Test
import java.nio.file.InvalidPathException

class Day16 {
    private val fileName = "input16.txt"
    private val input = this.javaClass.getResourceAsStream(fileName) ?: throw InvalidPathException(
            fileName,
            "Invalid input"
    )
    private val inputText = Utils.convertFileToString(input).split("\n")
    private val lines = inputText.subList(0, inputText.size - 1)

    private fun isEmptyLine(s: String) = s.isEmpty()

    private fun parseRule(rule: String): List<IntRange> {
        val result = mutableListOf<IntRange>()
        val parts = rule.split(": ")
        val ranges = parts[1].split(" or ")
        ranges.forEach {
            val minMax = it.split("-")
            result.add(IntRange(minMax[0].toInt(), minMax[1].toInt()))
        }
        return result
    }

    private fun numberIsInvalid(checkNumber: Int, ranges: List<IntRange>) = ranges.none {
        checkNumber in it
    }

    private fun numberIsValid(checkNumber: Int, ranges: List<IntRange>) = ranges.any {
        checkNumber in it
    }

    @Test
    fun ex1() {
        val rules = mutableListOf<IntRange>()
        var emptyLinesSoFar = 0
        var nearByTickets = false
        val invalidNumbers = mutableListOf<Int>()
        lines.forEach {
            if (isEmptyLine(it)) {
                emptyLinesSoFar += 1
            } else {
                when (emptyLinesSoFar) {
                    0 -> {
                        rules.addAll(parseRule(it))
                    }
                    1 -> {

                    }
                    else -> {
                        if (nearByTickets) {
                            val numbersInLine = it.split(",")
                            numbersInLine.forEach { number ->
                                if (numberIsInvalid(number.toInt(), rules)) invalidNumbers.add(number.toInt())
                            }
                        } else if (it == "nearby tickets:") {
                            nearByTickets = true
                        }
                    }

                }
            }

        }

        println("Error rate: ${invalidNumbers.sum()}")
    }

    @Test
    fun ex2() {
        val rules = mutableListOf<IntRange>()
        var emptyLinesSoFar = 0
        var nearByTickets = false
        var yourTicket = false
        val validTickets = mutableListOf<List<Int>>()
        var yourTicketNumbers = listOf<Int>()
        lines.forEach { line ->
            if (isEmptyLine(line)) {
                emptyLinesSoFar += 1
            } else {
                when (emptyLinesSoFar) {
                    0 -> {
                        rules.addAll(parseRule(line))
                    }
                    1 -> {
                        if (yourTicket) {
                            val numbersInLine = line.split(",")
                            val numbers = numbersInLine.map { it.toInt() }
                            yourTicketNumbers = numbers
                        } else if (line == "your ticket:") {
                            yourTicket = true
                        }
                    }
                    else -> {
                        if (nearByTickets) {
                            val numbersInLine = line.split(",")
                            val rulesSize = numbersInLine.size
                            val numbers = numbersInLine.map { it.toInt() }
                            val ticketIsValid = numbers.filterNot { numberIsInvalid(it, rules) }.size == rulesSize
                            if (ticketIsValid) validTickets.add(numbers)
                        } else if (line == "nearby tickets:") {
                            nearByTickets = true
                        }
                    }

                }
            }

        }

        var result = 1L

        val rulesPerIndex = mutableListOf<List<IntRange>>()
        for (i in 0..11 step 2) {
            rulesPerIndex.add(rules.subList(i, i + 2))
        }

        val validRangesIndexMap = mutableMapOf<Int, MutableList<IntRange>>()
        yourTicketNumbers.forEachIndexed { index, myNumber ->
            val tickersRows = validTickets.map { it[index] } + myNumber
            rulesPerIndex.forEach { rule ->
                val validColumn = tickersRows.all { numberIsValid(it, rule) }
                if (validColumn) {
                    val valueSoFar = validRangesIndexMap[index] ?: mutableListOf()
                    valueSoFar.add(rule.first())
                    validRangesIndexMap[index] = valueSoFar
                }
            }
        }

        val departureIndexes = mutableListOf<Int>()

        validRangesIndexMap.remove(19)
        validRangesIndexMap.remove(16)
        while(departureIndexes.size < 6) {
            var index = -1
            var list = emptyList<IntRange>()
            validRangesIndexMap.filter {
                it.value.size == 1
            }.forEach { (t, u) ->
                index = t
                list = u
            }
            departureIndexes.add(index)
            validRangesIndexMap.remove(index)
            validRangesIndexMap.forEach { map ->
                map.value.remove(list.first())
            }
        }

        departureIndexes.forEach {
            result *= yourTicketNumbers[it]
        }

        //18640377961 too low
        //3560312190551 too low
        //3709435214239
        println(result)
    }
}
