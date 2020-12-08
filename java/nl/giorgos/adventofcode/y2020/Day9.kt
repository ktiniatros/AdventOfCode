package nl.giorgos.adventofcode.y2020

import nl.giorgos.adventofcode.Utils
import org.junit.Test
import java.nio.file.InvalidPathException

class Day9 {
    private val fileName = "input9.txt"
    private val input = this.javaClass.getResourceAsStream(fileName) ?: throw InvalidPathException( fileName, "Invalid input")
    private val inputText = Utils.convertFileToString(input).split("\n")

    @Test
    fun ex1() {

    }

    @Test
    fun ex2() {

    }
}
