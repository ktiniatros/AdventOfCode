package nl.giorgos.adventofcode.y2020

import nl.giorgos.adventofcode.Utils
import org.junit.Test
import java.nio.file.InvalidPathException

class Day11 {
    private val fileName = "input11.txt"
    private val input = this.javaClass.getResourceAsStream(fileName) ?: throw InvalidPathException( fileName, "Invalid input")
    private val inputText = Utils.convertFileToString(input).split("\n")
    private val lines = inputText.subList(0, inputText.size - 1)

//    @Test
//    fun ex1() {
//        val before = System.currentTimeMillis()
//        var linesOfSeats = lines.map {
//            it.toCharArray().toMutableList()
//        }
//        var thereIsChange = true
//        var occupiedSeats = occupiedSeatsCount(linesOfSeats)
//        while (thereIsChange) {
////            println("thereIsChange: ${linesOfSeats.first()}")
//
//            val arrangedSeats = arrangeSeats(linesOfSeats)
//            val occupiedSeatsAfter = occupiedSeatsCount(arrangedSeats)
//
//            thereIsChange = occupiedSeats != occupiedSeatsAfter
//            if (thereIsChange) {
//                linesOfSeats = arrangedSeats
//                occupiedSeats = occupiedSeatsAfter
//            }
//        }
//
//        println("It took: ${System.currentTimeMillis() - before}")
//        println("There are $occupiedSeats occupied seats")
//
//    }

    private fun occupiedSeatsCount(linesOfSeats: List<MutableList<Char>>) = linesOfSeats.map {
        it.filter { char ->
            char == '#'
        }.count()
    }.sum()

//    private fun arrangeSeats(linesOfSeats: List<MutableList<Char>>): List<MutableList<Char>> {
//        val newList = List(linesOfSeats.size) {
//            mutableListOf<Char>()
//        }
//        linesOfSeats.forEachIndexed { lineIndex, line ->
//            line.forEachIndexed { columnIndex, seat ->
//                newList[lineIndex].add(seat)
//                when (seat) {
//                    'L' -> {
//                        if (emptySeatChanged(linesOfSeats, lineIndex, columnIndex)){
//                            newList[lineIndex][columnIndex] = '#'
//
//                        }
//                    }
//                    '#' -> {
//                        if (occupiedSeatChanged(linesOfSeats, lineIndex, columnIndex)) {
//                            newList[lineIndex][columnIndex] = 'L'
//                        }
//                    }
//                    else -> Unit
//                }
//            }
//        }
//        return newList
//    }
//
//    //If a seat is empty (L) and there are no occupied seats adjacent to it, the seat becomes occupied.
//    private fun emptySeatChanged(linesOfSeats: List<MutableList<Char>>, lineIndex: Int, columnIndex: Int): Boolean {
//        val pa = linesOfSeats.getOrNull(lineIndex - 1)?.getOrNull(columnIndex - 1)
//        val p = linesOfSeats.getOrNull(lineIndex - 1)?.getOrNull(columnIndex)
//        val pd = linesOfSeats.getOrNull(lineIndex - 1)?.getOrNull(columnIndex + 1)
//        val a = linesOfSeats.getOrNull(lineIndex)?.getOrNull(columnIndex - 1)
//        val d = linesOfSeats.getOrNull(lineIndex)?.getOrNull(columnIndex + 1)
//        val ka = linesOfSeats.getOrNull(lineIndex + 1)?.getOrNull(columnIndex - 1)
//        val k = linesOfSeats.getOrNull(lineIndex + 1)?.getOrNull(columnIndex)
//        val kd = linesOfSeats.getOrNull(lineIndex + 1)?.getOrNull(columnIndex + 1)
//
//        val adjacentSeats = listOf(pa, p, pd, a, d, ka, k, kd)
//
//        adjacentSeats.forEach {
//            if (it == '#') return false
//        }
//
//        return true
//    }
//
//    //If a seat is occupied (#) and four or more seats adjacent to it are also occupied, the seat becomes empty.
//    private fun occupiedSeatChanged(linesOfSeats: List<MutableList<Char>>, lineIndex: Int, columnIndex: Int): Boolean {
//        val pa = linesOfSeats.getOrNull(lineIndex - 1)?.getOrNull(columnIndex - 1)
//        val p = linesOfSeats.getOrNull(lineIndex - 1)?.getOrNull(columnIndex)
//        val pd = linesOfSeats.getOrNull(lineIndex - 1)?.getOrNull(columnIndex + 1)
//        val a = linesOfSeats.getOrNull(lineIndex)?.getOrNull(columnIndex - 1)
//        val d = linesOfSeats.getOrNull(lineIndex)?.getOrNull(columnIndex + 1)
//        val ka = linesOfSeats.getOrNull(lineIndex + 1)?.getOrNull(columnIndex - 1)
//        val k = linesOfSeats.getOrNull(lineIndex + 1)?.getOrNull(columnIndex)
//        val kd = linesOfSeats.getOrNull(lineIndex + 1)?.getOrNull(columnIndex + 1)
//
//        val adjacentSeats = listOf(pa, p, pd, a, d, ka, k, kd)
//
//        var adjacentOccupiedSeats = 0
//        adjacentSeats.forEach {
//            if (it == '#') adjacentOccupiedSeats += 1
//            if (adjacentOccupiedSeats == 4) return true
//        }
//
//        return false
//    }

