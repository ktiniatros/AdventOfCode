package nl.giorgos.adventofcode.y2020

import nl.giorgos.adventofcode.Utils
import org.junit.Test
import java.nio.file.InvalidPathException

class Day6 {
    private val input = this.javaClass.getResourceAsStream("input6.txt") ?: throw InvalidPathException( "input4", "Invalid input")
    private val inputText = Utils.convertFileToString(input).split("\n\n")

    private fun isNotBreakline(char: Char) = char != '\n'

    @Test
    fun ex1() {
        val result = inputText.map {
            it.toCharArray().filter(::isNotBreakline).distinct().count()
        }.sum()

        println(result)
    }

    @Test
    fun ex2() {
        val people = inputText.toMutableList()
        val last = inputText.last().dropLast(1)
        people.removeLast()
        people.add(last)

        val result = people.map(::findAllYes).sum()
        println(result)
    }

    private fun findAllYes(s: String): Int {
        val answers = s.split('\n')
        var yes = 0
        answers.first().forEach {
            for (i in 1 until answers.size) {
                val current = answers[i]
                if (!current.contains(it)) return@forEach
            }
            yes += 1
        }
        return yes
    }
}
