package nl.giorgos.adventofcode.y2020

import nl.giorgos.adventofcode.Utils
import org.junit.Test
import java.nio.file.InvalidPathException
import kotlin.math.abs

class Day12 {
    private val fileName = "input12.txt"
    private val input = this.javaClass.getResourceAsStream(fileName) ?: throw InvalidPathException( fileName, "Invalid input")
    private val inputText = Utils.convertFileToString(input).split("\n")
    private val lines = inputText.subList(0, inputText.size - 1)

    @Test
    fun ex1() {
        //positive east, south
        //negative west, north
        val position = arrayOf(0, 0)
        var currentDirection = 'E'
        lines.forEach {
            val number = it.substring(1 until it.length).toInt()
            when(it.first()) {
                'F' -> {
                    when (currentDirection) {
                        'N' -> position[1] -= number
                        'S' -> position[1] += number
                        'E' -> position[0] += number
                        'W' -> position[0] -= number
                        else -> throw Exception("WTF distance: $currentDirection")
                    }
                }
                'L' -> {
                    currentDirection = changeDirection(currentDirection, -number)
                }
                'R' -> {
                    currentDirection = changeDirection(currentDirection, number)
                }
                'N' -> {
                    position[1] -= number
                }
                'S' -> {
                    position[1] += number
                }
                'E' -> {
                    position[0] += number
                }
                'W' -> {
                    position[0] -= number
                }
                else -> throw Exception("WTF: ${it.first()}")
            }
        }

        val manhattanDistance = position.map(Math::abs).sum()
        println("manhattanDistance is: $manhattanDistance")
    }

    private fun changeDirection(currentDirection: Char, change: Int): Char {
        val currentDegrees = convertDirectionToDegrees(currentDirection)
        val newDirection = normaliseDegrees(currentDegrees + change)
        if (newDirection > 270) {
            throw Exception("Problem: $newDirection $change")
        }
        return convertDegreesToDirection(newDirection)
    }

    private fun normaliseDegrees(degrees: Int): Int {
        if (degrees > 270) {
            return degrees - 360
        }
        if (degrees < 0) {
            return 360 + degrees
        }
        return degrees
    }

    private fun convertDirectionToDegrees(direction: Char) = when(direction) {
        'N' -> 270
        'S' -> 90
        'E' -> 0
        'W' -> 180
        else -> throw Exception("Invalid direction: $direction")
    }

    private fun convertDegreesToDirection(degrees: Int) = when (degrees) {
        0 -> 'E'
        90 -> 'S'
        180 -> 'W'
        270 -> 'N'
        else -> throw Exception("Invalid degrees: $degrees")
    }

    private fun sign(i: Int) = if (i > 0) {
        1
    } else {
        -1
    }

    @Test
    fun ex2() {
        val waypoint = arrayOf(10, -1)
        val position = arrayOf(0, 0)

        lines.forEach {
            val number = it.substring(1 until it.length).toInt()
            when(it.first()) {
                'F' -> {
                    position[0] += number * waypoint[0]
                    position[1] += number * waypoint[1]
                }
                'L' -> {
                    val times = number/90
                    for (i in 1..times) {
                        moveLeft(waypoint)
                    }
                }
                'R' -> {
                    val times = number/90
                    for (i in 1..times) {
                        moveRight(waypoint)
                    }
                }
                'N' -> {
                    waypoint[1] -= number
                }
                'S' -> {
                    waypoint[1] += number
                }
                'E' -> {
                    waypoint[0] += number
                }
                'W' -> {
                    waypoint[0] -= number
                }
                else -> throw Exception("WTF: ${it.first()}")
            }
        }

        val manhattanDistance = position.map(Math::abs).sum()
        println("manhattanDistance is: $manhattanDistance")
    }

    private fun moveRight(point: Array<Int>) {
        val x1 = sign(point[0])
        val y1 = sign(point[1])
        val xSign = abs(x1) * (-(y1))

        val x = xSign * abs(point[1])
        val y = x1 * abs(point[0])
        point[0] = x
        point[1] = y
    }

    private fun moveLeft(point: Array<Int>) {
        val x1 = sign(point[0])
        val y1 = sign(point[1])

        val ySign = (-x1) * abs(y1)

        val x = y1 * abs(point[1])
        val y = ySign * abs(point[0])
        point[0] = x
        point[1] = y
    }
}