    @Test
    fun ex2() {
        val before = System.currentTimeMillis()
        var linesOfSeats = lines.map {
            it.toCharArray().toMutableList()
        }
        var thereIsChange = true
        var occupiedSeats = occupiedSeatsCount(linesOfSeats)
        while (thereIsChange) {
//            println("thereIsChange: ${linesOfSeats.first()}")

            val arrangedSeats = arrangeSeats(linesOfSeats)
            val occupiedSeatsAfter = occupiedSeatsCount(arrangedSeats)

            thereIsChange = occupiedSeats != occupiedSeatsAfter
            if (thereIsChange) {
                linesOfSeats = arrangedSeats
                occupiedSeats = occupiedSeatsAfter
            }
        }

        println("It took: ${System.currentTimeMillis() - before}")
        println("There are $occupiedSeats occupied seats")
    }

    private fun arrangeSeats(linesOfSeats: List<MutableList<Char>>): List<MutableList<Char>> {
        val newList = List(linesOfSeats.size) {
            mutableListOf<Char>()
        }
        linesOfSeats.forEachIndexed { lineIndex, line ->
            line.forEachIndexed { columnIndex, seat ->
                newList[lineIndex].add(seat)
                when (seat) {
                    'L' -> {
                        if (emptySeatChanged(linesOfSeats, lineIndex, columnIndex)){
                            newList[lineIndex][columnIndex] = '#'

                        }
                    }
                    '#' -> {
                        if (occupiedSeatChanged(linesOfSeats, lineIndex, columnIndex)) {
                            newList[lineIndex][columnIndex] = 'L'
                        }
                    }
                    else -> Unit
                }
            }
        }
        return newList
    }

    //If a seat is empty (L) and there are no occupied seats adjacent to it, the seat becomes occupied.
    private fun emptySeatChanged(linesOfSeats: List<MutableList<Char>>, lineIndex: Int, columnIndex: Int): Boolean {
        //pa
        var currenSeatChecking: Char? = '.'
        var currentLineIndex = lineIndex - 1
        var currentColumnIndex = columnIndex - 1
        while (currenSeatChecking != null && !"#L".contains(currenSeatChecking)) {
            currenSeatChecking = linesOfSeats.getOrNull(currentLineIndex)?.getOrNull(currentColumnIndex)
            currentLineIndex += -1
            currentColumnIndex += -1
        }
        if (currenSeatChecking == '#') return false

        //p
        currenSeatChecking = '.'
        currentLineIndex = lineIndex - 1
        currentColumnIndex = columnIndex
        while (currenSeatChecking != null && !"#L".contains(currenSeatChecking)) {
            currenSeatChecking = linesOfSeats.getOrNull(currentLineIndex)?.getOrNull(currentColumnIndex)
            currentLineIndex += -1
        }
        if (currenSeatChecking == '#') return false

        //pd
        currenSeatChecking = '.'
        currentLineIndex = lineIndex - 1
        currentColumnIndex = columnIndex + 1
        while (currenSeatChecking != null && !"#L".contains(currenSeatChecking)) {
            currenSeatChecking = linesOfSeats.getOrNull(currentLineIndex)?.getOrNull(currentColumnIndex)
            currentLineIndex += -1
            currentColumnIndex += 1
        }
        if (currenSeatChecking == '#') return false

        //a
        currenSeatChecking = '.'
        currentLineIndex = lineIndex
        currentColumnIndex = columnIndex - 1
        while (currenSeatChecking != null && !"#L".contains(currenSeatChecking)) {
            currenSeatChecking = linesOfSeats.getOrNull(currentLineIndex)?.getOrNull(currentColumnIndex)
            currentColumnIndex += -1
        }
        if (currenSeatChecking == '#') return false

        //d
        currenSeatChecking = '.'
        currentLineIndex = lineIndex
        currentColumnIndex = columnIndex + 1
        while (currenSeatChecking != null && !"#L".contains(currenSeatChecking)) {
            currenSeatChecking = linesOfSeats.getOrNull(currentLineIndex)?.getOrNull(currentColumnIndex)
            currentColumnIndex += 1
        }
        if (currenSeatChecking == '#') return false

        //ka
        currenSeatChecking = '.'
        currentLineIndex = lineIndex + 1
        currentColumnIndex = columnIndex - 1
        while (currenSeatChecking != null && !"#L".contains(currenSeatChecking)) {
            currenSeatChecking = linesOfSeats.getOrNull(currentLineIndex)?.getOrNull(currentColumnIndex)
            currentLineIndex += 1
            currentColumnIndex += -1
        }
        if (currenSeatChecking == '#') return false

        //k
        currenSeatChecking = '.'
        currentLineIndex = lineIndex + 1
        currentColumnIndex = columnIndex
        while (currenSeatChecking != null && !"#L".contains(currenSeatChecking)) {
            currenSeatChecking = linesOfSeats.getOrNull(currentLineIndex)?.getOrNull(currentColumnIndex)
            currentLineIndex += 1
        }
        if (currenSeatChecking == '#') return false

        //kd
        currenSeatChecking = '.'
        currentLineIndex = lineIndex + 1
        currentColumnIndex = columnIndex + 1
        while (currenSeatChecking != null && !"#L".contains(currenSeatChecking)) {
            currenSeatChecking = linesOfSeats.getOrNull(currentLineIndex)?.getOrNull(currentColumnIndex)
            currentLineIndex += 1
            currentColumnIndex += 1
        }
        if (currenSeatChecking == '#') return false

        return true
    }

