package nl.giorgos.adventofcode.y2020

import nl.giorgos.adventofcode.Utils
import org.junit.Test
import java.io.BufferedReader
import java.io.InputStream
import java.nio.file.InvalidPathException

class Day2 {
    class ParsedLine(line: String) {
        val min: Int
        val max: Int
        val char: Char
        val password: String

        init {
            val input = line.split(" ")
            val limitation = input[0].split("-")
            min = limitation.first().toInt()
            max = limitation.last().toInt()
            char = input[1].first()
            password = input[2]
        }

        fun isValid(): Boolean {
            val count = password.toCharArray().count { it == char }
            return count in min..max
        }

        fun isValidPartTwo() = (password[min-1] == char).xor(password[max-1] == char)
    }

    private val input = this.javaClass.getResourceAsStream("input2.txt") ?: throw InvalidPathException( "input4", "Invalid input")
    private val inputAsList = Utils.convertFileToStringArray(input)

    @Test
    fun part1() {
        val validCount = inputAsList.filter { ParsedLine(it).isValid() }.size
        println("There are $validCount valid passwords out of ${inputAsList.size}")
    }

    @Test
    fun part2() {
        val validCount = inputAsList.filter { ParsedLine(it).isValidPartTwo() }.size
        println("There are $validCount valid passwords out of ${inputAsList.size}")
    }

    fun convertFileToStringArray(inputStream: InputStream): List<String> {
        val inputAsList = mutableListOf<String>()
        val reader = BufferedReader(inputStream.reader())
        try {
            var line = reader.readLine()
            while (line != null) {
                inputAsList.add(line)
                line = reader.readLine()
            }
        } finally {
            reader.close()
        }

        return inputAsList
    }
}
