package nl.giorgos.adventofcode.y2020

import nl.giorgos.adventofcode.Utils
import org.junit.Test

import java.nio.file.InvalidPathException

class Day1 {

    private val input = this.javaClass.getResourceAsStream("input1.txt") ?: throw InvalidPathException( "input4", "Invalid input")
    private val list = Utils.convertFileToStringArray(input)

    @Test
    fun find2020() {
        val numbers = list.map { it.toInt() }
        for (i in numbers.indices) {
            val first = numbers[i]
            for (j in i+1 until numbers.size) {
                val second = numbers[j]
                if (first+second == 2020) {
//                    println("First is: $first")
//                    println("Second is: $second")
                    println("Multiplied is: ${first*second}")
                }
            }
        }
    }

    @Test
    fun find2020ForThree() {
        val numbers = list.map { it.toInt() }
        for (i in numbers.indices) {
            val first = numbers[i]
            for (j in i+1 until numbers.size) {
                val second = numbers[j]
                for (k in j+1 until numbers.size) {
                    val third = numbers[k]
                    if (first+second+third == 2020) {
//                        println("First is: $first")
//                        println("Second is: $second")
//                        println("Third is: $third")
                        println("Multiplied is: ${first*second*third}")
                    }
                }
            }
        }
    }
}
