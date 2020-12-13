package nl.giorgos.adventofcode.y2020

import nl.giorgos.adventofcode.Utils
import org.junit.Test
import java.math.BigInteger
import java.nio.file.InvalidPathException

class Day13 {
    private val fileName = "input13.txt"
    private val input = this.javaClass.getResourceAsStream(fileName) ?: throw InvalidPathException(
        fileName,
        "Invalid input"
    )
    private val inputText = Utils.convertFileToString(input).split("\n")
    private val lines = inputText.subList(0, inputText.size - 1)

    @Test
    fun ex1() {
        val myTimestamp = lines.first().toInt()
        val result = lines[1].split(',')
            .asSequence()
            .filter { it != "x" }
            .map {
                val itAsInt = it.toInt()
                Pair(itAsInt, itAsInt - (myTimestamp % itAsInt))
            }.sortedBy { it.second }.map { it.first * it.second }.first()

        println(result)
    }

    @Test
    fun ex2() {
        val before = System.currentTimeMillis()
        val busIds = lines[1].split(',')
        val invalid = (-1).toBigInteger()
        var offsets = busIds.mapIndexed { index, s -> Pair(
            index.toBigInteger(), if (s == "x") {
                invalid
            } else {
                s.toBigInteger()
            }
        ) }.filter { it.second != invalid }.sortedByDescending { it.second }
        println(offsets)


        var condition = false

        var loopIndex = 2.toBigInteger()
        val step = offsets.first().second

        var timestamp = loopIndex
        while (!condition) {
            timestamp = loopIndex * offsets.first().second - offsets.first().first

//            if (timestamp > 1068781.toBigInteger()) throw Exception("stap")
//            if (timestamp == 1068781.toBigInteger()) println("current timestamp: $loopIndex $timestamp")
            condition = offsets.map {
//                if (timestamp == 1068781.toBigInteger()) {
//                    println("First: ${it.first}, second: ${it.second}")
//                }
                (timestamp + it.first) % it.second == 0.toBigInteger()
            }.all { it }
            loopIndex += 1.toBigInteger()
        }

        println("Took; ${System.currentTimeMillis() - before}")
        println(timestamp)

    }

    @Test
    fun ex22() {
        val before = System.currentTimeMillis()
        val busIds = lines[1].split(',')
        val invalid = (-1).toBigInteger()
        val offsets = busIds.mapIndexed { index, s -> Pair(
            index.toBigInteger(), if (s == "x") {
                invalid
            } else {
                s.toBigInteger()
            }
        ) }.filter { it.second != invalid }.map { Pair(it.first.toInt(), it.second.toInt()) }


//        println(gcd(arrayOf(7, 14)))
//        println(gcd(arrayOf(14, 7)))
//        println(lcm(arrayOf(7, 14)))
//        println(lcm(arrayOf(14, 7)))
        println((gcdEx2(Pair(0, 7), Pair(2, 6))))

    }

    private fun gcdEx2(x: Pair<Int, Int>, y: Pair<Int, Int>): Int {
        return if (y.second == 0) x.second else gcdEx2(
            y, Pair(
                x.first,
                (x.first + x.second) % (y.second - y.first)
            )
        )
    }

    private fun gcd(x: Int, y: Int): Int {
        return if (y == 0) x else gcd(y, x % y)
    }
//
//    fun gcd(vararg numbers: Int): Int {
//        return Arrays.stream(numbers).reduce(0) { x, y -> gcd(x, y) }
//    }

    fun gcd(numbers: Array<Int>) = numbers.reduce { x, y -> gcd(x, y) }

    fun lcm(numbers: Array<Int>) = numbers.reduce { x, y -> x * (y / gcd(x, y)) }
//    fun lcm(vararg numbers: Int): Int {
//        return Arrays.stream(numbers).reduce(1) { x, y -> x * (y / gcd(x, y)) }
//    }

    fun List<BigInteger>.sum(): BigInteger = this.foldRight(0.toBigInteger()) { acc, next ->
        acc + next
    }

    @Test
    fun pamepali() {
        val before = System.currentTimeMillis()
        val busIds = lines[1].split(',')
        val invalid = (-1).toBigInteger()
        val offsets = busIds.mapIndexed { index, s -> Pair(
            index.toBigInteger(), if (s == "x") {
                invalid
            } else {
                s.toBigInteger()
            }
        ) }.filter { it.second != invalid }.sortedBy { it.second }.map { Pair(
            it.first.toInt(),
            it.second.toInt()
        ) }

        println(offsets)

        var c = offsets[0].second
        for (i in offsets.indices) {
            for (y in i+1 until offsets.size) {
                c = leastCommon(offsets[i], offsets[y], c)
            }
        }

        println(c)

    }

    private fun leastCommon(pair1: Pair<Int, Int>, pair2: Pair<Int, Int>, c: Int): Int {
        var current = c
        var c1 = current - pair1.first
        var c2 = current - pair2.first
        var check1 = c1 != 0 && c1 % pair1.second == 0
        var check2 = c2 != 0 && c2 % pair2.second == 0
        while (!(check1 && check2)) {
            c1 = current - pair1.first
            c2 = current - pair2.first
//            println("pame: $current $c2")
            check1 = c1 != 0 && c1 % pair1.second == 0
            check2 = c2 != 0 && c2 % pair2.second == 0
            current += 1
        }
        return current - 1
    }
}
