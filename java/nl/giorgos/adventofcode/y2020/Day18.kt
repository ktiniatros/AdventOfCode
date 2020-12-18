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

    private fun wrapParenthesisToPlus(line: String): String {
        var index: Int = line.indexOf('+')
        val plusOccurences = mutableListOf<Int>()
        while (index >= 0) {
            plusOccurences.add(index)
            index = line.indexOf('+', index + 1)
        }
        println(plusOccurences)

        val list = line.toCharArray().toMutableList()
        return if (plusOccurences.size > 0) {
            var leftParenthesisAdded = 0
            var rightParenthesisAdded = 0
             plusOccurences.forEach {
                 list.add(it - 2 + leftParenthesisAdded + rightParenthesisAdded, '(')
                 leftParenthesisAdded += 1
                 val nextIndex = it + leftParenthesisAdded + 2 + rightParenthesisAdded
                 val next = list[nextIndex]
                 if (next.isDigit()) {
                     list.add(it + leftParenthesisAdded + 3 + rightParenthesisAdded, ')')
                     rightParenthesisAdded += 1
                 } else if (next == '(') {
                     val s = list.joinToString("").substring(nextIndex + 1 until list.size)
                     val indexOfClosingParenthesis = findEndingParenthesis(s)
//                     println(list.joinToString(""))
//                     println("indexOfClosingParenthesis: $indexOfClosingParenthesis")
                     list.add(nextIndex + indexOfClosingParenthesis + 1, ')')
                     rightParenthesisAdded += 1
                     println(list.joinToString(""))
                 } else {
                     println("Invalid next: $next")
                 }
             }
            list.joinToString("")
        } else {
            line
        }
    }

    private fun findEndingParenthesis(s: String): Int {
        println("Find $s")
        var leftParenthesis = 1
        var rightParenthesis = 0
        s.forEachIndexed { index, c ->
            when(c) {
                '(' -> leftParenthesis += 1
                ')' -> rightParenthesis += 1
            }
            if (leftParenthesis == rightParenthesis) {
                return index
            }
        }
        throw java.lang.IllegalStateException("Invalid string for right parenthesis: $s")
    }

    @Test
    fun ex2() {
        println(wrapParenthesisToPlus(lines.first()))
    }
}
