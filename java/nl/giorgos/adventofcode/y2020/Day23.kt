package nl.giorgos.adventofcode.y2020

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*
import kotlin.math.max

class Day23 {
    @Test
    fun ex1() {
        val before = System.currentTimeMillis()
        var currentPickupIndex = 1
        val cupLabels = "394618527".toCharArray().map { Character.getNumericValue(it) }.toMutableList()
        var currentCup = cupLabels[0]
        val minimum = cupLabels.minOrNull() ?: throw Exception("Why cannot min?")
        val maximum = cupLabels.maxOrNull() ?: throw Exception("Why cannot max?")
        for (i in 1..100) {
//            println("Cups: $cupLabels")
//            println("")
//            println("")
//            println("")
//            println(i)
//            println("Current cup: $currentCup")
            var currentPickupIndex2 = currentPickupIndex + 1
            if (currentPickupIndex2 >= cupLabels.size) {
                currentPickupIndex2 -= cupLabels.size
            }
            var currentPickupIndex3 = currentPickupIndex2 + 1
            if (currentPickupIndex3 >= cupLabels.size) {
                currentPickupIndex3 -= cupLabels.size
            }
            val c1Removed = cupLabels[currentPickupIndex]
            val c2Removed = cupLabels[currentPickupIndex2]
            val c3Removed = cupLabels[currentPickupIndex3]
            val pickedUpCups = listOf(c1Removed, c2Removed, c3Removed)
            cupLabels.removeAll(pickedUpCups)

            var destinationCup = currentCup - 1
            if (destinationCup < minimum) {
                destinationCup = maximum
            }
            while (pickedUpCups.contains(destinationCup)) {
                destinationCup -= 1
                if (destinationCup < minimum) {
                    destinationCup = maximum
                }
            }
//            println("Destination cup: $destinationCup")

            val destinationIndex = cupLabels.indexOf(destinationCup)
            cupLabels.addAll(destinationIndex + 1, listOf(c1Removed, c2Removed, c3Removed))

            val currentCupIndex = cupLabels.indexOf(currentCup)
            var nextCurrentCupIndex = currentCupIndex + 1
            if (nextCurrentCupIndex >= cupLabels.size) {
                nextCurrentCupIndex -= cupLabels.size
            }
            currentCup = cupLabels[nextCurrentCupIndex]
            currentPickupIndex = nextCurrentCupIndex + 1
            if (currentPickupIndex >= cupLabels.size) {
                currentPickupIndex -= cupLabels.size
            }
        }

        println(cupLabels)

        val indexOfOne = cupLabels.indexOf(1)
        Collections.rotate(cupLabels, -indexOfOne)

        val actual = cupLabels.subList(1, cupLabels.size).joinToString("")
        println("It took ${System.currentTimeMillis() - before}ms")
        assertEquals("78569234", actual)
    }

//    In the above example (389125467), this would be 934001 and then 159792; multiplying these together produces 149245887792.
    @Test
    fun ex2() {
        var currentPickupIndex = 1
        val cupLabels = "389125467".toCharArray().map { Character.getNumericValue(it) }.toMutableList()
        var currentCup = cupLabels[0]
        val minimum = cupLabels.minOrNull() ?: throw Exception("Why cannot min?")
        var maximum = 1_000_000

//        while(cupLabels.last() != 1_000_000) {
//            maximum += 1
//            cupLabels.add(maximum)
//        }
    println("setup ready")

        for (i in 1..10_000_000) {
//            val before = System.currentTimeMillis()
    //        println("Cups: $cupLabels")
    //        println("")
    //        println("")
    //        println("")
    //        println(i)
    //        println("Current cup: $currentCup")
            var currentPickupIndex2 = currentPickupIndex + 1
            if (currentPickupIndex2 >= cupLabels.size) {
                currentPickupIndex2 -= cupLabels.size
            }
            var currentPickupIndex3 = currentPickupIndex2 + 1
            if (currentPickupIndex3 >= cupLabels.size) {
                currentPickupIndex3 -= cupLabels.size
            }
            val c1Removed = cupLabels[currentPickupIndex]
            val c2Removed = cupLabels[currentPickupIndex2]
            val c3Removed = cupLabels[currentPickupIndex3]
            val pickedUpCups = listOf(c1Removed, c2Removed, c3Removed)
            cupLabels.removeAll(pickedUpCups)

            var destinationCup = currentCup - 1
            if (destinationCup < minimum) {
                destinationCup = maximum
            }
            while (pickedUpCups.contains(destinationCup)) {
                destinationCup -= 1
                if (destinationCup < minimum) {
                    destinationCup = maximum
                }
            }
    //        println("Destination cup: $destinationCup")

            val destinationIndex = cupLabels.indexOf(destinationCup)
            cupLabels.addAll(destinationIndex + 1, listOf(c1Removed, c2Removed, c3Removed))

            val currentCupIndex = cupLabels.indexOf(currentCup)
            var nextCurrentCupIndex = currentCupIndex + 1
            if (nextCurrentCupIndex >= cupLabels.size) {
                nextCurrentCupIndex -= cupLabels.size
            }
            currentCup = cupLabels[nextCurrentCupIndex]
            currentPickupIndex = nextCurrentCupIndex + 1
            if (currentPickupIndex >= cupLabels.size) {
                currentPickupIndex -= cupLabels.size
            }

//            println("$i took: ${System.currentTimeMillis() - before}")
        }

    //    println(cupLabels)

        val indexOfOne = cupLabels.indexOf(1)
        println(indexOfOne)
    }