    //If a seat is occupied (#) and four or more seats adjacent to it are also occupied, the seat becomes empty.
    private fun occupiedSeatChanged(linesOfSeats: List<MutableList<Char>>, lineIndex: Int, columnIndex: Int): Boolean {
        var adjacentOccupiedSeats = 0

        //pa
        var currenSeatChecking: Char? = '.'
        var currentLineIndex = lineIndex - 1
        var currentColumnIndex = columnIndex - 1
        while (currenSeatChecking != null && !"#L".contains(currenSeatChecking)) {
            currenSeatChecking = linesOfSeats.getOrNull(currentLineIndex)?.getOrNull(currentColumnIndex)
            currentLineIndex += -1
            currentColumnIndex += -1
        }
        if (currenSeatChecking == '#') adjacentOccupiedSeats += 1

        //p
        currenSeatChecking = '.'
        currentLineIndex = lineIndex - 1
        currentColumnIndex = columnIndex
        while (currenSeatChecking != null && !"#L".contains(currenSeatChecking)) {
            currenSeatChecking = linesOfSeats.getOrNull(currentLineIndex)?.getOrNull(currentColumnIndex)
            currentLineIndex += -1
        }
        if (currenSeatChecking == '#') adjacentOccupiedSeats += 1

        //pd
        currenSeatChecking = '.'
        currentLineIndex = lineIndex - 1
        currentColumnIndex = columnIndex + 1
        while (currenSeatChecking != null && !"#L".contains(currenSeatChecking)) {
            currenSeatChecking = linesOfSeats.getOrNull(currentLineIndex)?.getOrNull(currentColumnIndex)
            currentLineIndex += -1
            currentColumnIndex += 1
        }
        if (currenSeatChecking == '#') adjacentOccupiedSeats += 1

        //a
        currenSeatChecking = '.'
        currentLineIndex = lineIndex
        currentColumnIndex = columnIndex - 1
        while (currenSeatChecking != null && !"#L".contains(currenSeatChecking)) {
            currenSeatChecking = linesOfSeats.getOrNull(currentLineIndex)?.getOrNull(currentColumnIndex)
            currentColumnIndex += -1
        }
        if (currenSeatChecking == '#') adjacentOccupiedSeats += 1

        //d
        currenSeatChecking = '.'
        currentLineIndex = lineIndex
        currentColumnIndex = columnIndex + 1
        while (currenSeatChecking != null && !"#L".contains(currenSeatChecking)) {
            currenSeatChecking = linesOfSeats.getOrNull(currentLineIndex)?.getOrNull(currentColumnIndex)
            currentColumnIndex += 1
        }
        if (currenSeatChecking == '#') adjacentOccupiedSeats += 1
        if (adjacentOccupiedSeats == 5) return true

        //ka
        currenSeatChecking = '.'
        currentLineIndex = lineIndex + 1
        currentColumnIndex = columnIndex - 1
        while (currenSeatChecking != null && !"#L".contains(currenSeatChecking)) {
            currenSeatChecking = linesOfSeats.getOrNull(currentLineIndex)?.getOrNull(currentColumnIndex)
            currentLineIndex += 1
            currentColumnIndex += -1
        }
        if (currenSeatChecking == '#') adjacentOccupiedSeats += 1
        if (adjacentOccupiedSeats == 5) return true

        //k
        currenSeatChecking = '.'
        currentLineIndex = lineIndex + 1
        currentColumnIndex = columnIndex
        while (currenSeatChecking != null && !"#L".contains(currenSeatChecking)) {
            currenSeatChecking = linesOfSeats.getOrNull(currentLineIndex)?.getOrNull(currentColumnIndex)
            currentLineIndex += 1
        }
        if (currenSeatChecking == '#') adjacentOccupiedSeats += 1
        if (adjacentOccupiedSeats == 5) return true

        //kd
        currenSeatChecking = '.'
        currentLineIndex = lineIndex + 1
        currentColumnIndex = columnIndex + 1
        while (currenSeatChecking != null && !"#L".contains(currenSeatChecking)) {
            currenSeatChecking = linesOfSeats.getOrNull(currentLineIndex)?.getOrNull(currentColumnIndex)
            currentLineIndex += 1
            currentColumnIndex += 1
        }
        if (currenSeatChecking == '#') adjacentOccupiedSeats += 1
        if (adjacentOccupiedSeats == 5) return true

        return false
    }
}
