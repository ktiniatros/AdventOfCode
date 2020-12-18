package nl.giorgos.adventofcode.y2020

import nl.giorgos.adventofcode.Utils
import org.junit.Test
import java.nio.file.InvalidPathException

class Day18 {
    private val fileName = "input18.txt"
    private val input = this.javaClass.getResourceAsStream(fileName) ?: throw InvalidPathException(
            fileName,
            "Invalid input"
    )
    private val inputText = Utils.convertFileToString(input).split("\n")
    private val lines = inputText.subList(0, inputText.size - 1)

    @Test
    fun ex1() {
        val result = lines.map {
            parseLine(it) { numbers, operators, res ->
                var result = res
                while (operators.isNotEmpty()) {
                    val nextOperator = operators.first()
                    result = if (nextOperator == '+') {
                        numbers[0] + numbers[1]
                    } else {
                        numbers[0] * numbers[1]
                    }
                    operators.removeAt(0)
                    numbers.removeAt(0)
                    numbers.removeAt(0)
                    numbers.add(0, result)
                }
                result
            }
        }.sum()
        //689976794 too low
        //202553439706
        println(result)

    }

    private fun parseLine(line: String, cb: (MutableList<Long>, MutableList<Char>, Long) -> Long): Long {
        var result = 1L
        var amountOfOpenParenthesis = 0
        val pendingSubResult = mutableListOf<Char>()

        val numbers = mutableListOf<Long>()
        val operators = mutableListOf<Char>()

        line.forEachIndexed { _, it ->

            if (amountOfOpenParenthesis > 0) {

                if (it == ')') {
                    val previousParenthesisCount = amountOfOpenParenthesis
                    amountOfOpenParenthesis -= 1
                    if (amountOfOpenParenthesis == 0) {
                        if (previousParenthesisCount > 0 && amountOfOpenParenthesis == 0) {
                            val thisDigit = parseLine(pendingSubResult.joinToString(""), cb)
                            numbers.add(thisDigit)
                        }
                        pendingSubResult.clear()
                    } else {
                        pendingSubResult.add(it)
                    }

                } else {
                    if (it == '(') {
                        amountOfOpenParenthesis += 1
                    }
                    pendingSubResult.add(it)
                }


            } else {
                if (it.isDigit()) {
                    val thisDigit = Character.getNumericValue(it)
                    numbers.add(thisDigit.toLong())
                } else {
                    when (it) {
                        ' ' -> Unit
                        '+' -> {
                            operators.add('+')
                        }
                        '*' -> {
                            operators.add('*')
                        }
                        '(' -> {
                            amountOfOpenParenthesis += 1
                        }
                        ')' -> {
                            throw IllegalStateException("Den eprese edw na er8ei")
                        }
                        else -> {
                            throw IllegalStateException("Invalid operator: $it")
                        }
                    }
                }
            }


        }

        result = cb(numbers, operators, result)

        return result
    }

    @Test
    fun ex2() {
        val result = lines.map {
            parseLine(it) { numbers, operators, res ->
                var result = res
                while (operators.isNotEmpty()) {
                    while (operators.contains('+')) {
                        val plusIndex = operators.indexOf('+')
                        result = numbers[plusIndex] + numbers[plusIndex + 1]
                        operators.removeAt(plusIndex)
                        numbers.removeAt(plusIndex)
                        numbers.removeAt(plusIndex)
                        numbers.add(plusIndex, result)
                    }
                    if (operators.isNotEmpty()) {
                        val multiplyIndex = operators.indexOf('*')
                        result = numbers[multiplyIndex] * numbers[multiplyIndex + 1]
                        operators.removeAt(multiplyIndex)
                        numbers.removeAt(multiplyIndex)
                        numbers.removeAt(multiplyIndex)
                        numbers.add(multiplyIndex, result)
                    }
                }
                result
            }
        }.sum()
        //88534268715686
        println(result)
    }
}