    @Test
    fun ex3() {
        val before = System.currentTimeMillis()
        var currentPickupIndex = 1
        val cupLabels = "389125467".toCharArray().map { Character.getNumericValue(it) }.toMutableList()
        val cupLabels2 = mutableListOf(1_000_000)
        var currentCup = cupLabels[0]
        val minimum = 1
        val maximum = 1_000_000
        var useFirstList = true
        for (i in 1..10_000_000) {
            var currentPickupIndex2 = currentPickupIndex + 1
            if (currentPickupIndex2 > maximum) {
                currentPickupIndex2 -= maximum
            }
            var currentPickupIndex3 = currentPickupIndex2 + 1
            if (currentPickupIndex3 > maximum) {
                currentPickupIndex3 -= maximum
            }
            val c1Removed = cupLabels.getOrElse(currentPickupIndex) {
                currentPickupIndex
            }
            val c2Removed = cupLabels.getOrElse(currentPickupIndex2) {
                currentPickupIndex2
            }
            val c3Removed = cupLabels.getOrElse(currentPickupIndex3) {
                currentPickupIndex3
            }
            val pickedUpCups = listOf(c1Removed, c2Removed, c3Removed)
            cupLabels.removeAll(pickedUpCups)

            var destinationCup = currentCup - 1
            if (destinationCup < minimum) {
                destinationCup = maximum
            }
            while (pickedUpCups.contains(destinationCup)) {
                destinationCup -= 1
                if (destinationCup < minimum) {
                    destinationCup = maximum
                }
            }

            useFirstList = !cupLabels2.contains(destinationCup)
//            println("Destination cup: $destinationCup")

           if (useFirstList) {
               val destinationIndex = cupLabels.indexOf(destinationCup)

               cupLabels.addAll(destinationIndex + 1, listOf(c1Removed, c2Removed, c3Removed))
           } else {
               val destinationIndex = cupLabels2.indexOf(destinationCup)

               cupLabels2.addAll(destinationIndex + 1, listOf(c1Removed, c2Removed, c3Removed))
           }


            var currentCupIndex = cupLabels.indexOf(currentCup)
            if (currentCupIndex == -1) {
                currentCupIndex = cupLabels2.indexOf(currentCup)
            }

            var nextCurrentCupIndex = currentCupIndex + 1
            if (nextCurrentCupIndex > maximum) {
                nextCurrentCupIndex -= maximum
            }
            currentCup = cupLabels.getOrElse(nextCurrentCupIndex) {
                cupLabels2.getOrElse(nextCurrentCupIndex) {
                    nextCurrentCupIndex
                }
            }
            currentPickupIndex = nextCurrentCupIndex + 1
            if (currentPickupIndex > maximum) {
                currentPickupIndex -= maximum
            }
        }

        println(cupLabels)

        if (cupLabels.contains(1)) {
            val indexOfOne = cupLabels.indexOf(1)

            println("indexOfOne: $indexOfOne")

            println("Result: ${cupLabels[indexOfOne + 1].toLong() * cupLabels[indexOfOne + 2].toLong()}")
        } else {
            if (cupLabels.contains(1)) {
                val indexOfOne = cupLabels2.indexOf(1)

                println("indexOfOne: $indexOfOne")

                println("Result: ${cupLabels2[indexOfOne + 1].toLong() * cupLabels2[indexOfOne + 2].toLong()}")
            }
        }
//        Collections.rotate(cupLabels, -indexOfOne)
//
//        val actual = cupLabels.subList(1, cupLabels.size).joinToString("")
        println("It took ${System.currentTimeMillis() - before}ms")
//        assertEquals("78569234", actual)
    }
}
