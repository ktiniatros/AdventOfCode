package nl.giorgos.adventofcode.y2020

import nl.giorgos.adventofcode.Utils
import org.junit.Test
import java.nio.file.InvalidPathException
import java.text.ParseException

class Day8 {
    private val fileName = "input8.txt"
    private val input = this.javaClass.getResourceAsStream(fileName) ?: throw InvalidPathException( fileName, "Invalid input")
    private val inputText = Utils.convertFileToString(input).split("\n")

    private val commands = inputText.subList(0, inputText.size  - 1)

    private val parsedCommands = commands.map {
        val parts = it.split(" ")
        if (parts.size != 2) throw ParseException("Invalid entry: $it", 1)
        Pair(parts.first(), parts[1].toInt())
    }

    private var acc = 0

    private val executedCommands = mutableListOf<Int>()

    @Test
    fun ex1() {
        var index = 0
        while (!executedCommands.contains(index)) {
            var offset = 1
            val command = parsedCommands[index].first
            val value = parsedCommands[index].second
            when (command) {
                "acc" -> {
                    acc += value
                }
                "jmp" -> {
                    offset = value
                }
                else -> Unit
            }
            executedCommands.add(index)
            index += offset
        }
        println("Executed: ${executedCommands.size} times and acc is now: $acc, last command was at line: ${executedCommands.last() + 1}")

    }

    @Test
    fun ex2() {
        val changesTriedAlready = mutableListOf<Int>()
        var index = 0
        var tries = 0
        while (index < 605) {
            acc = 0
            index = 0
            executedCommands.clear()
            tries += 1
            while (!executedCommands.contains(index) && index < 605) {
                var offset = 1
                var command = parsedCommands[index].first
                val value = parsedCommands[index].second
                if (changesTriedAlready.size != tries && listOf("nop", "jmp").contains(command) && !changesTriedAlready.contains(index)) {
                    changesTriedAlready.add(index)
                    command = if (command == "nop") {
                        "jmp"
                    } else {
                        "nop"
                    }
                }
                when (command) {
                    "acc" -> {
                        acc += value
                    }
                    "jmp" -> {
                        offset = value
                    }
                    else -> Unit
                }
                executedCommands.add(index)
                index += offset
            }
        }

        println("Executed: ${executedCommands.size} times and acc is now: $acc, last command was at line: ${executedCommands.last() + 1}")
    }
}
