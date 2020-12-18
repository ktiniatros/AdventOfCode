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
        val result = lines.map(::parseLine).sum()
        //689976794 too low
        //202553439706
        println(result)
    }

    private fun parseLine(line: String): Long {
//        println("Parsing: $line")
        var result = 1L
        var lastOperator = '*'
        var amountOfOpenParenthesis = 0
        val pendingSubResult = mutableListOf<Char>()
        line.forEachIndexed { index, it ->

            if (amountOfOpenParenthesis > 0) {

                if (it == ')') {
                    val previousParenthesisCount = amountOfOpenParenthesis
                    amountOfOpenParenthesis -= 1
                    if (amountOfOpenParenthesis == 0) {
                        if (previousParenthesisCount > 0 && amountOfOpenParenthesis == 0) {
//                            println("edw? ${pendingSubResult.joinToString("")}")
                            val thisDigit = parseLine(pendingSubResult.joinToString(""))
                            result = if (lastOperator == '*') {
                                result * thisDigit
                            } else {
                                result + thisDigit
                            }
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
//                    println(pendingSubResult.joinToString(""))
                }


            } else {
                if (it.isDigit()) {
                    val thisDigit = Character.getNumericValue(it)
//                    println("Prin: $previousDigit, twra: $thisDigit")
                    result = if (lastOperator == '*') {
                        result * thisDigit
                    } else {
                        result + thisDigit
                    }
//                    println("twra result: $result")
                } else {
                    when (it) {
                        ' ' -> Unit
                        '+' -> {
                            lastOperator = '+'
                        }
                        '*' -> {
                            lastOperator = '*'
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
        return result
    }

    private fun parseLine2(line: String): Long {
//        println("Parsing: $line")
        var result = 1L
        var lastOperator = '*'
        var amountOfOpenParenthesis = 0
        val pendingSubResult = mutableListOf<Char>()

        val numbers = mutableListOf<Long>()
        val operators = mutableListOf<Char>()

        line.forEachIndexed { index, it ->

            if (amountOfOpenParenthesis > 0) {

                if (it == ')') {
                    val previousParenthesisCount = amountOfOpenParenthesis
                    amountOfOpenParenthesis -= 1
                    if (amountOfOpenParenthesis == 0) {
                        if (previousParenthesisCount > 0 && amountOfOpenParenthesis == 0) {
                            val thisDigit = parseLine2(pendingSubResult.joinToString(""))
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

//        println(numbers)
//        println(operators)
        while(operators.isNotEmpty()) {
            while(operators.contains('+')) {
                val plusIndex = operators.indexOf('+')
                result = numbers[plusIndex] + numbers[plusIndex + 1]
                operators.removeAt(plusIndex)
                numbers.removeAt(plusIndex)
                numbers.removeAt(plusIndex)
                numbers.add(plusIndex, result)
            }
            if (operators.isNotEmpty()) {
//                println(operators)
//                println("now multiply: $numbers")
                val multiplyIndex = operators.indexOf('*')
//                println(multiplyIndex)
                result = numbers[multiplyIndex] * numbers[multiplyIndex + 1]
                operators.removeAt(multiplyIndex)
                numbers.removeAt(multiplyIndex)
                numbers.removeAt(multiplyIndex)
                numbers.add(multiplyIndex, result)
            }
        }

//        println(result)

        return result
    }

    @Test
    fun ex2() {
        println(lines.map(::parseLine2).sum())
    }
}
